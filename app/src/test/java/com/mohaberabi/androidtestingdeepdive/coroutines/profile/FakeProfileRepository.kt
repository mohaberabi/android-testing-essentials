package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import com.mohaberabi.androidtestingdeepdive.common.Friend
import kotlinx.coroutines.delay

class FakeProfileRepository : ProfileRepository {
    override suspend fun getName(id: String): String = "test"

    override suspend fun getFriends(): List<Friend> = listOf(Friend("test", "test"))

    override suspend fun getRate(): Float = -100f
}

class FakeProfileRepositoryV2 : ProfileRepository {
    override suspend fun getName(id: String): String = "test"

    override suspend fun getFriends(): List<Friend> {
        delay(1000L)
        return listOf(Friend("test", "test"))
    }

    override suspend fun getRate(): Float {
        delay(1000L)
        return -100f
    }
}
