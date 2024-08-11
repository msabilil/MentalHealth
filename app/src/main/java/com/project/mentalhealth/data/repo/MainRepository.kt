package com.project.mentalhealth.data.repo

import androidx.lifecycle.liveData
import com.project.mentalhealth.data.remote.firebase.FirebaseHelper
import com.project.mentalhealth.data.remote.network.ApiService
import com.project.mentalhealth.utils.AccountPreferences
import com.project.mentalhealth.utils.Result

class MainRepository(
    private val firebaseHelper: FirebaseHelper,
    private val accountPreferences: AccountPreferences,
    private val apiService: ApiService
) {
    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val loginResponse = firebaseHelper.login(email, password)

            if (loginResponse) {
                accountPreferences.savePreferences(email, true)
                emit(Result.Success(true))
            } else {
                emit(Result.Error("Invalid Credentials!"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val registerResponse = firebaseHelper.register(email, password)

            if (registerResponse) {
                emit(Result.Success(true))
            } else {
                emit(Result.Error("Error creating account"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun makeAppointment(username: String, appointmentDate: String) = liveData {
        emit(Result.Loading)
        try {
            val registerResponse = firebaseHelper.register(username, appointmentDate)

            if (registerResponse) {
                emit(Result.Success(true))
            } else {
                emit(Result.Error("Error making appointment"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getNews() = liveData {
        emit(Result.Loading)
        try {
            val newsResponse = apiService.getNews()
            emit(Result.Success(newsResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun logout() = accountPreferences.clearPreferences()

    fun getLoggedIn() = accountPreferences.getIsLoggedIn()
    fun getUsername() = accountPreferences.getUsername()
}