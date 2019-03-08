package test

import org.apache.hadoop.io.compress.GzipCodec
import org.apache.spark.{SparkConf, SparkContext}

object dsp_get_error_test {
  def main(args:Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("dsp_get_error")
    val sc = new SparkContext(sparkConf)

    var ip = "";var request_type = "";var url_host = "";var media_id = "";var strategy_id = "";var device_id = "";var ts = "";var aa = "";var bb = "";var cc = "";var ua = "";var url_type = "";
    val fileRDD = sc.textFile(args(0)).map {
      line => var arr = line.split("\t")
        if (arr.length == 12) {
          ip = arr(0)
          request_type = arr(1)
          url_host = arr(2)
          media_id = arr(3)
          strategy_id = arr(4)
          device_id = arr(5)
          ts = arr(6)
          aa = arr(7)
          bb = arr(8)
          ua = arr(9)
          cc = arr(10)
          url_type = arr(11)
        }
        (ip,request_type,url_host,media_id,strategy_id,device_id,ts,aa,bb,ua,cc,url_type)
    }
    val RDD_get = fileRDD.map(line => {
      val ip = line._1
      val request_type = line._2
      val url_host = line._3
      val media_id = line._4
      val strategy_id = line._5
      val device_id = line._6
      val ts = line._7
      val aa = line._8
      val bb = line._9
      val ua = line._10
      val cc = line._11
      val url_type = line._12
      ip + '\t' + request_type + '\t' + url_host + '\t' + media_id + '\t' + strategy_id + '\t' + device_id + '\t' + ts + '\t' + aa + '\t' + bb + '\t' + ua + '\t' + cc + '\t' + url_type
    })

    RDD_get.coalesce(args(2).toInt,true).saveAsTextFile(args(1),classOf[GzipCodec])
  }

}
