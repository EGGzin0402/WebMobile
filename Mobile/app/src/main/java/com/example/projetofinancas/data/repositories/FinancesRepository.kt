package com.example.projetofinancas.data.repositories

import com.example.projetofinancas.data.models.Finance

interface FinancesRepository {

    suspend fun createFinance(finance: Finance) : Result<Unit>

    suspend fun updateFinance(id: String, finance: Finance) : Result<Unit>

    suspend fun deleteFinance(id: String) : Result<Unit>

    suspend fun getFinance(id: String) : Result<Finance>

    suspend fun getAllFinances(): Result<List<Finance>>

}