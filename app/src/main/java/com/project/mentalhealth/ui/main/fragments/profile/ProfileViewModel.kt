package com.project.mentalhealth.ui.main.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mentalhealth.data.repo.MainRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getUsername() = runBlocking { mainRepository.getUsername().first() }
    fun logout() {
        viewModelScope.launch {
            mainRepository.logout()
        }
    }
}