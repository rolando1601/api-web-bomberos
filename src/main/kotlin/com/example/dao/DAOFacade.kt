package com.example.dao

import com.example.models.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

interface DAOFacade {

    // Institucion
    suspend fun allInstituciones(): List<Institucion>
    suspend fun getInstitucion(idInstitucion: Int): Institucion?
    suspend fun createInstitucion(idInstitucion: Int, nombreInstitucion: String, tipoInstitucion: String, nombrePersonaCargo: String, horaLlegada: LocalTime, folioPEmergencia: Int?): Institucion
    suspend fun deleteInstitucion(idInstitucion: Int): Boolean
    suspend fun updateInstitucion(idInstitucion: Int, nombreInstitucion: String, tipoInstitucion: String, nombrePersonaCargo: String, horaLlegada: LocalTime, folioPEmergencia: Int?): Institucion

    // Cuerpo
    suspend fun allCuerpos(): List<Cuerpo>
    suspend fun getCuerpo(idCuerpo: Int): Cuerpo?
    suspend fun createCuerpo(idCuerpo: Int, nombreCuerpo: String, provincia: String, region: String, comuna: String): Cuerpo
    suspend fun deleteCuerpo(idCuerpo: Int): Boolean
    suspend fun updateCuerpo(idCuerpo: Int, nombreCuerpo: String, provincia: String, region: String, comuna: String): Cuerpo

    // Compania
    suspend fun allCompanias(): List<Compania>
    suspend fun getCompania(idCompania: Int): Compania?
    suspend fun createCompania(idCompania: Int, nombreCia: String, direccionCia: String, especialidad: String, idCuerpo: Int): Compania
    suspend fun deleteCompania(idCompania: Int): Boolean
    suspend fun updateCompania(idCompania: Int, nombreCia: String, direccionCia: String, especialidad: String, idCuerpo: Int): Compania

    // Usuario
    suspend fun allUsuarios(): List<Usuario>
    suspend fun getUsuario(idUsuario: Int): Usuario?
    suspend fun createUsuario(idUsuario: Int, nombreUsuario: String, contrasena: String, rol: String): Usuario
    suspend fun deleteUsuario(idUsuario: Int): Boolean
    suspend fun updateUsuario(idUsuario: Int, nombreUsuario: String, contrasena: String, rol: String): Usuario

    // Voluntario
    suspend fun allVoluntarios(): List<Voluntario>
    suspend fun getVoluntario(idVoluntario: Int): Voluntario?
    suspend fun createVoluntario(idVoluntario: Int, nombreVol: String, fechaNac: LocalDate, direccion: String, numeroContacto: String, tipoSangre: String, enfermedades: String, alergias: String, fechaIngreso: LocalDate, claveRadial: String, cargoVoluntario: String, rutVoluntario: String, idCompania: Int, idUsuario: Int? ): Voluntario
    suspend fun deleteVoluntario(idVoluntario: Int): Boolean
    suspend fun updateVoluntario(idVoluntario: Int, nombreVol: String, fechaNac: LocalDate, direccion: String, numeroContacto: String, tipoSangre: String, enfermedades: String, alergias: String, fechaIngreso: LocalDate, claveRadial: String, cargoVoluntario: String, rutVoluntario: String, idCompania: Int, idUsuario: Int?): Voluntario

    // Inmueble
    suspend fun allInmuebles(): List<Inmueble>
    suspend fun getInmueble(idInmueble: Int): Inmueble?
    suspend fun createInmueble(idInmueble: Int, direccion: String, tipoInmueble: String, estadoInmueble: String, folioPEmergencia: Int?): Inmueble
    suspend fun deleteInmueble(idInmueble: Int): Boolean
    suspend fun updateInmueble(idInmueble: Int, direccion: String, tipoInmueble: String, estadoInmueble: String, folioPEmergencia: Int?): Inmueble

    // Victima
    suspend fun allVictimas(): List<Victima>
    suspend fun getVictima(idVictima: Int): Victima?
    suspend fun createVictima(idVictima: Int, rutVictima: String, nombreVictima: String, edadVictima: Int, descripcion: String, folioPEmergencia: Int): Victima
    suspend fun deleteVictima(idVictima: Int): Boolean
    suspend fun updateVictima(idVictima: Int, rutVictima: String, nombreVictima: String, edadVictima: Int, descripcion: String, folioPEmergencia: Int): Victima

    // Vehiculo
    suspend fun allVehiculos(): List<Vehiculo>
    suspend fun getVehiculo(idVehiculo: Int): Vehiculo?
    suspend fun createVehiculo(idVehiculo: Int, patente: String, marca: String, modelo: String, tipoVehiculo: String, folioPEmergencia: Int): Vehiculo
    suspend fun deleteVehiculo(idVehiculo: Int): Boolean
    suspend fun updateVehiculo(idVehiculo: Int, patente: String, marca: String, modelo: String, tipoVehiculo: String, folioPEmergencia: Int): Vehiculo

    // Emergencia
    suspend fun allEmergencias(): List<Emergencia>
    suspend fun getEmergencia(idEmergencia: Int): Emergencia?
    suspend fun createEmergencia(idEmergencia: Int, claveEmergencia: String, cuadrante: String, direccionEmergencia: String): Emergencia
    suspend fun deleteEmergencia(idEmergencia: Int): Boolean
    suspend fun updateEmergencia(idEmergencia: Int, claveEmergencia: String, cuadrante: String, direccionEmergencia: String): Emergencia

    // ParteEmergencia
    suspend fun allParteEmergencias(): List<Parte_emergencia>
    suspend fun getParteEmergencia(folioPEmergencia: Int): Parte_emergencia?
    suspend fun createParteEmergencia(folioPEmergencia: Int, tipoEmergencia: String, horaInicio: LocalTime, horaFin: LocalTime, fechaEmergencia: LocalDate, preInforme: String, oficial: String, idEmergencia: Int, folioPAsistencia: Int?): Parte_emergencia
    suspend fun deleteParteEmergencia(folioPEmergencia: Int): Boolean
    suspend fun updateParteEmergencia(folioPEmergencia: Int, tipoEmergencia: String, horaInicio: LocalTime, horaFin: LocalTime, fechaEmergencia: LocalDate, preInforme: String, oficial: String, idEmergencia: Int, folioPAsistencia: Int?): Parte_emergencia

    // ParteAsistencia
    suspend fun allParteAsistencias(): List<Parte_asistencia>
    suspend fun getParteAsistencia(folioPAsistencia: Int): Parte_asistencia?
    suspend fun createParteAsistencia(folioPAsistencia: Int, tipoLlamado: String, aCargoDelCuerpo: String, aCargoDeLaCompania: String, fechaAsistencia: LocalDate, horaInicio: LocalTime, horaFin: LocalTime, direccionAsistencia: String, totalAsistencia: Int, observaciones: String, idMovil: Int): Parte_asistencia
    suspend fun deleteParteAsistencia(folioPAsistencia: Int): Boolean
    suspend fun updateParteAsistencia(folioPAsistencia: Int, tipoLlamado: String, aCargoDelCuerpo: String, aCargoDeLaCompania: String, fechaAsistencia: LocalDate, horaInicio: LocalTime, horaFin: LocalTime, direccionAsistencia: String, totalAsistencia: Int, observaciones: String, idMovil: Int): Parte_asistencia

    // MaterialP
    suspend fun allMaterialP(): List<MaterialP>
    suspend fun getMaterialP(idMaterialP: Int): MaterialP?
    suspend fun createMaterialP(idMaterialP: Int, llamarEmpresaQuimica: Boolean, clasificacion: String, nombreMaP: String, folioPEmergencia: Int?): MaterialP
    suspend fun deleteMaterialP(idMaterialP: Int): Boolean
    suspend fun updateMaterialP(idMaterialP: Int, llamarEmpresaQuimica: Boolean, clasificacion: String, nombreMaP: String, folioPEmergencia: Int?): MaterialP

    // Movil
    suspend fun allMoviles(): List<Movil>
    suspend fun getMovil(idMovil: Int): Movil?
    suspend fun createMovil(idMovil: Int, nomenclatura: String, especialidad: String, folioPEmergencia: Int?): Movil
    suspend fun deleteMovil(idMovil: Int): Boolean
    suspend fun updateMovil(idMovil: Int, nomenclatura: String, especialidad: String, folioPEmergencia: Int?): Movil

    // ParteEmergenciaVoluntario
    suspend fun allParteEmergenciaVoluntarios(): List<ParteEmergenciaVoluntario>
    suspend fun getParteEmergenciaVoluntario(idParteVoluntario: Int): ParteEmergenciaVoluntario?
    suspend fun createParteEmergenciaVoluntario(idParteVoluntario: Int, folioPEmergencia: Int, idVoluntario: Int): ParteEmergenciaVoluntario
    suspend fun deleteParteEmergenciaVoluntario(idParteVoluntario: Int): Boolean
    suspend fun updateParteEmergenciaVoluntario(idParteVoluntario: Int, folioPEmergencia: Int, idVoluntario: Int): ParteEmergenciaVoluntario

    //parteAsistenciaVoluntario
    suspend fun allParteAsistenciaVoluntarios(): List<ParteAsistenciaVoluntario>
    suspend fun getParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): ParteAsistenciaVoluntario?
    suspend fun createParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int, folioPAsistencia: Int, idVoluntario: Int): ParteAsistenciaVoluntario
    suspend fun deleteParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): Boolean
    suspend fun updateParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int, folioPAsistencia: Int, idVoluntario: Int): ParteAsistenciaVoluntario


}
