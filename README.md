## kinesis お試し用

### aerospike setup
(入れるのが面倒な人はDev環境を使ってどうぞ)

#### aeorspike toolsをインストール
[ascliのダウンロードページ](http://www.aerospike.jp/download/tools/3.7.5/)からmac用をダウンロードしてインストール
※ セキュリティうんぬんを言われるので、設定>プライバシーポリシー からインストール

#### ローカルのaerospike serverを用意
[docker](https://docs.docker.com/docker-for-mac/)をインストールして起動しておく

```
docker run -ti --rm -v "`pwd`/aerospike/src/conf:/opt/aerospike/etc/conf" -v "`pwd`/aerospike/data:/opt/aerospike/etc/data" --name aerospike -p 3000:3000 -p 3001:3001 -p 3002:3002 -p 3003:3003 aerospike/aerospike-server /usr/bin/asd --foreground --config-file /opt/aerospike/etc/conf/aerospike.conf
```

### aws kinesis stream applications run

#### プロデューサーアプリケーション
`sbt "runMain applications.streams.producer.SampleImpressionStreamProducerApp"`

#### コンシューマアプリケーション
`sbt "runMain applications.streams.consumer.SampleImpressionStreamConsumerApp"`

#### シャード分割
TODO

#### シャードマージ
TODO 

#### UDF デプロイ
`ascli udf-put aerospike/udf/ad_set.lua`
