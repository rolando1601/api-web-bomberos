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

            createVoluntario(2, "voluntario1", "1990-01-01".toLocalDate(), "micasa", "9918111444","ab", "diabetico", "alergia", "2024-01-01".toLocalDate(), "clave", "cargo", "197976888", 1,null)
        }
    }
    // configureSerialization()
    //  configureDatabases()
    configureRouting()
}
