package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.Usuarios
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val nombreUsuario: String, val contrasena: String)

@Serializable
data class LoginResponse(val message: String, val usuario: Usuarios)

fun Route.usuarioRoutes(dao: DAOFacadeImpl) {
    route("/usuario") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Usuarios. Usa /crear, /obtener, /eliminar o /actualizar para más acciones.")
        }

        // Obtener todos los usuarios
        get("/obtener") {
            try {
                val usuarios = dao.allUsuarios()
                call.respond(HttpStatusCode.OK, usuarios)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener usuarios: ${e.message}"))
            }
        }

        // Obtener un usuario por ID
        get("/{id}") {
            val idUsuario = call.parameters["id"]?.toIntOrNull()
            if (idUsuario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@get
            }
            try {
                val usuario = dao.getUsuario(idUsuario)
                if (usuario != null) {
                    call.respond(HttpStatusCode.OK, usuario)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Usuario no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener usuario: ${e.message}"))
            }
        }

        // Crear un nuevo usuario
        post("/crear") {
            try {
                val nuevoUsuario = call.receive<Usuarios>()
                val usuarioCreado = dao.createUsuario(
                    nombreUsuario = nuevoUsuario.nombreUsuario,
                    contrasena = nuevoUsuario.contrasena
                )
                call.respond(HttpStatusCode.Created, usuarioCreado)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear usuario: ${e.message}"))
            }
        }

        // Eliminar un usuario por ID
        delete("/eliminar/{id}") {
            val idUsuario = call.parameters["id"]?.toIntOrNull()
            if (idUsuario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@delete
            }
            try {
                val eliminado = dao.deleteUsuario(idUsuario)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Usuario eliminado correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Usuario no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar usuario: ${e.message}"))
            }
        }

        // Actualizar un usuario
        put("/actualizar/{id}") {
            val idUsuario = call.parameters["id"]?.toIntOrNull()
            if (idUsuario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@put
            }
            try {
                val datosActualizados = call.receive<Usuarios>()
                val usuarioActualizado = dao.updateUsuario(
                    idUsuario = idUsuario,
                    nombreUsuario = datosActualizados.nombreUsuario,
                    contrasena = datosActualizados.contrasena
                )
                call.respond(HttpStatusCode.OK, usuarioActualizado)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar usuario: ${e.message}"))
            }
        }

        // Login de usuario
        post("/login") {
            try {
                val loginRequest = call.receive<LoginRequest>()
                val usuario = dao.loginUsuario(
                    nombreUsuario = loginRequest.nombreUsuario,
                    contrasena = loginRequest.contrasena
                )
                if (usuario != null) {
                    call.respond(
                        HttpStatusCode.OK,
                        LoginResponse(
                            message = "Login exitoso",
                            usuario = usuario.copy(contrasena = "") // Excluir contraseña
                        )
                    )
                } else {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Credenciales incorrectas"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al intentar iniciar sesión: ${e.message}"))
            }
        }
    }
}
