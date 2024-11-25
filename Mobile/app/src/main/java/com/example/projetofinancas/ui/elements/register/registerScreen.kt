package com.example.projetofinancas.ui.elements.register

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projetofinancas.R
import com.example.projetofinancas.ui.elements.input.InputButton
import com.example.projetofinancas.ui.elements.input.InputText
import com.example.projetofinancas.ui.elements.input.InvertedInputButton
import com.example.projetofinancas.ui.elements.input.InvertedInputText
import com.example.projetofinancas.ui.elements.input.InvertedInputTextLogin
import com.example.projetofinancas.ui.elements.login.LoginState
import com.example.projetofinancas.ui.elements.login.loginViewModel
import com.example.projetofinancas.ui.theme.Black
import com.example.projetofinancas.ui.theme.White
import com.example.projetofinancas.ui.theme.majorMonoDisplaytFontFamily

@Composable
fun registerScreen(
    viewModel: registerViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    onNavigateClick: () -> Unit
) {

    val loginState by viewModel.loginState.collectAsState()

    val showPassword = remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            navigateToHome()
        }
    }

    Surface(modifier = Modifier
        .fillMaxSize(),
        color = Black
    ) {
        Canvas(modifier = Modifier,
            onDraw = {
                withTransform({
                    translate(
                        left = (size.width / 2.3F),
                        top = (size.height / 7.5F)
                    )
                    rotate(degrees = -38F)
                    Modifier.shadow(1.dp)
                }){
                    drawRect(
                        color = White,
                        topLeft = Offset(
                            x = -(size.width / 3F),
                            y = -(size.height / 3F)
                        ),
                        size = size,
                    )
                }
                withTransform({
                    translate(
                        left = (size.width / 2.2F),
                        top = (size.height / 6.20F)
                    )
                    rotate(degrees = -80F)
                }){
                    drawRect(
                        color = Black,
                        topLeft = Offset(
                            x = -(size.width / 3F),
                            y = -(size.height / 3F)
                        ),
                        size = size
                    )
                }
            }
        )
        Box (modifier = Modifier
            .fillMaxSize()
            .offset(x = -85.dp, y = -140.dp),
            contentAlignment = Alignment.Center
        ) {
            registerTitle()
        }
        Box (modifier = Modifier
            .fillMaxSize()
            .offset(y = -70.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            registerForm(
                loginState = loginState,
                viewModel = viewModel,
                navigateToHome = navigateToHome,
                showPassword = showPassword.value,
                onShownPasswordChange = {
                    showPassword.value = !showPassword.value
                }
            )
            IconButton(
                onClick = onNavigateClick,
                modifier = Modifier
                    .offset(
                        x = -150.dp,
                        y = -225.dp
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .rotate(180F),
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "play",
                    tint = Color.White
                )
            }
        }
    }
}



@Composable
fun registerForm(
    loginState: LoginState,
    viewModel: registerViewModel,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    showPassword: Boolean,
    onShownPasswordChange: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            fontFamily = majorMonoDisplaytFontFamily,
        )
        Spacer(modifier = Modifier.padding(10.dp))
        InvertedInputText(
            title = "E-mail",
            value = email,
            onValueChange = {email = it}
        )
        Spacer(modifier = Modifier.padding(10.dp))
        InvertedInputTextLogin(
            title = "Senha",
            value = password,
            onValueChange = {password = it},
            showPassword = showPassword,
            onShownPasswordChange = { onShownPasswordChange() }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        InvertedInputButton(
            onClick = {
                viewModel.register(email, password)
                if (loginState is LoginState.Success) {
                    navigateToHome()
                }
            },
            modifier = Modifier
                .padding(10.dp),
            text = "entrar"
        )
    }
}

@Composable
fun registerTitle(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "Finance",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            fontFamily = majorMonoDisplaytFontFamily,
        )
        Text(
            text = "Vision",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            fontFamily = majorMonoDisplaytFontFamily,
        )
    }
}