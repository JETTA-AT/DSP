package test

import org.apache.spark.{SparkConf, SparkContext}

object split_check {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\hadoop-2.7.5")

    val sparkConf = new SparkConf().setAppName("simple").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    var date = "";
    var ip = "";
    var head = "";
    var json_str = "";
    var str1 = "";
    var server = "";
    var str2 = "";
    var http_type = ""
    var http = "";
    var url_host = ""
    val fileRDD = sc.textFile("file:///C:\\Users\\JShao02\\Desktop\\data\\dsp\\dsp-2018052316.log.gz").map {
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
          json_str = arr(3)
          str1 = arr(4)
          server = arr(5).replace(",","|")
          str2 = arr(6)
          http_type = arr(7)
        }
        (date, ip, http, url_host, json_str, str1, server, str2, http_type)
    }

    val newRDD = fileRDD.filter(_._3.equals("GET")).mapPartitions(url_split)
      .take(1000).foreach(println)

  }

  def url_split(iter: Iterator[(String, String, String, String, String, String, String, String, String)]): Iterator[(String)]= {
    var res = List[(String)]()
    var cur4_1 = "";
    var cur4_2 = "";
    var media_id = "";
    var base64_encode = ""
    while (iter.hasNext) {
      val cur0 = iter.next().toString()
      val cur = cur0.substring(1, cur0.length - 1).split(",")
//      println(cur0)
//      println("--------------------------")
//      println(cur.length)
//      println("++++++++++++++++++++++++++")
//      cur4_1 = cur.length.toString
      res.::=(cur4_1)
    }
    res.iterator
  }

}
