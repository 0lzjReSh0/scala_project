package com.services
import com.models._
import com.utils.DateUtils

class EnergyAnalyzer {
  def analyzeHourlyData(hourlyData: Seq[HourProduction]): Seq[DailyProduction] = {
    hourlyData.groupBy(hp => DateUtils.dayFromDateTimestamp(hp.hour))
      .map { case (day, data) => DailyProduction(day, data.map(_.energy).sum) }
      .toSeq
  }

  def analyzeDailyData(dailyData: Seq[DailyProduction]): Seq[WeekProduction] = {
    dailyData.groupBy(dp => DateUtils.weekFromDateTimestamp(dp.day.toInt))
      .map { case (week, data) => WeekProduction(week, data.map(_.energy).sum) }
      .toSeq
  }

  def analyzeWeeklyData(weeklyData: Seq[WeekProduction]): Seq[MonthProduction] = {
    weeklyData.groupBy(wp => DateUtils.monthFromWeekTimestamp(wp.week.toInt))
      .map { case (month, data) => MonthProduction(month, data.map(_.energy).sum) }
      .toSeq
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
