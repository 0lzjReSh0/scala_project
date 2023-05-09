package com

import com.models.{HourProduction, HydroPower, SolarPanel, WeekProduction, WindTurbine}
import com.services.{AlertSystem, EnergyAnalyzer}
import com.utils.DataStorage
import com.view.DataView

object EnergyAnalyzerApp extends App {
  val energyAnalyzer = new EnergyAnalyzer
  val alertSystem = new AlertSystem

  // Initialize some energy sources
  val sources = Seq(
    SolarPanel("SP1", 100.0, "operational"),
    WindTurbine("WT1", 200.0, "operational"),
    HydroPower("HP1", 300.0, "operational")
  )

  // Check the status of energy sources
  alertSystem.checkStatus(sources)

  val hourlyData = Seq(
    HourProduction(time = 1629500400000L, energy = 150),
    HourProduction(time = 1629504000000L, energy = 170),
    HourProduction(time = 1629540000000L, energy = 180),
    HourProduction(time = 1629504000000L, energy = 190),
    HourProduction(time = 1629500400000L, energy = 200),
    HourProduction(time = 1629504000000L, energy = 250),
  )

  // Save data
  DataStorage.saveData(hourlyData)

  // Read data
  val readData = DataStorage.readData()

  val dailyData = energyAnalyzer.analyzeHourlyData(readData)
  // println(dailyData)

  val weeklyData = energyAnalyzer.analyzeDailyData(dailyData)
  // println(weeklyData)

  val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData)
  // println(monthlyData)

  // Analyze data
  // println(s"Mean: ${energyAnalyzer.mean(hourlyData.map(_.energy))}")
  // println(s"Median: ${energyAnalyzer.median(hourlyData.map(_.energy))}")
  // println(s"Mode: ${energyAnalyzer.mode(hourlyData.map(_.energy))}")
  // println(s"Range: ${energyAnalyzer.range(hourlyData.map(_.energy))}")
  // println(s"Midrange: ${energyAnalyzer.midrange(hourlyData.map(_.energy))}")

  // Show data
  DataView.showData(monthlyData)


  val showDataOption = () => {
    println("Show Data\n1) Hourly data\n2) Daily data\n3) Weekly data\n4) Monthly data")
    val showOption = scala.io.StdIn.readLine()
    showOption match {
      case "1" => DataView.showData(hourlyData)
      case "2" => DataView.showData(dailyData)
      case "3" => DataView.showData(weeklyData)
      case "4" => DataView.showData(monthlyData)
      case _ => println("Invalid input")
    }
  }
  val analyzeDataOption = () => {
    println("Analyze Data\nNot yet done")
    val analyzeOption = scala.io.StdIn.readLine()
    analyzeOption match {
      case _: String => println("Not yet done")
    }
  }
  ////////// Main
  while(true) {
    println("\nAnalyzer User Menu\n1) Show data\n2) Analyze\n0) Quit\n")
    var option = scala.io.StdIn.readLine()
    option match {
      case "1" => {
        showDataOption()
      }
      case "2" => {
        // analyze
      }
      case "0" => {
        System.exit(0)
      }
      case _ => {
        println("Invalid input")
      }
    }
  }
  //////////
}
