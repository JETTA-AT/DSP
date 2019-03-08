package test

import org.apache.spark.{SparkConf, SparkContext}


object dsp_get {
  def main(args:Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\hadoop-2.7.5")

    val sparkConf = new SparkConf().setAppName("simple").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    var date = "";var ip = "";var head = "";var json_str = "";var str1 = "";var server = "";var str2 = "";var http_type = ""
    var http = "";var url_host = ""
    val fileRDD = sc.textFile("file:///C:\\Users\\JShao02\\Desktop\\data\\dsp\\testfile.txt")
      .map {
      line => var arr = line.split("##")
        if (arr.length == 8) {
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
          http_type = arr(7)
        }
        (date,ip,http,url_host,json_str,str1,server,str2,http_type)
    }

    val RDD_get = fileRDD.filter(_._3.equals("GET"))//.filter(_._4.contains("[?]"))//.toLocalIterator
    //val RDD2 = url_split(RDD_get)
      .mapPartitions(url_split)
      .foreach(println)

     //val newRDD = url_split(RDD_get).take(1000).foreach(println)
    //RDD_get.filter(_._3.substring(0,2).equals("dsp")).map(line => (line._3,1)).reduceByKey(_ + _).foreach(println)
    //RDD_get.coalesce(1,true).saveAsTextFile("file:///C:\\Users\\JShao02\\Documents\\data\\dsp_output")
  }

}
