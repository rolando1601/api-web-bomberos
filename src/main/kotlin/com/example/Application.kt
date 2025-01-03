package com.example

import com.example.plugins.*

import io.ktor.server.application.*
import com.example.dao.DatabaseSingleton
import com.example.dao.DAOFacadeImpl
import com.example.routes.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    // Configura CORS
    configureCORS()

    // Configura Content Negotiation
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    // Inicializa la base de datos
    DatabaseSingleton.init()

    // Instancia DAO
    val dao = DAOFacadeImpl()

    // Configura las rutas
    routing {
        materialPRoutes(dao)
        vehiculoRoutes(dao)
        victimaRoutes(dao)
        inmuebleRoutes(dao)
        institucionRoutes(dao)
        cuerpoRoutes(dao)
        parteEmergenciaRoutes(dao)
        parteAsistenciaRoutes(dao)
        movilRoutes(dao)
        voluntarioRoutes(dao)
        companiaRoutes(dao)
        usuarioRoutes(dao)
        emergenciaRoutes(dao)
        parteAsistenciaMovilRoutes(dao)
        parteEmergenciaMovilRoutes(dao)
        parteEmergenciaVoluntarioRoutes(dao)
        parteAsistenciaVoluntarioRoutes(dao)
    }
}
