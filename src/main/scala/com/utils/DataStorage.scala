package com.utils

import com.models.HourProduction
import java.io.{File, PrintWriter}
import scala.io.Source

object DataStorage {
  def readDataFromCSV(filePath: String): Seq[HourProduction] = {
    val bufferedSource = Source.fromFile(filePath)
    val data = (for (line <- bufferedSource.getLines.drop(1)) yield {
      val cols = line.split(",").map(_.trim)
      HourProduction(cols(0), cols(1).toLong, cols(2), cols(3).toDouble, cols(4))
    }).toList
    bufferedSource.close()
    data
  }


  def saveData(data: Seq[HourProduction], filePath: String = "hourlyData.csv"): Unit = {
    val writer = new PrintWriter(new File(filePath))
    data.foreach { record =>
      writer.write(s"${record.hour},${record.equipment_id},${record.energy_type},${record.energy},${record.equipment_status}\n")
    }
    writer.close()
  }


}
