package units

import java.text.SimpleDateFormat
import java.util.Locale

object get_timeStamp {
  def get_time(str: String): String = {
    val loc = new Locale("en")
    val fm = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss",loc)
    val dt2 = fm.parse(str)
    dt2.getTime().toString.substring(0,10)
  }

  def main(args: Array[String]): Unit = {
    println("[23/May/2018:16:00:00 +0800]".substring(1,21))
    println(get_time("[23/May/2018:16:00:00 +0800]".substring(1,21)))
  }
}
