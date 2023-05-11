package com.utils

import com.models.HourProduction
import java.io.{File, PrintWriter}
import scala.io.Source
import com.models.DataViewer
import java.text.SimpleDateFormat

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

  def loadToViewer(filePath: String): Seq[DataViewer] = {
    val bufferedSource = Source.fromFile(filePath)
    bufferedSource.getLines().drop(1).map(e => {
      val line = e.split(",")
      val status = if(line(4) == "operational") true else false
      new DataViewer(line(0), new SimpleDateFormat("YYYY-MM-dd HH:mm").format(line(1).toLong), line(2), line(3).toInt, status)
    }).toSeq
  }

}
