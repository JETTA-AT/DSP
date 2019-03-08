package server

import org.apache.hadoop.io.compress.GzipCodec
import org.apache.spark.{SparkConf, SparkContext}
import units.dmp_split.dmp_split2

object pdber {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("dmp_2")
    val sc = new SparkContext(sparkConf)

    val fileRDD = sc.textFile(args(0))
      .filter(_.contains("result="))
      .map(line =>
        if (line.split("result=").length > 1 && line.length > 20)
          (line.substring(0, 19),line.split("result=")(1).replace(",","#")))

    val RDD_get = fileRDD.mapPartitions(dmp_split2).map(line => {
      val date = line._1
      val dmp = line._2
      val device_id = line._3
      val media_id = line._4
      val ip = line._5
      val deal_id = line._6
      val ta_query = line._7.replace("#",",")
      val dmp_type = line._8
      date + '\t' + dmp + '\t' + device_id + '\t' + media_id + '\t' + ip + '\t' + deal_id + '\t' + ta_query + '\t' + dmp_type
    })

    RDD_get.coalesce(args(2).toInt,true).saveAsTextFile(args(1),classOf[GzipCodec])
  }
}
