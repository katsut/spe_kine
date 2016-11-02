package applications.streams.producer

import java.nio.ByteBuffer

import com.amazonaws.regions.Regions
import com.amazonaws.services.kinesis.producer.{KinesisProducer, KinesisProducerConfiguration}
import commons.AWSSupport

/**
  * Created by tsuruta on 2016/11/10.
  */
trait KinesisProducerApp extends AWSSupport {

  val producer = new KinesisProducer(new KinesisProducerConfiguration()
    .setCredentialsProvider(AWSCredentialsProvider.fromEnvironment)
    .setRegion(Regions.AP_NORTHEAST_1.getName))


  implicit class RichString (s: String) {
    def toByteBuffer = ByteBuffer.wrap(s.getBytes("UTF-8"))
  }

}
