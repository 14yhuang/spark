package com.rz.date.scala04

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataInputStream, FileSystem, Path}
import org.apache.hadoop.io.IOUtils


object HDFSApp1 {
  def main(args: Array[String]): Unit = {

    val configuration = new Configuration
    val uri = new URI("hdfs://192.168.137.190:9000" )
    val fileSystem = FileSystem.get(uri,configuration,"hadoop")

    val in:FSDataInputStream = fileSystem.open(new Path("/g5/asd.txt"))
    //把文件读到控制台
    IOUtils.copyBytes(in,System.out,1024)

    val flag = fileSystem.delete(new Path("/g5/test"),true)
    println(flag)
  }
}
