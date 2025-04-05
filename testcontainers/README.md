# SpringBootのTestcontainersサポート

https://spring.pleiades.io/spring-boot/reference/features/dev-services.html#features.dev-services.testcontainers

## テストでのTestcontainers使用

https://spring.pleiades.io/spring-boot/reference/testing/testcontainers.html

テストクラスごとにコンテナを定義する場合は `org.testcontainers:junit-jupiter`(`@Testcontainers` / `@Container`) は便利ですが、
おそらくSpringBootアプリケーションのテストでは不要です。

[MyContainersConfiguration](./src/test/java/dev/irof/app/MyContainersConfiguration.java) のように
`@ServiceConnection` のBeanを定義し、これをテストで使用するように `@Import` します。

複数のテストで使用する際、各テストクラスに `@Import(MyContainersConfiguration.class)` を書くのは冗長なので、
[@MyContainerJdbcTest](./src/test/java/dev/irof/app/MyContainerJdbcTest.java) のように
[合成アノテーション](https://spring.pleiades.io/spring-framework/reference/core/beans/classpath-scanning.html#beans-meta-annotations) を作成するのが良いでしょう。

## ローカル実行時のTestcontainers使用

```shell
../gradlew bootTestRun
```

`MyContainersConfiguration` を使用するようにセットアップされた [TestMyApplication](./src/test/java/dev/irof/app/TestMyApplication.java) を実行します。

`bootRun` では動きませんが、JDBCであれば以下のように `jdbc:tc:...` を使用できます。
これはSpringBootのTestcontainersサポートではなく、[TestcontainersのJDBCサポート](https://java.testcontainers.org/modules/databases/jdbc/) です。

```shell
SPRING_DATASOURCE_URL=jdbc:tc:mysql:latest:/// ../gradlew bootRun
```

## メモ

SpringBootではよくあることなのですが、SpringBootサポートがある場合、使用しているツールの標準の方法より簡潔な選択肢が出てきます。

TestcontainersではJUnit5サポート（ `org.testcontainers:junit-jupiter` ）やJDBCサポートとしてもJDBC URLとコンテナオブジェクト（ `MySQLContainer` など）が提供されています。
JDBCサポートではJDBC URLのほうがURLを変えるだけで使用できる高レベルAPIになるため、TestcontainersでJDBC接続先のコンテナのみを使用する場合はJDBC URLが推奨されていたりします。

しかしSpringBootのテストでは、このサンプルで示しているようにJDBC URLもJUnit5サポート機能（ `org.testcontainers:junit-jupiter` ）も使用せず、
コンテナオブジェクトを `@ServiceConnection` で使用し、合成アノテーションを作成するのがおそらく適切です。

ローカル実行はJDBC接続先だけでコンテナを使用するのであればJDBC URLで良いと思います。
`TestMyApplication` の作りは癖がありますし、ローカル実行とはいえテストリソースがクラスパスに含まれる点に若干の懸念があるためです。
JDBC接続先以外も使うのであればコンテナオブジェクト＋`@ServiceConnection` に寄せるか、他のコンテナ起動方法にした方がいいと思います。
