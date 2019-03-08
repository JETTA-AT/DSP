package server

import org.apache.hadoop.io.compress.GzipCodec
import org.apache.spark.{SparkConf, SparkContext}

object dsp_post {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("dsp_get_request")
    val sc = new SparkContext(sparkConf)

    var date = "";
    var ip = "";
    var head = "";
    var probuf_str = "";
    var str1 = "";
    var server = "";
    var str2 = "";
    var http_type = ""
    var http = "";
    var url_host = ""
    val fileRDD = sc.textFile(args(0)).map {
      line =>
        var arr = line.split("##")
        if (arr.length == 8) {
          date = arr(0)
          ip = arr(1)
          var head_all = arr(2).split(" ")
          if (head_all.length == 3) {
            http = head_all(0)
            url_host = head_all(1).substring(1)
          }
          probuf_str = arr(3)
          str1 = arr(4)
          server = arr(5).replace(",", "|")
          str2 = arr(6)
          http_type = arr(7)
        }
        (date, ip, http, url_host, probuf_str, str1, server, str2, http_type)
    }

    val RDD_get = fileRDD.filter(_._3.equals("POST")).filter(_._1.length.equals(28)).map(line => {
      val date = line._1
      val ip = line._2
      val http = line._3
      val url_host = line._4
      val probuf_str = line._5
      val str1 = line._6
      val server = line._7
      val str2 = line._8
      val http_type = line._9

      date + '\t' + ip + '\t' + http + '\t' + url_host + '\t' + probuf_str + '\t' + str1 + '\t' + server + '\t' + str2 + '\t' + http_type
    })

    RDD_get.coalesce(args(2).toInt,true).saveAsTextFile(args(1),classOf[GzipCodec])

  }
}
