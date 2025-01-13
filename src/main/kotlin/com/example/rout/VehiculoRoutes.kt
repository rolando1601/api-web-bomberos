package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.CreateVehiculosRequest
import com.example.models.Vehiculos
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.vehiculoRoutes(dao: DAOFacadeImpl) {
    route("/vehiculo") {

        // Ruta base para /vehiculo
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Vehículo. Usa /crear, /obtener o /{idVehiculo} para más acciones.")
        }

        // Obtener todos los vehículos (GET /vehiculo/obtener)
        get("/obtener") {
            try {
                val vehiculos = dao.allVehiculos() // Método DAO para obtener todos los vehículos
                call.respond(HttpStatusCode.OK, vehiculos)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener vehículos: ${e.message}"))
            }
        }

        // Crear un nuevo vehículo (POST /vehiculo/crear)
        post("/crear") {
            try {
                val vehiculo = call.receive<Vehiculos>()

                val createdVehiculo = dao.createVehiculo(
                    patente = vehiculo.patente,
                    marca = vehiculo.marca,
                    modelo = vehiculo.modelo,
                    tipoVehiculo = vehiculo.tipoVehiculo,
                    folioPEmergencia = vehiculo.folioPEmergencia
                )
                call.respond(HttpStatusCode.Created, createdVehiculo)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear vehículo: ${e.message}"))
            }
        }

        // Obtener un vehículo por ID (GET /vehiculo/{idVehiculo})
        get("/{idVehiculo}") {
            val idVehiculo = call.parameters["idVehiculo"]?.toIntOrNull()
            if (idVehiculo == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de vehículo inválido o faltante."))
                return@get
            }
            try {
                val vehiculo = dao.getVehiculo(idVehiculo)
                if (vehiculo == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Vehículo no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, vehiculo)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener vehículo: ${e.message}"))
            }
        }

        // Actualizar un vehículo (PUT /vehiculo/actualizar/{idVehiculo})
        put("/actualizar/{idVehiculo}") {
            val idVehiculo = call.parameters["idVehiculo"]?.toIntOrNull()
            if (idVehiculo == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de vehículo inválido o faltante."))
                return@put
            }
            try {
                val vehiculo = call.receive<Vehiculos>()
                val updatedVehiculo = dao.updateVehiculo(
                    idVehiculo = idVehiculo,
                    patente = vehiculo.patente,
                    marca = vehiculo.marca,
                    modelo = vehiculo.modelo,
                    tipoVehiculo = vehiculo.tipoVehiculo,
                    folioPEmergencia = vehiculo.folioPEmergencia
                )
                call.respond(HttpStatusCode.OK, updatedVehiculo)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar vehículo: ${e.message}"))
            }
        }

        // Eliminar un vehículo (DELETE /vehiculo/eliminar/{idVehiculo})
        delete("/eliminar/{idVehiculo}") {
            val idVehiculo = call.parameters["idVehiculo"]?.toIntOrNull()
            if (idVehiculo == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de vehículo inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteVehiculo(idVehiculo)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Vehículo eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Vehículo no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar vehículo: ${e.message}"))
            }
        }

        post("/crear-varios") {
            val request = call.receive<CreateVehiculosRequest>()

            // Verifica que el folioPEmergencia exista
            val parteEmergenciaExiste = dao.getParteEmergencia(request.folioPEmergencia) != null
            if (!parteEmergenciaExiste) {
                call.respond(HttpStatusCode.BadRequest, "El folioPEmergencia no existe.")
                return@post
            }

            // Mapea los datos recibidos al modelo Vehiculos
            val vehiculos = request.vehiculos.map { vehiculo ->
                Vehiculos(
                    patente = vehiculo.patente,
                    marca = vehiculo.marca,
                    modelo = vehiculo.modelo,
                    tipoVehiculo = vehiculo.tipoVehiculo,
                    folioPEmergencia = request.folioPEmergencia
                )
            }

            // Llama al DAO para guardar los vehículos
            val result = dao.createVehiculos(vehiculos, request.folioPEmergencia)

            // Devuelve la respuesta con los vehículos creados
            call.respond(HttpStatusCode.Created, result)
        }
    }
}
