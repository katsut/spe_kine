package applications.benchmarks

import com.aerospike.client.{Bin, Host, Key}
import com.aerospike.client.async.{AsyncClient, AsyncClientPolicy}
import com.aerospike.client.policy.WritePolicy

/**
  * Created by tsuruta on 2016/10/28.
  */
object AerospikeBenchmark {

  val DELIVERY_LIST = """{}""" // TODO サンプルデータを用意

  val keySize = 1000


  val namespace = "benchmarks"
  val setName = "benchmark_1"

  def main(args: Array[String]): Unit = {

    val clientPolicy = new AsyncClientPolicy()

    val hosts = new Host("52.199.52.47", 3000) :: Nil

    val client = new AsyncClient(clientPolicy, hosts: _*)

    val writePolicy = new WritePolicy()
    try {

      val start = System.currentTimeMillis()

      println(s"===== start $start")

      1 to 100000 foreach { i =>
        val record = new Key(namespace, setName, i.toString) -> (new Bin("deliveryList", DELIVERY_LIST) :: Nil)
        client.put(writePolicy, record._1, record._2: _*)
      }

      val end = System.currentTimeMillis()
      println(s"===== finish $end    duration:${end - start}")

    } finally {
      client.close()
    }

  }

}
