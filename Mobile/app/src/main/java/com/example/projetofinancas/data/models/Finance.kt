package com.example.projetofinancas.data.models

data class Finance(
    val id: String = "",
    var name: String = "",
    val price: Double = 0.0,
    val date: String = "",
    val profit: Boolean = true
)
