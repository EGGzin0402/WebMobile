package com.example.projetofinancas.data

import android.util.Log
import com.example.projetofinancas.data.models.Finance
import com.example.projetofinancas.data.repositories.AuthRepository
import com.example.projetofinancas.data.repositories.FinancesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FinancesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepositoryImpl
) : FinancesRepository {

    val currentUser = authRepository.getCurrentUser()
    val userId = currentUser?.uid

    private val collectionRef = firestore.collection("Users/${userId}/Finances")

    override suspend fun createFinance(finance: Finance): Result<Unit> {
        return try {
            collectionRef.add(finance)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateFinance(id: String, finance: Finance): Result<Unit> {
        return try {
            collectionRef.document(id).set(finance).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteFinance(id: String): Result<Unit> {
        return try {
            collectionRef.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFinance(id: String): Result<Finance> {
        return try {
            val snapshot = collectionRef.document(id).get().await()
            Log.d("FinancesRepository", "getFinance SNAPSHOT: $snapshot")
            val finances = snapshot.toObject(Finance::class.java)!!.copy(id = snapshot.id)
            Result.success(finances)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllFinances(): Result<List<Finance>> {
        return try {
            val snapshot = collectionRef.get().await()
            Log.d("FinancesRepository", "getAllFinances SNAPSHOT: ${snapshot.documents}")
            val finances = snapshot.documents.map { it.toObject(Finance::class.java)!!.copy(id = it.id) }
            Log.d("FinancesRepository", "getAllFinances: $finances")
            Result.success(finances)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}