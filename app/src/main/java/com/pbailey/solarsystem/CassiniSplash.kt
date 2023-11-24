package com.pbailey.solarsystem

import android.content.Intent
import android.net.sip.SipErrorCode.TIME_OUT
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pbailey.solarsystem.ui.theme.BackgroundColor
import com.pbailey.solarsystem.ui.theme.SolarSystemTheme
import com.pbailey.solarsystem.ui.theme.components.ValoraxText


class CassiniSplash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var displayed by remember { mutableStateOf(false) }
            SolarSystemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundColor
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundColor),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        ValoraxText(text = "Project Cassini")
                        displayed = true


                    }
                }
            }
            Handler().postDelayed(Runnable {
                val i = Intent(this@CassiniSplash, MainActivity::class.java)
                startActivity(i)
                finish()
            }, 3000L)
        }

    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    SolarSystemTheme {
        Greeting2("Android")
    }
}