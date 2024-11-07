package com.mohaberabi.androidtestingdeepdive.doubles

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PaymentProcessorTest {


    @Test
    fun `test stubbing`() {

        val service: PaymentServices = mockk()
        val processor = PaymentProcessor(service)
        every { service.processPayment(any()) } returns true
        val result = processor.pay(100)
        assertEquals(result, "true")
    }
}