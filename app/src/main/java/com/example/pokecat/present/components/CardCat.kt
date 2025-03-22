package com.example.pokecat.present.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokecat.present.main.models.Cat
import com.example.pokecat.utils.Utilities.Companion.hexToColor

@Composable
fun CardCat(cat: Cat) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color(hexToColor(cat.color)), shape = RoundedCornerShape(8.dp))
            .padding(4.dp)
            .width(170.dp)
            .height(100.dp),
    ) {
        Text(text = cat.name, color = Color.White, style = MaterialTheme.typography.titleMedium)

        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
            /*if (imgCat != null) {
                Image(painter = imgCat, contentDescription = null)
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_interrogation),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.defaultMinSize(32.dp, 32.dp)
                )
            }*/
        }

    }
}

