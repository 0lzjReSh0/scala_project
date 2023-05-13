package com.services

import com.models.{EnergySource}
import com.models.{HourProduction}

class AlertSystem {

  def checkStatus(sources: Seq[EnergySource]): Unit = {
    sources.foreach { source =>
      if (source.status != "operational") {
        println(s"ALERT: ${source.id} is not operational: ${source.status}")
      }
    }
  }

  def checkLowEnergy(sources: Seq[HourProduction], threshold: Double = 170.0): Unit = {
    sources.foreach { source =>
      if (source.energy < threshold) {
        println(s"ALERT: ${source.id} has low energy output: ${source.energy}")
      }
    }
  }
}
