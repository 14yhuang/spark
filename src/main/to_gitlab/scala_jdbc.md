package com.ruozedata.bigdata.scala02
import java.time.ZonedDateTime

import scalikejdbc._
import scalikejdbc.config._


object scalalikejdbctask {
  //加载数据库配置，从src/main/resources/application.conf加载
  DBs.setup()
  //自动选择模式吧，全体指定，如果这里不写，要在每个sql的def里指定
  implicit val session = AutoSession

  //注意这里也是设置数据库的配置，和上面DBs方式2选一
//  val driverClass = "com.mysql.jdbc.Driver"
//  val jdbcUrl = "jdbc:mysql://192.168.137.190:3306/ruozedb";
//  val username = "ruoze";
//  val pwd = "123456";
//  Class.forName(driverClass)
//  ConnectionPool.singleton(jdbcUrl, username, pwd)

  def main(args: Array[String]): Unit = {


        createTable("asd")
    //    insertable(12,"14yhuang")
    //    insertable(1,2,3)
    //      updatable(3,"ert")
    //      deletetable(id = 3)

    println(selectable2())
    //droptable

  }

  //这个注意给表名传参的话，string类型带"",相当于创建表时是"table",报错
  //所以要用replace去替代掉",注意直接在sql语句后面跟replace会找不到这个方法
  //但是注意小写sql会出这个问题，但是大写SQL不会
  def createTable(af:String): Unit = {

//    var sqlcreate =
//      """
//        CREATE TABLE IF NOT EXISTS ${af}(
//                          id int PRIMARY KEY NOT NULL auto_increment,
//                          name varchar(64),
//                          created_time timestamp not null DEFAULT current_timestamp
//                       )ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
//      """.stripMargin.replace("\"","")
//
//
//
//    sql"${sqlcreate}".execute.apply()

    SQL("""
      CREATE TABLE IF NOT EXISTS ${af}(
      id int PRIMARY KEY NOT NULL auto_increment,
      name varchar(64),
      created_time timestamp not null DEFAULT current_timestamp
      )ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1""").execute().apply()

// 错误事例
//    sql"""
//         CREATE TABLE IF NOT EXISTS (?)(
//                  id int PRIMARY KEY NOT NULL auto_increment,
//                  name varchar(64),
//                  created_time timestamp not null DEFAULT current_timestamp
//               )ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
//      """.bind(af).execute.apply()
  }

  def insertable(id: Int, name: String) = {
    sql"insert into asd (id,name) values(${id},${name})".update().apply()
  }

  def insertable(id: Int*) = {
    id.foreach(
      id => sql"insert into asd (id) values (${id})".update().apply()
    )
  }

  //sql里面不能有""，会报错
  def updatable(id: Int, name: String) = {
    sql"update asd set name=${name} where id =${id}".execute().apply()
  }

  //研究发现execute和update没什么区别,execute返回时bool，update是int
  def deletetable(id: Int) = {
    sql"delete from asd where id=${id}".update().apply()
  }

  //这个sql后面不跟东西，返回的是个迭代器
  def selectable() = {
    //调用case class
    sql"select * from asd".map(rs => toselect(rs.int("id"), rs.string("name"), rs.string("created_time"))).list().apply()

  }

  //注意这里因为只要一个字段，而class是3个字段，所以不写
  def selectable2() = {
    sql"select id from asd".map(rs => rs.int("id")).list().apply()

  }

  def selectable3() = {
//    bind也可以指定前面？的值，single如果返回的是多行数据，那么会抛异常报错
//    这种相当于先用object，自动调用apply方法，里面的操作和上面的方式一样的
    sql"select * from asd where id=(?)".bind(12).map((toselect(_))).list().single().apply()
  }

//相当于模板
  case class toselect(id: Int, name: String, create_time: String)

  object toselect extends SQLSyntaxSupport[toselect] {
    override val tableName = "toselect"

    def apply(rs: WrappedResultSet) = {
      new toselect(
        rs.int("id"), rs.string("name"), rs.string("created_time")
      )

    }

  }

  def droptable={
    sql"drop table if exists asd".update().apply()
  }

}




