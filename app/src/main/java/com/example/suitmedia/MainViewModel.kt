package com.example.suitmedia

import androidx.lifecycle.ViewModel
import com.example.suitmedia.data.Repository
import com.example.suitmedia.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun setNameUserLogin(name: String) = repository.setNameUserLogin(name)

    fun getValueNameLogin() = repository.getValueNameLogin()

    fun setSelectedUser(user: User) = repository.setSelectedUser(user)

    fun getSelectedUser() = repository.getSelectedUser()

    val users = repository.getUsers()
}