package com.project.mentalhealth

import android.app.Application
import com.project.mentalhealth.di.dataStoreModules
import com.project.mentalhealth.di.helpersModule
import com.project.mentalhealth.di.networkModules
import com.project.mentalhealth.di.repositoryModules
import com.project.mentalhealth.di.viewModelModules
import com.project.mentalhealth.utils.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MentalHealth : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MentalHealth)
            modules(
                dataStoreModules,
                networkModules,
                helpersModule,
                repositoryModules,
                viewModelModules
            )
        }
    }
}