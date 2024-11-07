package com.mohaberabi.androidtestingdeepdive.doubles


interface UsersSource {
    fun getUsersCount(): Int
}


class RealUserSource : UsersSource {

    override fun getUsersCount(): Int = 100
}

class UserRepository(
    private val source: UsersSource
) {
    fun getUsersCount() = source.getUsersCount()
}