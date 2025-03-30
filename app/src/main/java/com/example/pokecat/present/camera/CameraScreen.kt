package com.example.pokecat.present.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CameraScreen(cameraViewModel: CameraViewModel = hiltViewModel()) {

}

@Preview(showBackground = true)
@Composable
fun CameraScreenPreview() {
    CameraScreen()
}