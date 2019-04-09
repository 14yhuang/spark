package com.ruozedata.bigdata.sql05;

import org.apache.hadoop.hive.ql.exec.UDF;

//自定义hiveUDF
public class ruozeUDF extends UDF{

    public String evaluate(String name){
        return "ruozedata:" + name;
    }
// 静态方法是类方法，调用时不需要创建类实例。

    /**
     * 一般使用频繁的方法用静态方法，用的少的方法用动态的。静态的速度快，占内存。动态的速度相对慢些，但调用完后，立即释放类，可以节省内存，可以根据自己的需要选择是用动态方法还是静态方法。
     * @param args
     */
  public static void main(String[] args) {
        ruozeUDF udf = new ruozeUDF();
        System.out.println(udf.evaluate("doudou"));
  }
}
