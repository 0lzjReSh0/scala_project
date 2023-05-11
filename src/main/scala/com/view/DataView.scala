package com.view

import com.models.MonthProduction
import scala.io.Source
import java.text.SimpleDateFormat
import com.models.DataViewer
import com.models.RecordFilter

object DataView {
  def showData(data: Seq[MonthProduction]): Unit = {
    data.foreach(record => println(s"Month: ${record.month}, Energy: ${record.energy}"))
  }

  val showOption: Seq[DataViewer] => Unit = (records: Seq[DataViewer]) => {
    println("\nWelcome to DataViewer\n1. show all records\n2. filter by month\n3. filter by day\n4. filter by hour\n0. exit viewer")
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
      case 0 => 
        println("bye~~")
      case _ => 
        println("Invalid input")
        showOption(records)
    }
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
