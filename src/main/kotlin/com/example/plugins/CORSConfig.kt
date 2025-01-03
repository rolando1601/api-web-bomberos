package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*

fun Application.configureCORS() {
    install(CORS) {
        anyHost()
        allowHost("localhost:3000")
        allowHost("e9d6-190-211-2-199.ngrok-free.app", schemes = listOf("https"))
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("Custom-Header") // Reemplaza con el encabezado específico si lo sabes
        allowNonSimpleContentTypes = true // Permitir encabezados no simples
        allowHeader("ngrok-skip-browser-warning")
        allowCredentials = true
    }
}
