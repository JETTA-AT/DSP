package test

object dspc_split {
  def main(args: Array[String]): Unit = {
    val aa = "[23/May/2018:16:00:00 +0800]##171.210.46.167##GET /dspc.htm?sp=17&ext=MCwxMDYxMzE3MCwxMDYxMzE3MDAxLDEwMDAwMDAwMDAzODEsYmQ2YjBkYzhiZWYwNzI1MTdhMTlhZTM0YzFjZDMyZDcsLTMsREVWSUNFXzdkYjYwMDJiZDU1NTU5ZDAyYmE2ODA3N2NjYjMwYTAxLDE3MS4yMTAuNDYuMTY3LDE1MjcwNjIzMTQsMCwsLDIsMTI4MHg3MjAsNCxpcWl5aS5jb20=&target=https://pro.m.jd.com/mall/active/bRBwtvXfQ7iPa2WL2xqz1moubT6/index.html HTTP/1.1##-##-##Mozilla/5.0 (Linux; Android 7.0; SLA-AL00 Build/HUAWEISLA-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36 IqiyiApp/iqiyi IqiyiVersion/9.2.1##-##302"
    val bb = aa.split("##")(2)
    val cc = bb.split(" ")
    val dd = cc(1).split("[?]")(0).substring(1)
    println(dd)

  }

}
