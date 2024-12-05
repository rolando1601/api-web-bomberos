package com.example.dao

import com.example.models.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

interface DAOFacade {

    // Institucion
    suspend fun allInstituciones(): List<Instituciones>
    suspend fun getInstitucion(idInstitucion: Int): Instituciones?
    suspend fun createInstitucion( nombreInstitucion: String, tipoInstitucion: String, nombrePersonaCargo: String, horaLlegada: LocalTime, folioPEmergencia: Int?): Instituciones
    suspend fun deleteInstitucion(idInstitucion: Int): Boolean
    suspend fun updateInstitucion(idInstitucion: Int, nombreInstitucion: String, tipoInstitucion: String, nombrePersonaCargo: String, horaLlegada: LocalTime, folioPEmergencia: Int?): Instituciones

    // Cuerpo
    suspend fun allCuerpos(): List<Cuerpos>
    suspend fun getCuerpo(idCuerpo: Int): Cuerpos?
    suspend fun createCuerpo( nombreCuerpo: String, provincia: String, region: String, comuna: String): Cuerpos
    suspend fun deleteCuerpo(idCuerpo: Int): Boolean
    suspend fun updateCuerpo(idCuerpo: Int, nombreCuerpo: String, provincia: String, region: String, comuna: String): Cuerpos

    // Compania
    suspend fun allCompanias(): List<Companias>
    suspend fun getCompania(idCompania: Int): Companias?
    suspend fun createCompania( nombreCia: String, direccionCia: String, especialidad: String, idCuerpo: Int?): Companias
    suspend fun deleteCompania(idCompania: Int): Boolean
    suspend fun updateCompania(idCompania: Int, nombreCia: String, direccionCia: String, especialidad: String, idCuerpo: Int?): Companias

    // Usuario
    suspend fun allUsuarios(): List<Usuarios>
    suspend fun getUsuario(idUsuario: Int): Usuarios?
    suspend fun createUsuario( nombreUsuario: String, contrasena: String, rol: String): Usuarios
    suspend fun deleteUsuario(idUsuario: Int): Boolean
    suspend fun updateUsuario(idUsuario: Int, nombreUsuario: String, contrasena: String, rol: String): Usuarios

    // Voluntario
    suspend fun allVoluntarios(): List<Voluntarios>
    suspend fun getVoluntario(idVoluntario: Int): Voluntarios?
    suspend fun createVoluntario( nombreVol: String, fechaNac: LocalDate, direccion: String, numeroContacto: String, tipoSangre: String, enfermedades: String, alergias: String, fechaIngreso: LocalDate, claveRadial: String, cargoVoluntario: String, rutVoluntario: String, idCompania: Int, idUsuario: Int? ): Voluntarios
    suspend fun deleteVoluntario(idVoluntario: Int): Boolean
    suspend fun updateVoluntario(idVoluntario: Int, nombreVol: String, fechaNac: LocalDate, direccion: String, numeroContacto: String, tipoSangre: String, enfermedades: String, alergias: String, fechaIngreso: LocalDate, claveRadial: String, cargoVoluntario: String, rutVoluntario: String, idCompania: Int, idUsuario: Int?): Voluntarios

    // Inmueble
    suspend fun allInmuebles(): List<Inmuebles>
    suspend fun getInmueble(idInmueble: Int): Inmuebles?
    suspend fun createInmueble(direccion: String, tipoInmueble: String, estadoInmueble: String, folioPEmergencia: Int?): Inmuebles
    suspend fun deleteInmueble(idInmueble: Int): Boolean
    suspend fun updateInmueble(idInmueble: Int, direccion: String, tipoInmueble: String, estadoInmueble: String, folioPEmergencia: Int?): Inmuebles

    // Victima
    suspend fun allVictimas(): List<Victimas>
    suspend fun getVictima(idVictima: Int): Victimas?
    suspend fun createVictima( rutVictima: String, nombreVictima: String, edadVictima: Int, descripcion: String, folioPEmergencia: Int): Victimas
    suspend fun deleteVictima(idVictima: Int): Boolean
    suspend fun updateVictima(idVictima: Int, rutVictima: String, nombreVictima: String, edadVictima: Int, descripcion: String, folioPEmergencia: Int): Victimas

    // Vehiculo
    suspend fun allVehiculos(): List<Vehiculos>
    suspend fun getVehiculo(idVehiculo: Int): Vehiculos?
    suspend fun createVehiculo( patente: String, marca: String, modelo: String, tipoVehiculo: String, folioPEmergencia: Int?): Vehiculos
    suspend fun deleteVehiculo(idVehiculo: Int): Boolean
    suspend fun updateVehiculo(idVehiculo: Int, patente: String, marca: String, modelo: String, tipoVehiculo: String, folioPEmergencia: Int?): Vehiculos

    // Emergencia
    suspend fun allEmergencias(): List<Emergencias>
    suspend fun getEmergencia(idEmergencia: Int): Emergencias?
    suspend fun createEmergencia( claveEmergencia: String, cuadrante: String, direccionEmergencia: String): Emergencias
    suspend fun deleteEmergencia(idEmergencia: Int): Boolean
    suspend fun updateEmergencia(idEmergencia: Int, claveEmergencia: String, cuadrante: String, direccionEmergencia: String): Emergencias

    // ParteEmergencia
    suspend fun allParteEmergencias(): List<Partes_emergencia>
    suspend fun getParteEmergencia(folioPEmergencia: Int): Partes_emergencia?
    suspend fun createParteEmergencia( tipoEmergencia: String, horaInicio: LocalTime, horaFin: LocalTime, fechaEmergencia: LocalDate, preInforme: String, oficial: String, idEmergencia: Int, folioPAsistencia: Int?): Partes_emergencia
    suspend fun deleteParteEmergencia(folioPEmergencia: Int): Boolean
    suspend fun updateParteEmergencia(folioPEmergencia: Int, tipoEmergencia: String, horaInicio: LocalTime, horaFin: LocalTime, fechaEmergencia: LocalDate, preInforme: String, oficial: String, idEmergencia: Int, folioPAsistencia: Int?): Partes_emergencia

    // ParteAsistencia
    suspend fun allParteAsistencias(): List<Partes_asistencia>
    suspend fun getParteAsistencia(folioPAsistencia: Int): Partes_asistencia?
    suspend fun createParteAsistencia( tipoLlamado: String, aCargoDelCuerpo: String, aCargoDeLaCompania: String, fechaAsistencia: LocalDate, horaInicio: LocalTime, horaFin: LocalTime, direccionAsistencia: String, totalAsistencia: Int, observaciones: String, idMovil: Int?): Partes_asistencia
    suspend fun deleteParteAsistencia(folioPAsistencia: Int): Boolean
    suspend fun updateParteAsistencia(folioPAsistencia: Int, tipoLlamado: String, aCargoDelCuerpo: String, aCargoDeLaCompania: String, fechaAsistencia: LocalDate, horaInicio: LocalTime, horaFin: LocalTime, direccionAsistencia: String, totalAsistencia: Int, observaciones: String, idMovil: Int): Partes_asistencia

    // MaterialP
    suspend fun allMaterialP(): List<MaterialesP>
    suspend fun getMaterialP(idMaterialP: Int): MaterialesP?
    suspend fun createMaterialP( llamarEmpresaQuimica: Boolean, clasificacion: String, nombreMaP: String, folioPEmergencia: Int?): MaterialesP
    suspend fun deleteMaterialP(idMaterialP: Int): Boolean
    suspend fun updateMaterialP(idMaterialP: Int, llamarEmpresaQuimica: Boolean, clasificacion: String, nombreMaP: String, folioPEmergencia: Int?): MaterialesP

    // Movil
    suspend fun allMoviles(): List<Moviles>
    suspend fun getMovil(idMovil: Int): Moviles?
    suspend fun createMovil( nomenclatura: String, especialidad: String, folioPEmergencia: Int?): Moviles
    suspend fun deleteMovil(idMovil: Int): Boolean
    suspend fun updateMovil(idMovil: Int, nomenclatura: String, especialidad: String, folioPEmergencia: Int?): Moviles

    // ParteEmergenciaVoluntario
    suspend fun allParteEmergenciaVoluntarios(): List<PartesEmergenciaVoluntarios>
    suspend fun getParteEmergenciaVoluntario(idParteVoluntario: Int): PartesEmergenciaVoluntarios?
    suspend fun createParteEmergenciaVoluntario( folioPEmergencia: Int, idVoluntario: Int): PartesEmergenciaVoluntarios
    suspend fun deleteParteEmergenciaVoluntario(idParteVoluntario: Int): Boolean
    suspend fun updateParteEmergenciaVoluntario(idParteVoluntario: Int, folioPEmergencia: Int, idVoluntario: Int): PartesEmergenciaVoluntarios

    //parteAsistenciaVoluntario
    suspend fun allParteAsistenciaVoluntarios(): List<PartesAsistenciaVoluntarios>
    suspend fun getParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): PartesAsistenciaVoluntarios?
    suspend fun createParteAsistenciaVoluntario( folioPAsistencia: Int, idVoluntario: Int): PartesAsistenciaVoluntarios
    suspend fun deleteParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): Boolean
    suspend fun updateParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int, folioPAsistencia: Int, idVoluntario: Int): PartesAsistenciaVoluntarios


}
