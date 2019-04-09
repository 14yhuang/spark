package com.ruozedata.bigdata.scala02

object Applyapp {
  def main(args: Array[String]): Unit = {
    //c就相当于new的ApplyTest
    val c= ApplyTest()
    //class ApplyTest的apply
    c()
  }
}

//伴生对象和类，object可以直接调用，class要new
class ApplyTest{
  def test()={
    println("class Applytest enter")
  }
  def apply()={
    println("class apply")
    println(ApplyTest.inc)
  }
}

object ApplyTest{
  var count=0
  private def inc={
    count=count+1
    count
  }

  def apply()={
    println("object apply")
    new ApplyTest
  }
}