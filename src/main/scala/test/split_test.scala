package test

import org.apache.spark.{SparkConf, SparkContext}

object split_test {
  def main(args:Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\hadoop-2.7.5")

    val sparkConf = new SparkConf().setAppName("simple").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    var date = "";var ip = "";var head = "";var json_str = "";var str1 = "";var server = "";var str2 = "";var http_type = ""
    var http = "";var url_host = ""
    val fileRDD = sc.textFile("file:///C:\\Users\\JShao02\\Desktop\\data\\dsp\\testfile.txt").map {
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
          server = arr(5)
          str2 = arr(6)
          http_type = arr(7)
        }
        (date,ip,http,url_host,json_str,str1,server,str2,http_type)
    }

    val newRDD = fileRDD//.take(1000)
//    println(newRDD.count())

    //newRDD.filter(_._3.equals("POST")).foreach(println)


    //newRDD.filter(_._3.equals("GET")).map(line => (if(line._4.split("[?]").length ==2) ((line._4.split("[?]"))(0),(line._4.split("[?]"))(1)) else (line._4,"")  )).foreach(println)


    //查看
    var url_host1 = "";var arr1 = ""
    newRDD.filter(_._3.equals("GET")).map{
        line => var arr = line._4.split("[?]")
          if(arr.length == 2){
            url_host1 = arr(0)
            arr1 = arr(1)
          }
          (url_host1,1)
      }//.filter(_._1.length < 20).reduceByKey(_ + _).
    newRDD.foreach(println)


    import java.util.Base64
    val decoder = Base64.getUrlDecoder
    val newRDD1 = newRDD.filter(_._3.equals("GET"))
      .map(
        line => (
          line._1,line._2,line._3,
          if(line._4.split("[?]").length == 2){
            ((line._4.split("[?]"))(0),
              (line._4.split("[?]"))(1))
          }
//          else{
//            (line._4,"")
//          }
          ,line._5,line._6,line._7,line._8,line._9
        )
      ).take(1000)//.foreach(println)

    //fileRDD.filter(_._3.contains("dspc")).map(_._3).take(10).foreach(println)
  }
}
