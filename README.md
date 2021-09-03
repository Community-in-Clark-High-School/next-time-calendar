# next-time-calendar
NEXTタイムの選択専用サイトなのにゃ！！  
  
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
