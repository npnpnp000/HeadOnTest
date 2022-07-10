package com.adviserall22spdaslld.model.response

data class Responses(
    val `data`: List<Data>
)

data class Data(
    val City: String,
    val House_number: Int,
    val ID: Int,
    val Latitude: Double,
    val Longitude: Double,
    val Street: String
)