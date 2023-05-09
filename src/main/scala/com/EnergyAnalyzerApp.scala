package com

import com.models.HourProduction
import com.services.{AlertSystem, EnergyAnalyzer}
import com.utils.DataStorage
import com.view.DataView

object EnergyAnalyzerApp extends App {
  val energyAnalyzer = new EnergyAnalyzer
  val alertSystem = new AlertSystem

  var running = true
  val hourlyData = DataStorage.readDataFromCSV("E:\\javaidea\\scala_project-master\\src\\main\\scala\\com\\energy_data.csv")

  while (running) {
    println("Welcome to the Renewable Energy System. Please select an option:")
    println("1. Check the status of energy sources")
    println("2. Save new hourly data")
    println("3. View energy data")
    println("4. Analyze energy data")
    println("5. Exit")

    val option = scala.io.StdIn.readInt()

    option match {
      case 1 =>
        // Assuming `hourlyData` is converted to a Seq[EnergySource]
        alertSystem.checkStatus(hourlyData)

      case 2 =>
        // Let the user input new data
        println("Please enter the new data in the format: timestamp,equipment_id,energy_type,energy_output,equipment_status")
        val inputData = scala.io.StdIn.readLine().split(",")
        val newHourlyData = HourProduction(inputData(0), inputData(1).toLong, inputData(2), inputData(3).toDouble, inputData(4))

        // Save data
        DataStorage.saveData(Seq(newHourlyData))
        println("Data saved successfully")

      case 3 =>
        // Read data
        val readData = DataStorage.readDataFromCSV("E:\\javaidea\\scala_project-master\\src\\main\\scala\\com\\energy_data.csv")

        // Analyze data
        val dailyData = energyAnalyzer.analyzeHourlyData(readData)
        val weeklyData = energyAnalyzer.analyzeDailyData(dailyData)
        val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData)

        // Show data
        DataView.showData(monthlyData)

      case 4 =>
        // Read data
        val readData = DataStorage.readDataFromCSV("E:\\javaidea\\scala_project-master\\src\\main\\scala\\com\\energy_data.csv")
        // Analyze data
        val dailyData = energyAnalyzer.analyzeHourlyData(readData)
        val weeklyData = energyAnalyzer.analyzeDailyData(dailyData)
        val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData)

        // Show data
        DataView.showData(monthlyData)

        // Analyze data
        println(s"Mean: ${energyAnalyzer.mean(monthlyData.map(_.energy))}")
        println(s"Median: ${energyAnalyzer.median(monthlyData.map(_.energy))}")
        println(s"Mode: ${energyAnalyzer.mode(monthlyData.map(_.energy))}")
        println(s"Range: ${energyAnalyzer.range(monthlyData.map(_.energy))}")
        println(s"Midrange: ${energyAnalyzer.midrange(monthlyData.map(_.energy))}")

      case 5 =>
        running = false

      case _ => println("Invalid option")
    }
  }
}
