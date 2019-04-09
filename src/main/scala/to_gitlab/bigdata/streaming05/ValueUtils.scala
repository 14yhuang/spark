package com.ruozedata.bigdata.streaming05

import com.typesafe.config.ConfigFactory



//用来读取application.conf的其他参数
object ValueUtils {
  //加载application.conf
  val load=ConfigFactory.load()

  def getStringValue(key:String,defaultValue:String="")={
    load.getString(key)
  }

  def main(args: Array[String]): Unit = {
    println(getStringValue("kafka.topics"))
  }

}
