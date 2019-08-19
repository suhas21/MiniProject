package com.example.suhas.mini1.SourceandDestination

import com.google.gson.annotations.SerializedName

data class TrainsItem(@SerializedName("number")
                      val number: String = "",
                      @SerializedName("dest_arrival_time")
                      val destArrivalTime: String = "",
                      @SerializedName("classes")
                      val classes: List<ClassesItem>?,
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("from_station")
                      val fromStation: FromStation,
                      @SerializedName("days")
                      val days: List<DaysItem>?,
                      @SerializedName("src_departure_time")
                      val srcDepartureTime: String = "",
                      @SerializedName("travel_time")
                      val travelTime: String = "",
                      @SerializedName("to_station")
                      val toStation: ToStation)