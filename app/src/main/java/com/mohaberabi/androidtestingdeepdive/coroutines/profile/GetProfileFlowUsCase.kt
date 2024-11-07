package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import com.mohaberabi.androidtestingdeepdive.common.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

class GetProfileFlowUsCase(
    private val profileRepository: ProfileRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    operator fun invoke() = flow {
        val name = profileRepository.getName("test")
        val rate = profileRepository.getRate()
        val friends = profileRepository.getFriends()
        val profile = Profile(
            name = name,
            rate = rate,
            friends = friends
        )
        emit(Result.success(profile))
    }.retry(2) {
        (it is Exception).also { delay(1000) }
    }.catch { emit(Result.failure(it)) }
        .flowOn(dispatcher)
}
