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
    HourProduction(hour = 1629500400000L, energy = 150),
    HourProduction(hour = 1629504000000L, energy = 170),
    HourProduction(hour = 1629540000000L, energy = 180),
    HourProduction(hour = 1629504000000L, energy = 190),
    HourProduction(hour = 1629500400000L, energy = 200),
    HourProduction(hour = 1629504000000L, energy = 250),
  )

  // Save data
  DataStorage.saveData(hourlyData)

  // Read data
  val readData = DataStorage.readData()

  val dailyData = energyAnalyzer.analyzeHourlyData(readData)
  println(dailyData)

  val weeklyData = energyAnalyzer.analyzeDailyData(dailyData)
  println(weeklyData)

  val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData)
  println(monthlyData)

  // Analyze data
  println(s"Mean: ${energyAnalyzer.mean(hourlyData.map(_.energy))}")
  println(s"Median: ${energyAnalyzer.median(hourlyData.map(_.energy))}")
  println(s"Mode: ${energyAnalyzer.mode(hourlyData.map(_.energy))}")
  println(s"Range: ${energyAnalyzer.range(hourlyData.map(_.energy))}")
  println(s"Midrange: ${energyAnalyzer.midrange(hourlyData.map(_.energy))}")

  // Show data
  DataView.showData(monthlyData)
}
