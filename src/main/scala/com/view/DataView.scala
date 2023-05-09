package com.view

import com.models.MonthProduction

object DataView {

  def showData(data: Seq[MonthProduction]): Unit = {
    data.foreach(record => println(s"Month: ${record.month}, Energy: ${record.energy}"))
  }

}
