package com.ruozedata.bigdata.scala03

import java.io.File

object ImplicitAspect {
  implicit def man2superman(man:Man):Superman = new Superman(man.name,man.name)
  implicit def file2RichFile(file:File):RichFile = new RichFile(file)
}
