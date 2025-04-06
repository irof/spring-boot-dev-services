# SpringBootのTestcontainersサポート

- [Spring Boot / リファレンス / コア機能 / 開発時のサービス / テストコンテナーのサポート](https://spring.pleiades.io/spring-boot/reference/features/dev-services.html#features.dev-services.testcontainers)
- [Spring Boot / リファレンス / テスト / Testcontainers](https://spring.pleiades.io/spring-boot/reference/testing/testcontainers.html)

## ざっくり導入手順

- 依存に `testAndDevelopmentOnly("org.springframework.boot:spring-boot-testcontainers")` を追加
- 使用するコンテナに応じたTestcontainersモジュールを依存に追加
    - ここでは `org.testcontainers:mysql` を追加
- `@TestConfiguration` で `ServiceConnection` をBean定義して、テストで使う
  - ```java
    @Bean
    @ServiceConnection
    MySQLContainer<?> mysql() {
        return new MySQLContainer<>("mysql:lts");
    }
    ```

## テストでのTestcontainers使用

テストクラスごとにコンテナを定義する場合は `org.testcontainers:junit-jupiter`(`@Testcontainers` / `@Container`) は便利ですが、
おそらくSpringBootアプリケーションのテストでは [MyContainersConfiguration](./src/test/java/dev/irof/app/MyContainersConfiguration.java) のように
`@ServiceConnection` のBeanを定義し、これをテストで使用するように `@Import` する形のほうが合っています。

複数のテストで使用する際、各テストクラスに `@Import(MyContainersConfiguration.class)` を書くのは冗長なので、
[@MyContainerJdbcTest](./src/test/java/dev/irof/app/MyContainerJdbcTest.java) のように
[合成アノテーション](https://spring.pleiades.io/spring-framework/reference/core/beans/classpath-scanning.html#beans-meta-annotations) を作成するのが良いでしょう。

## ローカル実行時のTestcontainers使用

```shell
../gradlew bootTestRun
```

`MyContainersConfiguration` を使用するようにセットアップされた [TestMyApplication](./src/test/java/dev/irof/app/TestMyApplication.java) を実行します。


### bootTestRunを使わないパターン

以下のように `spring.datasource.url` を `jdbc:tc:...` にすれば `TestMyApplication` とか作らなくてもいけます。

```shell
SPRING_DATASOURCE_URL=jdbc:tc:mysql:latest:/// ../gradlew bootRun
```

これはSpringBootのTestcontainersサポートではなく、[TestcontainersのJDBCサポート](https://java.testcontainers.org/modules/databases/jdbc/) です。
この形でやるなら `application.yml` とかに書いてプロファイルで有効にするとかになると思います。JDBCでないと使えないので注意が必要です。

## メモ

SpringBootではよくあることなのですが、SpringBootサポートがある場合、使用しているツールの標準の方法より簡潔な選択肢が出てきます。

TestcontainersではJUnit5サポート（ `org.testcontainers:junit-jupiter` ）やJDBCサポートとしてもJDBC URLとコンテナオブジェクト（ `MySQLContainer` など）が提供されています。
JDBCサポートではJDBC URLのほうがURLを変えるだけで使用できる高レベルAPIになるため、TestcontainersでJDBC接続先のコンテナのみを使用する場合はJDBC URLが推奨されていたりします。

しかしSpringBootのテストでは、このサンプルで示しているようにJDBC URLもJUnit5サポート機能（ `org.testcontainers:junit-jupiter` ）も使用せず、
ここで実装しているようにコンテナオブジェクトを `@ServiceConnection` で使用し、合成アノテーションを作成するのが適切です。

Spring Bootのリファレンスでは `@Container` や `@ImportTestcontainers` を使用するものも[紹介されています](https://spring.pleiades.io/spring-boot/reference/features/dev-services.html#features.dev-services.testcontainers.at-development-time.importing-container-declarations)が、
ちゃんと読めばこれはTestcontainersを昔から使っているなど、この使い方にこだわりのある方向けだということがわかります。ちゃんと読むの難しいとかは、まぁ、わかる。

ローカル実行はJDBCだけでコンテナを使用するのであればJDBC URLで良いと思います。
`TestMyApplication` の作りは癖がありますし、ローカル実行とはいえテストリソースがクラスパスに含まれる点に若干の懸念があるためです。
JDBC以外も使うのであればコンテナオブジェクト＋`@ServiceConnection` に寄せるか、他のコンテナ起動方法にした方がいいと思います。

