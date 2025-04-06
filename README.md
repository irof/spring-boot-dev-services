# Spring Bootの開発時サービスサポートのサンプル実装

- [Spring Boot / リファレンス / コア機能 / 開発時のサービス](https://spring.pleiades.io/spring-boot/reference/features/dev-services.html)

Spring Boot 3.4.4でのサンプル実装です。
それぞれの `README.md` とコードを参照ください。

## 背景

Spring Boot 3.1でTestcontainersとDocker Composeのサポートが導入されました。
TestcontainersやDocker ComposeはそれぞれSpring Bootのサポートがなくても使われてきましたが、
サポートが導入されたことで非常に使いやすくなりました。

それぞれが独立したツールであり、実際に併用されてきため、動かす方法は幾通りもあります。
それだけに情報も錯綜するため、何をどう使ったら動くか（また動かないか）がわかりづらくもあります。

なので極力シンプルかつ実プロダクトで使う場合のイメージで動くものを書いておこうと思いました。

## 実装とコメント

- [Testcontainers](./testcontainers)
- [Docker Compose](./docker-compose)

それぞれのREADMEを参照ください。

## 所感

各READMEに書いた「ざっくり導入手順」を見て分かるように、簡単に使えるのはDocker Composeの方です。
一方でTestcontainersの方が柔軟で痒いところにも手が届きそうな感じです。

Docker Composeサポートはシンプルケースでは `org.springframework.boot:spring-boot-docker-compose` を依存に入れるだけで済み、 `ServiceConnection` などは表に出てきません。
すでにDocker Composeを使っている場合、依存を追加するだけで動作しかねません。
楽なのはいいですが、設定が必要な状況になると急に難度が上がるように感じるかもしれません。

TestcontainersサポートはTestcontainersの `Container` を使って `@ServiceConnection` も書くので、うまくいかなくてもこの延長線上でなんとかなる感はあります。
[スライスをテストする](https://spring.pleiades.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.autoconfigured-tests)場合などは
必要なコンテナのみを使用するのも簡単です。
Testcontainers単体で使う場合でも複数通りの使い方があり、さらにSpring Bootのドキュメントでもいくつかの使い方が示されているため、
どうやったら動くのかわかりづらいかもしれませんし、効果のない組み合わせで使ったりとかも散見されます。

ローカル実行はサポート機能は使わなくていいかなぁというのが正直なところですが、使うならDocker Composeかなと思います。
ただテストで使っていない場合にローカル実行のために依存を追加するかというと正直微妙なところ。
TestcontainersはJDBC URLなら使いたいですが、JDBC以外には使えませんし、名前に冠している通りテスト向きで、ローカル実行ではどうしてもぎこちない感じが拭えません。

## メモ

- Testcontainersのバージョンも[Spring BootでManagement](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.1-Release-Notes#dependency-management-for-testcontainers)されています。Testcontainersに限らず、Spring BootがManagementしているバージョンはよほどの事情がない限り自分で指定しない方がいいです。
- Gradleの `testAndDevelopmentOnly` は[Spring Boot 3.2で追加](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.2-Release-Notes#testanddevelopmentonly-gradle-configuration)されました。Testcontainersのためだそうですが、他でも使えるかもですね。
