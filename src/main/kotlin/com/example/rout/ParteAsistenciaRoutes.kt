package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.ParteAsistenciaRequest
import com.example.models.ParteAsistenciaResponse
import com.example.models.Partes_asistencia
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteAsistenciaRoutes(dao: DAOFacadeImpl) {
    route("/parte-asistencia") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Asistencia. Usa /crear, /obtener, /actualizar, /eliminar o /buscar para más acciones.")
        }

        // Obtener todos los partes de asistencia
        get("/obtener") {
            try {
                val partesAsistencia = dao.allPartesAsistencia()
                call.respond(HttpStatusCode.OK, partesAsistencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener partes de asistencia: ${e.message}")
                )
            }
        }

        // Crear un nuevo parte de asistencia
        post("/crear") {
            try {
                val parteAsistencia = call.receive<Partes_asistencia>()
                val createdParteAsistencia = dao.createParteAsistencia(
                    aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                    aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                    fechaAsistencia = parteAsistencia.fechaAsistencia,
                    horaInicio = parteAsistencia.horaInicio,
                    horaFin = parteAsistencia.horaFin,
                    direccionAsistencia = parteAsistencia.direccionAsistencia,
                    totalAsistencia = parteAsistencia.totalAsistencia,
                    observaciones = parteAsistencia.observaciones,
                    idTipoLlamado = parteAsistencia.idTipoLlamado
                )
                call.respond(HttpStatusCode.Created, createdParteAsistencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear parte de asistencia: ${e.message}")
                )
            }
        }

        // Buscar un parte de asistencia por folio
        get("/buscar/{folioPAsistencia}") {
            val folioPAsistencia = call.parameters["folioPAsistencia"]?.toIntOrNull()
            if (folioPAsistencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPAsistencia inválido o faltante."))
                return@get
            }
            try {
                //obtiene el parte de asistencia y sus relaciones
                val partesAsistencia = dao.getParteAsistencia(folioPAsistencia)
                if (partesAsistencia != null){
                    val (tipoCitacion, moviles,voluntarios ) = dao.getParteAsistenciaWithRelations(folioPAsistencia)
                    val response = ParteAsistenciaResponse(
                        folioPAsistencia = partesAsistencia.folioPAsistencia,
                        aCargoDelCuerpo = partesAsistencia.aCargoDelCuerpo,
                        encargadoCuerpo = dao.getVoluntario(partesAsistencia.aCargoDelCuerpo),
                        aCargoDeLaCompania = partesAsistencia.aCargoDeLaCompania,
                        encargadoCompania = dao.getVoluntario(partesAsistencia.aCargoDeLaCompania),
                        fechaAsistencia = partesAsistencia.fechaAsistencia,
                        horaInicio = partesAsistencia.horaInicio,
                        horaFin = partesAsistencia.horaFin,
                        direccionAsistencia = partesAsistencia.direccionAsistencia,
                        totalAsistencia = partesAsistencia.totalAsistencia,
                        observaciones = partesAsistencia.observaciones,
                        idTipoLlamado = partesAsistencia.idTipoLlamado,
                        tipoLlamado = tipoCitacion,
                        voluntarios = voluntarios,
                        moviles = moviles
                    )
                    call.respond(HttpStatusCode.OK, response)
                }else{
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de asistencia no encontrado."))
                }
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al buscar Parte de asistencia: ${e.message}")
                )
            }
        }

        // Actualizar un parte de asistencia
        put("/actualizar/{folioPAsistencia}") {
            val folioPAsistencia = call.parameters["folioPAsistencia"]?.toIntOrNull()
            if (folioPAsistencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPAsistencia inválido o faltante."))
                return@put
            }
            try {
                val parteAsistencia = call.receive<Partes_asistencia>()
                val updatedParteAsistencia = dao.updateParteAsistencia(
                    folioPAsistencia = folioPAsistencia,
                    aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                    aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                    fechaAsistencia = parteAsistencia.fechaAsistencia,
                    horaInicio = parteAsistencia.horaInicio,
                    horaFin = parteAsistencia.horaFin,
                    direccionAsistencia = parteAsistencia.direccionAsistencia,
                    totalAsistencia = parteAsistencia.totalAsistencia,
                    observaciones = parteAsistencia.observaciones,
                    idTipoLlamado = parteAsistencia.idTipoLlamado
                )
                call.respond(HttpStatusCode.OK, updatedParteAsistencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar parte de asistencia: ${e.message}")
                )
            }
        }

        // Eliminar un parte de asistencia
        delete("/eliminar/{folioPAsistencia}") {
            val folioPAsistencia = call.parameters["folioPAsistencia"]?.toIntOrNull()
            if (folioPAsistencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPAsistencia inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteAsistencia(folioPAsistencia)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Parte de asistencia eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de asistencia no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar parte de asistencia: ${e.message}")
                )
            }
        }
        // Guardar un parte de asistencia (crear o actualizar)
        post("/guardar") {
            try {
                val parteAsistencia = call.receive<Partes_asistencia>()
                val resultado = if (parteAsistencia.folioPAsistencia == null) {
                    dao.createParteAsistencia(
                        aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                        aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                        fechaAsistencia = parteAsistencia.fechaAsistencia,
                        horaInicio = parteAsistencia.horaInicio,
                        horaFin = parteAsistencia.horaFin,
                        direccionAsistencia = parteAsistencia.direccionAsistencia,
                        totalAsistencia = parteAsistencia.totalAsistencia,
                        observaciones = parteAsistencia.observaciones,
                        idTipoLlamado = parteAsistencia.idTipoLlamado
                    )
                } else {
                    dao.updateParteAsistencia(
                        folioPAsistencia = parteAsistencia.folioPAsistencia,
                        aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                        aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                        fechaAsistencia = parteAsistencia.fechaAsistencia,
                        horaInicio = parteAsistencia.horaInicio,
                        horaFin = parteAsistencia.horaFin,
                        direccionAsistencia = parteAsistencia.direccionAsistencia,
                        totalAsistencia = parteAsistencia.totalAsistencia,
                        observaciones = parteAsistencia.observaciones,
                        idTipoLlamado = parteAsistencia.idTipoLlamado
                    )
                }
                val( tipoCitacion, moviles, voluntarios) = dao.getParteAsistenciaWithRelations(resultado.folioPAsistencia!!)
                val response = ParteAsistenciaResponse(
                    folioPAsistencia = resultado.folioPAsistencia,
                    aCargoDelCuerpo = resultado.aCargoDelCuerpo,
                    encargadoCuerpo = dao.getVoluntario(resultado.aCargoDelCuerpo),
                    aCargoDeLaCompania = resultado.aCargoDeLaCompania,
                    encargadoCompania = dao.getVoluntario(resultado.aCargoDeLaCompania),
                    fechaAsistencia = resultado.fechaAsistencia,
                    horaInicio = resultado.horaInicio,
                    horaFin = resultado.horaFin,
                    direccionAsistencia = resultado.direccionAsistencia,
                    totalAsistencia = resultado.totalAsistencia,
                    observaciones = resultado.observaciones,
                    idTipoLlamado = resultado.idTipoLlamado,
                    tipoLlamado = tipoCitacion,
                    voluntarios = voluntarios,
                    moviles = moviles
                )
                call.respond(HttpStatusCode.OK, response)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al guardar parte asistencia: ${e.message}")
                )
            }
        }




        get("/relaciones/{folioPAsistencia}") {
            val folioPAsistencia = call.parameters["folioPAsistencia"]?.toIntOrNull()
            if (folioPAsistencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPAsistencia inválido o faltante."))
                return@get
            }
            try {
                // Obtén el Parte de Asistencia
                val parteAsistencia = dao.getParteAsistencia(folioPAsistencia)
                if (parteAsistencia == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de asistencia no encontrado."))
                    return@get
                }

                // Obtén las relaciones del Parte de Asistencia
                val (tipoCitacion, moviles, voluntarios) = dao.getParteAsistenciaWithRelations(folioPAsistencia)

                // Construye la respuesta usando el modelo ParteAsistenciaResponse
                val response = ParteAsistenciaResponse(
                    folioPAsistencia = parteAsistencia.folioPAsistencia,
                    aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                    encargadoCuerpo = dao.getVoluntario(parteAsistencia.aCargoDelCuerpo),
                    aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                    encargadoCompania = dao.getVoluntario(parteAsistencia.aCargoDeLaCompania),
                    fechaAsistencia = parteAsistencia.fechaAsistencia,
                    horaInicio = parteAsistencia.horaInicio,
                    horaFin = parteAsistencia.horaFin,
                    direccionAsistencia = parteAsistencia.direccionAsistencia,
                    totalAsistencia = parteAsistencia.totalAsistencia,
                    observaciones = parteAsistencia.observaciones,
                    idTipoLlamado = parteAsistencia.idTipoLlamado,
                    tipoLlamado = tipoCitacion,
                    voluntarios = voluntarios,
                    moviles = moviles
                )

                call.respond(HttpStatusCode.OK, response)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener relaciones del parte de asistencia: ${e.message}")
                )
            }
        }



//        post("/guardar") {
//            try {
//                // Recibir el objeto ParteAsistenciaRequest
//                val request = call.receive<ParteAsistenciaRequest>()
//
//                // Verificar si el folio del parte de asistencia está presente (actualización) o no (creación)
//                val resultado = if (request.parteAsistencia.folioPAsistencia == null) {
//                    // Crear nuevo parte de asistencia
//                    dao.createParteAsistencia(
//                        aCargoDelCuerpo = request.parteAsistencia.aCargoDelCuerpo,
//                        aCargoDeLaCompania = request.parteAsistencia.aCargoDeLaCompania,
//                        fechaAsistencia = request.parteAsistencia.fechaAsistencia,
//                        horaInicio = request.parteAsistencia.horaInicio,
//                        horaFin = request.parteAsistencia.horaFin,
//                        direccionAsistencia = request.parteAsistencia.direccionAsistencia,
//                        totalAsistencia = request.parteAsistencia.totalAsistencia,
//                        observaciones = request.parteAsistencia.observaciones,
//                        idTipoLlamado = request.parteAsistencia.idTipoLlamado
//                    )
//                } else {
//                    // Actualizar el parte de asistencia existente
//                    val folioPAsistencia = request.parteAsistencia.folioPAsistencia
//                    dao.updateParteAsistencia(
//                        folioPAsistencia = folioPAsistencia,
//                        aCargoDelCuerpo = request.parteAsistencia.aCargoDelCuerpo,
//                        aCargoDeLaCompania = request.parteAsistencia.aCargoDeLaCompania,
//                        fechaAsistencia = request.parteAsistencia.fechaAsistencia,
//                        horaInicio = request.parteAsistencia.horaInicio,
//                        horaFin = request.parteAsistencia.horaFin,
//                        direccionAsistencia = request.parteAsistencia.direccionAsistencia,
//                        totalAsistencia = request.parteAsistencia.totalAsistencia,
//                        observaciones = request.parteAsistencia.observaciones,
//                        idTipoLlamado = request.parteAsistencia.idTipoLlamado
//                    )
//
//                    // Actualizar relaciones con móviles y voluntarios
//                    folioPAsistencia?.let {
//                        // Limpiar asociaciones existentes
//                        dao.deleteParteAsistenciaMoviles(folioPAsistencia)
//                        dao.deleteParteAsistenciaVoluntarios(folioPAsistencia)
//
//                        // Asociar nuevos móviles
//                        request.moviles?.forEach { idMovil ->
//                            dao.createParteAsistenciaMovil(folioPAsistencia, idMovil)
//                        }
//
//                        // Asociar nuevos voluntarios
//                        request.voluntarios?.forEach { idVoluntario ->
//                            dao.createParteAsistenciaVoluntario(folioPAsistencia, idVoluntario)
//                        }
//                    }
//
//                    request.parteAsistencia
//                }
//
//                // Obtener relaciones actualizadas
//                val (tipoCitacion, moviles, voluntarios) = dao.getParteAsistenciaWithRelations(resultado.folioPAsistencia!!)
//
//                // Construir y responder con el modelo ParteAsistenciaResponse
//                val response = ParteAsistenciaResponse(
//                    folioPAsistencia = resultado.folioPAsistencia,
//                    aCargoDelCuerpo = resultado.aCargoDelCuerpo,
//                    encargadoCuerpo = dao.getVoluntario(resultado.aCargoDelCuerpo),
//                    aCargoDeLaCompania = resultado.aCargoDeLaCompania,
//                    encargadoCompania = dao.getVoluntario(resultado.aCargoDeLaCompania),
//                    fechaAsistencia = resultado.fechaAsistencia,
//                    horaInicio = resultado.horaInicio,
//                    horaFin = resultado.horaFin,
//                    direccionAsistencia = resultado.direccionAsistencia,
//                    totalAsistencia = resultado.totalAsistencia,
//                    observaciones = resultado.observaciones,
//                    idTipoLlamado = resultado.idTipoLlamado,
//                    tipoLlamado = tipoCitacion,
//                    moviles = moviles,
//                    voluntarios = voluntarios
//                )
//
//                call.respond(HttpStatusCode.OK, response)
//            } catch (e: IllegalArgumentException) {
//                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
//            } catch (e: Exception) {
//                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al guardar parte asistencia: ${e.message}"))
//            }
//        }
//


    }
}
