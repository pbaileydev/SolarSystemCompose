package com.pbailey.solarsystem.ui.theme

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbailey.network.CelestialBodyResponseList
import com.pbailey.network.SolarNetworkCalls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SolarViewModel() : ViewModel() {
    lateinit var planetListInterface:PlanetListInterface
    interface PlanetListInterface{
        fun showPlanets(list:CelestialBodyResponseList)
        fun showMoons(list:CelestialBodyResponseList)

        fun showComets(list:CelestialBodyResponseList)
    }
    val retrofit: SolarNetworkCalls = SolarNetworkCalls()
    var planetState: MutableLiveData<CelestialBodyResponseList>? = MutableLiveData(CelestialBodyResponseList(null))
    var isInitialLoad: Boolean = true
    var selectedChip:String = "Planets"
    fun setPlanetInterface(planetListInterface: PlanetListInterface){
        this.planetListInterface = planetListInterface
    }
    fun getPlanetData(){
        retrofit.getPlanet().enqueue(object: Callback<CelestialBodyResponseList>{
            override fun onResponse(
                call: Call<CelestialBodyResponseList>,
                response: Response<CelestialBodyResponseList>
            ) {
                isInitialLoad = false
                if(response!=null) {
                    if (response.code() == 200) {
                        planetListInterface.showPlanets(response.body()!!)
                    } else {
                        Log.d("PlanetResponse", "Failed with code: " + response.code())
                    }
                }
            }

            override fun onFailure(call: Call<CelestialBodyResponseList>, t: Throwable) {
                if(t.message!=null){
                    Log.d("PlanetResponse",t.message!!)
                }
                else{
                    Log.d("PlanetResponse","Failed with no message from server")
                }
            }
        })

    }

    fun setPlanetState(list: CelestialBodyResponseList) {
        planetState?.value = list

    }
    fun getMoonData(){
        isInitialLoad = false
        retrofit.getMoons().enqueue(object: Callback<CelestialBodyResponseList>{
            override fun onResponse(
                call: Call<CelestialBodyResponseList>,
                response: Response<CelestialBodyResponseList>
            ) {
                if(response!=null) {
                    if (response.code() == 200) {
                        planetListInterface.showMoons(response.body()!!)
                    } else {
                        Log.d("PlanetResponse", "Failed with code: " + response.code())
                    }
                }
            }

            override fun onFailure(call: Call<CelestialBodyResponseList>, t: Throwable) {
                if(t.message!=null){
                    Log.d("PlanetResponse",t.message!!)
                }
                else{
                    Log.d("PlanetResponse","Failed with no message from server")
                }
            }
        })

    }

    fun getCometData(){
        isInitialLoad = false
        retrofit.getComets().enqueue(object: Callback<CelestialBodyResponseList>{
            override fun onResponse(
                call: Call<CelestialBodyResponseList>,
                response: Response<CelestialBodyResponseList>
            ) {
                if(response!=null) {
                    if (response.code() == 200) {
                        planetListInterface.showComets(response.body()!!)
                    } else {
                        Log.d("PlanetResponse", "Failed with code: " + response.code())
                    }
                }
            }

            override fun onFailure(call: Call<CelestialBodyResponseList>, t: Throwable) {
                if(t.message!=null){
                    Log.d("PlanetResponse",t.message!!)
                }
                else{
                    Log.d("PlanetResponse","Failed with no message from server")
                }
            }
        })

    }
}