package com.project.mentalhealth.di

import com.project.mentalhealth.BuildConfig
import com.project.mentalhealth.data.remote.firebase.FirebaseHelper
import com.project.mentalhealth.data.remote.network.ApiService
import com.project.mentalhealth.data.repo.MainRepository
import com.project.mentalhealth.ui.auth.fragments.login.LoginViewModel
import com.project.mentalhealth.ui.auth.fragments.register.RegisterViewModel
import com.project.mentalhealth.ui.main.MainViewModel
import com.project.mentalhealth.ui.main.fragments.home.HomeViewModel
import com.project.mentalhealth.ui.main.fragments.profile.ProfileViewModel
import com.project.mentalhealth.utils.AccountPreferences
import com.project.mentalhealth.utils.dataStore
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataStoreModules = module {
    single { AccountPreferences.getInstance(androidContext().dataStore) }
}

val networkModules = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val helpersModule = module {
    single { FirebaseHelper() }
}

val repositoryModules = module {
    single { MainRepository(get(), get(), get()) }
}

val viewModelModules = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { MainViewModel(get()) }
}