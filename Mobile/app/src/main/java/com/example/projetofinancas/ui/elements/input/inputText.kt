package com.example.projetofinancas.ui.elements.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputText(
    modifier: Modifier = Modifier,
    title: String? = null,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .background(Color.Transparent),
        shape = RoundedCornerShape(100),
        label = {Text(title ?: "")},
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextLogin(
    modifier: Modifier = Modifier,
    title: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    showPassword: Boolean = false,
    onShownPasswordChange: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .background(Color.Transparent),
        shape = RoundedCornerShape(100),
        label = {Text(title ?: "")},
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            cursorColor = Color.Black
        ),
        trailingIcon = {
            IconButton(
                onClick = { onShownPasswordChange() }
            ) {
                Icon(
                    imageVector = if (showPassword) {
                        Icons.Default.Check
                    }else{
                        Icons.Default.Close
                    },
                    contentDescription = "Localized description",
                    tint = Color.Black
                )
            }
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvertedInputText(
    modifier: Modifier = Modifier,
    title: String? = null,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .background(Color.Transparent),
        shape = RoundedCornerShape(100),
        label = {Text(title ?: "")},
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvertedInputTextLogin(
    modifier: Modifier = Modifier,
    title: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    showPassword: Boolean = false,
    onShownPasswordChange: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .background(Color.Transparent),
        shape = RoundedCornerShape(100),
        label = {Text(title ?: "")},
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        trailingIcon = {
            IconButton(
                onClick = { onShownPasswordChange() }
            ) {
                Icon(
                    imageVector = if (showPassword) {
                        Icons.Default.Check
                    }else{
                        Icons.Default.Close
                    },
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }
    )
}