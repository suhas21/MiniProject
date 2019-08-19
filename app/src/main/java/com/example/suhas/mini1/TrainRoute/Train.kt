package com.example.suhas.mini1.TrainRoute

import com.example.suhas.mini1.TrainRoute.ClassesItem
import com.example.suhas.mini1.TrainRoute.DaysItem
import com.google.gson.annotations.SerializedName

data class Train(@SerializedName("number")
                 val number: String = "",
                 @SerializedName("classes")
                 val classes: List<ClassesItem>?,
                 @SerializedName("name")
                 val name: String = "",
                 @SerializedName("days")
                 val days: List<DaysItem>?)