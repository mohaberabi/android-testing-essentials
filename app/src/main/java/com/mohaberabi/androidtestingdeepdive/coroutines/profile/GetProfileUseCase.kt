package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import com.mohaberabi.androidtestingdeepdive.common.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GetProfileUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke() = coroutineScope {
        val name = async { profileRepository.getName("test") }
        val rate = async { profileRepository.getRate() }
        val friends = async { profileRepository.getFriends() }
        Profile(
            name = name.await(),
            rate = rate.await(),
            friends = friends.await()
        )

    }
}

class GetProfileUseCase2(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke() = coroutineScope {
        val name = profileRepository.getName("test")
        val rate = profileRepository.getRate()
        val friends = profileRepository.getFriends()
        Profile(
            name = name,
            rate = rate,
            friends = friends
        )

    }
}

class GetProfileUseCaseSwitchDispatcher(
    private val profileRepository: ProfileRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke() = withContext(dispatcher) {
        val name = profileRepository.getName("test")
        val rate = profileRepository.getRate()
        val friends = profileRepository.getFriends()
        Profile(
            name = name,
            rate = rate,
            friends = friends
        )

    }
}