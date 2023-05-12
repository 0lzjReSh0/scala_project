package com.models

final case class MonthProduction(month:Long, energy:Double) extends EnergySource {

  override def id: String = ""

  override def status: String = ""

}