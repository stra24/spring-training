-- レコードを一度すべて削除する。（アプリ再起動時に同じレコードが増えるのを避けるため）
TRUNCATE TABLE comments, articles RESTART IDENTITY CASCADE;

INSERT INTO articles (title, content, created_at, updated_at, version) VALUES
  ('はじめてのSpring Data JDBC', '最小の一覧表示を作って動作を確認します。', now(), now(), 0),
  ('Repositoryの基本', 'findAllとfindByIdを試して使い方を掴みます。', now(), now(), 0),
  ('Thymeleafで表示', '取得したデータを表にして画面に表示します。', now(), now(), 0),
  ('検索機能の追加', 'キーワード検索で絞り込みできるようにします。', now(), now(), 0),
  ('ページネーション入門', 'Pageを使って一覧をページ分割します。', now(), now(), 0),
  ('作成フォームの実装', '新規作成フォームから登録できるようにします。', now(), now(), 0),
  ('更新処理の実装', '編集画面を用意して更新できるようにします。', now(), now(), 0),
  ('削除処理の実装', '削除ボタンを追加して削除できるようにします。', now(), now(), 0),
  ('バリデーションの基礎', '@Validated と BindingResult を理解します。', now(), now(), 0),
  ('エラーメッセージ表示', 'th:errors と #fields を使って表示します。', now(), now(), 0),
  ('例外ハンドリング', '@ControllerAdviceでエラー画面に遷移させます。', now(), now(), 0),
  ('404/500ページ', 'ステータスコード別にエラー画面を用意します。', now(), now(), 0),
  ('DTOとは何か', 'Entityと画面表示用のデータを分けます。', now(), now(), 0),
  ('Serviceの責務', 'ControllerとRepositoryの間で処理を整理します。', now(), now(), 0),
  ('トランザクション', '@Transactionalの基本を押さえます。', now(), now(), 0),
  ('楽観ロック', 'versionカラムを使った更新競合対策を学びます。', now(), now(), 0),
  ('並び替え', '更新日時の降順で表示するようにします。', now(), now(), 0),
  ('クエリ最適化', '必要な条件で必要なデータだけ取得します。', now(), now(), 0),
  ('テストの追加', 'Repository/Serviceのテストを書いて安心します。', now(), now(), 0),
  ('まとめ', 'CRUD + 検索 + ページングの一連を復習します。', now(), now(), 0);

INSERT INTO comments (article_id, content, created_at) VALUES
  (1, 'この記事わかりやすい！', now()),
  (1, 'JDBCの雰囲気が掴めました。', now()),
  (2, 'findByの命名規則が助かります。', now()),
  (3, '表で表示されると見やすいですね。', now());