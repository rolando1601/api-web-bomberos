package com.example.dao

import com.example.models.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

interface DAOFacade {

    // Voluntario
    suspend fun allVoluntarios(): List<Voluntario>
    suspend fun getVoluntario(idVoluntario: Int): Voluntario?
    suspend fun createVoluntario(idVoluntario: Int, nombreVol: String, fechaNac: LocalDate, direccion: String, numeroContacto: String, tipoSangre: String, enfermedades: String, alergias: String, fechaIngreso: LocalDate, claveRadial: String, cargoVoluntario: String, idUsuario: Int, idCompania: Int, folioPEmergencia: Int, folioPAsistencia: Int): Voluntario
    suspend fun deleteVoluntario(idVoluntario: Int): Boolean
    suspend fun updateVoluntario(idVoluntario: Int, nombreVol: String, fechaNac: LocalDate, direccion: String, numeroContacto: String, tipoSangre: String, enfermedades: String, alergias: String, fechaIngreso: LocalDate, claveRadial: String, cargoVoluntario: String, idUsuario: Int, idCompania: Int, folioPEmergencia: Int, folioPAsistencia: Int): Voluntario


    // Institucion
    suspend fun allInstituciones(): List<Institucion>
    suspend fun getInstitucion(idInstitucion: Int): Institucion?
    suspend fun createInstitucion(idInstitucion: Int, nombreInstitucion: String, tipoInstitucion: String, nombrePersonaCargo: String, horaLlegada: LocalTime, folioPEmergencia: Int): Institucion
    suspend fun deleteInstitucion(idInstitucion: Int): Boolean
    suspend fun updateInstitucion(idInstitucion: Int, nombreInstitucion: String, tipoInstitucion: String, nombrePersonaCargo: String, horaLlegada: LocalTime, folioPEmergencia: Int): Institucion

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

    // Inmueble
    suspend fun allInmuebles(): List<Inmueble>
    suspend fun getInmueble(idInmueble: Int): Inmueble?
    suspend fun createInmueble(idInmueble: Int, direccion: String, tipoInmueble: String, estadoInmueble: String, folioPEmergencia: Int): Inmueble
    suspend fun deleteInmueble(idInmueble: Int): Boolean
    suspend fun updateInmueble(idInmueble: Int, direccion: String, tipoInmueble: String, estadoInmueble: String, folioPEmergencia: Int): Inmueble

    // Victima
    suspend fun allVictimas(): List<Victima>
    suspend fun getVictima(rutVictima: String): Victima?
    suspend fun createVictima(rutVictima: String, nombreVictima: String, edadVictima: Int, folioPEmergencia: Int): Victima
    suspend fun deleteVictima(rutVictima: String): Boolean
    suspend fun updateVictima(rutVictima: String, nombreVictima: String, edadVictima: Int, folioPEmergencia: Int): Victima

    // Vehiculo
    suspend fun allVehiculos(): List<Vehiculo>
    suspend fun getVehiculo(patente: String): Vehiculo?
    suspend fun createVehiculo(patente: String, marca: String, modelo: String, tipoVehiculo: String, folioPEmergencia: Int): Vehiculo
    suspend fun deleteVehiculo(patente: String): Boolean
    suspend fun updateVehiculo(patente: String, marca: String, modelo: String, tipoVehiculo: String, folioPEmergencia: Int): Vehiculo

    // Emergencia
    suspend fun allEmergencias(): List<Emergencia>
    suspend fun getEmergencia(idEmergencia: Int): Emergencia?
    suspend fun createEmergencia(idEmergencia: Int, claveEmergencia: String, cuadrante: String, direccion: String): Emergencia
    suspend fun deleteEmergencia(idEmergencia: Int): Boolean
    suspend fun updateEmergencia(idEmergencia: Int, claveEmergencia: String, cuadrante: String, direccion: String): Emergencia

    // ParteEmergencia
    suspend fun allParteEmergencias(): List<ParteEmergencia>
    suspend fun getParteEmergencia(folioPEmergencia: Int): ParteEmergencia?
    suspend fun createParteEmergencia(folioPEmergencia: Int, tipoEmergencia: String, horaInicio: LocalTime, horaFin: LocalTime, fechaEmergencia: LocalDate, preInforme: String?, oficial: String, idEmergencia: Int, folioPAsistencia: Int): ParteEmergencia
    suspend fun deleteParteEmergencia(folioPEmergencia: Int): Boolean
    suspend fun updateParteEmergencia(folioPEmergencia: Int, tipoEmergencia: String, horaInicio: LocalTime, horaFin: LocalTime, fechaEmergencia: LocalDate, preInforme: String?, oficial: String, idEmergencia: Int, folioPAsistencia: Int): ParteEmergencia

    // ParteAsistencia
    suspend fun allParteAsistencias(): List<ParteAsistencia>
    suspend fun getParteAsistencia(folioPAsistencia: Int): ParteAsistencia?
    suspend fun createParteAsistencia(folioPAsistencia: Int, tipoLlamado: String, aCargoDelCuerpo: String, aCargoDeLaCompania: String, fechaAsistencia: LocalDate, horaInicio: LocalTime, horaFin: LocalTime, direccion: String, totalAsistencia: Int, observaciones: String?, materialMayorAsistencia: String?): ParteAsistencia
    suspend fun deleteParteAsistencia(folioPAsistencia: Int): Boolean
    suspend fun updateParteAsistencia(folioPAsistencia: Int, tipoLlamado: String, aCargoDelCuerpo: String, aCargoDeLaCompania: String, fechaAsistencia: LocalDate, horaInicio: LocalTime, horaFin: LocalTime, direccion: String, totalAsistencia: Int, observaciones: String?, materialMayorAsistencia: String?): ParteAsistencia

    // MaterialP
    suspend fun allMaterialesP(): List<MaterialP>
    suspend fun getMaterialP(idMaterialP: Int): MaterialP?
    suspend fun createMaterialP(idMaterialP: Int, numeroONU: String, clasificacion: String, nombre: String, folioPEmergencia: Int): MaterialP
    suspend fun deleteMaterialP(idMaterialP: Int): Boolean
    suspend fun updateMaterialP(idMaterialP: Int, numeroONU: String, clasificacion: String, nombre: String, folioPEmergencia: Int): MaterialP
}
