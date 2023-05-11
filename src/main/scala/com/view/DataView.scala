package com.view

import com.models.MonthProduction
import scala.io.Source
import java.text.SimpleDateFormat

object DataView {

  def showData(data: Seq[MonthProduction]): Unit = {
    data.foreach(record => println(s"Month: ${record.month}, Energy: ${record.energy}"))
  }

  def showAlternatively(fileName: String): Unit = {
    val bufferedSource = Source.fromFile("src/main/scala/com/energy_data.csv")
    println("|Equipment ID|DateTime|Energy Type|Energy Amount|Status|")
    bufferedSource.getLines().drop(1).foreach(e => {
      var line = e.split(",")
      var datetime = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(line(1).toLong)
      println(s"|${line(0)}|${datetime}|${line(2)}|${line(3)}|${line(4)}|")
      
    })
  }

}
