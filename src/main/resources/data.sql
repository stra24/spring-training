-- レコードを一度すべて削除する。（アプリ再起動時に同じレコードが増えるのを避けるため）
TRUNCATE TABLE comments, articles RESTART IDENTITY CASCADE;

INSERT INTO articles (title, content, created_at, updated_at, version) VALUES
  ('はじめてのSpring Data JDBC', '最小の一覧表示を作って動作を確認します。', now(), now(), 0),
  ('Repositoryの基本', 'findAllとfindByIdを試して使い方を掴みます。', now(), now(), 0),
  ('Thymeleafで表示', '取得したデータを表にして画面に表示します。', now(), now(), 0);