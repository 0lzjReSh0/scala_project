package com.utils

import com.models.HourProduction

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
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

  def saveData(data: Seq[HourProduction], filePath: String = "src/main/scala/com/energy_data.csv"): Unit = {
    val writer = new FileWriter(new File(filePath), true)
    data.foreach { record =>
      writer.write(s"${record.equipment_id},${record.hour},${record.energy_type},${record.energy},${record.equipment_status}\n")
    }
    writer.close()
  }

  def saveToViewer(data: Seq[DataViewer], filePath: String): Unit = {
    val writer = new PrintWriter(new File(filePath))
    // Write the header
    writer.write("equipment_id,datetime,energy_type,energy_amount,status\n")
    // Write the data
    data.foreach { record =>
      val status = if(record.status) "operational" else "inoperational"
      writer.write(s"${record.equipment},${record.datetime},${record.energy_type},${record.energy},${status}\n")
    }
    writer.close()
  }

  def loadToViewer(filePath: String): Seq[DataViewer] = {
    val bufferedSource = Source.fromFile(filePath)
    val writer = new BufferedWriter(new FileWriter("src/main/scala/com/energy_data_time.csv"))

    // write header
    writer.write("equipment_id,timestamp,energy_type,energy_output,equipment_status\n")

    val data = bufferedSource.getLines().drop(1).map(e => {
      println(s"Reading line: $e") // debug line
      val line = e.split(",")
      val status = if (line(4) == "operational") true else false
      val viewer = new DataViewer(line(0), new SimpleDateFormat("YYYY-MM-dd HH:mm").format(line(1).toLong), line(2), line(3).toDouble, status)

      // write data
      writer.write(s"${viewer.equipment},${viewer.datetime},${viewer.energy_type},${viewer.energy},${if (viewer.status) "operational" else "non-operational"}\n")

      println(s"Created DataViewer: $viewer") // debug line
      viewer
    }).toSeq

    bufferedSource.close()
    writer.close() // make sure to close the writer after you're done

    data
  }


}
