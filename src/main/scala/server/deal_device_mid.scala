package server

import org.apache.hadoop.io.compress.GzipCodec
import org.apache.spark.{SparkConf, SparkContext}

object deal_device_mid {
  def main(args:Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("deal_device")
    val sc = new SparkContext(sparkConf)

    var deal_id = "";
    var device_id = "";
    val fileRDD = sc.textFile(args(0)).filter(!_.contains("\t")).map {
      line =>
        var arr = line.split("\t")
        if (arr.length == 2) {
          deal_id = arr(0).replace("\t","")
          device_id = arr(1).replace("\t","")
        }
        (deal_id, device_id)
    } //foreach(println)

    /**中间表 可部分时间 deal_id 计算UV PV 频次**/
    val RDD1 = fileRDD.map(line => (line._1+"#"+line._2,1)).reduceByKey(_ + _)
    //.filter(_._1.length > 2).filter(_._1.length <= 30).filter(_._1.length > 8).filter(_._2.length <= 50)

    val RDD2 = RDD1.map(line => (
      if(line._1.split("#").length == 2 )
        line._1.split("#")(0)
      else -1,
      if(line._1.split("#").length == 2)
        line._1.split("#")(1)
      else -1,
      line._2
    ))

    val RDD3 = RDD2.filter(!_._1.equals(-1)).map(line => {
      val deal_id = line._1
      val device_id = line._2
      val cnt = line._3
      deal_id + "\t" + device_id + "\t" + cnt
    })

    RDD3.coalesce(args(2).toInt,true).saveAsTextFile(args(1),classOf[GzipCodec])
}
}

