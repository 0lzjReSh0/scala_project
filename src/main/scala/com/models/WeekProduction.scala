package com.models

final case class WeekProduction(week:Long, energy:Double) extends EnergySource {

  override def id: String = ""

  override def status: String = ""

}
