package com.utils

import java.text.SimpleDateFormat

object DateUtils {
  def dayFromDateTimestamp(timestamp: Long): Int = (timestamp / (60 * 60 * 1000 * 24)).toInt
  def weekFromDateTimestamp(day: Int): Int = day / 7
  def monthFromWeekTimestamp(week: Int): Int = week / 4
  def unixTimestamp(timestamp: String): String = {
    new SimpleDateFormat("YYYY-MM-dd HH:mm").format(timestamp.toLong)
  }
}
