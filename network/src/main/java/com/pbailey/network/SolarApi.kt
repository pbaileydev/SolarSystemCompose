package com.pbailey.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SolarApi {
    //https://api.le-systeme-solaire.net/rest/bodies/?data=englishName,isPlanet?&filter[]=isPlanet,eq,true

    @GET("bodies/?data=englishName,discoveredBy,discoveryDate?&filter[]=isPlanet,eq,true")
    fun getPlanets(): Call<PlanetaryResponseList>

}