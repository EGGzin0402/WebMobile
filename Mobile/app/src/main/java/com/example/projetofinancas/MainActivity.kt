package com.example.projetofinancas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.projetofinancas.ui.elements.home.HomeViewModel
import com.example.projetofinancas.ui.navigation.FinNavHost
import com.example.projetofinancas.ui.theme.ProjetoFinancasTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        Log.d("FinanceApplication", "FirebaseApp initialized")
        enableEdgeToEdge()
        setContent {
            ProjetoFinancasTheme {

                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    FinNavHost(navController, modifier = Modifier)
                }
            }
        }
    }
}

