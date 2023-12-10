package com.pbailey.solarsystem

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.pbailey.solarsystem.ui.theme.BackgroundColor
import com.pbailey.solarsystem.ui.theme.MainTextColor
import com.pbailey.solarsystem.ui.theme.SolarSystemTheme
import com.pbailey.solarsystem.ui.theme.components.ValoraxText
import com.pbailey.solarsystem.utilities.valoraxFamily
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Paths

class CreditsActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolarSystemTheme {
                Scaffold(
                    topBar= {
                        TopAppBar(title = { androidx.compose.material.Text(text="Credits", fontFamily = valoraxFamily) } ,
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
                    Column(
                        modifier = Modifier
                            .background(BackgroundColor)
                            .padding(it)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                    ) {
                        getCredits(this@CreditsActivity)
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getCredits(context:Context){
    val path = context.filesDir.absolutePath
    val file = File("$path/credits.txt")
    Log.d("FileIOIO",path)

    val fileInputStream: InputStream = context.assets.open("models/credits.txt")
    var creditsList:ArrayList<String> = ArrayList()
    fileInputStream.bufferedReader().forEachLine {
        creditsList.add(it)
    }
    for(credit:String in creditsList){
        ValoraxText(text = credit)
    }
}
@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SolarSystemTheme {
        Greeting3("Android")
    }
}