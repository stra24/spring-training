package com.example.springtraining.repository;

import com.example.springtraining.dao.ArticleDao;
import com.example.springtraining.domain.article.Article;
import com.example.springtraining.exception.ArticleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ArticleRepository {

  private final ArticleDao dao;

  public List<Article> findAll() {
    List<Article> list = new ArrayList<>();
    dao.findAll().forEach(list::add);
    return list;
  }

  // 更新日時の降順で並び替えた一覧を取得する。
  public List<Article> findAllOrderedByUpdatedAtDesc() {
    return dao.findAllOrderByUpdatedAtDesc();
  }

  // IDを条件に1件を取得する。（存在しなければ例外をスロー）
  public Article findById(Long id) {
    return dao.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
  }

  // 保存する。
  public void save(Article article) {
    dao.save(article);
  }

  // IDを指定して1件削除する。
  public void deleteById(Long id) {
    dao.deleteById(id);
  }

  // タイトルに指定のキーワードを含む記事を検索する。
  public List<Article> searchByTitleKeyword(String keyword) {
    return dao.findByTitleContainingOrderByUpdatedAtDesc(keyword);
  }

  // 記事一覧をページ情報付きで取得する。
  public Page<Article> findPageOrderedByUpdatedAtDesc(int page, int size) {
    int offset = page * size; // page は0始まりで受け取る想定（先頭のページの場合、pageは0）
    List<Article> content = dao.findPageOrderByUpdatedAtDesc(size, offset);
    long total = dao.countAll();
    return new PageImpl<>(
        content,
        PageRequest.of(page, size),
        total
    );
  }

  // @Lockでの悲観ロック付きでIDを条件に1件を取得する。（存在しなければ例外をスロー）
  public Article findByIdForUpdate(Long id) {
    return dao.findOneById(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした id=" + id));
  }

  // @Queryでの悲観ロック付きでIDを条件に1件を取得する。（存在しなければ例外をスロー）
  public Article findByIdForUpdateWithSql(Long id) {
    return dao.findByIdForUpdate(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした id=" + id));
  }
}
