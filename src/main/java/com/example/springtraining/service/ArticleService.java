package com.example.springtraining.service;

import com.example.springtraining.client.ArticleApiClient;
import com.example.springtraining.domain.dto.ArticleDetailDto;
import com.example.springtraining.domain.dto.ArticleDto;
import com.example.springtraining.domain.dto.CommentDto;
import com.example.springtraining.domain.dto.TagDto;
import com.example.springtraining.domain.entity.Article;
import com.example.springtraining.domain.entity.Comment;
import com.example.springtraining.domain.entity.Tag;
import com.example.springtraining.domain.form.ArticleForm;
import com.example.springtraining.domain.form.CommentForm;
import com.example.springtraining.domain.request.ArticleCreateRequest;
import com.example.springtraining.domain.request.ArticleUpdateRequest;
import com.example.springtraining.domain.row.ArticleCommentRow;
import com.example.springtraining.exception.ResourceNotFoundException;
import com.example.springtraining.repository.ArticleRepository;
import com.example.springtraining.repository.CommentRepository;
import com.example.springtraining.repository.TagRepository;
import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ArticleService {

  private final CommentService commentService;
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final TagRepository tagRepository;
  private final ArticleApiClient articleApiClient;

  private static final Logger log = LoggerFactory.getLogger(ArticleService.class);

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

    // 1. 記事を保存して保存後の記事を取得。（採番されたIDが代入されてある）
    Article createdArticle = articleRepository.save(article);

    // 2. 採番された記事IDで中間テーブルに保存する。
    List<Long> tagIds = (form.getTagIds() == null) ? List.of() : form.getTagIds();
    tagIds.forEach(tagId -> articleRepository.createArticleTag(createdArticle.getId(), tagId));
  }

  // 新規作成する。
  @Transactional
  public ArticleDetailDto createArticle(ArticleCreateRequest request) {
    Article article = request.toNewArticle();

    // 1. 記事を保存して保存後の記事を取得。（採番されたIDが代入されてある）
    Article createdArticle = articleRepository.save(article);

    // 2. 採番された記事IDで中間テーブルに保存する。
    List<Long> tagIds = (request.getTagIds() == null) ? List.of() : request.getTagIds();
    tagIds.forEach(tagId -> articleRepository.createArticleTag(createdArticle.getId(), tagId));

    // 3. 記事に紐づくタグ一覧を取得する。
    List<Tag> tags = tagRepository.findTagsByArticleId(createdArticle.getId());

    // 4. 記事詳細を生成して返す。
    return ArticleDetailDto.from(createdArticle, new ArrayList<>(), tags);
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

    // 4. 中間テーブルを全削除
    articleRepository.deleteArticleTags(id);

    // 5. 中間テーブルに保存
    List<Long> tagIds = (form.getTagIds() == null) ? List.of() : form.getTagIds();
    tagIds.forEach(tagId -> articleRepository.createArticleTag(articleForUpdate.getId(), tagId));
  }

  // 更新する。
  @Transactional
  public ArticleDetailDto updateArticle(Long id, ArticleUpdateRequest request) {
    // 1. 既存の記事を取得する。
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(Article.class, String.valueOf(id)));

    // 2. リクエストの内容を既存の記事に上書きする。
    Article articleForUpdate = request.toUpdatedArticle(article);

    // 3. 記事を上書き保存する。
    Article updatedArticle = articleRepository.save(articleForUpdate);

    // 4. 中間テーブルを全削除する。
    articleRepository.deleteArticleTags(id);

    // 5. 中間テーブルに保存する。
    List<Long> tagIds = (request.getTagIds() == null) ? List.of() : request.getTagIds();
    tagIds.forEach(tagId -> articleRepository.createArticleTag(updatedArticle.getId(), tagId));

    // 6. 記事に紐づくタグ一覧を取得する。
    List<Tag> tags = tagRepository.findTagsByArticleId(updatedArticle.getId());

    // 7. 記事に紐づくコメント一覧を取得する。
    List<Comment> comments = commentRepository.findCommentsByArticleId(updatedArticle.getId());

    // 8. 記事詳細を生成して返す。
    return ArticleDetailDto.from(updatedArticle, comments, tags);
  }

  // 削除する。
  @Transactional
  public void deleteArticle(Long id) {
    // 既存の記事が存在するかチェックして、存在すれば削除する。存在しなければ例外を投げる。
    articleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(Article.class, String.valueOf(id)));

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
      throw new ResourceNotFoundException(Article.class, String.valueOf(id));
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

    // 4) 記事IDに紐づくタグ一覧を取得する。
    List<TagDto> tagDtos = tagRepository.findTagsByArticleId(id).stream()
        .map(tag -> new TagDto(tag.getId(), tag.getName()))
        .toList();

    // 5) 親＋子の形に組み立てて返す。
    return new ArticleDetailDto(
        head.articleId(),
        head.title(),
        head.content(),
        head.updatedAt(),
        comments,
        tagDtos
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

  // 【悲観ロック確認用】@Lockの悲観ロック付き、かつ、遅い更新。
  @Transactional
  public void updateArticleSlowWithPessimisticLock(Long id, ArticleForm form) {
    // 1. @Lock(PESSIMISTIC_WRITE) が付いているメソッドで取得 → 行ロック獲得
    Article article = articleRepository.findByIdForUpdate(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));
    log.info("@Lock + 遅い更新のメソッドで悲観ロックをしました。");

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    // 3. あえて5秒待つ（この間、同じ行を触ろうとすると待たされる）
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // 何もしない
    }

    // 4. 保存（本Serviceのメソッド終了時にトランザクションが終わり、ロックが解放される）
    articleRepository.save(articleForUpdate);
    log.info("@Lock + 遅い更新のメソッドで悲観ロックを解除しました。");
  }

  // 【悲観ロック確認用】@Lockの悲観ロック付き、かつ、速い更新。
  @Transactional
  public void updateArticleFastWithPessimisticLock(Long id, ArticleForm form) {
    // 1. @Lock(PESSIMISTIC_WRITE) が付いているメソッドで取得 → 行ロック獲得
    Article article = articleRepository.findByIdForUpdate(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));
    log.info("@Lock + 速い更新のメソッドで悲観ロックをしました。");

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    // 3. すぐに保存（本Serviceのメソッド終了時にトランザクションが終わり、ロックが解放される）
    articleRepository.save(articleForUpdate);
    log.info("@Lock + 速い更新のメソッドで悲観ロックを解除しました。");
  }

  // 【悲観ロック確認用】@Query("SELECT ... FOR UPDATE")の悲観ロック付き、かつ、遅い更新。
  @Transactional
  public void updateArticleSlowWithPessimisticLockWithSql(Long id, ArticleForm form) {
    // 1. @Query("SELECT ... FOR UPDATE") が付いているメソッドで取得 → 行ロック獲得
    Article article = articleRepository.findByIdForUpdateWithSql(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));
    log.info("@Query + 遅い更新のメソッドで悲観ロックをしました。");

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    // 3. あえて5秒待つ（この間、同じ行を触ろうとすると待たされる）
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // 何もしない
    }

    // 4. 保存（本Serviceのメソッド終了時にトランザクションが終わり、ロックが解放される）
    articleRepository.save(articleForUpdate);
    log.info("@Query + 遅い更新のメソッドで悲観ロックを解除しました。");
  }

  // 【悲観ロック確認用】@Query("SELECT ... FOR UPDATE")の悲観ロック付き、かつ、速い更新。
  @Transactional
  public void updateArticleFastWithPessimisticLockWithSql(Long id, ArticleForm form) {
    // 1. @Query("SELECT ... FOR UPDATE") が付いているメソッドで取得 → 行ロック獲得
    Article article = articleRepository.findByIdForUpdateWithSql(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));
    log.info("@Query + 速い更新のメソッドで悲観ロックをしました。");

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    // 3. すぐに保存（本Serviceのメソッド終了時にトランザクションが終わり、ロックが解放される）
    articleRepository.save(articleForUpdate);
    log.info("@Query + 速い更新のメソッドで悲観ロックを解除しました。");
  }

  // 記事一覧取得APIを呼び出して、記事一覧を取得。
  @Transactional(readOnly = true)
  public List<ArticleDto> getArticlesByApi(@Nullable String keyword) {
    return articleApiClient.getArticles(keyword);
  }

  // 記事詳細取得APIを呼び出して、記事詳細を取得。
  @Transactional(readOnly = true)
  public ArticleDetailDto getArticleByApi(Long id) {
    ResponseEntity<ArticleDetailDto> response = articleApiClient.getArticle(id);

    log.info("ステータスコード: {}", response.getStatusCode());
    log.info("レスポンスヘッダー: {}", response.getHeaders());

    return response.getBody();
  }
}