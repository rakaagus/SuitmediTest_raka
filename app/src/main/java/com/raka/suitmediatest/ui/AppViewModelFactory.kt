package com.raka.suitmediatest.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raka.suitmediatest.data.AppRepository
import com.raka.suitmediatest.di.Injection
import com.raka.suitmediatest.ui.thirdscreen.ThirdViewModel

class AppViewModelFactory(private val appRepository: AppRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThirdViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        @Volatile
        private var INSTANCE: AppViewModelFactory? = null

        fun getInstance(context: Context):AppViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: AppViewModelFactory(Injection.providerRepository(context))
            }.also { INSTANCE = it }
    }
}