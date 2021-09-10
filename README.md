# next-time-calendar
NEXTタイムの選択専用サイトなのにゃ！！  
  - [各ディレクトリの説明](#ざっくりとした各ディレクトリの説明)
  - [ビルド方法](#ビルド方法)
  - [新たに欲しい機能](#新たに欲しい機能)
  - [WebAPI仕様](#WebAPI仕様)
    - [アクセストークンの取得@開発中](#アクセストークンの取得@開発中)
  
## ( ..)φメモメモ
### ざっくりとした各ディレクトリの説明
  - /src
    - .java
  - /WebContent
    - .html
    - .css
    - .js
    - .jsp
  - /res
    - その他ファイル(画像等)
    
※サーブレット追加時に/WebContent/WEB-INF/web.xmlにこんな感じで書くとURLの自由度上がるっぽい？  
[詳しくはこちら！](https://cloud.google.com/appengine/docs/flexible/java/configuring-the-web-xml-deployment-descriptor?hl=ja#JSPs)
```xml
  <servlet>
    <servlet-name>login</servlet-name>
    <jsp-file>/login.jsp</jsp-file>
  </servlet>

  <servlet-mapping>
    <servlet-name>login</servlet-name>
    <url-pattern>/login</url-pattern>
  </servlet-mapping>
```

### ビルド方法

```shell
mvn package
```

### 新たに欲しい機能
  - ログイン画面以外すべてajax
  - 授業の参加人数の制限機能
  - ユーザーのグループ分け(複数グループ所属可能)
  - 授業ごとに参加できるグループ制限
***
## WebAPI仕様
### ⚠️超絶重要☆彡
```diff
- 現在、一部のAPI仕様は、策定のみ若しくは開発中で、動作は保証されていません。
- また予告等なしに仕様を変更する可能性があります 
```
***

## アクセストークンの取得@開発中
```
POST https://sub.shachiku.tk/nexttime/issuetoken
```
### リクエスト
#### ヘッダー
```
content-type: application/x-www-form-urlencoded
```
#### ペイロード
```
id=<user id>&
pass=<password>&
refresh=<refresh token>
```
  - `user id`: ユーザーID
  - `password`: パスワード
  - `refresh token`: リフレッシュトークン
  - `user id`と`password`か、`refresh token`のどちらかを入力してください。
### レスポンス
#### ヘッダー
```
content-type: application/json
```
#### 成功例
```json
{"access_token":{"value":"be52ffaf-fbd5-4030-8994-55090ec9a67d","expiration":1631402894},"refresh_token":{"value":"894a5dde-4e5d-485e-8a0d-91b0cc6c24fe","expiration":1633908494},"success":true}
```
  - `access_token`: アクセストークン
  - `refresh_token`: リフレッシュトークン
  - `expiration`: 各トークンの有効期限(UNIX時間)
#### 失敗例
```json
{"success":false,"cause":"IDかパスワードが間違っています。"}
```
  - `cause`: エラーメッセージ
