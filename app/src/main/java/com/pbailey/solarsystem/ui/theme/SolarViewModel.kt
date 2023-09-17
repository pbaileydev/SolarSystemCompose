package com.pbailey.solarsystem.ui.theme

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbailey.network.PlanetaryResponseList
import com.pbailey.network.SolarNetworkCalls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SolarViewModel() : ViewModel() {
    lateinit var planetListInterface:PlanetListInterface
    interface PlanetListInterface{
        fun showPlanets(list:PlanetaryResponseList)
    }
    val retrofit: SolarNetworkCalls = SolarNetworkCalls()
    var planetState: MutableLiveData<PlanetaryResponseList>? = MutableLiveData(PlanetaryResponseList(null))
    fun setPlanetInterface(planetListInterface: PlanetListInterface){
        this.planetListInterface = planetListInterface
    }
    fun getPlanetData(){
        retrofit.getPlanet().enqueue(object: Callback<PlanetaryResponseList>{
            override fun onResponse(
                call: Call<PlanetaryResponseList>,
                response: Response<PlanetaryResponseList>
            ) {
                if(response!=null) {
                    if (response.code() == 200) {
                        planetListInterface.showPlanets(response.body()!!)
                    } else {
                        Log.d("PlanetResponse", "Failed with code: " + response.code())
                    }
                }
            }

            override fun onFailure(call: Call<PlanetaryResponseList>, t: Throwable) {
                if(t.message!=null){
                    Log.d("PlanetResponse",t.message!!)
                }
                else{
                    Log.d("PlanetResponse","Failed with no message from server")
                }
            }
        })

    }

    fun setPlanetState(list: PlanetaryResponseList) {
        planetState?.value = list

    }
}