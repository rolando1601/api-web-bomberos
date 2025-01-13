package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.*

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
                    val (moviles, voluntarios, materialesP) = dao.getParteEmergenciaWithRelations(folioPEmergencia)
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
                        parteAsistencia = parteEmergencia.folioPAsistencia?.let { dao.getParteAsistencia(it) },
                        voluntarios = voluntarios,
                        moviles = moviles,
                        materialesP = materialesP,
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
                dao.deleteParteEmergenciaVoluntarios(folioPEmergencia)
                dao.deleteParteEmergenciaMoviles(folioPEmergencia)
                dao.deleteParteEmergenciaMateriales(folioPEmergencia)
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
                val (moviles, voluntarios, materialesP) = dao.getParteEmergenciaWithRelations(folioPEmergencia)

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
                    parteAsistencia = parteEmergencia.folioPAsistencia?.let { dao.getParteAsistencia(it) },
                    materialesP = materialesP,
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
//        post("/guardar") {
//            try {
//                // Recibir el objeto ParteEmergencia
//                val request = call.receive<ParteEmergenciaRequest2>()
//
//                // Verificar si el folio del parte de emergencia está presente (actualización) o no (creación)
//                val resultado = if (request.parteEmergencia.folioPEmergencia == null) {
//                    // Crear un nuevo parte de emergencia
//                    dao.createParteEmergencia(
//                        horaInicio = request.parteEmergencia.horaInicio,
//                        horaFin = request.parteEmergencia.horaFin,
//                        fechaEmergencia = request.parteEmergencia.fechaEmergencia,
//                        preInforme = request.parteEmergencia.preInforme,
//                        llamarEmpresaQuimica = request.parteEmergencia.llamarEmpresaQuimica,
//                        descripcionMaterialP = request.parteEmergencia.descripcionMaterialP,
//                        direccionEmergencia = request.parteEmergencia.direccionEmergencia,
//                        idOficial = request.parteEmergencia.idOficial,
//                        idClaveEmergencia = request.parteEmergencia.idClaveEmergencia,
//                        folioPAsistencia = request.parteEmergencia.folioPAsistencia,
//                    )
//                } else {
//
//                    // Actualizar un parte de emergencia existente
//                    val folioPEmergencia = request.parteEmergencia.folioPEmergencia
//                    dao.updateParteEmergencia(
//                        folioPEmergencia = folioPEmergencia,
//                        horaInicio = request.parteEmergencia.horaInicio,
//                        horaFin = request.parteEmergencia.horaFin,
//                        fechaEmergencia = request.parteEmergencia.fechaEmergencia,
//                        preInforme = request.parteEmergencia.preInforme,
//                        llamarEmpresaQuimica = request.parteEmergencia.llamarEmpresaQuimica,
//                        descripcionMaterialP = request.parteEmergencia.descripcionMaterialP,
//                        direccionEmergencia = request.parteEmergencia.direccionEmergencia,
//                        idOficial = request.parteEmergencia.idOficial,
//                        idClaveEmergencia = request.parteEmergencia.idClaveEmergencia,
//                        folioPAsistencia = request.parteEmergencia.folioPAsistencia,
//
//                    )
//                // Actualizar relaciones con móviles, voluntarios y parte de asistencia
//                folioPEmergencia?.let {
//                    //limpiar relaciones
//                    dao.deleteParteEmergenciaMoviles(folioPEmergencia)
//                    dao.deleteParteEmergenciaVoluntarios(folioPEmergencia)
//                    dao.deleteParteEmergenciaMateriales(folioPEmergencia)
//                    //asociar nuevos moviles
//
//                    request.materialesP?.forEach { idMaterialP ->
//                        dao.createParteEmergenciaMaterial(folioPEmergencia, idMaterialP)
//                    }
//                    //asociar nuevos materiales
//                    request.moviles?.forEach { idMovil ->
//                        dao.createParteEmergenciaMovil(folioPEmergencia, idMovil)
//                    }
//                    //asociar nuevos voluntarios
//                    request.voluntarios?.forEach { idVoluntario ->
//                        dao.createParteEmergenciaVoluntario(folioPEmergencia, idVoluntario)
//                    }
//
//                }
//                    request.parteEmergencia
//                }
//                //se crea el parte de emergencia se asocian los moviles, voluntarios y materiales
//                if(request.parteEmergencia.folioPEmergencia == null){
//                    request.moviles?.forEach { idMovil ->
//                        dao.createParteEmergenciaMovil(resultado.folioPEmergencia!!, idMovil)
//                    }
//                    request.voluntarios?.forEach { idVoluntario ->
//                        dao.createParteEmergenciaVoluntario(resultado.folioPEmergencia!!, idVoluntario)
//                    }
//                    request.materialesP?.forEach { idMaterialP ->
//                        dao.createParteEmergenciaMaterial(resultado.folioPEmergencia!!, idMaterialP)
//                    }
//                }
//                //obtiener relaciones actualizadas
//                val (moviles, voluntarios, materialesP, parteAsistencia) = dao.getParteEmergenciaWithRelations(resultado.folioPEmergencia!!)
//
//                val response = ParteEmergenciaResponse(
//                    folioPEmergencia = resultado.folioPEmergencia,
//                    horaInicio = resultado.horaInicio,
//                    horaFin = resultado.horaFin,
//                    fechaEmergencia = resultado.fechaEmergencia,
//                    preInforme = resultado.preInforme,
//                    llamarEmpresaQuimica = resultado.llamarEmpresaQuimica,
//                    descripcionMaterialP = resultado.descripcionMaterialP,
//                    direccionEmergencia = resultado.direccionEmergencia,
//                    idOficial = resultado.idOficial,
//                    oficial = dao.getVoluntario(resultado.idOficial),
//                    idClaveEmergencia = resultado.idClaveEmergencia,
//                    claveEmergencia = dao.getClaveEmergencia(resultado.idClaveEmergencia),
//                    folioPAsistencia = resultado.folioPAsistencia,
//                    parteAsistencia = parteAsistencia,
//                    materialesP = materialesP,
//                    voluntarios = voluntarios,
//                    moviles = moviles
//
//                )
//                call.respond(HttpStatusCode.OK, response)
//            } catch (e: IllegalArgumentException) {
//                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
//            } catch (e: Exception) {
//                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al guardar parte emergencia: ${e.message}"))
//            }
//        }

        // Ruta para guardar un parte de emergencia
        post("/guardar") {
            try {
                // Recibir el objeto ParteEmergenciaRequest2
                val request = call.receive<ParteEmergenciaRequest2>()

                // Verificar si el folio del parte de emergencia está presente (actualización) o no (creación)
                val resultado = if (request.parteEmergencia.folioPEmergencia == null) {
                    // Crear un nuevo parte de emergencia
                    dao.createParteEmergencia(
                        horaInicio = request.parteEmergencia.horaInicio,
                        horaFin = request.parteEmergencia.horaFin,
                        fechaEmergencia = request.parteEmergencia.fechaEmergencia,
                        preInforme = request.parteEmergencia.preInforme,
                        llamarEmpresaQuimica = request.parteEmergencia.llamarEmpresaQuimica,
                        descripcionMaterialP = request.parteEmergencia.descripcionMaterialP,
                        direccionEmergencia = request.parteEmergencia.direccionEmergencia,
                        idOficial = request.parteEmergencia.idOficial,
                        idClaveEmergencia = request.parteEmergencia.idClaveEmergencia,
                        folioPAsistencia = request.parteEmergencia.folioPAsistencia,
                    )
                } else {
                    // Actualizar un parte de emergencia existente
                    val folioPEmergencia = request.parteEmergencia.folioPEmergencia
                    dao.updateParteEmergencia(
                        folioPEmergencia = folioPEmergencia,
                        horaInicio = request.parteEmergencia.horaInicio,
                        horaFin = request.parteEmergencia.horaFin,
                        fechaEmergencia = request.parteEmergencia.fechaEmergencia,
                        preInforme = request.parteEmergencia.preInforme,
                        llamarEmpresaQuimica = request.parteEmergencia.llamarEmpresaQuimica,
                        descripcionMaterialP = request.parteEmergencia.descripcionMaterialP,
                        direccionEmergencia = request.parteEmergencia.direccionEmergencia,
                        idOficial = request.parteEmergencia.idOficial,
                        idClaveEmergencia = request.parteEmergencia.idClaveEmergencia,
                        folioPAsistencia = request.parteEmergencia.folioPAsistencia,
                    )

                    // Limpiar relaciones existentes y asociar nuevas
                    folioPEmergencia?.let { folio ->
                        // Limpiar relaciones
                        dao.deleteParteEmergenciaMoviles(folio)
                        dao.deleteParteEmergenciaVoluntarios(folio)
                        dao.deleteParteEmergenciaMateriales(folio)
                        dao.deleteVictimasByFolio(folio)
                        dao.deleteVehiculosByFolio(folio)
                        dao.deleteInstitucionesByFolio(folio)
                        dao.deleteInmueblesByFolio(folio)

                        // Reasociar relaciones
                        request.materialesP?.forEach { idMaterialP -> dao.createParteEmergenciaMaterial(folio, idMaterialP) }
                        request.moviles?.forEach { idMovil -> dao.createParteEmergenciaMovil(folio, idMovil) }
                        request.voluntarios?.forEach { idVoluntario -> dao.createParteEmergenciaVoluntario(folio, idVoluntario) }

                        request.victimas?.let { victimas ->
                            dao.createVictimas(victimas.map { Victimas(null, it.rutVictima, it.nombreVictima, it.edadVictima, it.descripcion, folio) }, folio)
                        }

                        request.vehiculos?.let { vehiculos ->
                            dao.createVehiculos(vehiculos.map { Vehiculos(null, it.patente, it.marca, it.modelo, it.tipoVehiculo, folio) }, folio)
                        }

                        request.instituciones?.let { instituciones ->
                            dao.createInstituciones(instituciones.map { Instituciones(null, it.nombreInstitucion, it.tipoInstitucion, it.nombrePersonaCargo, it.horaLlegada, folio) }, folio)
                        }

                        request.inmuebles?.let { inmuebles ->
                            dao.createInmuebles(inmuebles.map { Inmuebles(null, it.direccion, it.tipoInmueble, it.estadoInmueble, folio) }, folio)
                        }
                    }

                    request.parteEmergencia
                }

                // Si es una creación, asocia las relaciones
                if (request.parteEmergencia.folioPEmergencia == null) {
                    val folio = resultado.folioPEmergencia!!
                    request.moviles?.forEach { idMovil -> dao.createParteEmergenciaMovil(folio, idMovil) }
                    request.voluntarios?.forEach { idVoluntario -> dao.createParteEmergenciaVoluntario(folio, idVoluntario) }
                    request.materialesP?.forEach { idMaterialP -> dao.createParteEmergenciaMaterial(folio, idMaterialP) }

                    request.victimas?.let { victimas ->
                        dao.createVictimas(victimas.map { Victimas(null, it.rutVictima, it.nombreVictima, it.edadVictima, it.descripcion, folio) }, folio)
                    }

                    request.vehiculos?.let { vehiculos ->
                        dao.createVehiculos(vehiculos.map { Vehiculos(null, it.patente, it.marca, it.modelo, it.tipoVehiculo, folio) }, folio)
                    }

                    request.instituciones?.let { instituciones ->
                        dao.createInstituciones(instituciones.map { Instituciones(null, it.nombreInstitucion, it.tipoInstitucion, it.nombrePersonaCargo, it.horaLlegada, folio) }, folio)
                    }

                    request.inmuebles?.let { inmuebles ->
                        dao.createInmuebles(inmuebles.map { Inmuebles(null, it.direccion, it.tipoInmueble, it.estadoInmueble, folio) }, folio)
                    }
                }

                // Obtener relaciones actualizadas
                val (moviles, voluntarios, materialesP, parteAsistencia) = dao.getParteEmergenciaWithRelations(resultado.folioPEmergencia!!)

                // Crear respuesta
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
                    parteAsistencia = parteAsistencia,
                    materialesP = materialesP,
                    voluntarios = voluntarios,
                    moviles = moviles
                )

                // Responder con éxito
                call.respond(HttpStatusCode.OK, response)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al guardar parte de emergencia: ${e.message}"))
            }
        }



    }
}




