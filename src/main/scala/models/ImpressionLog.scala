package models

/**
  * Created by tsuruta on 2016/11/11.
  */
case class ImpressionLog(time: String, user: String, adId: Int) {
  def toTsv(time: String): String = s"""{"application_id":0,"time":"$time","uuid":"$user","ad_id":$adId,"placement_id":2,"url":"//uresta.jp/オキテネムル","referer":"https://www.google.co.jp/","ua":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36","device":2,"lang":"ja,en-US;q=0.8,en;q=0.6","remote_ip_address":"","media_id":3,"article_id":7720,"priority":0.40650835985540756,"manual_priority":null,"similarity":0.5934916401445924,"manual_similarity":null,"default":0,"table_name":"article_impressions"}"""
}
