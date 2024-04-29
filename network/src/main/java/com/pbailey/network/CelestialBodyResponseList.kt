package com.pbailey.network

import com.google.gson.annotations.SerializedName

data class CelestialBodyResponseList(
@SerializedName("bodies")
val bodies: List<CelestialBody>?
)
