package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Usuarios
import com.example.dao.DAOFacadeImpl
import kotlinx.serialization.Serializable
import com.example.models.LoginRequest
import com.example.models.LoginResponse

fun Route.usuarioRoutes(dao: DAOFacadeImpl) {
    route("/usuario") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Usuarios. Usa /crear, /obtener, /eliminar o /actualizar para más acciones.")
        }

        // Obtener todos los usuarios (GET /usuario/obtener)
        get("/obtener") {
            try {
                val usuarios = dao.allUsuarios()
                call.respond(HttpStatusCode.OK, usuarios)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener usuarios: ${e.message}")
                )
            }
        }

        // Obtener un usuario por ID (GET /usuario/{id})
        get("/{id}") {
            try {
                val idUsuario = call.parameters["id"]?.toIntOrNull()
                if (idUsuario == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@get
                }

                val usuario = dao.getUsuario(idUsuario)
                if (usuario != null) {
                    call.respond(HttpStatusCode.OK, usuario)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Usuario no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener usuario: ${e.message}")
                )
            }
        }

        // Crear un nuevo usuario (POST /usuario/crear)
        post("/crear") {
            try {
                val nuevoUsuario = call.receive<Usuarios>()

                val usuarioCreado = dao.createUsuario(
                    nombreUsuario = nuevoUsuario.nombreUsuario,
                    contrasena = nuevoUsuario.contrasena,
                    idRol = nuevoUsuario.idRol
                )
                call.respond(HttpStatusCode.Created, usuarioCreado)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear usuario: ${e.message}")
                )
            }
        }

        // Eliminar un usuario por ID (DELETE /usuario/eliminar/{id})
        delete("/eliminar/{id}") {
            try {
                val idUsuario = call.parameters["id"]?.toIntOrNull()
                if (idUsuario == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@delete
                }

                val eliminado = dao.deleteUsuario(idUsuario)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Usuario eliminado correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Usuario no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar usuario: ${e.message}")
                )
            }
        }

        // Actualizar un usuario (PUT /usuario/actualizar/{id})
        put("/actualizar/{id}") {
            try {
                val idUsuario = call.parameters["id"]?.toIntOrNull()
                if (idUsuario == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@put
                }

                val datosActualizados = call.receive<Usuarios>()

                val usuarioActualizado = dao.updateUsuario(
                    idUsuario = idUsuario,
                    nombreUsuario = datosActualizados.nombreUsuario,
                    contrasena = datosActualizados.contrasena,
                    idRol = datosActualizados.idRol
                )
                call.respond(HttpStatusCode.OK, usuarioActualizado)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar usuario: ${e.message}"))
            }
        }

        post("/login") {
            try {
                val loginRequest = call.receive<LoginRequest>()
                val (nombreUsuario, contrasena) = loginRequest


                if (nombreUsuario.isBlank() || contrasena.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Nombre de usuario o contraseña no pueden estar vacíos"))
                    return@post
                }

                val usuario = dao.loginUsuario(nombreUsuario, contrasena)

                if (usuario != null) {
                    val usuarioRespuesta = usuario.copy(contrasena = "") // Excluir la contraseña de la respuesta
                    val response = LoginResponse(
                        message = "Login exitoso",
                        usuario = usuarioRespuesta
                    )
                    call.respond(HttpStatusCode.OK, response)
                } else {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Credenciales incorrectas"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al intentar iniciar sesión: ${e.message}"))
            }
        }




    }
}
