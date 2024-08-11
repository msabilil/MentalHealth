package com.project.mentalhealth.ui.auth.fragments.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.mentalhealth.data.repo.MainRepository
import com.project.mentalhealth.utils.Event

class RegisterViewModel(private val repository: MainRepository): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val toastMessage = MutableLiveData<Event<String>>()

    fun register(email: String, password: String) = repository.register(email, password)
}