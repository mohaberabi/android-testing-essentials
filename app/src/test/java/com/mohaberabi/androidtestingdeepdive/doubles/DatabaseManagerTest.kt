package com.mohaberabi.androidtestingdeepdive.doubles

import junit.framework.TestCase.assertEquals
import org.junit.Test

class DatabaseManagerTest {


    val fakeDatabase = object : AppDataBase {
        var saved = ""
        override fun save(data: String) {
            saved = data
        }

        override fun get(id: String): String = saved
    }


    @Test

    fun ` testing the fakes `() {
        val manager = DatabaseManager(fakeDatabase)
        manager.save("mohab")
        assertEquals(manager.get("mohab"), "mohab")
    }
}