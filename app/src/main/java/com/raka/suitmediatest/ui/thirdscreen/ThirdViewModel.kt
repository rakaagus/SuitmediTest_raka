package com.raka.suitmediatest.ui.thirdscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raka.suitmediatest.data.AppRepository
import com.raka.suitmediatest.data.remote.response.DataItem

class ThirdViewModel(appRepository: AppRepository): ViewModel() {
    val user: LiveData<PagingData<DataItem>> =
        appRepository.getUser().cachedIn(viewModelScope)
}