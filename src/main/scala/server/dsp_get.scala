package server

import java.util.Base64

import org.apache.hadoop.io.compress.GzipCodec
import org.apache.spark.{SparkConf, SparkContext}
import java.util.Locale
import java.text.SimpleDateFormat
import units.get_base64

object dsp_get {
  def main(args:Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("dsp_get_request")
    val sc = new SparkContext(sparkConf)

    var date = "";var ip = "";var head = "";var json_str = "";var str1 = "";
    var server = "";var str2 = "";var http_type = "";var http = "";var url_host = ""
    val fileRDD = sc.textFile(args(0)).filter(!_.contains("\t")).map {
      line => var arr = line.split("##")
        if (arr.length >= 8) {
          date = arr(0)
          ip = arr(1)
          var head_all = arr(2).split(" ")
          if(head_all.length == 3){
            http = head_all(0)
            url_host = head_all(1).substring(1)
          }
          json_str = arr(3)
          str1 = arr(4)
          server = arr(5).replace(",","|")
          str2 = arr(6)
          http_type = arr(7).replace(",","|")
        }
        (date,ip,http,url_host,json_str,str1,server,str2,http_type)
    }

    val RDD_get = fileRDD.filter(_._3.equals("GET")).filter(_._1.length.equals(28))
      .mapPartitions(url_split)
      .map(line => {
        val ip = line._1
        val request_type = line._2
        val url_host = line._3
        val media_id = line._4
        val strategy_id = line._5
        val device_id = line._6
        val ts = line._7
        val str1 = line._8
        val str2 = line._9
        val ua = line._10
        val str3 = line._11
        val url_return_type = line._12
        ip + '\t' + request_type + '\t' + url_host + '\t' + media_id + '\t' + strategy_id + '\t' + device_id + '\t' + ts + '\t' + str1 + '\t' + str2 + '\t' + ua + '\t' + str3 + '\t' + url_return_type
      })


    RDD_get.coalesce(args(2).toInt,true).saveAsTextFile(args(1),classOf[GzipCodec])
  }

//  def get_base64(string: String): String ={
//    //val new_str = string.substring(0,string.length - 4)
//    val decoder = Base64.getUrlDecoder
//    try {
//      new String(decoder.decode(string))
//    }
//    catch {
//      case ex: IllegalArgumentException => "decoder_error"
//    }
//  }
  import units.get_base64.get_base64
  def url_split(iter: Iterator[(String, String, String, String, String, String, String, String, String)]): Iterator[(String, String, String, String, String, String, String, String, String, String, String, String)] = {
    var res = List[(String, String, String, String, String, String, String, String, String, String, String, String)]()
    var cur4_1 = "";var cur4_2 = "";var media_id = "";var base64_encode = ""
    while (iter.hasNext){
      val cur0 = iter.next().toString()
      val cur = cur0.substring(1,cur0.length-1).split(",")
      if(cur.length == 9) {
        val cur1 = cur(0).substring(1,21)
        val loc = new Locale("en")
        val fm = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss",loc)
        val dt2 = fm.parse(cur1)
        val new_ts = dt2.getTime().toString.substring(0,10)

        val cur2 = cur(1)
        val cur3 = cur(2)
        val cur4 = cur(3)
        if(cur4.split("[?]").length >= 2){
          cur4_1 = cur4.split("[?]")(0)
          cur4_2 = cur4.split("[?]")(1)
        }
        val cur5 = cur(4)
        val cur6 = cur(5)
        val cur7 = cur(6)
        val cur8 = cur(7)
        val cur9 = cur(8)
        val iter2 = cur4_2.split("[&]").toIterator
        while (iter2.hasNext) {
          val spilt_str = iter2.next().split("[=]")
          if (spilt_str.length >= 2) {
            if(spilt_str(0).equals("sp"))
              media_id = spilt_str(1)
            if (spilt_str(0).equals("ext")) {
              base64_encode = spilt_str(1)
              if (base64_encode.length > 4 && !base64_encode.contains("\\")) {
                //import server.dsp_get.get_base64
                val base64_str = get_base64(base64_encode)
                val cur4_2_0 = base64_str.split("[,]")
                if (cur4_2_0.length.equals(16)) {
                  res.::=(cur4_2_0(7), cur3, cur4_1, media_id, cur4_2_0(2), cur4_2_0(6), new_ts, cur5, cur6, cur7, cur8, cur9)
                }
              }
            }
          }
        }
      }
    }
    res.iterator
  }
}

