package applications.streams.producer

import models.SampleImpressionLog
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
          val record = SampleImpressionLog(
            now,
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

}

