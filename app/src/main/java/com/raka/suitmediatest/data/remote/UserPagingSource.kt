package com.raka.suitmediatest.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raka.suitmediatest.data.remote.response.DataItem

class UserPagingSource(private val apiService: ApiService) : PagingSource<Int, DataItem>() {
    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getUser(page, params.loadSize)

            LoadResult.Page(
                data = responseData.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.data.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}