package com.example.plugins

import com.example.dao.DAOFacade
import com.example.dao.DAOFacadeImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val dao = DAOFacadeImpl()

    routing {
        get("/") {

        }

    }
}
