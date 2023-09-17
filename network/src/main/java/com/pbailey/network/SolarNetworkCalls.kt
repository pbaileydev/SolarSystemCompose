package com.pbailey.network

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

class SolarNetworkCalls {
    val retrofit = Retrofit.Builder().baseUrl("https://api.le-systeme-solaire.net/rest/").
    addConverterFactory(GsonConverterFactory.create()).build()
    val solarApi = retrofit.create(SolarApi::class.java)
    //Below is a proper request to get the list of planets in our solar system
    //https://api.le-systeme-solaire.net/rest/bodies/?data=englishName,isPlanet?&filter[]=isPlanet,eq,true
    fun getPlanet(): Call<PlanetaryResponseList> {
        return solarApi.getPlanets()
    }
}