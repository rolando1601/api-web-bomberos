package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.*

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDate

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
                        victimas = dao.getVictimasByFolio(folioPEmergencia),
                        vehiculos = dao.getVehiculosByFolio(folioPEmergencia),
                        instituciones = dao.getInstitucionesByFolio(folioPEmergencia),
                        inmuebles = dao.getInmueblesByFolio(folioPEmergencia)

                    )
                    call.respond(HttpStatusCode.OK, response)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "parte no encontrado"))
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
                dao.deleteVictimasByFolio(folioPEmergencia)
                dao.deleteVehiculosByFolio(folioPEmergencia)
                dao.deleteInstitucionesByFolio(folioPEmergencia)
                dao.deleteInmueblesByFolio(folioPEmergencia)
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
                    voluntarios = voluntarios,
                    victimas = dao.getVictimasByFolio(folioPEmergencia),
                    vehiculos = dao.getVehiculosByFolio(folioPEmergencia),
                    instituciones = dao.getInstitucionesByFolio(folioPEmergencia),
                    inmuebles = dao.getInmueblesByFolio(folioPEmergencia)
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
                    moviles = moviles,
                    victimas = dao.getVictimasByFolio(resultado.folioPEmergencia!!),
                    vehiculos = dao.getVehiculosByFolio(resultado.folioPEmergencia!!),
                    instituciones = dao.getInstitucionesByFolio(resultado.folioPEmergencia!!),
                    inmuebles = dao.getInmueblesByFolio(resultado.folioPEmergencia!!)
                )

                // Responder con éxito
                call.respond(HttpStatusCode.OK, response)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al guardar parte de emergencia: ${e.message}"))
            }
        }






        // Obtener partes de emergencia dentro de un rango de fechas
        get("/fechas-simple") {
            try {
                // Leer los parámetros de fecha desde la URL
                val fechaInicioParam = call.request.queryParameters["fechaInicio"]
                val fechaFinParam = call.request.queryParameters["fechaFin"]

                // Validar que las fechas no sean nulas
                if (fechaInicioParam.isNullOrBlank() || fechaFinParam.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Parámetros 'fechaInicio' y 'fechaFin' son obligatorios."))
                    return@get
                }

                // Convertir las fechas a LocalDate
                val fechaInicio = LocalDate.parse(fechaInicioParam)
                val fechaFin = LocalDate.parse(fechaFinParam)

                // Validar que fechaInicio <= fechaFin
                if (fechaInicio > fechaFin) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "La fecha de inicio no puede ser mayor que la fecha de fin."))
                    return@get
                }

                // Obtener los partes de emergencia desde el DAO
                val partesEmergencia = dao.getPartesEmergenciaByFechas(fechaInicio, fechaFin)

                // Responder con los resultados
                call.respond(HttpStatusCode.OK, partesEmergencia)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener partes de emergencia: ${e.message}"))
            }
        }

        // Obtener partes de emergencia dentro de un rango de fechas con sus relaciones
        get("/fechas") {
            try {
                // Leer los parámetros de fecha desde la URL
                val fechaInicioParam = call.request.queryParameters["fechaInicio"]
                val fechaFinParam = call.request.queryParameters["fechaFin"]

                // Validar que las fechas no sean nulas
                if (fechaInicioParam.isNullOrBlank() || fechaFinParam.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Parámetros 'fechaInicio' y 'fechaFin' son obligatorios."))
                    return@get
                }

                // Convertir las fechas a LocalDate
                val fechaInicio = LocalDate.parse(fechaInicioParam)
                val fechaFin = LocalDate.parse(fechaFinParam)

                // Validar que fechaInicio <= fechaFin
                if (fechaInicio > fechaFin) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "La fecha de inicio no puede ser mayor que la fecha de fin."))
                    return@get
                }

                // Obtener los partes de emergencia dentro del rango de fechas
                val partesEmergencia = dao.getPartesEmergenciaByFechas(fechaInicio, fechaFin)

                // Construir la respuesta con sus relaciones
                val response = partesEmergencia.map { parteEmergencia ->
                    val (moviles, voluntarios, materialesP) = dao.getParteEmergenciaWithRelations(parteEmergencia.folioPEmergencia!!)
                    ParteEmergenciaResponse(
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
                        victimas = dao.getVictimasByFolio(parteEmergencia.folioPEmergencia!!),
                        vehiculos = dao.getVehiculosByFolio(parteEmergencia.folioPEmergencia!!),
                        instituciones = dao.getInstitucionesByFolio(parteEmergencia.folioPEmergencia!!),
                        inmuebles = dao.getInmueblesByFolio(parteEmergencia.folioPEmergencia!!)
                    )
                }

                // Responder con los resultados
                call.respond(HttpStatusCode.OK, response)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener partes de emergencia: ${e.message}"))
            }
        }

        // Obtener partes de emergencia de un día específico
        get("/fecha") {
            try {
                // Leer el parámetro de fecha desde la URL
                val fechaParam = call.request.queryParameters["fecha"]

                // Validar que el parámetro de fecha no sea nulo o vacío
                if (fechaParam.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El parámetro 'fecha' es obligatorio."))
                    return@get
                }

                // Convertir el parámetro a LocalDate
                val fecha = LocalDate.parse(fechaParam)

                // Obtener los partes de emergencia para esa fecha
                val partesEmergencia = dao.getPartesEmergenciaByFecha(fecha)

                // Responder con los resultados
                call.respond(HttpStatusCode.OK, partesEmergencia)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de fecha inválido. Usa el formato 'YYYY-MM-DD'."))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener partes de emergencia: ${e.message}"))
            }
        }













    }
}




