package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Movil
import com.example.dao.DAOFacadeImpl
import com.example.models.Moviles
import io.ktor.server.request.*

fun Route.movilRoutes(dao: DAOFacadeImpl) {
    route("/movil") {

        // Ruta para obtener todos los móviles
        get("/obtener") {
            try {
                val moviles = dao.allMoviles() // Método DAO para obtener todos los móviles
                call.respond(HttpStatusCode.OK, moviles)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener móviles: ${e.message}")
                )
            }
        }
        post("/crear") {
            try {
                val nuevoMovil = call.receive<Moviles>() // Recibe el cuerpo de la solicitud como un objeto Moviles
                val movilCreado = dao.createMovil(
                    nomenclatura = nuevoMovil.nomenclatura,
                    especialidad = nuevoMovil.especialidad,
                )
                call.respond(HttpStatusCode.Created, movilCreado) // Responde con el móvil creado
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear el móvil: ${e.message}")
                )
            }
        }


    }
}
