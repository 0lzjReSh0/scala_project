package com.view

import com.models.MonthProduction
import scala.io.Source
import java.text.SimpleDateFormat
import com.models.DataViewer
import com.models.RecordFilter
import java.io.PrintWriter
import java.io.File
import com.utils.DataStorage
import com.models.HourProduction
import java.time.LocalDate
import java.time.Month
object DataView {



  def showData(data: Seq[MonthProduction]): Unit = {
    if (data.isEmpty) {
      println("No data to show.")
      return
    }

    println("Showing total energy production for each month:")

    data.foreach(record => {
      val month = LocalDate.of(1970, Month.JANUARY, 1).plusMonths(record.month).getMonth
      println(s" - ${month}: ${record.energy} units")
    })

    val totalEnergy = data.map(_.energy).sum
    val avgEnergy = totalEnergy / data.length
    val minEnergy = data.map(_.energy).min
    val maxEnergy = data.map(_.energy).max

    println(s"\nSummary:")
    println(s" - Total energy: $totalEnergy units")
    println(s" - Average energy per month: $avgEnergy units")
    println(s" - Minimum energy in a month: $minEnergy units")
    println(s" - Maximum energy in a month: $maxEnergy units")
  }


  def showViewerData(data: Seq[DataViewer]): Unit = {
    data.foreach(record => println(s"Equipment ID: ${record.equipment}, Hour: ${record.datetime}, Energy Type: ${record.energy_type}, Energy: ${record.energy}, Equipment Status: ${record.status}"))
  }

  val showOption: Seq[DataViewer] => Unit = (records: Seq[DataViewer]) => {
    println("\nWelcome to DataViewer\n1. show all records\n2. filter by month\n3. filter by day\n4. filter by hour\n5. add a record\n6. remove a record\n0. exit viewer")
    val innerOption = scala.io.StdIn.readInt()
    innerOption match {
      case 1 => 
        showAllRecords(records)
        showOption(records)
      case 2 => 
        println("Enter month to show")
        val range = scala.io.StdIn.readLine()
        recordsFilter(records, new RecordFilter('M', range))
        showOption(records)
      case 3 => 
        println("Enter day to show")
        val range = scala.io.StdIn.readLine()
        recordsFilter(records, new RecordFilter('D', range))
        showOption(records)
      case 4 => 
        println("Enter hour to show")
        val range = scala.io.StdIn.readLine()
        recordsFilter(records, new RecordFilter('H', range))
        showOption(records)
      case 5 =>
        println("Enter equipment id, datetime, energy type, energy amount and status (separated by commas)")
        val recordData = scala.io.StdIn.readLine().split(",")
        val newRecord = new DataViewer(recordData(0), recordData(1), recordData(2), recordData(3).toDouble, recordData(4) == "operational")
        val updatedRecords = addRecord(records, newRecord)
        DataStorage.saveToViewer(updatedRecords, "src/main/scala/com/energy_data_time.csv") // replace "your_file_path.csv" with your actual file path
        showOption(updatedRecords)
      case 6 =>
        println("Enter equipment id and datetime of the record you want to remove")
        val recordData = scala.io.StdIn.readLine().split(",")
        val updatedRecords = removeRecord(records, recordData(0), recordData(1))
        DataStorage.saveToViewer(updatedRecords, "src/main/scala/com/energy_data_time.csv") // replace "your_file_path.csv" with your actual file path
        showOption(updatedRecords)

      case 0 => 
        println("bye~~")
      case _ => 
        println("Invalid input")
        showOption(records)
    }
  }
  def addRecord(records: Seq[DataViewer], record: DataViewer): Seq[DataViewer] = {
    records :+ record
  }

  def removeRecord(records: Seq[DataViewer], equipment: String, datetime: String): Seq[DataViewer] = {
    records.filterNot(record => record.equipment == equipment && record.datetime == datetime)
  }

  val printOneRecord: DataViewer => Unit = (e: DataViewer) => {
    var status = ""
    if(e.status) status = "operational" else status = "inoperational"
    println(s"|${e.equipment}|${e.datetime}|${e.energy_type}|${e.energy}|${status}|")
  }

  def showAllRecords(records: Seq[DataViewer]): Unit = {
    println("\n|Equipment ID|DateTime|Energy Type|Energy Amount|Status|")
    records.foreach(e => printOneRecord(e))
    println()
  }

  def saveToViewer(records: Seq[DataViewer], filePath: String): Unit = {
    val writer = new PrintWriter(new File(filePath))
    writer.write("equipment_id,hour,energy_type,energy,equipment_status\n") // write header
    records.foreach { record =>
      val status = if (record.status) "operational" else "inoperational"
      writer.write(s"${record.equipment},${record.datetime},${record.energy_type},${record.energy},${status}\n")
    }
    writer.close()
  }

  def showDataByEnergyType(data: Seq[HourProduction]): Unit = {
    data.groupBy(_.energy_type).foreach { case (energyType, records) =>
      println(s"Energy type: $energyType")
      records.foreach(record => println(s"Hour: ${record.hour}, Energy: ${record.energy}"))
    }
  }

  def showSortedData(data: Seq[HourProduction]): Unit = {
    val sortedData = data.sortWith(_.energy < _.energy)
    sortedData.foreach(record => println(s"Hour: ${record.hour}, Energy: ${record.energy}"))
  }


  def showSearchResults(data: Seq[HourProduction], keyword: String): Unit = {
    val results = data.filter(d => d.energy_type.contains(keyword) || d.equipment_id.contains(keyword))
    results.foreach(record => println(s"Hour: ${record.hour}, Energy: ${record.energy}"))
  }

  def recordsFilter(records: Seq[DataViewer],filter: RecordFilter): Unit = {
    println("\n|Equipment ID|DateTime|Energy Type|Energy Amount|Status|")
    filter.filterBy match {
      case 'M' =>
        records.filter(_.datetime.substring(5, 7) == filter.range).foreach(e => printOneRecord(e))
      case 'D' => 
        records.filter(_.datetime.substring(8, 10) == filter.range).foreach(e => printOneRecord(e))
      case 'H' => 
        records.filter(_.datetime.substring(11, 13) == filter.range).foreach(e => printOneRecord(e))
    }
  }

}
