package com.raka.suitmediatest.di

import android.content.Context
import com.raka.suitmediatest.data.AppRepository
import com.raka.suitmediatest.data.remote.ApiConfig

object Injection {
    fun providerRepository(context: Context): AppRepository{
        val apiService = ApiConfig.getService()
        return AppRepository(apiService)
    }
}