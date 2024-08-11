package com.project.mentalhealth.ui.auth.fragments.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.mentalhealth.data.repo.MainRepository
import com.project.mentalhealth.utils.Event

class LoginViewModel(private val repository: MainRepository): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val toastMessage = MutableLiveData<Event<String>>()

    fun login(email: String, password: String) = repository.login(email, password)

    fun getIsLoggedIn() = repository.getLoggedIn().asLiveData()
}