package com.project.mentalhealth.data.remote.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class FirebaseHelper(
    private val database: FirebaseDatabase = Firebase.database
) {
    private val userReference = database.reference.child(USERS)
    private val profileReference = userReference.child(PASSWORD)
    private val appointmentReference = userReference.child(APPOINTMENTS)

    suspend fun login(email: String, password: String): Boolean {
        return try {
            val dataSnapshot = userReference.child(email).get().await()
            val truePass = dataSnapshot.child(PASSWORD).value
            truePass == password
        } catch (e: Exception) {
            false
        }
    }

    suspend fun register(email: String, password: String): Boolean {
        return try {
            userReference.child(email).child(PASSWORD).setValue(password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun makeAppointment(username: String, appointmentDate: String): Boolean {
        return try {
            userReference.child(username).child(APPOINTMENTS).push().setValue(appointmentDate).await()
            true
        } catch (e: Exception) {
            false
        }
    }


    companion object {
        private const val USERS = "users"
        private const val PASSWORD = "password"
        private const val APPOINTMENTS = "appointments"
    }
}