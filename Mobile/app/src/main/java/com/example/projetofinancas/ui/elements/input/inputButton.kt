package com.example.projetofinancas.ui.elements.input

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.projetofinancas.ui.theme.majorMonoDisplaytFontFamily

@Composable
fun InputButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(15)
    ) {
        Text(
            text = text,
            fontFamily = majorMonoDisplaytFontFamily
        )
    }
}

@Composable
fun InvertedInputButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(15)
    ) {
        Text(
            text = text,
            fontFamily = majorMonoDisplaytFontFamily
        )
    }
}