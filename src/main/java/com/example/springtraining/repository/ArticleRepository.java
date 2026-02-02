package com.example.springtraining.repository;

import com.example.springtraining.dao.ArticleDao;
import com.example.springtraining.domain.entity.Article;
import com.example.springtraining.domain.row.ArticleCommentRow;
import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ArticleRepository {

  private final ArticleDao articleDao;

  private static final Logger log = LoggerFactory.getLogger(ArticleRepository.class);

  public List<Article> findAll() {
    List<Article> list = new ArrayList<>();
    articleDao.findAll().forEach(list::add);
    return list;
  }

  // IDの降順で並び替えた一覧を取得する。
  public List<Article> findAllOrderByIdDesc() {
    return articleDao.findAllOrderByIdDesc();
  }

  // IDを条件に1件を取得する。
  public Optional<Article> findById(Long id) {
    return articleDao.findById(id);
  }

  // 保存する。
  public Article save(Article article) {
    return articleDao.save(article);
  }

  // IDを指定して1件削除する。
  public void deleteById(Long id) {
    articleDao.deleteById(id);
  }

  // タイトルに指定のキーワードを含む記事をIDの降順で取得する。
  public List<Article> findByTitleContainingOrderByIdDesc(@Nullable String keyword) {
    String safeKeyword = (keyword == null) ? "" : keyword; // nullを空文字に寄せる
    return articleDao.findByTitleContainingOrderByIdDesc(safeKeyword);
  }

  // 検索条件で絞った一覧をIDの降順でページ情報付きで取得する。
  public Page<Article> findPageByConditionOrderByIdDesc(
      @Nullable String keyword,
      int page,
      int size
  ) {
    String safeKeyword = (keyword == null) ? "" : keyword; // nullを空文字に寄せる
    int offset = page * size;

    List<Article> content = articleDao.findPageByConditionOrderByIdDesc(safeKeyword, size, offset);
    long count = articleDao.countByCondition(safeKeyword);

    return new PageImpl<>(
        content,
        PageRequest.of(page, size),
        count
    );
  }

  // 記事とコメントのJOIN結果を取得する。
  public List<ArticleCommentRow> findDetailRowsById(Long id) {
    return articleDao.findDetailRowsById(id);
  }

  // 悲観ロックのパターンA：@Lockでの悲観ロック付きで、IDを条件に1件を取得する。
  public Optional<Article> findByIdForUpdate(Long id) {
    Optional<Article> article = articleDao.findOneById(id);
    log.info("@Lockで" + "id = " + id + "の記事を悲観ロックしました。");
    return article;
  }

  // 悲観ロックのパターンB：@Queryでの悲観ロック付きで、IDを条件に1件を取得する。
  public Optional<Article> findByIdForUpdateWithSql(Long id) {
    Optional<Article> article = articleDao.findByIdForUpdate(id);
    log.info("@Queryで" + "id = " + id + "の記事を悲観ロックしました。");
    return article;
  }

  // 指定の記事IDの中間テーブルを削除する。
  public void deleteArticleTags(Long articleId) {
    articleDao.deleteArticleTags(articleId);
  }

  // 指定の記事ID・タグIDの中間テーブルを作成する。
  public void createArticleTag(Long articleId, Long tagId) {
    articleDao.insertArticleTag(articleId, tagId);
  }
}