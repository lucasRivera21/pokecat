package com.example.pokecat.present.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color as colorBase
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokecat.R
import com.example.pokecat.present.components.CustomButton
import com.example.pokecat.present.details.models.DetailsToShow
import com.example.pokecat.utils.Credentials.Companion.BG_DONT_FOUND_CAT
import com.example.pokecat.utils.Utilities.Companion.hexToColor

@Composable
fun DetailsScreen(
    cardId: Int?,
    photoUri: String?,
    navController: NavController,
    paddingValues: PaddingValues,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val isLoading by detailsViewModel.isLoading.collectAsState()
    val catDetail by detailsViewModel.catDetail.collectAsState()

    LaunchedEffect(Unit) {
        if (cardId!! > 0) {
            detailsViewModel.getCard(cardId)
        } else {
            detailsViewModel.identifyCat(photoUri!!)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color =
                if (!isLoading) {
                    Color(hexToColor(catDetail?.color ?: BG_DONT_FOUND_CAT))
                } else {
                    MaterialTheme.colorScheme.background
                }
            )
            .padding(
                start = 16.dp,
                top = paddingValues.calculateTopPadding(),
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        if (!isLoading) {
            DetailHeader(navController, catDetail)

            Image(
                bitmap = catDetail?.img?.asImageBitmap()
                    ?: ImageBitmap.imageResource(R.drawable.img_dont_image_default),
                contentDescription = "cat image",
                modifier = Modifier
                    .offset(y = (-50).dp)
                    .size(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        border = BorderStroke(
                            6.dp,
                            Color(hexToColor(catDetail?.color ?: BG_DONT_FOUND_CAT))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .align(Alignment.Center)
                    .zIndex(1f),
                contentScale = ContentScale.Crop
            )

            DetailContent(
                Modifier
                    .align(Alignment.BottomCenter),
                cardId = cardId,
                catDetail = catDetail,
                scrollState = scrollState
            )
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun DetailHeader(navController: NavController, catDetail: DetailsToShow?) {
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
                tint = colorBase.White
            )
        }
        Text(
            text = catDetail?.name ?: "",
            modifier = Modifier.align(Alignment.Center),
            color = colorBase.White,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun DetailContent(modifier: Modifier, cardId: Int?, catDetail: DetailsToShow?, scrollState: ScrollState) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(
                colorBase.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(top = 62.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TextDetail(
                "Temperament",
                catDetail?.temperament ?: "Unknown"
            )

            TextDetail(
                "Origin",
                catDetail?.origin ?: "Unknown"
            )

            TextDetail(
                "Weight",
                catDetail?.weight ?: "Unknown"
            )

            TextDetail(
                "Description",
                catDetail?.description ?: "Unknown"
            )
        }

        if (cardId!! < 0) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                CustomButton(
                    buttonText = "Register",
                    colorButton = Color(hexToColor(catDetail?.color ?: BG_DONT_FOUND_CAT)),
                    borderColor = Color(hexToColor(catDetail?.color ?: BG_DONT_FOUND_CAT)),
                    textColor = Color.White
                ) { }

                CustomButton(
                    buttonText = "Isn't this your cat?",
                    colorButton = Color.White,
                    borderColor = Color(hexToColor(catDetail?.color ?: BG_DONT_FOUND_CAT)),
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
        Text(
            "$subtitle:",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            content,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}