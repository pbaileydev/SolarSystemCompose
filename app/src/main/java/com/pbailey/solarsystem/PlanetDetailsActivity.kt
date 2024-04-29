package com.pbailey.solarsystem

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.google.gson.Gson
import com.pbailey.network.CelestialBody
import com.pbailey.solarsystem.ui.theme.*
import java.io.File


class PlanetDetailsActivity : ComponentActivity() {
    var mContext: Context = this@PlanetDetailsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val valoraxFamily = FontFamily(
                Font(R.font.valorax_font_family, FontWeight.Normal),
            )
            SolarSystemTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                ModalDrawer(drawerContent ={
                    Column(){
                        Row(modifier = Modifier
                            .background(PlumbColor)
                            .height(48.dp)){
                            Text(color = MainTextColor, text = "Project Cassini")

                        }
                    }
                } ) {

                }
                Scaffold(

                    topBar = {
                        TopAppBar(title = {Text(text="Planet Profile", fontFamily = valoraxFamily)} ,
                            backgroundColor = BackgroundColor, contentColor = MainTextColor, navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                                        contentDescription = "Back Arrow"
                                    )
                                }
                            })
                    }
                ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Background(data = intent.getBundleExtra("DATA")!!.getString("PLANET_INFO")!!,mContext)
                }
                    }
            }
        }
    }
}

@Composable
fun Background(data:String,context:Context) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor)) {
        PlanetCard(data,context)
    }
}

@Composable
fun PlanetCard(data:String,context:Context){
    val planet = Gson().fromJson(data,CelestialBody::class.java)
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier
        .padding(16.dp)
        .fillMaxHeight()) {
        Column(modifier = Modifier
            .background(ItemColor)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())) {
            var imageId = -1
            if (planet.name.equals("Mercury")) {
                imageId = R.drawable.mercury
            } else if (planet.name.equals("Venus")) {
                imageId = R.drawable.venus
            } else if (planet.name.equals("Earth")) {
                imageId = R.drawable.earth
            } else if (planet.name.equals("Mars")) {
                imageId = R.drawable.mars
            } else if (planet.name.equals("Jupiter")) {
                imageId = R.drawable.jupiter
            } else if (planet.name.equals("Saturn")) {
                imageId = R.drawable.saturn
            } else if (planet.name.equals("Uranus")) {
                imageId = R.drawable.uranus
            } else if (planet.name.equals("Neptune")) {
                imageId = R.drawable.neptune
            }

            val valoraxFamily = FontFamily(
                Font(R.font.valorax_font_family, FontWeight.Normal),
            )
            if(imageId != -1) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(312.dp)
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(id = imageId),
                        contentDescription = planet.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(312.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.width(72.dp).height(72.dp)
                            .align(Alignment.BottomEnd).padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.augmented_reality_svgrepo_com),
                            contentDescription = "View with AR",
                            colorFilter = ColorFilter.tint(MainTextColor),
                            modifier = Modifier.width(56.dp).height(56.dp)
                                .background(OrangeAccentColor).clickable {
                                val sceneViewerIntent = Intent(context, ArActivity::class.java)
                                sceneViewerIntent.putExtra("DATA", planet.name)
                                context.startActivity(sceneViewerIntent)
                            }
                        )
                    }
                }
            }
            Text(text=planet.name, modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
                ,style = MaterialTheme.typography.h3,
                fontFamily = valoraxFamily,textAlign = TextAlign.Center, color = MainTextColor)
            var discoverer = "Unknown"
            val discoveredByLabel = "Discovered By: "
            val discoveryDateLabel = "Discovery Date: "
            var discoveredDate = "Unknown"
            if(planet.discoveredBy != null ) {
                if (!planet.discoveredBy.equals("")) {
                    discoverer = planet.discoveredBy
                }
            }
            if(planet.discoveredBy != null) {
                if (!planet.discoveryDate.equals("")) {
                    discoveredDate = planet.discoveryDate
                }
            }
                Text(text=discoveredByLabel+discoverer, fontFamily = valoraxFamily, modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ,style = MaterialTheme.typography.h5,textAlign = TextAlign.Center, color = MainTextColor)
            Text(text=discoveryDateLabel+discoveredDate, fontFamily = valoraxFamily, modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ,style = MaterialTheme.typography.h5,textAlign = TextAlign.Center, color = MainTextColor)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    SolarSystemTheme {

    }
}