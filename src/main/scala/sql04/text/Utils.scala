package sql04.text

import org.apache.spark.sql.types.{DataType, LongType, StringType}

object Utils {
  def castTo(value:String,dataType: DataType)={
    dataType match {
        // case LongType => value.toLong 一样
      case _:LongType => value.toLong
      case _:StringType => value
    }
  }

}
