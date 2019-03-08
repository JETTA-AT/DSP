package units

import units.get_dmp.get_dmp1

object dmp_split {
  def dmp_split(iter: Iterator[(Any)]): Iterator[(String, String, String, String, String, String,String)] = {
    var res = List[(String, String, String, String, String, String,String)]()
    while (iter.hasNext) {
      val cur0 = iter.next().toString()
      val cur = cur0.substring(1, cur0.length - 1).split(",")
      if (cur.length.equals(2)) {
        val cur1 = cur(0)
        val cur2_0 = cur(1).substring(1, cur(1).length - 2)
        val cur2_0_json = "{\""+cur2_0.substring(cur2_0.indexOf("|") - 1)
          .replace(";","\",\"")
          .replace(":","\":\"")+"\"}"
        val cur2 = cur2_0.split("[;]")
        if(cur2.length > 5){
          val cur2_1 = cur2(0).split(":")(1)
          val cur2_2 = cur2(1).split(":")(1).replace("DEVICE_","")
          val cur2_3 = cur2(2).split(":")(1)
          val cur2_4 = cur2(3).split(":")(1)
          val cur2_5 = cur2(4).split(":")(1)
          res.::=(cur1, cur2_1, cur2_2, cur2_3, cur2_4, cur2_5, cur2_0_json)
        }
      }
    }
    res.iterator
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
//        val dmp_TA = get_dmp1(cur2_0_json)
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

    def dmp_split2(iter: Iterator[(Any)]): Iterator[(String, String, String, String, String, String,String,String)] = {
      var res = List[(String, String, String, String, String, String, String, String)]()
      while (iter.hasNext) {
        val cur0 = iter.next().toString()
        val cur = cur0.substring(1, cur0.length - 1).split(",")
        if (cur.length.equals(2)) {
          val cur1 = cur(0)
          val cur2_0 = cur(1).substring(1, cur(1).length - 2)
          val cur2_0_json = cur2_0.substring(cur2_0.indexOf("|") - 1)

          val cur2 = cur2_0.split("[;]")
          if (cur2.length >= 5) {
            val cur2_1 = cur2(0).split(":")(1)
            val cur2_2 = cur2(1).split(":")(1).replace("DEVICE_", "")
            val cur2_3 = cur2(2).split(":")(1)
            val cur2_4 = cur2(3).split(":")(1)
            val cur2_5 = cur2(4).split(":")(1)
            if(cur2_1.equals("odx")){
              res.::=(cur1, cur2_1, cur2_2, cur2_3, cur2_4, cur2_5, cur2_0_json, "1")

            }
            else{
              val dmp_TA = get_dmp1(cur2_0_json)
              res.::=(cur1, cur2_1, cur2_2, cur2_3, cur2_4, cur2_5, cur2_0_json, dmp_TA)
            }

          }
        }
      }
      res.iterator
    }

  def dmp_split3(iter: Iterator[(Any)]): Iterator[(String, String, String, String, String, String,String,String)] = {
    var res = List[(String, String, String, String, String, String,String,String)]()
    while (iter.hasNext) {
      val cur0 = iter.next().toString()
      val cur = cur0.substring(1, cur0.length - 1).split(",")
      if (cur.length.equals(2)) {
        val cur1 = cur(0)
        val cur2_0 = cur(1).substring(1, cur(1).length - 2)
        val cur2_0_json = cur2_0.substring(cur2_0.indexOf("|") - 1)
        val dmp_TA = get_dmp1(cur2_0_json)
        val cur2 = cur2_0.split("[;]")
        if(cur2.length > 5 ){
          val cur2_1 = cur2(0).split(":")(1)
          val cur2_2 = cur2(1).split(":")(1).replace("DEVICE_","")
          val cur2_3 = cur2(2).split(":")(1)
          val cur2_4 = cur2(3).split(":")(1)
          val cur2_5 = cur2(4).split(":")(1)
          res.::=(cur1, cur2_1, cur2_2, cur2_3, cur2_4, cur2_5, cur2_0_json ,dmp_TA)
        }
      }
    }
    res.iterator
  }
}
