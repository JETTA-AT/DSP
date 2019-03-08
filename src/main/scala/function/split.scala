package function

import units.get_timeStamp.get_time
import units.get_base64.get_base64

object split {
  def url_split(iter: Iterator[(String, String, String, String, String, String, String, String, String)]): Iterator[(String, String, String, String, String, String, String, String, String, String, String, String)] = {
    var res = List[(String, String, String, String, String, String, String, String, String, String, String, String)]()
    var cur4_1 = "";var cur4_2 = "";var media_id = "";var base64_encode = ""
    while (iter.hasNext){
      val cur0 = iter.next().toString()
      val cur = cur0.substring(1,cur0.length-1).split(",")
      if(cur.length == 9) {
        val cur1 = cur(0).substring(1,21)
        val new_ts = get_time(cur1)

        val cur2 = cur(1)
        val cur3 = cur(2)
        val cur4 = cur(3)
        if(cur4.split("[?]").length.equals(2)){
          cur4_1 = cur4.split("[?]")(0)
          cur4_2 = cur4.split("[?]")(1)
        }
        val cur5 = cur(4)
        val cur6 = cur(5)
        val cur7 = cur(6)
        val cur8 = cur(7)
        val cur9 = cur(8)
        val iter2 = cur4_2.split("[&]").toIterator
        while (iter2.hasNext) {
          val spilt_str = iter2.next().split("[=]")
          if (spilt_str.length >= 2) {
            if(spilt_str(0).equals("sp"))
              media_id = spilt_str(1)
            if (spilt_str(0).equals("ext")) {
              base64_encode = spilt_str(1)
              if (base64_encode.length > 4 && !base64_encode.contains("\\")) {
                val base64_str = get_base64(base64_encode)
                val cur4_2_0 = base64_str.split("[,]")
                if (cur4_2_0.length.equals(16)) {
                  res.::=(cur4_2_0(7), cur3, cur4_1, media_id, cur4_2_0(2), cur4_2_0(6), new_ts, cur5, cur6, cur7, cur8, cur9)
                }
              }
            }
          }
        }
      }
    }
    res.iterator
  }

}
