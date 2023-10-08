package com.example.portfoliochildemotionpreventappall.appViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel private constructor() : ViewModel() {
    private val jwtTokenLiveData = MutableLiveData<String>()

    private val userIdLiveData = MutableLiveData<String>()
    private val userPwdLiveData = MutableLiveData<String>()

    private val messageListLiveData = MutableLiveData<List<String>>()

    private val userLiveData = MutableLiveData<String>()

    fun setJwtToken(token: String) {
        jwtTokenLiveData.value = token
    }

    fun getJwtToken(): LiveData<String> {
        return jwtTokenLiveData
    }

    fun setUserId(id: String) {
        userIdLiveData.value = id
    }

    fun getUserId(): LiveData<String> {
        return userIdLiveData
    }

    fun setUserPwd(pwd: String) {
        userPwdLiveData.value = pwd
    }

    fun getUserPwd(): LiveData<String> {
        return userPwdLiveData
    }

    fun setMessageList(messages: List<String>) {
        messageListLiveData.value = messages
    }

    fun getMessageList(): LiveData<List<String>> {
        return messageListLiveData
    }

    fun setUser(user: String) {
        userLiveData.value = user
    }

    fun getUser(): LiveData<String> {
        return userLiveData
    }

    companion object {
        private var instance: AppViewModel? = null

        fun getInstance(): AppViewModel {
            if (instance == null) {
                instance = AppViewModel()
            }
            return instance!!
        }
    }
}