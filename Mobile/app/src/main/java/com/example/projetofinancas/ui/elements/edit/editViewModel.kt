package com.example.projetofinancas.ui.elements.edit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofinancas.data.models.Finance
import com.example.projetofinancas.data.repositories.FinancesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class editViewModel @Inject constructor (
    private val repository: FinancesRepository
) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun loadFinanceById(id: String) {
        viewModelScope.launch {
            repository.getFinance(id).onSuccess { finance ->
                Log.d("DetailsViewModel", "Fetched finance: $finance")
                itemUiState = ItemUiState(itemDetails = finance.toItemDetails())
            }.onFailure { exception ->
                // Lidar com o erro, se necessário
                Log.e("DetailsViewModel", "Error fetching finance by ID", exception)
            }
        }
    }

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && date.isNotBlank()
        }
    }

    suspend fun updateFinance() {
        if (validateInput()) {
            repository.updateFinance(itemUiState.itemDetails.id, itemUiState.itemDetails.toItem())
        }
    }

}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: String = "",
    val name: String = "",
    val price: String = "",
    val date: String = "",
    val profit: Boolean = false
)

fun ItemDetails.toItem(): Finance = Finance(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    date = date,
    profit = profit
)

fun Finance.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    date = date,
    profit = profit
)