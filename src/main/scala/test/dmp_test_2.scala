package test

import org.apache.spark.{SparkConf, SparkContext}
import units.dmp_split._

object dmp_test_2 {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\hadoop-2.7.5")

    val sparkConf = new SparkConf().setAppName("dmp_test").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val fileRDD = sc.textFile("file:///C:\\Users\\JShao02\\Desktop\\data\\DMP\\odx.txt")
      .filter(_.contains("result=")).filter(!_.contains(":;"))//.take(500000).foreach(println)
      .map(line =>
      if (line.split("result=").length > 1 && line.length > 30){
        (line.substring(0, 19),line.split("result=")(1).replace(",","#"))
      })//.count()

      //.count()
      //println(fileRDD)
      //7415
      //4188900
      .mapPartitions(dmp_split2)//.count()//.filter(_._2.equals("tencent"))
    //println(fileRDD)
    fileRDD.foreach(println)
      //.foreach(println)

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
//  def dmp_split2(iter: Iterator[(Any)]): Iterator[(String, String, String, String, String, String,String,String)] = {
//    var res = List[(String, String, String, String, String, String,String,String)]()
//    while (iter.hasNext) {
//      val cur0 = iter.next().toString()
//      val cur = cur0.substring(1, cur0.length - 1).split(",")
//      if (cur.length.equals(2)) {
//        val cur1 = cur(0)
//        val cur2_0 = cur(1).substring(1, cur(1).length - 2)
//        val cur2_0_json = cur2_0.substring(cur2_0.indexOf("|") - 1)
//        val dmp_TA = get_dmp(cur2_0_json)
//        val cur2 = cur2_0.split("[;]")
//        if(cur2.length > 5 ){
//          val cur2_1 = cur2(0).split(":")(1)
//          val cur2_2 = cur2(1).split(":")(1).replace("DEVICE_","")
//          val cur2_3 = cur2(2).split(":")(1)
//          val cur2_4 = cur2(3).split(":")(1)
//          val cur2_5 = cur2(4).split(":")(1)
//          res.::=(cur1, cur2_1, cur2_2, cur2_3, cur2_4, cur2_5, cur2_0_json ,dmp_TA)
//        }
//      }
//    }
//    res.iterator
//  }


}
