package com.ruozedata.bigdata.scala03
//注意导这个包
import scala.io.Source

object FileApp {
  def main(args: Array[String]): Unit = {
    //读取文件
    val file = Source.fromFile("f:/asd.txt")

    for(line <- file.getLines()){
      println(line)
    }

  }
}
