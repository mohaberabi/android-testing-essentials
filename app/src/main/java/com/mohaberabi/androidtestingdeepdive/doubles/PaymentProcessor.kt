package com.mohaberabi.androidtestingdeepdive.doubles


interface PaymentServices {
    fun processPayment(amount: Int): Boolean
}


class PaymentProcessor(
    private val service: PaymentServices
) {


    fun pay(amount: Int): String {
        val done = service.processPayment(amount)
        return "$done"
    }
}