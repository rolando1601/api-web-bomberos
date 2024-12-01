package com.example.dao

import com.example.models.*
import com.typesafe.config.ConfigFactory
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseSingleton {

    fun init(){
        val config = ConfigFactory.load()
        val driverClassName = config.getString("database.driverClassName")
        val jdbcURL = config.getString("database.jdbcURL")
        val user = config.getString("database.user")
        val password = config.getString("database.password")
        val database = Database.connect(jdbcURL, driverClassName, user, password)
        transaction(database) {
            SchemaUtils.create(Voluntarios)
        }

    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}