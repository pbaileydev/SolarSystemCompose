package com.pbailey.solarsystem

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.pbailey.network.CelestialBody
import com.pbailey.network.CelestialBodyResponseList
import com.pbailey.solarsystem.PlanetDetailsActivity
import com.pbailey.solarsystem.ui.theme.*
import com.pbailey.solarsystem.ui.theme.components.CassiniChip
import com.pbailey.solarsystem.ui.theme.components.SwitchData
import com.pbailey.solarsystem.ui.theme.components.ValoraxText
import com.pbailey.solarsystem.utilities.valoraxFamily
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), SolarViewModel.PlanetListInterface, SwitchData{
    override fun getData(data: String) {
        solarViewModel.selectedChip = data
        if(data.equals("Planets")){
            solarViewModel.getPlanetData()
        }
        else if(data.equals("Moons")){
            solarViewModel.getMoonData()
        }
        else{
            solarViewModel.getCometData()
        }
    }
    var switchData = this

    lateinit var solarViewModel: SolarViewModel
    var mContext: Context = this@MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        solarViewModel = ViewModelProvider(this).get(SolarViewModel::class.java)
        solarViewModel.setPlanetInterface(this)
        setContent {
            SolarSystemTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                ModalDrawer(drawerState = drawerState,drawerContent ={
                    //https://www.freepik.com/free-vector/flying-satellite-with-antenna-space-cartoon-icon-illustration_13309438.htm#query=satellite&position=2&from_view=search&track=sph&uuid=d91e119c-1440-4f26-a431-3297fb57ca12
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundColor)) {
                            Row(
                                modifier = Modifier
                                    .background(OrangeAccentColor)
                                    .height(80.dp)
                                    .fillMaxWidth()
                            ) {
                                ValoraxText(
                                    text = "Project Cassini",
                                    modifier = Modifier.padding(start=16.dp,top=32.dp)
                                )

                            }
                        Spacer(modifier = Modifier
                            .background(LightPurpleColor)
                            .fillMaxWidth()
                            .height(1.dp))
                        Row(
                            modifier = Modifier
                                .height(64.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ValoraxText(
                                text = "Settings",modifier = Modifier.padding(start=16.dp)
                            )

                        }
                        Spacer(modifier = Modifier
                            .background(LightPurpleColor)
                            .fillMaxWidth()
                            .height(1.dp))
                        Row(
                            modifier = Modifier
                                .height(64.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ValoraxText(
                                text = "More", modifier = Modifier.padding(start=16.dp)
                            )

                        }
                        Spacer(modifier = Modifier
                            .background(LightPurpleColor)
                            .fillMaxWidth()
                            .height(1.dp))
                        Row(
                            modifier = Modifier
                                .height(64.dp)
                                .fillMaxWidth()
                                .clickable(onClick = { showCredits() }),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ValoraxText(
                                text = "Credits", modifier = Modifier.padding(start=16.dp)
                            )

                        }
                        Spacer(modifier = Modifier
                            .background(LightPurpleColor)
                            .fillMaxWidth()
                            .height(1.dp))

                    }
                } ) {


                    Scaffold(

                        topBar = {
                            TopAppBar(title = {
                                ValoraxText(text = "Project Cassini")
                            },
                                backgroundColor = BackgroundColor,
                                contentColor = MainTextColor,
                                elevation = 0.dp,
                            navigationIcon = {
                                IconButton(onClick = {scope.launch {
                                    drawerState.apply {
                                        if (isClosed)
                                            open()
                                        else
                                            close()
                                    }
                                }}) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_menu_24),
                                        contentDescription = "Back Arrow"
                                    )
                                }
                            })
                        },
                    ) {
                        Surface(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize(),
                            color = BackgroundColor
                        ) {

                            if(solarViewModel.isInitialLoad){
                                solarViewModel.getPlanetData()
                            }
                            var planetState = solarViewModel.planetState?.observeAsState(null)
                            MainScreen(planetList = planetState?.value, mContext, solarViewModel = solarViewModel,switchData=switchData)
                        }
                    }
                    // A surface container using the 'background' color from the theme
                }
            }
        }
    }

    private fun showCredits() {
        val intent = Intent(this@MainActivity,CreditsActivity::class.java)
        startActivity(intent)

    }


    override fun showPlanets(list: CelestialBodyResponseList) {
        Log.d("PlanetaryResponse", Gson().toJson(list))
        solarViewModel.setPlanetState(list)
        
    }
    override fun showMoons(list: CelestialBodyResponseList) {
        Log.d("MoonsResponse", Gson().toJson(list))
        solarViewModel.setPlanetState(list)

    }

    override fun showComets(list: CelestialBodyResponseList) {
        Log.d("CometsResponse", Gson().toJson(list))
        solarViewModel.setPlanetState(list)
    }
}
private fun getMoonData(solarViewModel: SolarViewModel){
    solarViewModel.getPlanetData()
}
@Composable
fun MainScreen(planetList:CelestialBodyResponseList?,context: Context, solarViewModel: SolarViewModel,switchData:SwitchData){
    if(planetList==null){
        return
    }
    else{
        if(planetList.bodies!=null) {
            Column(modifier = Modifier
                .fillMaxSize()
                 ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly){
                    CassiniChip(text = "Planets", painterResource(id = R.drawable.planet_2_svgrepo_com), switchData = switchData,solarViewModel.selectedChip )
                    CassiniChip(text = "Moons", painterResource(id = R.drawable.moon_svgrepo_com), switchData = switchData,solarViewModel.selectedChip)
                    CassiniChip(text = "Comets", painterResource(id = R.drawable.satellite_svgrepo_com), switchData = switchData,solarViewModel.selectedChip)
                }
                Card(shape = RoundedCornerShape(12.dp),modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize(),
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                BackgroundColor
                            )
                            .verticalScroll(rememberScrollState())
                    ) {
                        for (item in planetList.bodies!!) {
                            PlanetRow(planet = item) {
                                var intent = Intent(context, PlanetDetailsActivity::class.java)
                                var bundle: Bundle = Bundle()
                                bundle.putString("PLANET_INFO", Gson().toJson(item))
                                intent.putExtra("DATA", bundle)
                                context.startActivity(intent)
                            }
                            Spacer(modifier= Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(BackgroundColor))
                        }
                    }
                }
            }

        }
    }

}

@Composable fun MenuItem(text:String){
    Row(modifier = Modifier.height(40.dp)){
        ValoraxText(text = text)
        
    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanetRow(planet: CelestialBody, navigateToPlanet:()->Unit){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(ItemColor)
                .clickable(onClick = navigateToPlanet),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
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
                if(imageId != -1) {
                    Image(
                        bitmap = ImageBitmap.imageResource(id = imageId),
                        contentDescription = planet.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

            }
            
            ValoraxText(text = planet.name, style = MaterialTheme.typography.h4, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight())

        }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SolarSystemTheme {
        Greeting("Android")
    }
}