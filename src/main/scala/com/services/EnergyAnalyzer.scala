package com.services
import com.models._
import com.utils.DateUtils

class EnergyAnalyzer {


def analyzeViewerData(data: Seq[DataViewer]): Seq[DataViewer] = {
  // Your code to analyze the data
  // This is just an example, you will need to replace it with your own code
  data.map(record => record.copy(energy = record.energy * 2))
}

  def analyze[T <: EnergySource, R <: EnergySource](
    data: Seq[T],
    groupFunc: T => Int,
    produceFunc: (Int, Seq[T]) => R
  ): Seq[R] = {
    data.groupBy(groupFunc)
      .map { case (key, values) => produceFunc(key, values) }
      .toSeq
  }

  def analyzeHourlyData(
    hourlyData: Seq[HourProduction],
    analyzeFunc: (Seq[HourProduction], HourProduction => Int, (Int, Seq[HourProduction]) => DailyProduction) => Seq[DailyProduction]
  ): Seq[DailyProduction] = {
    analyzeFunc(
      hourlyData,
      hp => DateUtils.dayFromDateTimestamp(hp.hour),
      (day, data) => DailyProduction(day, data.map(_.energy).sum)
    )
  }

  def analyzeDailyData(
    dailyData: Seq[DailyProduction],
    analyzeFunc: (Seq[DailyProduction], DailyProduction => Int, (Int, Seq[DailyProduction]) => WeekProduction) => Seq[WeekProduction]
  ): Seq[WeekProduction] = {
    analyzeFunc(
      dailyData,
      dp => DateUtils.weekFromDateTimestamp(dp.day.toInt),
      (week, data) => WeekProduction(week, data.map(_.energy).sum)
    )
  }

  def analyzeWeeklyData(
    weeklyData: Seq[WeekProduction],
    analyzeFunc: (Seq[WeekProduction], WeekProduction => Int, (Int, Seq[WeekProduction]) => MonthProduction) => Seq[MonthProduction]
  ): Seq[MonthProduction] = {
    analyzeFunc(
      weeklyData,
      wp => DateUtils.monthFromWeekTimestamp(wp.week.toInt),
      (month, data) => MonthProduction(month, data.map(_.energy).sum)
    )
  }
  def filterByEnergyType(data: Seq[HourProduction], energyType: String): Seq[HourProduction] = {
    data.filter(_.energy_type == energyType)
  }

  def sortByEnergy(data: Seq[HourProduction]): Seq[HourProduction] = {
    data.sortBy(_.energy)
  }

  def searchData(data: Seq[HourProduction], keyword: String): Seq[HourProduction] = {
    data.filter(d => d.energy_type.contains(keyword) || d.equipment_id.contains(keyword))
  }
  def mean(data: Seq[Double]): Double = data.sum / data.length

  def median(data: Seq[Double]): Double = {
    val sorted = data.sorted
    if (data.length % 2 == 0) (sorted(data.length / 2 - 1) + sorted(data.length / 2)) / 2.0
    else sorted(data.length / 2)
  }
  def sortDataByEnergyOutput(data: Seq[HourProduction]): Seq[HourProduction] = {
    data.sortBy(_.energy)
  }

  def mode(data: Seq[Double]): Double = data.groupBy(identity).maxBy(_._2.size)._1

  def range(data: Seq[Double]): Double = data.max - data.min

  def midrange(data: Seq[Double]): Double = (data.max + data.min) / 2.0
}
