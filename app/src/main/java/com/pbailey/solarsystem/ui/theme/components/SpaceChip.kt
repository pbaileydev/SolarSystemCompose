package com.pbailey.solarsystem.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pbailey.solarsystem.R
import com.pbailey.solarsystem.ui.theme.BackgroundColor
import com.pbailey.solarsystem.ui.theme.LightPurpleColor
import com.pbailey.solarsystem.ui.theme.MainTextColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CassiniChip(text:String, painter: Painter) {
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        onClick = { selected = !selected },
        selected = selected,
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = LightPurpleColor,
            selectedContentColor = MainTextColor,
            backgroundColor = BackgroundColor,
            contentColor = LightPurpleColor
        ),
        border = BorderStroke(
            ChipDefaults.OutlinedBorderSize,
            LightPurpleColor
        ),
        leadingIcon = if (selected) {
            {
                Image(
                    painter = painter,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(16.dp),colorFilter = ColorFilter.tint(MainTextColor)
                )
            }
        } else {
            {
                Image(
                    painter = painter,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(16.dp), colorFilter = ColorFilter.tint(LightPurpleColor)
                )
            }
        },
    ){
        ValoraxText(text = text, style = MaterialTheme.typography.body2)
            
        }
}