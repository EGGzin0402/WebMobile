package com.example.projetofinancas.domain

import android.app.Application
import com.example.projetofinancas.data.AuthRepositoryImpl
import com.example.projetofinancas.data.FinancesRepositoryImpl
import com.example.projetofinancas.data.repositories.AuthRepository
import com.example.projetofinancas.data.repositories.FinancesRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Provides
    fun provideFirebaseApp(application: Application): FirebaseApp {
        return FirebaseApp.initializeApp(application) ?: FirebaseApp.getInstance()
    }

    @Provides
    fun provideFinancesRepository(firestore: FirebaseFirestore, authRepository: AuthRepositoryImpl): FinancesRepository {
        return FinancesRepositoryImpl(firestore, authRepository)
    }

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

}