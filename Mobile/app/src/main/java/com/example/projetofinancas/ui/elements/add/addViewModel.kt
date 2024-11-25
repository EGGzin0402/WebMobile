package com.example.projetofinancas.ui.elements.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.projetofinancas.data.models.Finance
import com.example.projetofinancas.data.repositories.FinancesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class addViewModel @Inject constructor (
    private val repository: FinancesRepository
) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && date.isNotBlank()
        }
    }

    suspend fun addFinance() {
        if (validateInput()) {
            repository.createFinance(itemUiState.itemDetails.toItem())
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