package com.example.springtraining.service;

import com.example.springtraining.domain.article.Article;
import com.example.springtraining.domain.article.ArticleForm;
import com.example.springtraining.domain.article.Comment;
import com.example.springtraining.domain.article.CommentForm;
import com.example.springtraining.domain.article.Tag;
import com.example.springtraining.repository.ArticleRepository;
import com.example.springtraining.repository.TagRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ArticleService {

  private final ArticleRepository repository;
  private final CommentService commentService;
  private final TagRepository tagRepository;

  @Transactional(readOnly = true)
  public List<Article> listAll() {
    return repository.findAll();
  }

  // 更新日時の降順で一覧を取得する。
  @Transactional(readOnly = true)
  public List<Article> listOrderedByUpdatedAtDesc() {
    return repository.findAllOrderedByUpdatedAtDesc();
  }

  // IDで1件取得する。
  @Transactional(readOnly = true)
  public Article get(Long id) {
    return repository.findById(id);
  }

  // 新規作成する。
  @Transactional
  public Article create(ArticleForm form) {
    Article article = form.toNewArticle();
    return repository.save(article);
  }

  // 更新する。
  @Transactional
  public Article update(Long id, ArticleForm form) {
    // 1. 既存の記事を取得
    Article article = repository.findById(id);

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    // 3. 保存（idが入っているのでUPDATEになる）
    return repository.save(articleForUpdate);
  }

  // 削除する。
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  // タイトルで検索する。
  @Transactional(readOnly = true)
  public List<Article> searchByTitleKeyword(String keyword) {
    return repository.searchByTitleKeyword(keyword);
  }

  // 該当ページの全ての記事を取得する。
  @Transactional(readOnly = true)
  public Page<Article> getPage(int page, int size) {
    return repository.findPageOrderedByUpdatedAtDesc(page, size);
  }

  // 既存の記事にコメントを追加する。
  @Transactional
  public void addCommentByArticle(Long articleId, CommentForm form) {
    // 1. 親（記事）を取得
    Article article = repository.findById(articleId);

    // 2. コメント（子）を生成
    Comment comment = Comment.newComment(articleId, form.getContent());

    // 3. 親（記事）に子（コメント）を追加
    article.addComment(comment);

    // 4. 親ごと保存（→子の既存データが一括DELETEされてから、今回の子のデータが一括INSERTされる）
    repository.save(article);
  }

  // 同じコメントを2件登録しようとするが、2件目で例外を発生させる。
  @Transactional
  public void addCommentAndThrowException(Long articleId, CommentForm form) {
    var article = repository.findById(articleId);
    article.addComment(Comment.newComment(articleId, form.getContent()));
    // 1件目の登録は成功する。
    repository.save(article);

    try {
      // 2件目の登録は例外発生により失敗する。
      commentService.addCommentAndThrowException(articleId, form.getContent());
    } catch (Exception e) {
      System.out.println("コメント保存でエラー発生: " + e.getMessage());
    }

    // ここまでエラーなく終われば記事はコミットされる。
    // commentService.addCommentAndThrowExceptionでの処理内容はロールバックされるため、
    // 追加されるコメントはarticleRepository.saveで保存した1件のみ。
  }

  // すぐ終わる通常の更新。
  @Transactional
  public void updateTitleFast(Long id, String newTitle) {
    Article article = repository.findById(id);
    article.setTitle(newTitle);
    try {
      repository.save(article);
    } catch (OptimisticLockingFailureException e) {
      throw new RuntimeException("（速い更新）他のユーザーによってすでに更新されています。最新の状態を表示してからやり直してください。", e);
    }
  }

  // わざと時間がかかる更新。
  // これを先に実行しておいて、あとから速い更新のほうを実行すると楽観ロックを再現できる。
  @Transactional
  public void updateTitleSlow(Long id, String newTitle) {
    Article article = repository.findById(id);
    article.setTitle(newTitle);

    // あえて5秒待つ（この間に他タブで更新してもらう）
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // 学習用なので何もしない
    }

    try {
      repository.save(article);
    } catch (OptimisticLockingFailureException e) {
      // ここで楽観ロックが起きる想定
      throw new RuntimeException("（遅い更新）他のユーザーによってすでに更新されています。最新の状態を表示してからやり直してください。", e);
    }
  }

  // @Lockの悲観ロック付きで、わざと時間がかかる更新。
  @Transactional
  public void updateTitleSlowWithPessimisticLock(Long id, String newTitle) {
    System.out.println("遅いほうのメソッドでこれから悲観ロックをします。");

    // 1. @Lock(PESSIMISTIC_WRITE) が付いているメソッドで取得 → 行ロック獲得
    Article article = repository.findByIdForUpdate(id);

    System.out.println("遅いほうのメソッドで悲観ロックをしました。");

    // 2. タイトル変更
    article.setTitle(newTitle);

    // 3. あえて5秒待つ（この間、同じ行を触ろうとすると待たされる）
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // 学習用なので何もしない
    }

    // 4. 保存（本Serviceのメソッド終了時にトランザクションが終わり、ロックが解放される）
    repository.save(article);
    System.out.println("遅いほうのメソッドでロックを解除します。");
  }

  // @Lockの悲観ロック付きで、すぐ終わる更新。
  @Transactional
  public void updateTitleFastWithPessimisticLock(Long id, String newTitle) {
    System.out.println("速いほうのメソッドでこれから悲観ロックをします。");

    // 1. @Lock 付きメソッドでロックを取りに行く
    Article article = repository.findByIdForUpdate(id);

    System.out.println("速いほうのメソッドで悲観ロックをしました。");

    // 2. すぐに更新して保存
    article.setTitle(newTitle);
    repository.save(article);

    System.out.println("速いほうのメソッドでロックを解除します。");
  }

  // @Query + FOR UPDATEの悲観ロック付きで、わざと時間がかかる更新。
  @Transactional
  public void updateTitleSlowWithPessimisticLockBySql(Long id, String newTitle) {
    System.out.println("遅いほうのメソッドでこれから悲観ロックをします。");

    // 1. Query("... FOR UPDATE") が付いているメソッドで取得 → 行ロック獲得
    Article article = repository.findByIdForUpdateWithSql(id);

    System.out.println("遅いほうのメソッドで悲観ロックをしました。");

    // 2. タイトル変更
    article.setTitle(newTitle);

    // 3. あえて5秒待つ（この間、同じ行を触ろうとすると待たされる）
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // 学習用なので何もしない
    }

    // 4. 保存（本Serviceのメソッド終了時にトランザクションが終わり、ロックが解放される）
    repository.save(article);
    System.out.println("遅いほうのメソッドでロックを解除します。");
  }

  // @Query + FOR UPDATEの悲観ロック付きで、すぐ終わる更新。
  @Transactional
  public void updateTitleFastWithPessimisticLockBySql(Long id, String newTitle) {
    System.out.println("速いほうのメソッドでこれから悲観ロックをします。");

    // 1. @Query("... FOR UPDATE") 付きメソッドでロックを取りに行く
    Article article = repository.findByIdForUpdateWithSql(id);

    System.out.println("速いほうのメソッドで悲観ロックをしました。");

    // 2. すぐに更新して保存
    article.setTitle(newTitle);
    repository.save(article);
    System.out.println("速いほうのメソッドでロックを解除します。");
  }

  // 記事IDに紐づくタグリストを取得する。
  @Transactional(readOnly = true)
  public List<Tag> getTagsOfArticle(Long articleId) {
    Article article = repository.findById(articleId);
    var tagIds = article.getTagIds();
    if (tagIds.isEmpty()) {
      return List.of();
    }
    return tagRepository.findAll().stream()
        .filter(tag -> tagIds.contains(tag.getId()))
        .toList();
  }
}