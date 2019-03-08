package units

import java.util.Base64

object get_base64 {
  def get_base64(string: String): String ={
    val decoder = Base64.getUrlDecoder
    try {
      new String(decoder.decode(string))
    }
    catch {
      case ex: IllegalArgumentException => "decoder_error"
    }
  }
}
