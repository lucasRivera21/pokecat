package com.example.pokecat.present.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    buttonText: String,
    colorButton: Color,
    borderColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = colorButton,
            contentColor = textColor,
            disabledContainerColor = colorButton,
            disabledContentColor = colorButton
        ),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, borderColor),
    ) {
        Text(text = buttonText, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    CustomButton(
        buttonText = "Custom Button",
        colorButton = Color.Blue,
        borderColor = Color.Black,
        textColor = Color.White
    ) {}
}