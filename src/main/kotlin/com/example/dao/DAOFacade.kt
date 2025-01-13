package com.example.dao

import Quadruple
import com.example.models.*
import kotlinx.datetime.LocalDate
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
    suspend fun createUsuario(nombreUsuario: String, contrasena: String): Usuarios
    suspend fun deleteUsuario(idUsuario: Int): Boolean
    suspend fun updateUsuario(idUsuario: Int, nombreUsuario: String, contrasena: String): Usuarios
    suspend fun loginUsuario(nombreUsuario: String, contrasena: String): Usuarios?

    // Voluntarios
    suspend fun allVoluntarios(): List<Voluntarios>
    suspend fun getVoluntario(idVoluntario: Int): Voluntarios?
    suspend fun createVoluntario(nombreVol: String, fechaNac: LocalDate, direccion: String, numeroContacto: String, tipoSangre: String?, enfermedades: String, alergias: String, fechaIngreso: LocalDate, claveRadial: String, rutVoluntario: String, idCompania: Int, idUsuario: Int?, idCargo: Int, apellidop: String, apellidom: String, activo: Boolean): Voluntarios
    suspend fun deleteVoluntario(idVoluntario: Int): Boolean
    suspend fun updateVoluntario(idVoluntario: Int, nombreVol: String, fechaNac: LocalDate, direccion: String, numeroContacto: String, tipoSangre: String?, enfermedades: String, alergias: String, fechaIngreso: LocalDate, claveRadial: String, rutVoluntario: String, idCompania: Int, idUsuario: Int?, idCargo: Int, apellidop: String, apellidom: String, activo: Boolean): Voluntarios
    suspend fun getVoluntarioByIdUsuario(idUsuario: Int): Voluntarios?
    // Extensión: Obtiene un voluntario con sus relaciones (Compañía, Usuario, Cargo)
    suspend fun getVoluntarioWithRelations(idVoluntario: Int): Triple< Companias?, Usuarios?, Cargos?>

    // Inmueble
    suspend fun allInmuebles(): List<Inmuebles>
    suspend fun getInmueble(idInmueble: Int): Inmuebles?
    suspend fun createInmueble(direccion: String, tipoInmueble: String, estadoInmueble: String, folioPEmergencia: Int?): Inmuebles
    suspend fun deleteInmueble(idInmueble: Int): Boolean
    suspend fun updateInmueble(idInmueble: Int, direccion: String, tipoInmueble: String, estadoInmueble: String, folioPEmergencia: Int?): Inmuebles

    // Victima
    suspend fun allVictimas(): List<Victimas>
    suspend fun getVictima(idVictima: Int): Victimas?
    suspend fun createVictima( rutVictima: String?, nombreVictima: String?, edadVictima: String?, descripcion: String?, folioPEmergencia: Int): Victimas
    suspend fun deleteVictima(idVictima: Int): Boolean
    suspend fun updateVictima(idVictima: Int, rutVictima: String?, nombreVictima: String?, edadVictima: String?, descripcion: String?, folioPEmergencia: Int): Victimas

    // Vehiculo
    suspend fun allVehiculos(): List<Vehiculos>
    suspend fun getVehiculo(idVehiculo: Int): Vehiculos?
    suspend fun createVehiculo( patente: String, marca: String, modelo: String, tipoVehiculo: String, folioPEmergencia: Int?): Vehiculos
    suspend fun deleteVehiculo(idVehiculo: Int): Boolean
    suspend fun updateVehiculo(idVehiculo: Int, patente: String, marca: String, modelo: String, tipoVehiculo: String, folioPEmergencia: Int?): Vehiculos

    // ClaveEmergencias
    suspend fun allClaveEmergencias(): List<ClaveEmergencias>
    suspend fun getClaveEmergencia(idClaveEmergencia: Int): ClaveEmergencias?
    suspend fun createClaveEmergencia(nombreClaveEmergencia: String): ClaveEmergencias
    suspend fun deleteClaveEmergencia(idClaveEmergencia: Int): Boolean
    suspend fun updateClaveEmergencia(idClaveEmergencia: Int, nombreClaveEmergencia: String): ClaveEmergencias


    // Partes_emergencia
    suspend fun allPartesEmergencia(): List<Partes_emergencia>
    suspend fun getParteEmergencia(folioPEmergencia: Int): Partes_emergencia?
    suspend fun createParteEmergencia(horaInicio: LocalTime, horaFin: LocalTime, fechaEmergencia: LocalDate, preInforme: String, llamarEmpresaQuimica: Boolean, descripcionMaterialP: String, direccionEmergencia: String, idOficial: Int, idClaveEmergencia: Int, folioPAsistencia: Int?): Partes_emergencia
    suspend fun deleteParteEmergencia(folioPEmergencia: Int): Boolean
    suspend fun updateParteEmergencia(folioPEmergencia: Int, horaInicio: LocalTime, horaFin: LocalTime, fechaEmergencia: LocalDate, preInforme: String, llamarEmpresaQuimica: Boolean, descripcionMaterialP: String, direccionEmergencia: String, idOficial: Int, idClaveEmergencia: Int, folioPAsistencia: Int?): Partes_emergencia
    suspend fun getParteEmergenciaWithRelations(folioPEmergencia: Int): Quadruple<List<Moviles>, List<Voluntarios>, List<MaterialesP>, Partes_asistencia?>


    // Partes_asistencia
    suspend fun allPartesAsistencia(): List<Partes_asistencia>
    suspend fun getParteAsistencia(folioPAsistencia: Int): Partes_asistencia?
    suspend fun createParteAsistencia(aCargoDelCuerpo: Int, aCargoDeLaCompania: Int, fechaAsistencia: LocalDate, horaInicio: LocalTime, horaFin: LocalTime, direccionAsistencia: String, totalAsistencia: Int, observaciones: String, idTipoLlamado: Int): Partes_asistencia
    suspend fun deleteParteAsistencia(folioPAsistencia: Int): Boolean
    suspend fun updateParteAsistencia(folioPAsistencia: Int, aCargoDelCuerpo: Int, aCargoDeLaCompania: Int, fechaAsistencia: LocalDate, horaInicio: LocalTime, horaFin: LocalTime, direccionAsistencia: String, totalAsistencia: Int, observaciones: String, idTipoLlamado: Int): Partes_asistencia
    suspend fun getParteAsistenciaWithRelations(folioPAsistencia: Int): Triple<TipoCitacion?, List<Moviles>, List<Voluntarios>>
    suspend fun getParteAsistenciaResponse (folioPAsistencia: Int): ParteAsistenciaResponse?

    // MaterialP
    suspend fun allMaterialesP(): List<MaterialesP>
    suspend fun getMaterialP(idMaterialP: Int): MaterialesP?
    suspend fun createMaterialP( clasificacion: String): MaterialesP
    suspend fun deleteMaterialP(idMaterialP: Int): Boolean
    suspend fun updateMaterialP(idMaterialP: Int, clasificacion: String ): MaterialesP

    // Movil
    suspend fun allMoviles(): List<Moviles>
    suspend fun getMovil(idMovil: Int): Moviles?
    suspend fun createMovil( nomenclatura: String, especialidad: String): Moviles
    suspend fun deleteMovil(idMovil: Int): Boolean
    suspend fun updateMovil(idMovil: Int, nomenclatura: String, especialidad: String): Moviles

    // ParteEmergenciaVoluntario
    suspend fun allParteEmergenciaVoluntarios(): List<PartesEmergenciaVoluntarios>
    suspend fun getParteEmergenciaVoluntario(idParteVoluntario: Int): PartesEmergenciaVoluntarios?
    suspend fun createParteEmergenciaVoluntario( folioPEmergencia: Int, idVoluntario: Int): PartesEmergenciaVoluntarios
    suspend fun deleteParteEmergenciaVoluntario(idParteVoluntario: Int): Boolean
    suspend fun updateParteEmergenciaVoluntario(idParteVoluntario: Int, folioPEmergencia: Int, idVoluntario: Int): PartesEmergenciaVoluntarios
    suspend fun getVoluntariosPorParteEmergencia(folioPAsistencia: Int): List<Voluntarios>

    //parteAsistenciaVoluntario
    suspend fun allParteAsistenciaVoluntarios(): List<PartesAsistenciaVoluntarios>
    suspend fun getParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): PartesAsistenciaVoluntarios?
    suspend fun createParteAsistenciaVoluntario( folioPAsistencia: Int, idVoluntario: Int): PartesAsistenciaVoluntarios
    suspend fun deleteParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): Boolean
    suspend fun updateParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int, folioPAsistencia: Int, idVoluntario: Int): PartesAsistenciaVoluntarios
    suspend fun getVoluntariosPorParteAsistencia(folioPAsistencia: Int): List<Voluntarios>

    //ParteAsistenciaMovil
    suspend fun allParteAsistenciaMovil(): List<PartesAsistenciaMoviles>
    suspend fun getParteAsistenciaMovil(idParteAsistenciaMovil: Int): PartesAsistenciaMoviles?
    suspend fun createParteAsistenciaMovil(folioPAsistencia: Int, idMovil: Int): PartesAsistenciaMoviles
    suspend fun deleteParteAsistenciaMovil(idParteAsistenciaMovil: Int): Boolean
    suspend fun updateParteAsistenciaMovil(idParteAsistenciaMovil: Int, folioPAsistencia: Int, idMovil: Int): PartesAsistenciaMoviles
    suspend fun getMovilesPorParteAsistencia(folioPAsistencia: Int): List<Moviles>

    //ParteEmergenciaMovil
    suspend fun allParteEmergenciaMovil(): List<PartesEmergenciaMoviles>
    suspend fun getParteEmergenciaMovil(idParteEmergenciaMovil: Int): PartesEmergenciaMoviles?
    suspend fun createParteEmergenciaMovil(folioPEmergencia: Int, idMovil: Int): PartesEmergenciaMoviles
    suspend fun deleteParteEmergenciaMovil(idParteEmergenciaMovil: Int): Boolean
    suspend fun updateParteEmergenciaMovil(idParteEmergenciaMovil: Int, folioPEmergencia: Int, idMovil: Int): PartesEmergenciaMoviles
    suspend fun getParteEmergenciaMovilByFolio(folioPEmergencia: Int): List<PartesEmergenciaMoviles>
    suspend fun getMovilesPorParteEmergencia(folioPEmergencia: Int): List<Moviles>

    // ParteEmergenciaMaterial
    suspend fun allParteEmergenciaMaterial(): List<PartesEmergenciaMateriales>
    suspend fun getParteEmergenciaMaterial(idparteemergenciamaterialp: Int): PartesEmergenciaMateriales?
    suspend fun createParteEmergenciaMaterial(folioPEmergencia: Int, idMaterialP: Int): PartesEmergenciaMateriales
    suspend fun deleteParteEmergenciaMaterial(idparteemergenciamaterialp: Int): Boolean
    suspend fun updateParteEmergenciaMaterial(idparteemergenciamaterialp: Int, folioPEmergencia: Int, idMaterialP: Int): PartesEmergenciaMateriales
    suspend fun getMaterialPByParteEmergencia(folioPEmergencia: Int): List<MaterialesP>

    // Cargos
    suspend fun allCargos(): List<Cargos>
    suspend fun getCargo(idCargo: Int): Cargos?
    suspend fun createCargo(idCargo: Int, nombreCarg: String): Cargos
    suspend fun deleteCargo(idCargo: Int): Boolean
    suspend fun updateCargo(idCargo: Int, nombreCarg: String): Cargos

    // TipoCitacion
    suspend fun allTipoCitaciones(): List<TipoCitacion>
    suspend fun getTipoCitacion(idTipoLlamado: Int): TipoCitacion?
    suspend fun createTipoCitacion(nombreTipoLlamado: String): TipoCitacion
    suspend fun deleteTipoCitacion(idTipoLlamado: Int): Boolean
    suspend fun updateTipoCitacion(idTipoLlamado: Int, nombreTipoLlamado: String): TipoCitacion

    //login con cargo
    suspend fun loginConCargo (nombreUsuario: String, contrasena: String): Pair<Usuarios?, Cargos?>

    //arreglo de victimas
    suspend fun createVictimas(victimas: List<Victimas>, folioPEmergencia: Int): List<Victimas>
    //arreglo de vehiculos
    suspend fun createVehiculos(vehiculos: List<Vehiculos>, folioPEmergencia: Int): List<Vehiculos>
    //arreglo de instituciones
    suspend fun createInstituciones(instituciones: List<Instituciones>, folioPEmergencia: Int): List<Instituciones>
    //arreglo de inmuebles
    suspend fun createInmuebles(inmuebles: List<Inmuebles>, folioPEmergencia: Int): List<Inmuebles>
    //deleteVictimasByFolio
    suspend fun deleteVictimasByFolio(folioPEmergencia: Int): Boolean
    //deleteVehiculosByFolio
    suspend fun deleteVehiculosByFolio(folioPEmergencia: Int): Boolean
    //deleteInstitucionesByFolio
    suspend fun deleteInstitucionesByFolio(folioPEmergencia: Int): Boolean
    //deleteInmueblesByFolio
    suspend fun deleteInmueblesByFolio(folioPEmergencia: Int): Boolean
    //getVictimasByFolio
    suspend fun getVictimasByFolio(folioPEmergencia: Int): List<Victimas>


}
