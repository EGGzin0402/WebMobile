package com.example.projetofinancas.ui.elements.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projetofinancas.R
import com.example.projetofinancas.ui.elements.input.InputButton
import com.example.projetofinancas.ui.elements.input.InputText
import com.example.projetofinancas.ui.theme.majorMonoDisplaytFontFamily
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddScreen(
    viewModel: addViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .padding(top = 38.dp)
            .fillMaxSize(),
    ) { innerPadding ->
        IconButton(
            onClick = navigateBack,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 10.dp),
            textAlign = TextAlign.Center,
            text = "Adicionar Despesa",
            fontFamily = majorMonoDisplaytFontFamily,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )

        addForm(
            financeState = viewModel.itemUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.addFinance()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            onValueChange = viewModel::updateUiState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addForm(
    financeState: ItemUiState,
    onSaveClick: () -> Unit,
    onValueChange: (ItemDetails) -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedOption by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        InputText(
            modifier = Modifier.fillMaxWidth(),
            title = "Nome",
            value = financeState.itemDetails.name,
            onValueChange = { onValueChange(financeState.itemDetails.copy(name = it)) }
        )
        InputText(
            modifier = Modifier.fillMaxWidth(),
            title = "Valor",
            value = financeState.itemDetails.price,
            onValueChange = {onValueChange(financeState.itemDetails.copy(price = it))}
        )
        OutlinedCard (

        ) {
            TextField(
                value = if (financeState.itemDetails.profit) {
                    "Entrada"
                } else {
                    "Saída"
                },
                enabled = false,
                onValueChange = { onValueChange(financeState.itemDetails.copy(profit = selectedOption)) },
                modifier = Modifier
                    .clickable(onClick = { expanded = true })
                    .fillMaxWidth(),
                label = { Text("Selecione") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledLabelColor = Color.Black,
                ),
                shape = RoundedCornerShape(50)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
            ) {

                DropdownMenuItem(
                    onClick = {
                        selectedOption = true
                        onValueChange(financeState.itemDetails.copy(profit = selectedOption))
                        expanded = false
                    },
                    text = {
                        Text(text = "Entrada")
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        selectedOption = false
                        onValueChange(financeState.itemDetails.copy(profit = selectedOption))
                        expanded = false
                    },
                    text = {
                        Text(text = "Saída")
                    }
                )

            }
        }

        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val selectedDate = remember { mutableStateOf(financeState.itemDetails.date) }

        val initialMillis = selectedDate.value.takeIf { it.isNotEmpty() }?.let {
            dateFormatter.parse(it)?.time
        }

        val datePickerState = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Input,
            initialSelectedDateMillis = initialMillis
        )

        LaunchedEffect(financeState.itemDetails.date) {
            if (financeState.itemDetails.date.isNotEmpty()) {
                val initialMillis = dateFormatter.parse(financeState.itemDetails.date)?.time

                if (initialMillis != null && datePickerState.selectedDateMillis != initialMillis) {
                    datePickerState.selectedDateMillis = initialMillis
                    selectedDate.value = financeState.itemDetails.date
                }
            }
        }

        LaunchedEffect(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let { millis ->
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = millis
                    add(Calendar.DAY_OF_MONTH, 1) // Adiciona 1 dia
                }
                val formattedDate = dateFormatter.format(calendar.time)
                if (formattedDate != selectedDate.value){
                    selectedDate.value = formattedDate
                    onValueChange(financeState.itemDetails.copy(date = formattedDate))
                }
            }
        }

        DatePicker(
            title = { Text("Selecione uma data") },
            state = datePickerState,
        )

        InputButton(
            onClick = onSaveClick,
            text = "Salvar"
        )

    }
}