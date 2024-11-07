package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import com.mohaberabi.androidtestingdeepdive.common.Friend

interface ProfileRepository {


    suspend fun getName(id: String): String
    suspend fun getFriends(): List<Friend>
    suspend fun getRate(): Float
}