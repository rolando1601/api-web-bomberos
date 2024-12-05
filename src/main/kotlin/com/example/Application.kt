package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import com.example.dao.DatabaseSingleton
import com.example.dao.DAOFacadeImpl
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
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
            createParteAsistencia( "incendio", "cuerpo1", "compania2", "2024-01-01".toLocalDate(),"12:20".toLocalTime(), "12:30".toLocalTime(), "chillan", 6, "clave", null)
            //createCompania(1, "compania1", "direccion1", "especialidad1", null)
            //createVehiculo(1, "patente1", "marca1", "modelo1", "color1",null )
        }
    }
    // configureSerialization()
    //  configureDatabases()
    configureRouting()
}
