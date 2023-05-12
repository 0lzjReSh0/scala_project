package com

import com.models.HourProduction
import com.models.DataViewer
import com.services.{AlertSystem, EnergyAnalyzer}
import com.utils.DataStorage
import com.view.DataView

object EnergyAnalyzerApp extends App {
  val energyAnalyzer = new EnergyAnalyzer
  val alertSystem = new AlertSystem


  var running = true
  val hourlyData = DataStorage.readDataFromCSV("src/main/scala/com/energy_data.csv")

  while (running) {
    println("Welcome to the Renewable Energy System. Please select an option:")
    println("1. Check the status of energy sources")
    println("2. Save new hourly data")
    println("3. View energy data")
    println("4. Analyze energy data")
    println("5. Show alternatively(view)")
    println("0. Exit")

    val option = scala.io.StdIn.readInt()

    option match {
      case 1 =>
        alertSystem.checkStatus(hourlyData)

      case 2 =>
        println("Please enter the new data in the format:\nequipment_id,timestamp,energy_type,energy_output,equipment_status")
        val inputString = scala.io.StdIn.readLine()
        try {
          val inputData = inputString.split(",")
          val newHourlyData = HourProduction(inputData(0), inputData(1).toLong, inputData(2), inputData(3).toDouble, inputData(4))
          DataStorage.saveData(Seq(newHourlyData))
          println("Data saved successfully")
        } catch {
          case t: Throwable => println("Invalid Input")
        }

      case 3 =>
        println("Enter file name")
        val fileName = scala.io.StdIn.readLine()
        try {
          val readData = DataStorage.readDataFromCSV(fileName)
          val dailyData = energyAnalyzer.analyzeHourlyData(readData, energyAnalyzer.analyze)
          val weeklyData = energyAnalyzer.analyzeDailyData(dailyData, energyAnalyzer.analyze)
          val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData, energyAnalyzer.analyze)
          DataView.showData(monthlyData)
        } catch {
          case t: Throwable => println("An error occurred. " + t.getMessage)
        }

      case 4 =>
        println("Enter file name")
        val fileName = scala.io.StdIn.readLine()
        try {
          val readData = DataStorage.readDataFromCSV(fileName)
          val dailyData = energyAnalyzer.analyzeHourlyData(readData, energyAnalyzer.analyze)
          val weeklyData = energyAnalyzer.analyzeDailyData(dailyData, energyAnalyzer.analyze)
          val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData, energyAnalyzer.analyze)
          DataView.showData(monthlyData)
          println(s"Mean: ${energyAnalyzer.mean(monthlyData.map(_.energy))}")
          println(s"Median: ${energyAnalyzer.median(monthlyData.map(_.energy))}")
          println(s"Mode: ${energyAnalyzer.mode(monthlyData.map(_.energy))}")
          println(s"Range: ${energyAnalyzer.range(monthlyData.map(_.energy))}")
          println(s"Midrange: ${energyAnalyzer.midrange(monthlyData.map(_.energy))}")
        } catch {
          case t: Throwable => println("An error occurred. " + t.getMessage)
        }

      case 5 =>
        println("Enter file path")
        val fileName = scala.io.StdIn.readLine()
        try {
          val dataCollection: Seq[DataViewer] = DataStorage.loadToViewer(fileName)
          DataView.showOption(dataCollection)
        } catch {
          case t: Throwable => println("An error occurred. " + t.getMessage)
        }

      case 0 =>
        running = false

      case _ => println("Invalid option")
    }
  }
}

