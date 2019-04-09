package com.ruozedata.bigdata.scala02

//key-value
object MapApp {
  def main(args: Array[String]): Unit = {
    //不可变的
    val  a =Map("name"->"ruoze","age"->30)
    println(a("name"),a("age"))

    import scala.collection.mutable.HashMap
    val b =HashMap("name"->"ruoze","age"->30)
    b("age")=19

//如果得不到，就报后面那个字符串
    b.getOrElse("gender","unknown")

    b += ("asd"->"dsa")
    println(b)

    for ((key,value)<- b){
      println(key + ":" + value)
    }
//存在就得到key对饮的value，得不到就0
    for ((key,_) <- b){
      println(key +":"+b.getOrElse(key,0))
    }
//b.keySet 为 key的集合
    for (ele <- b.keySet){
      println(ele +":"+ b.getOrElse(ele,0))
    }
    println(b.keySet)

    for (ele <- b.values){
      println(ele)
    }

  }




}
