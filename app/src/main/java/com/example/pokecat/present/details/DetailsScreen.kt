package com.example.pokecat.present.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.pokecat.R
import com.example.pokecat.present.components.CustomButton

@Composable
fun DetailsScreen(cardId: Int?, photoUri: String?, navController: NavController) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        if (cardId != null) {
            //Card Exist
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(horizontal = 16.dp)
    ) {

        DetailHeader(navController)

        Image(
            painter = painterResource(R.drawable.cat_test_1),
            contentDescription = "cat image",
            modifier = Modifier
                .offset(y = (-50).dp)
                .size(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(border = BorderStroke(6.dp, Color.Cyan), shape = RoundedCornerShape(12.dp))
                .align(Alignment.Center)
                .zIndex(1f),
            contentScale = ContentScale.Crop
        )

        DetailContent(
            Modifier
                .align(Alignment.BottomCenter)
                .scrollable(scrollState, Orientation.Vertical),
            cardId = cardId
        )


    }
}

@Composable
fun DetailHeader(navController: NavController) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back icon",
                modifier = Modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .size(24.dp),
                tint = Color.White
            )
        }
        Text(
            text = "Name Cat",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun DetailContent(modifier: Modifier, cardId: Int?) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(
                Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(top = 62.dp, start = 16.dp, end = 16.dp, bottom = 4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TextDetail(
                "Origin",
                "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum"
            )
        }

        if (cardId!! < 0) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                CustomButton(
                    buttonText = "Register",
                    colorButton = Color.Cyan,
                    borderColor = Color.Cyan,
                    textColor = Color.White
                ) { }

                CustomButton(
                    buttonText = "Isn't this your cat?",
                    colorButton = Color.White,
                    borderColor = Color.Cyan,
                    textColor = Color.Black
                ) { }
            }
        }
    }
}

@Composable
fun TextDetail(subtitle: String, content: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text("$subtitle:", color = Color.Black, style = MaterialTheme.typography.titleMedium)
        Text(content, color = Color.Black, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    //DetailsScreen(photoUri = null, cardId = null)
}