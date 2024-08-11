package com.project.mentalhealth.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.mentalhealth.data.repo.MainRepository
import com.project.mentalhealth.utils.Event
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainViewModel(private val repository: MainRepository): ViewModel() {
    val appointmentDate = MutableLiveData<String>()
    val username = runBlocking { repository.getUsername().first() }

    fun makeAppointment() = repository.makeAppointment(username, appointmentDate.value ?: "")
}