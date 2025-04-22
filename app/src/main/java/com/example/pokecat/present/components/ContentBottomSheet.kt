package com.example.pokecat.present.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContentBottomSheet(catRecognitionList: List<String>, onClickItem: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        items(catRecognitionList) {
            CatRecognitionItem(it){ itemSelected ->
                onClickItem(itemSelected)
            }
        }
    }
}

@Composable
fun CatRecognitionItem(nameCat: String, onClickItem: (String) -> Unit) {
    HorizontalDivider()
    Box(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClickItem(nameCat)
            }
            .padding(12.dp)
    )
    {
        Text(
            text = nameCat,
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContentBottomSheet() {
    val catRecognitionList = listOf("Cat 1", "Cat 2", "Cat 3")
    ContentBottomSheet(catRecognitionList){}
}