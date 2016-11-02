package applications.streams.consumer

import java.net.InetAddress
import java.util
import java.util.UUID

import com.amazonaws.regions.Regions
import com.amazonaws.services.kinesis.clientlibrary.interfaces.{IRecordProcessor, IRecordProcessorCheckpointer, IRecordProcessorFactory}
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.{InitialPositionInStream, KinesisClientLibConfiguration, ShutdownReason, Worker}
import com.amazonaws.services.kinesis.model.Record
import commons.AWSSupport

/**
  * Created by tsuruta on 2016/10/27.
  *
  * TODO 調べる
  * [済] Consumerの設定について
  * [済] Checkpointについて
  * デプロイ時や障害復旧後のデータ整合性
  * 移行について => Amazon Kinesis エージェント を使うと良さそう
  * KCLのスケール
  * シャード数のオートスケール
  *
  */
object SampleImpressionStreamConsumerApp extends AWSSupport {

  def main(args: Array[String]): Unit = {

    val workerId = InetAddress.getLocalHost.getCanonicalHostName + ":" + UUID.randomUUID

    // kinesis client 設定
    val kinesisConf: KinesisClientLibConfiguration =
      new KinesisClientLibConfiguration("SampleImpressionStreamConsumerApp", "uzo_lcl_sample_stream_1", AWSCredentialsProvider.fromEnvironment, workerId)
        .withRegionName(Regions.AP_NORTHEAST_1.getName)
        .withUserAgent(KinesisClientLibConfiguration.KINESIS_CLIENT_LIB_USER_AGENT)
        .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON) // (LATEST or TRIM_HORIZON) チェックポイントが保存されていればチェックポイントからフェッチを開始する、チェックポイントが無い場合、ストリーム内の設定された位置からフェッチを開始する
        .withFailoverTimeMillis(KinesisClientLibConfiguration.DEFAULT_FAILOVER_TIME_MILLIS) // 失敗扱いされ、他のワーカーの処理対象となるまでの時間
        .withMaxRecords(KinesisClientLibConfiguration.DEFAULT_MAX_RECORDS) // getRecords() 時に取得される最大レコード
        .withIdleTimeBetweenReadsInMillis(KinesisClientLibConfiguration.DEFAULT_IDLETIME_BETWEEN_READS_MILLIS) // Idle time between calls to fetch data from Kinesis
        .withCallProcessRecordsEvenForEmptyRecordList(true) // 処理対象が無くても処理結果を保存するか
        .withCleanupLeasesUponShardCompletion(true) // Clean up shards we've finished processing

    // ワーカーの作成と起動
    new Worker(ProcessorFactory, kinesisConf).run()
  }

}




object ProcessorFactory extends IRecordProcessorFactory {
  override protected def createProcessor(): IRecordProcessor = new IRecordProcessor {

    override def initialize(shardId: String): Unit = {}

    override def processRecords(records: util.List[Record], checkpointer: IRecordProcessorCheckpointer): Unit = {
      import scala.collection.JavaConversions._
      records map { r =>
        r -> new String(r.getData.array)
      } foreach { case (r, s) =>
        println(s)
      }

      checkpointer.checkpoint()
    }

    override def shutdown(checkpointer: IRecordProcessorCheckpointer, reason: ShutdownReason): Unit = {}

  }
}


