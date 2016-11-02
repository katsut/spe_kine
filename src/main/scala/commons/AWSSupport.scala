package commons

import com.amazonaws.auth.{AWSCredentialsProvider, EnvironmentVariableCredentialsProvider}

/**
  * Created by tsuruta on 2016/11/10.
  */
trait AWSSupport {
  object AWSCredentialsProvider {
    def fromEnvironment: AWSCredentialsProvider = new EnvironmentVariableCredentialsProvider()
  }

}
