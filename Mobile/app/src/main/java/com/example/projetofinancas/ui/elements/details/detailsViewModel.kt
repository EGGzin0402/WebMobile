package com.example.projetofinancas.ui.elements.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofinancas.data.models.Finance
import com.example.projetofinancas.data.repositories.FinancesRepository
import com.example.projetofinancas.ui.elements.add.ItemDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class detailsViewModel @Inject constructor(
    private val repository: FinancesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemDetailsUiState())
    val uiState: StateFlow<ItemDetailsUiState> = _uiState

    fun loadFinanceById(id: String) {
        viewModelScope.launch {
            repository.getFinance(id).onSuccess { finance ->
                Log.d("DetailsViewModel", "Fetched finance: $finance")
                _uiState.value = ItemDetailsUiState(itemDetails = finance.toItemDetails())
            }.onFailure { exception ->
                // Lidar com o erro, se necessário
                Log.e("DetailsViewModel", "Error fetching finance by ID", exception)
            }
        }
    }

    suspend fun deleteItem() {
        Log.d("DetailsViewModel", "Deleting item with ID: ${uiState.value.itemDetails.id}")
        repository.deleteFinance(uiState.value.itemDetails.id)
        Log.d("DetailsViewModel", "Item deleted successfully")
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ItemDetailsUiState(
    val itemDetails: ItemDetails = ItemDetails()
)


fun Finance.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    date = date,
    profit = profit
)