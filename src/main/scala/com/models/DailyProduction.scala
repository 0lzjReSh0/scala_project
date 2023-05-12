package com.models

final case class DailyProduction(day:Long, energy:Double) extends EnergySource {

  override def id: String = ""

  override def status: String = ""

}
