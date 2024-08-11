package com.project.mentalhealth.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.mentalhealth.R
import com.project.mentalhealth.databinding.ActivityAuthenticationBinding
import com.project.mentalhealth.ui.auth.fragments.login.LoginFragment

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.authentication_container, LoginFragment())
                .commit()
        }
    }
}