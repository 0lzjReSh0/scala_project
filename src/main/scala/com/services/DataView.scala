package com.services
import com.models.HourProduction
import com.services.EnergyAnalyzer
// DataView.scala
object DataView {
  val energyAnalyzer = new EnergyAnalyzer
  def showData(data: Seq[Any]): Unit = {
    // Implement data view here
  }

  def showDataByEnergyType(data: Seq[HourProduction]): Unit = {
    data.groupBy(_.energy_type).foreach { case (energyType, records) =>
      println(s"Energy type: $energyType")
      showData(records)
    }
  }

  def showSortedData(data: Seq[HourProduction]): Unit = {
    val sortedData = energyAnalyzer.sortByEnergy(data)
    showData(sortedData)
  }

  def showSearchResults(data: Seq[HourProduction], keyword: String): Unit = {
    val results = energyAnalyzer.searchData(data, keyword)
    showData(results)
  }
}

