package com.example.projetofinancas.ui.elements.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetofinancas.R
import com.example.projetofinancas.data.models.Finance
import com.example.projetofinancas.ui.theme.White
import com.example.projetofinancas.ui.theme.majorMonoDisplaytFontFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToAdd: () -> Unit,
    navController: NavController
) {

    val finances = viewModel.financesState.collectAsState()

    LaunchedEffect(Unit){
        Log.d("HomeScreen", "LaunchedEffect")
        viewModel.getAllFinances()
        Log.d("HomeScreen", "LaunchedEffect: ${finances.value}")
    }

    Scaffold (
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("account") },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_large))
                        .offset(x = 35.dp),
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = ""
                    )
                }
                FloatingActionButton(
                    onClick = navigateToAdd,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = ""
                    )
                }
            }
        },
        modifier = Modifier
            .padding(top = 38.dp)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 10.dp),
                textAlign = TextAlign.Center,
                text = "Finance Vision",
                fontFamily = majorMonoDisplaytFontFamily,
                fontSize = 48.sp,
                lineHeight = 56.sp,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 10.dp),
                textAlign = TextAlign.Center,
                text = "Bem Vindo",
                fontFamily = majorMonoDisplaytFontFamily,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )

            if (finances.value.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(White),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "SEM ITEMS",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(top = 16.dp)
                        .background(White),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        items = finances.value, key = {it.id}
                    ){
                        FinanceCard(
                            item = it,
                            navController = navController
                        )
                    }
                }

            }

        }




    }


}


@Composable
fun FinanceCard(
    item: Finance,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Card(
        modifier = Modifier
            .clickable(
                enabled = true,
                onClick = {
                    Log.d("FinanceCard", "Clicked on item: ${item.id}")
                    navController.navigate("details/${item.id}")
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))

                Spacer(Modifier.weight(1f))
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row {
                Text(
                    text = "R$ ${item.price}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = if (item.profit){"Entrada"} else{"Saída"},
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }

}