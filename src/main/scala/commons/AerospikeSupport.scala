package commons

import com.aerospike.client.Host
import com.aerospike.client.async.{AsyncClient, AsyncClientPolicy}
import com.aerospike.client.policy.WritePolicy

/**
  * Created by tsuruta on 2016/11/11.
  */
trait AerospikeSupport {

  lazy val clientPolicy = new AsyncClientPolicy()

  lazy val hosts = new Host("localhost", 3000) :: Nil

  lazy val client = new AsyncClient(clientPolicy, hosts: _*)

  object policy {
    object writes {
      val default = new WritePolicy()
    }
  }

}
