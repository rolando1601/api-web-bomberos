package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*

fun Application.configureCORS() {
    install(CORS) {
        anyHost()  // Permite cualquier host (útil si tu API es accesible desde cualquier dominio)
        allowHost("localhost", schemes = listOf("http", "https")) // Permite localhost con cualquier esquema (HTTP o HTTPS)
        allowHost("80bd730667f2.ngrok.app", schemes = listOf("http", "https")) // ngrok, permitiendo ambos esquemas
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("Custom-Header") // Reemplaza con cualquier encabezado específico si lo conoces
        allowNonSimpleContentTypes = true  // Permitir contenido no simple
        allowHeader("ngrok-skip-browser-warning")
        allowCredentials = true  // Permite credenciales como cookies o autenticación

    }
}