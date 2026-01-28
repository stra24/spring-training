package com.example.springtraining.repository;

import com.example.springtraining.dao.ArticleDao;
import com.example.springtraining.domain.entity.Article;
import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ArticleRepository {

  private final ArticleDao articleDao;

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
  public void save(Article article) {
    articleDao.save(article);
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
}