## kinesis + aerospike お試し

### aerospike setup

#### aeorspike toolsをインストール
[ascliのダウンロードページ](http://www.aerospike.jp/download/tools/3.7.5/)からmac用をダウンロードしてインストール
※ セキュリティうんぬんを言われるので、設定>プライバシーポリシー からインストール

#### ローカルのaerospike serverを用意
[docker](https://docs.docker.com/docker-for-mac/)をインストールして起動しておく

プロジェクトルートで以下を実行
```
docker run -ti --rm -v $(pwd)/src/aerospike/conf:/opt/aerospike/etc/conf -v "$(pwd)/data:/opt/aerospike/etc/data" --name aerospike -p 3000:3000 -p 3001:3001 -p 3002:3002 -p 3003:3003 aerospike/aerospike-server /usr/bin/asd --foreground --config-file /opt/aerospike/etc/conf/aerospike.conf
```

#### UDF デプロイ
```
デプロイ
ascli udf-put src/aerospike/udf/ad_set.lua

デプロイ確認
ascli udf-list

UDF実行
TODO 複数パラメータの渡し方がわからない
ascli udf-record-apply test ad 1 ad_set modify 1 "2016-01-01 00:00:00.000" "0000000000"
```


### kinesis stream applications run

#### プロデューサーアプリケーション
```
sbt "runMain applications.streams.producer.SampleImpressionStreamProducerApp"
````

#### コンシューマアプリケーション
```
sbt "runMain applications.streams.consumer.SampleImpressionStreamConsumerApp"
```

### kinesis stream シャード管理

#### シャード分割
TODO

#### シャードマージ
TODO 

