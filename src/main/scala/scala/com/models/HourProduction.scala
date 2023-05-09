package com.models

final case class HourProduction(
                                 equipment_id: String,
                                 hour: Long,
                                 energy_type: String,
                                 energy: Double,
                                 equipment_status: String
                               ) extends EnergySource {
  override def id: String = equipment_id
  override def status: String = equipment_status
}
