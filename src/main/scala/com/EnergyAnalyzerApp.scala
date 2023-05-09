package com

import com.models.{HourProduction, WeekProduction}
import com.services.EnergyAnalyzer

object EnergyAnalyzerApp extends App {
  val energyAnalyzer = new EnergyAnalyzer

  val hourlyData = Seq(
    HourProduction(hour = 1629500400000L, energy = 150),
    HourProduction(hour = 1629504000000L, energy = 170),
    HourProduction(hour = 1629540000000L, energy = 180),
    HourProduction(hour = 1629504000000L, energy = 190),
    HourProduction(hour = 1629500400000L, energy = 200),
    HourProduction(hour = 1629504000000L, energy = 250),

  )


  val dailyData = energyAnalyzer.analyzeHourlyData(hourlyData)
  println(dailyData)

  val weeklyData = energyAnalyzer.analyzeDailyData(dailyData)
  println(weeklyData)

  val monthlyData = energyAnalyzer.analyzeWeeklyData(weeklyData)
  println(monthlyData)
}