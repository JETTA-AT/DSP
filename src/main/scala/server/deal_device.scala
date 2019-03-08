package server

import org.apache.hadoop.io.compress.GzipCodec
import org.apache.spark.{SparkConf, SparkContext}


object deal_device {
  def main(args:Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("deal_device")
    val sc = new SparkContext(sparkConf)

    var deal_id = "";
    var device_id = "";
    val fileRDD = sc.textFile(args(0)).filter(!_.contains("\t")).map {
      line =>
        var arr = line.split(" ")
        if (arr.length == 2) {
          deal_id = arr(0)
          device_id = arr(1)
        }
        (deal_id, device_id)
    } //foreach(println)

    /** 出现频次 + group by deal **/
    val RDD1 = fileRDD.map(line => (line._1 + "#" + line._2, 1)).reduceByKey(_ + _)
    val RDD2 = RDD1.map(line => (if (line._1.split("#").length == 2) line._1.split("#")(0) else -1, line._2))
    val RDD3 = RDD2.map(line => (if (line._2 >= 10) line._1 + "#" + "10" else line._1 + "#" + line._2, 1)).reduceByKey(_ + _)
    val RDD4 = RDD3.map(line => (line._1.split("#")(0), line._1.split("#")(1), line._2))
    //RDD4.foreach(println)

    val RDD5 = RDD4.map(line => {
      val deal_id = line._1
      val reqn = line._2
      val cnt = line._3
      args(2) + '\t' +deal_id + '\t' + reqn + '\t' + cnt
    })

    RDD5.coalesce(1,true).saveAsTextFile(args(1))
  }
}
