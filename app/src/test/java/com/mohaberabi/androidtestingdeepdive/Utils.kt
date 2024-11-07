package com.mohaberabi.androidtestingdeepdive

import junit.framework.TestCase.assertEquals


infix fun <T> T.shouldBeEqualTo(other: T) {
    assertEquals(this, other)
}

infix fun <T> T.shouldNotEqualTo(other: T) {
    assertEquals(this == other, false)
}

infix fun CharSequence.hasSize(s: Int) {
    assertEquals(this.length, s)
}

val Boolean.shouldBeTrue
    get() = assertEquals(
        this,
        true
    )
val Boolean.shouldBeFalse
    get() = assertEquals(
        this,
        false
    )