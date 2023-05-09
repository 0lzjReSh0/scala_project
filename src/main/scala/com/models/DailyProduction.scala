package com.models
import com.models.Production

final case class DailyProduction(val time: Long, val energy: Double) extends Production
