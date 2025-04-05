# SpringBootの開発時サービスサポート

https://spring.pleiades.io/spring-boot/reference/features/dev-services.html

Spring Boot 3.4.4でのサンプル実装です。

## 背景

Spring Boot 3.1でTestcontainersとDocker Composeのサポートが導入されました。
TestcontainersやDocker ComposeはそれぞれSpringBootのサポートがなくても使われてきましたが、
サポートが導入されたことで非常に使いやすくなりました。

それぞれが独立したツールであり、実際に併用されてきため、動かす方法は幾通りもあります。
それだけに情報も錯綜するため、何をどう使ったら動くか（また動かないか）がわかりづらくもあります。

## 所感

Docker Composeサポートはシンプルケースでは `org.springframework.boot:spring-boot-docker-compose` を依存に入れるだけで済み、 `ServiceConnection` などは表に出てきません。
すでに `compose.yml` がある条件だと依存追加以外に何もしなくても動作するだけに、設定が必要な状況になると急に難度が上がるように感じるかもしれません。

TestcontainersサポートはTestcontainersの `Container` を使って `@ServiceConnection` も書くので、うまくいかなくてもこの延長線上でなんとかなる感はあります。
[スライスをテストする](https://spring.pleiades.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.autoconfigured-tests)場合などは
必要なコンテナのみを使用するのも簡単です。

ローカル実行はサポート機能は使わなくていいかなぁというのが正直なところですが、使うならDocker Composeかなと思います。
ただテストで使っていない場合にローカル実行のために依存を追加するかというと正直微妙なところ。
TestcontainersはJDBC URLなら使いたいですが、JDBC以外には使えませんし、名前に冠している通りテスト向きで、ローカル実行ではどうしてもぎこちない感じが拭えません。
