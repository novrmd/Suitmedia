package com.example.suitmedia.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class PagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, User>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> = try {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val responseData = apiService.getUsers(position, 3)
        LoadResult.Page(
            data = responseData.data!!,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (responseData.data.isNullOrEmpty()) null else position + 1
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}