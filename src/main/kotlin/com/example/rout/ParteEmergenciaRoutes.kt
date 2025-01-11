package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.ParteEmergenciaResponse
import com.example.models.Partes_emergencia
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteEmergenciaRoutes(dao: DAOFacadeImpl) {
    route("/parte-emergencia") {

        // Ruta base
        get {
            call.respond(
                HttpStatusCode.OK,
                "Ruta base de Parte Emergencia. Usa /crear, /obtener, /actualizar, /eliminar o /buscar para más acciones."
            )
        }

        // Obtener todos los partes de emergencia
        get("/obtener") {
            try {
                val partesEmergencia = dao.allPartesEmergencia()
                call.respond(HttpStatusCode.OK, partesEmergencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener partes de emergencia: ${e.message}")
                )
            }
        }

        // Crear un nuevo parte de emergencia
        post("/crear") {
            try {
                val parteEmergencia = call.receive<Partes_emergencia>()
                val createdParteEmergencia = dao.createParteEmergencia(
                    horaInicio = parteEmergencia.horaInicio,
                    horaFin = parteEmergencia.horaFin,
                    fechaEmergencia = parteEmergencia.fechaEmergencia,
                    preInforme = parteEmergencia.preInforme,
                    llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
                    descripcionMaterialP = parteEmergencia.descripcionMaterialP,
                    direccionEmergencia = parteEmergencia.direccionEmergencia,
                    idOficial = parteEmergencia.idOficial,
                    idClaveEmergencia = parteEmergencia.idClaveEmergencia,
                    folioPAsistencia = parteEmergencia.folioPAsistencia,
                    idMaterialP = parteEmergencia.idMaterialP
                )
                call.respond(HttpStatusCode.Created, createdParteEmergencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear parte de emergencia: ${e.message}")
                )
            }
        }

        // Buscar un parte de emergencia por folio
        get("/buscar/{folioPEmergencia}") {
            val folioPEmergencia = call.parameters["folioPEmergencia"]?.toIntOrNull()
            if (folioPEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPEmergencia inválido o faltante."))
                return@get
            }
            try {
                //obtiene el parte de emergencia y sus relaciones
                val parteEmergencia = dao.getParteEmergencia(folioPEmergencia)
                if (parteEmergencia != null) {
                    val (moviles, voluntarios) = dao.getParteEmergenciaWithRelations(folioPEmergencia)
                    val response = ParteEmergenciaResponse(
                        folioPEmergencia = parteEmergencia.folioPEmergencia,
                        horaInicio = parteEmergencia.horaInicio,
                        horaFin = parteEmergencia.horaFin,
                        fechaEmergencia = parteEmergencia.fechaEmergencia,
                        preInforme = parteEmergencia.preInforme,
                        llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
                        descripcionMaterialP = parteEmergencia.descripcionMaterialP,
                        direccionEmergencia = parteEmergencia.direccionEmergencia,
                        idOficial = parteEmergencia.idOficial,
                        oficial = dao.getVoluntario(parteEmergencia.idOficial),
                        idClaveEmergencia = parteEmergencia.idClaveEmergencia,
                        claveEmergencia = dao.getClaveEmergencia(parteEmergencia.idClaveEmergencia),
                        folioPAsistencia = parteEmergencia.folioPAsistencia,
                        parteAsistencia = parteEmergencia.folioPAsistencia?.let { dao.getParteAsistenciaResponse(it) },
                        idMaterialP = parteEmergencia.idMaterialP,
                        materialesP = parteEmergencia.idMaterialP?.let { dao.getMaterialP(it) },
                        voluntarios = voluntarios,
                        moviles = moviles
                    )
                    call.respond(HttpStatusCode.OK, response)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Voluntario no encontrado"))
                }
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al buscar voluntario: ${e.message}")
                )
            }
        }


        // Actualizar un parte de emergencia
        put("/actualizar/{folioPEmergencia}") {
            val folioPEmergencia = call.parameters["folioPEmergencia"]?.toIntOrNull()
            if (folioPEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPEmergencia inválido o faltante."))
                return@put
            }
            try {
                val parteEmergencia = call.receive<Partes_emergencia>()
                val updatedParteEmergencia = dao.updateParteEmergencia(
                    folioPEmergencia = folioPEmergencia,
                    horaInicio = parteEmergencia.horaInicio,
                    horaFin = parteEmergencia.horaFin,
                    fechaEmergencia = parteEmergencia.fechaEmergencia,
                    preInforme = parteEmergencia.preInforme,
                    llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
                    descripcionMaterialP = parteEmergencia.descripcionMaterialP,
                    direccionEmergencia = parteEmergencia.direccionEmergencia,
                    idOficial = parteEmergencia.idOficial,
                    idClaveEmergencia = parteEmergencia.idClaveEmergencia,
                    folioPAsistencia = parteEmergencia.folioPAsistencia,
                    idMaterialP = parteEmergencia.idMaterialP
                )
                call.respond(HttpStatusCode.OK, updatedParteEmergencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar parte de emergencia: ${e.message}")
                )
            }
        }

        // Eliminar un parte de emergencia
        delete("/eliminar/{folioPEmergencia}") {
            val folioPEmergencia = call.parameters["folioPEmergencia"]?.toIntOrNull()
            if (folioPEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPEmergencia inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteEmergencia(folioPEmergencia)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Parte de emergencia eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de emergencia no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar parte de emergencia: ${e.message}")
                )
            }
        }

//        // Ruta para guardar un parte de emergencia
//        post("/guardar") {
//            try {
//                val parteEmergencia = call.receive<Partes_emergencia>()
//
//                // Si el folioPEmergencia es nulo, se crea un nuevo registro
//                if (parteEmergencia.folioPEmergencia == null) {
//                    dao.createParteEmergencia(
//                        horaInicio = parteEmergencia.horaInicio,
//                        horaFin = parteEmergencia.horaFin,
//                        fechaEmergencia = parteEmergencia.fechaEmergencia,
//                        preInforme = parteEmergencia.preInforme,
//                        llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
//                        descripcionMaterialP = parteEmergencia.descripcionMaterialP,
//                        direccionEmergencia = parteEmergencia.direccionEmergencia,
//                        idOficial = parteEmergencia.idOficial,
//                        idClaveEmergencia = parteEmergencia.idClaveEmergencia,
//                        folioPAsistencia = parteEmergencia.folioPAsistencia,
//                        idMaterialP = parteEmergencia.idMaterialP
//                    ).also {
//                        call.respond(HttpStatusCode.Created, it)
//                    }
//                } else {
//                    // Si el folioPEmergencia existe, se actualiza el registro
//                    dao.updateParteEmergencia(
//                        folioPEmergencia = parteEmergencia.folioPEmergencia,
//                        horaInicio = parteEmergencia.horaInicio,
//                        horaFin = parteEmergencia.horaFin,
//                        fechaEmergencia = parteEmergencia.fechaEmergencia,
//                        preInforme = parteEmergencia.preInforme,
//                        llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
//                        descripcionMaterialP = parteEmergencia.descripcionMaterialP,
//                        direccionEmergencia = parteEmergencia.direccionEmergencia,
//                        idOficial = parteEmergencia.idOficial,
//                        idClaveEmergencia = parteEmergencia.idClaveEmergencia,
//                        folioPAsistencia = parteEmergencia.folioPAsistencia,
//                        idMaterialP = parteEmergencia.idMaterialP
//                    ).also {
//                        call.respond(HttpStatusCode.OK, it)
//                    }
//                }
//            } catch (e: Exception) {
//                call.respond(
//                    HttpStatusCode.InternalServerError,
//                    mapOf("error" to "Error al guardar parte de emergencia: ${e.message}")
//                )
//            }
//        }

        get("/relaciones/{folioPEmergencia}") {
            val folioPEmergencia = call.parameters["folioPEmergencia"]?.toIntOrNull()
            if (folioPEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPEmergencia inválido o faltante."))
                return@get
            }
            try {
                // Obtén el Parte de Asistencia
                val parteEmergencia = dao.getParteEmergencia(folioPEmergencia)
                if (parteEmergencia == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de emergencia no encontrado."))
                    return@get
                }

                // Obtén las relaciones del Parte de Asistencia
                val (moviles, voluntarios) = dao.getParteEmergenciaWithRelations(folioPEmergencia)

                // Construye la respuesta usando el modelo ParteAsistenciaResponse
                val response = ParteEmergenciaResponse(
                    folioPEmergencia = parteEmergencia.folioPEmergencia,
                    horaInicio = parteEmergencia.horaInicio,
                    horaFin = parteEmergencia.horaFin,
                    fechaEmergencia = parteEmergencia.fechaEmergencia,
                    preInforme = parteEmergencia.preInforme,
                    llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
                    descripcionMaterialP = parteEmergencia.descripcionMaterialP,
                    direccionEmergencia = parteEmergencia.direccionEmergencia,
                    idOficial = parteEmergencia.idOficial,
                    oficial = dao.getVoluntario(parteEmergencia.idOficial),
                    idClaveEmergencia = parteEmergencia.idClaveEmergencia,
                    claveEmergencia = dao.getClaveEmergencia(parteEmergencia.idClaveEmergencia),
                    folioPAsistencia = parteEmergencia.folioPAsistencia,
                    parteAsistencia = parteEmergencia.folioPAsistencia?.let { dao.getParteAsistenciaResponse(it) },
                    idMaterialP = parteEmergencia.idMaterialP,
                    moviles = moviles,
                    voluntarios = voluntarios
                )

                call.respond(HttpStatusCode.OK, response)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener relaciones del parte de asistencia: ${e.message}")
                )
            }
        }

        // Ruta para guardar un parte de emergencia
        post("/guardar") {
            try {
                val parteEmergencia = call.receive<Partes_emergencia>()
                val resultado = if (parteEmergencia.folioPEmergencia== null){
                    dao.createParteEmergencia(
                        horaInicio = parteEmergencia.horaInicio,
                        horaFin = parteEmergencia.horaFin,
                        fechaEmergencia = parteEmergencia.fechaEmergencia,
                        preInforme = parteEmergencia.preInforme,
                        llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
                        descripcionMaterialP = parteEmergencia.descripcionMaterialP,
                        direccionEmergencia = parteEmergencia.direccionEmergencia,
                        idOficial = parteEmergencia.idOficial,
                        idClaveEmergencia = parteEmergencia.idClaveEmergencia,
                        folioPAsistencia = parteEmergencia.folioPAsistencia,
                        idMaterialP = parteEmergencia.idMaterialP
                    )
                } else {
                    println("Parte Emergencia existente" + parteEmergencia.folioPEmergencia)
                    dao.updateParteEmergencia(
                        folioPEmergencia = parteEmergencia.folioPEmergencia,
                        horaInicio = parteEmergencia.horaInicio,
                        horaFin = parteEmergencia.horaFin,
                        fechaEmergencia = parteEmergencia.fechaEmergencia,
                        preInforme = parteEmergencia.preInforme,
                        llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
                        descripcionMaterialP = parteEmergencia.descripcionMaterialP,
                        direccionEmergencia = parteEmergencia.direccionEmergencia,
                        idOficial = parteEmergencia.idOficial,
                        idClaveEmergencia = parteEmergencia.idClaveEmergencia,
                        folioPAsistencia = parteEmergencia.folioPAsistencia,
                        idMaterialP = parteEmergencia.idMaterialP
                    )
                }
                val (moviles, voluntarios) = dao.getParteEmergenciaWithRelations(resultado.folioPEmergencia!!)
                val response = ParteEmergenciaResponse(
                    folioPEmergencia = resultado.folioPEmergencia,
                    horaInicio = resultado.horaInicio,
                    horaFin = resultado.horaFin,
                    fechaEmergencia = resultado.fechaEmergencia,
                    preInforme = resultado.preInforme,
                    llamarEmpresaQuimica = resultado.llamarEmpresaQuimica,
                    descripcionMaterialP = resultado.descripcionMaterialP,
                    direccionEmergencia = resultado.direccionEmergencia,
                    idOficial = resultado.idOficial,
                    oficial = dao.getVoluntario(resultado.idOficial),
                    idClaveEmergencia = resultado.idClaveEmergencia,
                    claveEmergencia = dao.getClaveEmergencia(resultado.idClaveEmergencia),
                    folioPAsistencia = resultado.folioPAsistencia,
                    parteAsistencia = resultado.folioPAsistencia?.let { dao.getParteAsistenciaResponse(it) },
                    idMaterialP = resultado.idMaterialP,
                    materialesP = resultado.idMaterialP?.let { dao.getMaterialP(it) },
                    voluntarios = voluntarios,
                    moviles = moviles
                )
                call.respond(HttpStatusCode.OK, response)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al guardar parte emergencia: ${e.message}")
                )
            }
        }


    }
}


