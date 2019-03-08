package test

import org.apache.spark.{SparkConf, SparkContext}
import units.dmp_split._

object dmp_test_3 {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\hadoop-2.7.5")

    val sparkConf = new SparkConf().setAppName("dmp_test").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val fileRDD = sc.textFile("file:///C:\\Users\\JShao02\\Desktop\\data\\DMP\\odx.txt")
      .filter(_.contains("result=")).filter(!_.contains("\t"))//.filter(_.contains("|"))//.take(500000).foreach(println)
      .map(line =>
      if (line.split("result=").length > 1)
        (line.substring(0, 19),line.split("result=")(1).replace(",","#")))
      //.count()
      //fileRDD.foreach(println)
      //7415
      //4188900
      .mapPartitions(dmp_split2).count()//.filter(_._2.equals("tencent"))
      //println(fileRDD)
//      .map(line=>(line._2,1)).reduceByKey(_+_)
//      .foreach(println)

    //    val RDD_get = fileRDD.map(line => {
    //      val date = line._1
    //      val dmp = line._2
    //      val device_id = line._3
    //      val media_id = line._4
    //      val ip = line._5
    //      val deal_id = line._6
    //      val dmp_type = line._7.replace("#",",")
    //      date + '\t' + dmp + '\t' + device_id + '\t' + media_id + '\t' + ip + '\t' + deal_id + '\t' + dmp_type
    //    }).count()
    //    println(fileRDD)
    //7399
    //

    //RDD_get.coalesce(1,true).saveAsTextFile("file:///C:\\Users\\JShao02\\Desktop\\data\\output\\dmp_test")

    //JSONObject itemJSONObj = JSONObject.parseObject(JSON.toJSONString(itemMap))
  }
}
