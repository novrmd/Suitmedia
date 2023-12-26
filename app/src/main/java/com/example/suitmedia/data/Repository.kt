package com.example.suitmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService
) {

    private val nameLogin = MediatorLiveData<String>()
    private val selectedUser = MediatorLiveData<User>()
    private val users = MutableLiveData<Result<PagingData<User>>>()

    fun setSelectedUser(user: User) {
        selectedUser.value = user
    }

    fun getSelectedUser(): LiveData<User> = selectedUser

    fun setNameUserLogin(name: String) {
        nameLogin.value = name
    }

    fun getValueNameLogin(): LiveData<String> = nameLogin

    fun getUsers(): LiveData<Result<PagingData<User>>> {
        users.value = Result.Loading
        Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 3,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { PagingSource(apiService) }
        ).liveData.observeForever { users.value = Result.Success(it) }
        return users
    }
}