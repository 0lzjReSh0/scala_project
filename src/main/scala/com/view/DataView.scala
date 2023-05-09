package com.view

import com.models.Production
import com.models.MonthProduction
import com.models.WeekProduction

object DataView {

  def showData(data: Seq[Production]): Unit = {
    data.foreach(record => println(s"Time: ${record.time}, Energy: ${record.energy}"))
  }
}
