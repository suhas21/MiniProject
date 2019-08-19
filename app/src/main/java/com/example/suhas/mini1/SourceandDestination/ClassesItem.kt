package com.example.suhas.mini1.SourceandDestination

import com.google.gson.annotations.SerializedName

data class ClassesItem(@SerializedName("code")
                       val code: String = "",
                       @SerializedName("name")
                       val name: String = "")