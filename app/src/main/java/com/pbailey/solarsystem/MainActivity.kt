package com.pbailey.solarsystem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.pbailey.network.Planet
import com.pbailey.network.PlanetaryResponseList
import com.pbailey.solarsystem.PlanetDetailsActivity
import com.pbailey.solarsystem.ui.theme.SolarSystemTheme
import com.pbailey.solarsystem.ui.theme.SolarViewModel

class MainActivity : ComponentActivity(), SolarViewModel.PlanetListInterface {
    lateinit var solarViewModel: SolarViewModel
    var mContext: Context = this@MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        solarViewModel = ViewModelProvider(this).get(SolarViewModel::class.java)
        solarViewModel.setPlanetInterface(this)
        setContent {
            SolarSystemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    solarViewModel.getPlanetData()
                    var planetState = solarViewModel.planetState?.observeAsState(null)
                    MainScreen(planetList = planetState?.value,mContext)
                }
            }
        }
    }

    override fun showPlanets(list: PlanetaryResponseList) {
        Log.d("PlanetaryResponse", Gson().toJson(list))
        solarViewModel.setPlanetState(list)
        
    }
}

@Composable
fun MainScreen(planetList:PlanetaryResponseList?,context: Context){
    if(planetList==null){
        return
    }
    else{
        if(planetList.bodies!=null) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())) {
                for (item in planetList.bodies!!) {
                    PlanetRow(planet = item){
                        var intent = Intent(context, PlanetDetailsActivity::class.java)
                        var bundle:Bundle = Bundle()
                        bundle.putString("PLANET_INFO",Gson().toJson(item))
                        intent.putExtra("DATA",bundle)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanetRow(planet: Planet, navigateToPlanet:()->Unit){
    Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.padding(8.dp), onClick = navigateToPlanet) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Gray),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var imageId = -1
            if(planet.name.equals("Mercury")){
                imageId = R.drawable.mercury
            }
            else if(planet.name.equals("Venus")){
                imageId = R.drawable.venus
            }
            else if(planet.name.equals("Earth")){
                imageId = R.drawable.earth
            }
            else if(planet.name.equals("Mars")){
                imageId = R.drawable.mars
            }
            else if(planet.name.equals("Jupiter")){
                imageId = R.drawable.jupiter
            }
            else if(planet.name.equals("Saturn")){
                imageId = R.drawable.saturn
            }
            else if(planet.name.equals("Uranus")){
                imageId = R.drawable.uranus
            }
            else if(planet.name.equals("Neptune")){
                imageId = R.drawable.neptune
            }
            Card(shape = RoundedCornerShape(15.dp), modifier = Modifier
                .width(76.dp)
                .padding(8.dp)) {
                Image(bitmap = ImageBitmap.imageResource(id = imageId), contentDescription = planet.name,
                    modifier = Modifier.fillMaxSize()
                        , contentScale = ContentScale.Crop)

            }

            Text(text = planet.name, style = MaterialTheme.typography.h2, color = Color.Black,
            modifier = Modifier.fillMaxWidth())

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SolarSystemTheme {
        Greeting("Android")
    }
}