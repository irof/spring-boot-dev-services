# Spring BootのDocker Composeサポートを使用した複数アプリケーションのサンプル実装

複数のアプリケーションで共有するサービス（メッセージキュー）と個別のサービス（RDBMS）をDockerComposeで扱うサンプルです。
基本知識として [トップのREADME](../README.md) と [DockerComposerサポートのRADME](../docker-compose/README.md) を前提とします。

## 構成

- `alpha`, `bravo` : アプリケーションです。
- `shared`: アプリケーションで使用する共通ライブラリです。

## 動かし方

### 単独で動作させる

1. アプリケーションを起動する。

特に変わったことはありません。そのアプリケーションが必要なサービスのみ起動します。

個別に動作させる手順では二つのアプリケーションを同時起動はできません。
後者の方がJDBC接続エラーで起動失敗します。

起動失敗時のエラーメッセージは以下のようになります。

```
ERROR 00000 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
```

複数のアプリケーションを起動させたい場合はこの手順で起動したものは一旦終了し、両方を動作させる手順を行なってください。

### 両方を動作させる

1. DockerComposeをdefaultプロファイルで起動する [起動スクリプト](./shared/runDockerCompose.sh)
2. 起動が完了するのを待つ
3. 各アプリケーションを起動する

アプリケーションを起動する際に特段の設定は不要です。
同名のDockerComposeが起動済みなのでDockerComposeの実行はスキップされます。
起動はスキップしますが、接続情報はDockerCompose設定ファイルから取得されます。

#### スキップの確認とスキップされなかった場合の挙動

起動時に `There are already Docker Compose services running, skipping startup` のログが出ていれば大丈夫ですが、
起動完了前にアプリケーションを起動してしまうと起動プロセスが競合して色々なことが起こってしまいます。
たとえばシャットダウン時の挙動ですが、スキップされた場合は停止もしませんが、自分で起動したものは停止します。
このため半端にサービスを停止してしまったりします。
うまく動かなくなっってしまった場合はDockerComposeが起動したコンテナを手動で片付けてください。

## 解説

同じDockerCompose設定ファイルを使用するにあたり `shared` の中のものをクラスパスで参照しています。
適切な共通ライブラリがあればそれに追加してもよいですし、なければ専用のを作っても良いと思います。

このサンプルではマルチモジュール構成にしているためDockerCompose設定ファイルは相対パス参照でもよく思えますが、
`alpha` と `bravo` が別リポジトリの場合にはローカル環境のフォルダ構成などを統一する必要が出てきます。
開発標準などでルールを決めれば良いところではあるのですが、個人の環境に依存する様々なトラブルの要因になりえるため、
避けています。クラスパス参照については [DockerComposerサポートのRADME](../docker-compose/README.md) を参照ください。

共通ライブラリをインハウスリポジトリ（Sonatype Nexus、GitHub Packages、Amazon CodeArtifactなど）で管理することを前提にしています。
インハウスリポジトリ運用をしない場合にこの構成がとれるのはモノレポ運用の場合です。（このサンプルはモノレポに近い。）
インハウスリポジトリでの共通ライブラリ運用が不要な規模であればDocker Composeの共有もこの方式をとる必要はないと思います。

DockerComposeのprofileを使用することで各アプリケーションが使用するDBをわかるようにしています。
profileなしで起動すると `JdbcConnectionDetails` が複数作成され、JDBC接続に何を使用すればよいのかSpringBootがわからなくなります。
JDBC接続情報を個別に設定したり個別に `ServiceConnection` を作るなどの対応も可能ではありますが、
SpringBootの開発時サービスサポート機能で賄うことで極力シンプルにしています。

### 設定のポイント

- DockerComposeのprofileを使用することでアプリケーションと対象サービスの紐付けを制御しつつ、アプリケーション単独で起動する際に余計なサービスが起動しないようにする。
- DockerComposeのnameを使用することで共有できるようにする。nameを使用しないと同じ設定ファイルでも別の名前（`resources` や `main` など）で起動してしまう。

## 動作確認

起動後に `shared` の `driver.RabbitDriver` を実行してください。
それぞれのListenerがDBにレコードを書き込み、全件をログ出力します。
メッセージキューは共有され、RDBMSは個別になっていることがわかるかと思います。
