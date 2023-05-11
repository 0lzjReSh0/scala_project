package com

import com.models.HourProduction
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
    println("5. Show alternatively")
    println("0. Exit")

    val option = scala.io.StdIn.readInt()

    option match {
      case 1 =>
        // Assuming `hourlyData` is converted to a Seq[EnergySource]
        alertSystem.checkStatus(hourlyData)

      case 2 =>
        // Let the user input new data
        println("Please enter the new data in the format:\ntimestamp,equipment_id,energy_type,energy_output,equipment_status")
        val inputString = scala.io.StdIn.readLine()
        try {
          val inputData = inputString.split(",")
          val newHourlyData = HourProduction(inputData(0), inputData(1).toLong, inputData(2), inputData(3).toDouble, inputData(4))
          // Save data
          DataStorage.saveData(Seq(newHourlyData))
          println("Data saved successfully")
        } catch {
          case t: Throwable => println("Invalid Input") 
        }
      case 3 =>
        println("Enter file name")
        val fileName = scala.io.StdIn.readLine()
        // Read data
        try {
          val readData = DataStorage.readDataFromCSV(fileName)
  
          // Analyze data
          val dailyData = energyAnalyzer.analyzeHourlyData(readData, energyAnalyzer.analyze)
          val weeklyData = energyAnalyzer.analyzeDailyData(dailyData, energyAnalyzer.analyze)
          val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData, energyAnalyzer.analyze)
  
          // Show data
          DataView.showData(monthlyData)
        } catch {
          case t: Throwable => println(s"${t}") 
        }

      case 4 =>
        println("Enter file name")
        val fileName = scala.io.StdIn.readLine()
        // Read data
        try {
          val readData = DataStorage.readDataFromCSV(fileName)
  
          // Analyze data
          val dailyData = energyAnalyzer.analyzeHourlyData(readData, energyAnalyzer.analyze)
          val weeklyData = energyAnalyzer.analyzeDailyData(dailyData, energyAnalyzer.analyze)
          val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData, energyAnalyzer.analyze)
  
          // Show data
          DataView.showData(monthlyData)
  
          // Analyze data
          println(s"Mean: ${energyAnalyzer.mean(monthlyData.map(_.energy))}")
          println(s"Median: ${energyAnalyzer.median(monthlyData.map(_.energy))}")
          println(s"Mode: ${energyAnalyzer.mode(monthlyData.map(_.energy))}")
          println(s"Range: ${energyAnalyzer.range(monthlyData.map(_.energy))}")
          println(s"Midrange: ${energyAnalyzer.midrange(monthlyData.map(_.energy))}")
        } catch {
          case t: Throwable => println(s"${t}") 
        }
      
      case 5 =>
        println("Enter file name")
        val fileName = scala.io.StdIn.readLine()
        DataView.showAlternatively(fileName)

      case 0 =>
        running = false

      case _ => println("Invalid option")
    }
  }
}
