package com.example.projetofinancas.ui.elements.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projetofinancas.data.models.Finance
import com.example.projetofinancas.data.repositories.FinancesRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: FinancesRepository
): ViewModel(){

    private val _financesState = MutableStateFlow<List<Finance>>(emptyList())
    val financesState: StateFlow<List<Finance>> = _financesState.asStateFlow()

    suspend fun getAllFinances(){
        try {
            // Log de início da função
            Log.d("HomeViewModel", "Fetching finances...")

            // Chama o repositório para buscar os dados
            val result = repository.getAllFinances()

            // Verifica se a chamada foi bem-sucedida
            result.onSuccess { finances ->
                _financesState.value = finances
                Log.d("HomeViewModel", "Successfully fetched finances: ${finances.size} items")
            }.onFailure { exception ->
                // Log de erro em caso de falha
                Log.e("HomeViewModel", "Error fetching finances: ${exception.message}")
            }

        } catch (e: Exception) {
            // Trata qualquer erro inesperado
            Log.e("HomeViewModel", "Unexpected error: ${e.message}")
        }
    }
}