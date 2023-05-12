package com.models

case class DataViewer(equipment: String, datetime: String, energy_type: String, energy: Double, status: Boolean)
case class RecordFilter(filterBy: Char, range: String)
