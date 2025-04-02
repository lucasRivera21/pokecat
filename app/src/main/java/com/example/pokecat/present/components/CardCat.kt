package com.example.pokecat.present.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokecat.R
import com.example.pokecat.present.main.models.CatCard
import com.example.pokecat.utils.Credentials.Companion.BG_DONT_FOUND_CAT
import com.example.pokecat.utils.Utilities.Companion.hexToColor

@Composable
fun CardCat(cat: CatCard, onClickCard: () -> Unit) {
    val cardColor =
        if (!cat.isFounded) Color(hexToColor(BG_DONT_FOUND_CAT)) else Color(hexToColor(cat.color))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = cardColor, shape = RoundedCornerShape(8.dp))
            .padding(4.dp)
            .width(170.dp)
            .height(100.dp)
            .clickable { onClickCard() },
    ) {
        Text(text = cat.name, color = Color.White, style = MaterialTheme.typography.titleMedium)

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            if (cat.imgBitmap != null && cat.isFounded) {
                Image(
                    bitmap = cat.imgBitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.clip(
                        RoundedCornerShape(
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painterResource(R.drawable.ic_interrogation),
                    contentDescription = "Interrogation Icon",
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }
        }

    }
}

