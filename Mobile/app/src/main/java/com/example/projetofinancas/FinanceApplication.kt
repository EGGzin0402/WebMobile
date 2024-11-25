package com.example.projetofinancas

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FinanceApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}