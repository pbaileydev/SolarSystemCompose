package com.pbailey.network

import com.google.gson.annotations.SerializedName

data class Planet (
    @SerializedName("englishName")
    val name:String,
    @SerializedName("discoveredBy")
val discoveredBy:String,
@SerializedName("discoveryDate")
val discoveryDate:String
)