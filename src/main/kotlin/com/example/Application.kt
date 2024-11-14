package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import com.example.dao.DatabaseSingleton
import com.example.dao.DAOFacadeImpl
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init()
    val dao = DAOFacadeImpl().apply {
        runBlocking {
            createVoluntario(1, "Juan Perez")
        }
    }
    // configureSerialization()
    //  configureDatabases()
    configureRouting()
}
