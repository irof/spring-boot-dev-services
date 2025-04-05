# Spring BootのDocker Composeサポート

https://spring.pleiades.io/spring-boot/reference/features/dev-services.html#features.dev-services.docker-compose

## ローカル実行時のDocker Compose使用

```shell
../gradlew bootRun
```

[build.gradle.kts](build.gradle.kts) で依存に `org.springframework.boot:spring-boot-docker-compose` を追加するだけで、
working directoryの `compose.yml` を使ってくれます。

## テストでのDocker Compose使用

https://spring.pleiades.io/spring-boot/reference/features/dev-services.html#features.dev-services.docker-compose.tests

ローカル実行とテストともにDocker Composeサポートを使用する場合は `testAndDevelopmentOnly` とした上で、
`spring.docker.compose.skip.in-tests=false` を指定する必要があります。

テスト時のみで使用する場合は `testRuntimeOnly` でいいんじゃないかなと。

## メモ

`compose.yml` の検出はworking directoryを起点に行いますが、working directoryは実行の仕方によって変わります。
たとえば`MyApplication`をIDEで実行した場合、未設定だとルートプロジェクトがworking directoryになったりしますが、
テストを実行した場合はプロジェクトルートがworking directoryになったりします。

このサンプルではマルチプロジェクト構成で動作を安定させるために `spring.docker.compose.file=classpath:dev-compose.yml`
のようにクラスパスで指定しています。（ [application.yml](./src/main/resources/application.yml) 参照）

テストでは `classpath:test-compose.yml` を使用しているのは別設定も使用できることを示していますが、同じにできるならした方がよいでしょう。
ただし `main` に置くとリリースモジュールに含まれることになるのは少し気になるところです。気になるなら除外しましょう。

Docker Composeを開発時のアプリケーション起動やテスト時以外でも起動するのであればデフォルトのままworking directoryを使用するとして、
うまく動作しない時はworking directoryを設定するか、 `spring.docker.compose.file` を絶対パスで指定するのが良いでしょう。
