package com.example.projetofinancas.ui.elements.account

import androidx.lifecycle.ViewModel
import com.example.projetofinancas.data.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class accountViewModel @Inject constructor (
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logout() {
        authRepository.logout()
    }

    fun getCurrentUser() = authRepository.getCurrentUser()

}