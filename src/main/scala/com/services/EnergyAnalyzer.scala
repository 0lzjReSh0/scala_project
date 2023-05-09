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
}