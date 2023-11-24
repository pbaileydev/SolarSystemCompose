package com.pbailey.solarsystem.ui.theme.components

import android.graphics.fonts.FontStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.pbailey.solarsystem.ui.theme.MainTextColor
import com.pbailey.solarsystem.utilities.valoraxFamily

@Composable
fun ValoraxText(text:String, color: Color = MainTextColor, style:TextStyle = MaterialTheme.typography.h5,modifier: Modifier? = null){
    if (modifier != null) {
        Text(
            color = color,
            text = text,
            fontFamily = valoraxFamily,
            style = style,
            modifier = modifier
        )
    }
    else{
        Text(
            color = color,
            text = text,
            fontFamily = valoraxFamily,
            style = style,
        )
    }
}