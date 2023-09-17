package com.pbailey.network

import com.google.gson.annotations.SerializedName

data class PlanetaryResponseList(
@SerializedName("bodies")
val bodies: List<Planet>?
)
