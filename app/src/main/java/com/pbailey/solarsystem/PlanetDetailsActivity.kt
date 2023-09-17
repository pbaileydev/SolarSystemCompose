package com.pbailey.solarsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.pbailey.network.Planet
import com.pbailey.solarsystem.ui.theme.ui.theme.SolarSystemTheme

class PlanetDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolarSystemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Background(data = intent.getBundleExtra("DATA")!!.getString("PLANET_INFO")!!)
                }
            }
        }
    }
}

@Composable
fun Background(data:String) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        PlanetCard(data)
    }
}

@Composable
fun PlanetCard(data:String){
    val planet = Gson().fromJson(data,Planet::class.java)
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier
        .padding(16.dp).fillMaxHeight()) {
        Column(modifier = Modifier
            .background(Color.Gray)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())) {
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
            Image(bitmap = ImageBitmap.imageResource(id = imageId), contentDescription = planet.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(312.dp)
                , contentScale = ContentScale.FillBounds)
            Text(text=planet.name, modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
                ,style = MaterialTheme.typography.h3,textAlign = TextAlign.Center)
            var discoverer = "Unknown"
            val discoveredByLabel = "Discovered By: "
            val discoveryDateLabel = "Discovery Date: "
            var discoveredDate = "Unknown"
            if(!planet.discoveredBy.equals(""))
             discoverer = planet.discoveredBy
            if(!planet.discoveryDate.equals(""))
                discoveredDate = planet.discoveryDate
                Text(text=discoveredByLabel+discoverer, modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ,style = MaterialTheme.typography.h5,textAlign = TextAlign.Center)
            Text(text=discoveryDateLabel+discoveredDate, modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ,style = MaterialTheme.typography.h5,textAlign = TextAlign.Center)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    SolarSystemTheme {

    }
}