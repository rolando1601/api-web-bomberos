package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import com.example.dao.DatabaseSingleton
import com.example.dao.DAOFacadeImpl
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init()
    val dao = DAOFacadeImpl().apply {
        runBlocking {
              deleteVoluntario(1)

        }
    }
    // configureSerialization()
    //  configureDatabases()
    configureRouting()
}
