package com.services
import com.models._
import com.utils.DateUtils

class EnergyAnalyzer {

  /* following code are  modified to high order function */
  /* switch to original version if wont work  */
//   def analyzeHourlyData(hourlyData: Seq[HourProduction]): Seq[DailyProduction] = {
//     hourlyData.groupBy(hp => DateUtils.dayFromDateTimestamp(hp.hour))
//       .map { case (day, data) => DailyProduction(day, data.map(_.energy).sum) }
//       .toSeq
//   }

//   def analyzeDailyData(dailyData: Seq[DailyProduction]): Seq[WeekProduction] = {
//     dailyData.groupBy(dp => DateUtils.weekFromDateTimestamp(dp.day.toInt))
//       .map { case (week, data) => WeekProduction(week, data.map(_.energy).sum) }
//       .toSeq
//   }

//   def analyzeWeeklyData(weeklyData: Seq[WeekProduction]): Seq[MonthProduction] = {
//     weeklyData.groupBy(wp => DateUtils.monthFromWeekTimestamp(wp.week.toInt))
//       .map { case (month, data) => MonthProduction(month, data.map(_.energy).sum) }
//       .toSeq
//   }

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
