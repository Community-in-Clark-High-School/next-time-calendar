# next-time-calendar
NEXTタイムの選択専用サイトなのにゃ！！  
  - [各ディレクトリの説明](#ざっくりとした各ディレクトリの説明)
  - [ビルド方法](#ビルド方法)
  - [新たに欲しい機能](#新たに欲しい機能)
  - [WebAPI仕様](#WebAPI仕様)
    - [アクセストークンの取得](#アクセストークンの取得)
  
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

## WebAPI仕様
### ⚠️超絶重要☆彡
```diff
- 現在、API仕様の策定のみで、実際に使うことはできません。  
- また予告等なしに仕様を変更する可能性があります。
```
***

## アクセストークンの取得
```
POST https://sub.shachiku.tk/nexttime/gettoken
```
### ペイロード
```
id=<user id>&
pass=<password>&
refresh=<refresh token>
```
  - `user id`ユーザーID
  - `password`パスワード
  - `refresh token`リフレッシュトークン
  - (`user id`と`password`)か`refresh token`のどちらかを入力してください。
### 応答例
#### 成功時
```json
{"success":true,"access_token":{"value":"4f85aadd-a6ac-62d0-5d16-56c5249d683a", "expiration":1630785923},"refresh_token":{"value":"3387d934-37dc-737c-c9b1-d6df25a0f0af","expiration":1638648323}}
```
  - `access_token`アクセストークン
  - `refresh_token`リフレッシュトークン
  - `expiration`トークンの有効期限(UNIX時間)
#### 失敗時
```json
{"success":false,"cause":"IDかパスワードが間違っています。"}
```
  - `cause`エラーメッセージ
