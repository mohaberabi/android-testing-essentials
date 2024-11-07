package com.mohaberabi.androidtestingdeepdive.doubles


data class Dep1(val value: Int)
data class Dep2(val value: Int)


class MyCalculator(
    private val dep1: Dep1,
    private val dep2: Dep2
) {


    fun add() = dep2.value + dep1.value
    fun subtract() = dep2.value - dep1.value

}