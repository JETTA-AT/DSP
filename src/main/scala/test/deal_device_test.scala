package test

import org.apache.spark.{SparkConf, SparkContext}

object deal_device_test {

  def main(args:Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\hadoop-2.7.5")

    val sparkConf = new SparkConf().setAppName("simple").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    var deal_id = "";
    var device_id = "";
    val fileRDD = sc.textFile("file:///C:\\Users\\JShao02\\Desktop\\data\\20180912_test").map {
      line =>
        var arr = line.split(" ")
        if (arr.length == 2) {
          deal_id = arr(0)
          device_id = arr(1)
        }
        (deal_id, device_id)
    }//foreach(println)

    /** 出现频次 + group by deal **/
//    val RDD1 = fileRDD.map(line => (line._1+"#"+line._2,1)).reduceByKey(_ + _)
//    val RDD2 = RDD1.map(line => (if(line._1.split("#").length == 2) line._1.split("#")(0) else -1,line._2))
//    val RDD3 = RDD2.map(line => (if(line._2 >= 10) line._1+"#"+"10" else line._1+"#"+line._2,1)).reduceByKey(_ + _)
//    val RDD4 = RDD3.map(line => (line._1.split("#")(0),line._1.split("#")(1),line._2))
//    RDD4.foreach(println)

    /**中间表 可部分时间 deal_id 计算UV PV 频次**/
//    val RDD1 = fileRDD.map(line => (line._1+"#"+line._2,1)).reduceByKey(_ + _)
//    val RDD2 = RDD1.map(line => (
//      if(line._1.split("#").length == 2)
//        line._1.split("#")(0),line._1.split("#")(1),line._2)
//    )
//    //RDD2.foreach(println)
//
//    val RDD3 = RDD2.map(line => {
//      val deal_id = line._1
//      val device_id = line._2
//      val cnt = line._3
//      deal_id + "\t" + device_id + "\t" + cnt
//    })

    val RDD1 = fileRDD.filter(!_._1.contains("~")).filter(_._1.length > 2).filter(_._1.length <= 30).filter(_._1.length > 8).filter(_._2.length <= 50)
      .map(line => (line._1+"#"+line._2,1)).reduceByKey(_ + _)
    val RDD2 = RDD1.map(line => (
      if(line._1.split("#").length == 2)
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

    RDD3.foreach(println)



  }
}
