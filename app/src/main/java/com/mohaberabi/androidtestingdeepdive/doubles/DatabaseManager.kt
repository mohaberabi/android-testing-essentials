package com.mohaberabi.androidtestingdeepdive.doubles


interface AppDataBase {
    fun save(data: String)
    fun get(id: String): String?
}


class InMemoryDatabase : AppDataBase {
    private val database = mutableMapOf<String, String>()
    override fun save(data: String) {
        database[data] = data
    }

    override fun get(id: String): String? = database[id]

}

class DatabaseManager(
    private val database: AppDataBase
) {
    fun save(data: String) = database.save(data)

    fun get(id: String): String? = database.get(id)
}