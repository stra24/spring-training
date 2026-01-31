package com.example.springtraining.service;

import com.example.springtraining.domain.dto.ArticleDetailDto;
import com.example.springtraining.domain.dto.ArticleDto;
import com.example.springtraining.domain.dto.CommentDto;
import com.example.springtraining.domain.entity.Article;
import com.example.springtraining.domain.form.ArticleForm;
import com.example.springtraining.domain.form.CommentForm;
import com.example.springtraining.domain.row.ArticleCommentRow;
import com.example.springtraining.repository.ArticleRepository;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ArticleService {

  private final CommentService commentService;
  private final ArticleRepository articleRepository;

  @Transactional(readOnly = true)
  public List<ArticleDto> getArticles() {
    return articleRepository.findAll().stream()
        .map(ArticleDto::from)
        .toList();
  }

  // IDの降順で一覧を取得する。
  @Transactional(readOnly = true)
  public List<ArticleDto> getArticlesOrderByIdDesc() {
    return articleRepository.findAllOrderByIdDesc().stream()
        .map(ArticleDto::from)
        .toList();
  }

  // IDで1件取得する。（存在しなければ例外をスロー）
  @Transactional(readOnly = true)
  public ArticleDto getArticle(Long id) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));
    return ArticleDto.from(article);
  }

  // 新規作成する。
  @Transactional
  public void createArticle(ArticleForm form) {
    Article article = form.toNewArticle();
    articleRepository.save(article);
  }

  // 更新する。
  @Transactional
  public void updateArticle(Long id, ArticleForm form) {
    // 1. 既存の記事を取得
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    // 3. 保存（idが入っているのでUPDATEになる）
    articleRepository.save(articleForUpdate);
  }

  // 削除する。
  @Transactional
  public void deleteArticle(Long id) {
    articleRepository.deleteById(id);
  }

  // 検索する。
  @Transactional(readOnly = true)
  public List<ArticleDto> searchArticles(@Nullable String keyword) {
    return articleRepository.findByTitleContainingOrderByIdDesc(keyword).stream()
        .map(ArticleDto::from)
        .toList();
  }

  // 検索条件で絞ったうえで、該当ページの記事を取得する。
  @Transactional(readOnly = true)
  public Page<ArticleDto> searchArticlePageByCondition(
      @Nullable String keyword,
      int page,
      int size
  ) {
    return articleRepository.findPageByConditionOrderByIdDesc(keyword, page, size)
        .map(ArticleDto::from);
  }

  // 記事1件＋コメント一覧を取得する。
  @Transactional(readOnly = true)
  public ArticleDetailDto getArticleDetail(Long id) {

    List<ArticleCommentRow> rows = articleRepository.findDetailRowsById(id);

    // 1) 記事が存在しない場合（JOIN結果が0行）は例外を投げる。
    if (rows.isEmpty()) {
      throw new IllegalArgumentException("記事が見つかりませんでした。id = " + id);
    }

    // 2) 記事情報（親）は全行共通なので先頭行から拾う。
    ArticleCommentRow head = rows.getFirst();

    // 3) コメント部分だけをList化する。（LEFT JOINでコメントが無いとnullになるので除外）
    List<CommentDto> comments = rows.stream()
        .filter(r -> r.commentId() != null)
        .map(r -> new CommentDto(
            r.commentId(),
            r.commentContent(),
            r.commentCreatedAt()
        ))
        .toList();

    // 4) 親＋子の形に組み立てて返す。
    return new ArticleDetailDto(
        head.articleId(),
        head.title(),
        head.content(),
        head.updatedAt(),
        comments
    );
  }

  // 同じコメントを2件登録しようとするが、2件目で例外を発生させる。
  @Transactional
  public void addCommentAndThrowException(Long articleId, CommentForm form) {
    // 1件目の登録は成功する。
    commentService.addComment(articleId, form);

    try {
      // 2件目の登録は例外発生により失敗する。
      commentService.addCommentAndThrowException(articleId, form);
    } catch (Exception e) {
      System.out.println("2件目のコメント保存に失敗しました: " + e.getMessage());
    }
  }

  // 【楽観ロック確認用】すぐ終わる通常の更新。
  @Transactional
  public void updateArticleFast(Long id, ArticleForm form) {
    // 1. 既存の記事を取得
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    try {
      // 3. 保存（idが入っているのでUPDATEになる）
      articleRepository.save(articleForUpdate);
    } catch (OptimisticLockingFailureException e) {
      throw new RuntimeException(
          "（速い更新メソッドで例外発生）他のユーザーによってすでに更新されています。最新の状態を表示してからやり直してください。",
          e
      );
    }
  }

  // 【楽観ロック確認用】わざと時間がかかる更新。
  // これを先に実行しておいて、あとから速い更新のほうを実行すると楽観ロックを再現できる。
  @Transactional
  public void updateArticleSlow(Long id, ArticleForm form) {
    // 1. 既存の記事を取得
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    // 3. あえて5秒待つ
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // 何もしない
    }

    try {
      // 4. 保存（idが入っているのでUPDATEになる）
      // 速い更新のほうで先に更新されるため、ここで例外（OptimisticLockingFailureException）が発生する想定。
      articleRepository.save(articleForUpdate);
    } catch (OptimisticLockingFailureException e) {
      throw new RuntimeException(
          "（遅い更新メソッドで例外発生）他のユーザーによってすでに更新されています。最新の状態を表示してからやり直してください。",
          e
      );
    }
  }
}