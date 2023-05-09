package com.utils

object DateUtils {
  def dayFromDateTimestamp(timestamp: Long): Int = (timestamp / (60 * 60 * 1000 * 24)).toInt
  def weekFromDateTimestamp(day: Int): Int = day / 7
  def monthFromWeekTimestamp(week: Int): Int = week / 4

}
