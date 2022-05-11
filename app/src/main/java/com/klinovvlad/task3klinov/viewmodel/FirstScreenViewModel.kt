package com.klinovvlad.task3klinov.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klinovvlad.task3klinov.db.UserDatabaseEntity
import com.klinovvlad.task3klinov.model.*
import com.klinovvlad.task3klinov.utils.USER_DATABASE_LIMIT
import javax.inject.Inject

class FirstScreenViewModel(private val usersDecorator: UsersDecorator) : ViewModel() {

    private val _dataList = MutableLiveData<List<UserDatabaseEntity>>()
    val dataList: LiveData<List<UserDatabaseEntity>>
        get() = _dataList

    fun getUsers() {
        val currentUsers = _dataList.value ?: emptyList()
        usersDecorator.getUsers(
            currentUsers.size,
            { users -> _dataList.postValue(currentUsers + users) },
            USER_DATABASE_LIMIT
        )
    }

}