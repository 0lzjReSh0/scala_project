package com.utils

import java.io.{File, PrintWriter}

import com.models.HourProduction
import scala.io.Source

object DataStorage {

  def saveData(data: Seq[HourProduction], filePath: String = "hourlyData.txt"): Unit = {
    val writer = new PrintWriter(new File(filePath))
    data.foreach{record => writer.write(s"${record.hour},${record.energy}\n")}
    writer.close()
  }

  def readData(filePath: String = "hourlyData.txt"): Seq[HourProduction] = {
    val bufferedSource = Source.fromFile(filePath)
    val data = bufferedSource.getLines().toList.map { line =>
      val Array(hour, energy) = line.split(",")
      HourProduction(hour.toLong, energy.toDouble)
    }
    bufferedSource.close()
    data
  }
}
