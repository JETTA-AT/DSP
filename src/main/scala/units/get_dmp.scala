package units

object get_dmp {
  //  1|566:1;1|567:0;1|11265:0;1|20746:0;1|565:0;1|568:0;1|564:0
  def get_dmp1(iter: String):String = {
    var aa = "0"
    val cur = iter.split(";").toIterator
    while(cur.hasNext){
      val cur0 = cur.next()
      val cur1 = cur0.split(":")
      if(cur1.last.equals("1"))
        aa = "1"
      else if(aa.equals("0") && cur1.last.equals("2"))
        aa = "2"
      else if(!aa.equals("1") && !aa.equals("2") && cur1.last.equals("3"))
        aa = "3"
    }
    aa
  }

  def main(args: Array[String]): Unit = {
    val aa = "1|568:0;1|566:1;1|567:0;1|11265:0;1|20746:0;1|565:3;1|564:2"
    println(get_dmp1(aa))
  }
}
