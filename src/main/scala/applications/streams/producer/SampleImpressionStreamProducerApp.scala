package applications.streams.producer

import org.joda.time.{DateTime, DateTimeZone}

import scala.concurrent.Future
import scala.language.postfixOps

/**
  * Created by tsuruta on 2016/11/10.
  */
object SampleImpressionStreamProducerApp extends KinesisProducerApp {
  object Stub {
    val users: Seq[String] = 1 to 10000 map (i => f"user_$i%010d")
    val usersLength = users.length
    val adIds: Seq[Int] = 1 to 100
    val adIdLength = adIds.length
  }

  val PUT_PAR_SEC = 1

  def main(args: Array[String]) = {

    import Stub._

    import scala.concurrent.ExecutionContext.Implicits.global

    val jst = DateTimeZone.forID("Asia/Tokyo")
    1 to 600 foreach { i =>
      val now = DateTime.now(jst).toString
      Future {
        (1 to PUT_PAR_SEC par) foreach { j =>
          val record = RecordEntity(
            users(i * j % usersLength),
            adIds(i * j % adIdLength)
          )
          producer.addUserRecord("uzo_lcl_sample_stream_1", record.user, record.toTsv(now).toByteBuffer)
        }
      } onComplete { f =>
        println(s"put ${i * PUT_PAR_SEC} 件")
      }
      Thread.sleep(1000) // １秒待機
    }

  }

  case class RecordEntity(user: String, adId: Int) {
    def toTsv(time: String): String = s"""{"application_id":0,"time":"$time","uuid":"$user","ad_id":$adId,"placement_id":2,"url":"//uresta.jp/オキテネムル","referer":"https://www.google.co.jp/","ua":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36","device":2,"lang":"ja,en-US;q=0.8,en;q=0.6","remote_ip_address":"","media_id":3,"article_id":7720,"priority":0.40650835985540756,"manual_priority":null,"similarity":0.5934916401445924,"manual_similarity":null,"default":0,"table_name":"article_impressions"}"""
  }

}

