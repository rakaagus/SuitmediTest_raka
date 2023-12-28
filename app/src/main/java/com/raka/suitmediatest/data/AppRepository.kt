package com.raka.suitmediatest.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.raka.suitmediatest.data.remote.ApiService
import com.raka.suitmediatest.data.remote.UserPagingSource
import com.raka.suitmediatest.data.remote.response.DataItem

class AppRepository(private val apiService: ApiService) {

    fun getUser(): LiveData<PagingData<DataItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }


    companion object{
        private var INSTANCE: AppRepository? = null
        fun getInstance(
            apiService: ApiService,
        ) : AppRepository = INSTANCE ?: synchronized(this){
            INSTANCE ?: AppRepository(
                apiService,
            )
        }.also { INSTANCE = it }
    }
}