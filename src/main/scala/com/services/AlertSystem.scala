package com.services

import com.models.{EnergySource}

class AlertSystem {

  def checkStatus(sources: Seq[EnergySource]): Unit = {
    sources.foreach { source =>
      if (source.status != "operational") {
        println(s"ALERT: ${source.id} is not operational: ${source.status}")
      }
    }
  }
}
