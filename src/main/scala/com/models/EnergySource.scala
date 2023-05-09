package com.models

trait EnergySource {
  def id: String
  def status: String
}

case class SolarPanel(id: String, capacity: Double, status: String) extends EnergySource

case class WindTurbine(id: String, capacity: Double, status: String) extends EnergySource

case class HydroPower(id: String, capacity: Double, status: String) extends EnergySource
