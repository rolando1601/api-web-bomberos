package com.example.dao


import com.example.dao.DatabaseSingleton.dbQuery
import com.example.models.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*
import com.example.models.ParteEmergencia
import kotlinx.datetime.LocalDateTime


class DAOFacadeImpl : DAOFacade {

    // Voluntario implementation


    private fun resultToVoluntario(row: ResultRow) = Voluntario(
        idVoluntario = row[Voluntarios.idVoluntario],
        nombreVol = row[Voluntarios.nombreVol],
        fechaNac = row[Voluntarios.fechaNac],
        direccion = row[Voluntarios.direccion],
        numeroContacto = row[Voluntarios.numeroContacto],
        tipoSangre = row[Voluntarios.tipoSangre],
        enfermedades = row[Voluntarios.enfermedades],
        alergias = row[Voluntarios.alergias],
        fechaIngreso = row[Voluntarios.fechaIngreso],
        claveRadial = row[Voluntarios.claveRadial],
        cargoVoluntario = row[Voluntarios.cargoVoluntario],
        idUsuario = row[Voluntarios.idUsuario],
        idCompania = row[Voluntarios.idCompania],
        folioPEmergencia = row[Voluntarios.folioPEmergencia], // Sin intentar resolver `ParteEmergencias`
        folioPAsistencia = row[Voluntarios.folioPAsistencia]  // Sin intentar resolver `ParteAsistencias`
    )


    override suspend fun allVoluntarios(): List<Voluntario> = dbQuery {
        Voluntarios.selectAll().map(::resultToVoluntario)
    }

    override suspend fun getVoluntario(idVoluntario: Int): Voluntario? = dbQuery {
        Voluntarios.select { Voluntarios.idVoluntario eq idVoluntario }
            .mapNotNull(::resultToVoluntario)
            .singleOrNull()
    }

    override suspend fun createVoluntario(
        idVoluntario: Int,
        nombreVol: String,
        fechaNac: LocalDate,
        direccion: String,
        numeroContacto: String,
        tipoSangre: String,
        enfermedades: String,
        alergias: String,
        fechaIngreso: LocalDate,
        claveRadial: String,
        cargoVoluntario: String,
        idUsuario: Int,
        idCompania: Int,
        folioPEmergencia: Int,
        folioPAsistencia: Int
    ): Voluntario {
        var generatedId = 0
        dbQuery {
            generatedId = (Voluntarios.insert {
                it[Voluntarios.idVoluntario] = idVoluntario
                it[Voluntarios.nombreVol] = nombreVol
                it[Voluntarios.fechaNac] = fechaNac
                it[Voluntarios.direccion] = direccion
                it[Voluntarios.numeroContacto] = numeroContacto
                it[Voluntarios.tipoSangre] = tipoSangre
                it[Voluntarios.enfermedades] = enfermedades
                it[Voluntarios.alergias] = alergias
                it[Voluntarios.fechaIngreso] = fechaIngreso
                it[Voluntarios.claveRadial] = claveRadial
                it[Voluntarios.cargoVoluntario] = cargoVoluntario
                it[Voluntarios.idUsuario] = idUsuario
                it[Voluntarios.idCompania] = idCompania
                it[Voluntarios.folioPEmergencia] = folioPEmergencia
                it[Voluntarios.folioPAsistencia] = folioPAsistencia
            } get Voluntarios.idVoluntario)!!
        }
        return Voluntario(
            generatedId, nombreVol, fechaNac, direccion, numeroContacto, tipoSangre, enfermedades, alergias,
            fechaIngreso, claveRadial, cargoVoluntario, idUsuario, idCompania, folioPEmergencia, folioPAsistencia
        )
    }



    override suspend fun deleteVoluntario(idVoluntario: Int): Boolean = dbQuery {
        Voluntarios.deleteWhere { Voluntarios.idVoluntario eq idVoluntario } > 0
    }

    override suspend fun updateVoluntario(
        idVoluntario: Int,
        nombreVol: String,
        fechaNac: LocalDate,
        direccion: String,
        numeroContacto: String,
        tipoSangre: String,
        enfermedades: String,
        alergias: String,
        fechaIngreso: LocalDate,
        claveRadial: String,
        cargoVoluntario: String,
        idUsuario: Int,
        idCompania: Int,
        folioPEmergencia: Int,
        folioPAsistencia: Int
    ): Voluntario {
        val rowsUpdated = dbQuery {
            Voluntarios.update({ Voluntarios.idVoluntario eq idVoluntario }) {
                it[Voluntarios.nombreVol] = nombreVol
                it[Voluntarios.fechaNac] = fechaNac
                it[Voluntarios.direccion] = direccion
                it[Voluntarios.numeroContacto] = numeroContacto
                it[Voluntarios.tipoSangre] = tipoSangre
                it[Voluntarios.enfermedades] = enfermedades
                it[Voluntarios.alergias] = alergias
                it[Voluntarios.fechaIngreso] = fechaIngreso
                it[Voluntarios.claveRadial] = claveRadial
                it[Voluntarios.cargoVoluntario] = cargoVoluntario
                it[Voluntarios.idUsuario] = idUsuario
                it[Voluntarios.idCompania] = idCompania
                it[Voluntarios.folioPEmergencia] = folioPEmergencia
                it[Voluntarios.folioPAsistencia] = folioPAsistencia
            }
        }

        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Voluntario con id $idVoluntario no encontrado")
        }

        return Voluntario(
            idVoluntario,
            nombreVol,
            fechaNac,
            direccion,
            numeroContacto,
            tipoSangre,
            enfermedades,
            alergias,
            fechaIngreso,
            claveRadial,
            cargoVoluntario,
            idUsuario,
            idCompania,
            folioPEmergencia,
            folioPAsistencia
        )
    }



    // Institucion implementation

    private fun resultToInstitucion(row: ResultRow) = Institucion(
        idInstitucion = row[Instituciones.idInstitucion],
        nombreInstitucion = row[Instituciones.nombreInstitucion],
        tipoInstitucion = row[Instituciones.tipoInstitucion],
        nombrePersonaCargo = row[Instituciones.nombrePersonaCargo],
        horaLlegada = row[Instituciones.horaLlegada].toString(),
        folioPEmergencia = row[Instituciones.folioPEmergencia]
    )

    override suspend fun allInstituciones(): List<Institucion> = dbQuery {
        Instituciones.selectAll().map(::resultToInstitucion)
    }

    override suspend fun getInstitucion(idInstitucion: Int): Institucion? = dbQuery {
        Instituciones.select { Instituciones.idInstitucion eq idInstitucion }
            .mapNotNull(::resultToInstitucion)
            .singleOrNull()
    }

    override suspend fun createInstitucion(
        idInstitucion: Int,
        nombreInstitucion: String,
        tipoInstitucion: String,
        nombrePersonaCargo: String,
        horaLlegada: LocalTime,
        folioPEmergencia: Int
    ): Institucion {
        var generatedId = 0
        dbQuery {
            generatedId = (Instituciones.insert {
                if (idInstitucion != null) it[Instituciones.idInstitucion] = idInstitucion
                it[Instituciones.nombreInstitucion] = nombreInstitucion
                it[Instituciones.tipoInstitucion] = tipoInstitucion
                it[Instituciones.nombrePersonaCargo] = nombrePersonaCargo
                it[Instituciones.horaLlegada] = horaLlegada
                it[Instituciones.folioPEmergencia] = folioPEmergencia
            } get Instituciones.idInstitucion)!!
        }
        return Institucion(
            generatedId,
            nombreInstitucion,
            tipoInstitucion,
            nombrePersonaCargo,
            horaLlegada.toString(),
            folioPEmergencia
        )
    }


    override suspend fun deleteInstitucion(idInstitucion: Int): Boolean = dbQuery {
        Instituciones.deleteWhere { Instituciones.idInstitucion eq idInstitucion } > 0
    }

    override suspend fun updateInstitucion(
        idInstitucion: Int,
        nombreInstitucion: String,
        tipoInstitucion: String,
        nombrePersonaCargo: String,
        horaLlegada: LocalTime,
        folioPEmergencia: Int
    ): Institucion {
        val rowsUpdated = dbQuery {
            Instituciones.update({ Instituciones.idInstitucion eq idInstitucion }) {
                it[Instituciones.nombreInstitucion] = nombreInstitucion
                it[Instituciones.tipoInstitucion] = tipoInstitucion
                it[Instituciones.nombrePersonaCargo] = nombrePersonaCargo
                it[Instituciones.horaLlegada] = horaLlegada
                it[Instituciones.folioPEmergencia] = folioPEmergencia
            }
        }

        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Institucion con id $idInstitucion no encontrada")
        }

        return Institucion(
            idInstitucion,
            nombreInstitucion,
            tipoInstitucion,
            nombrePersonaCargo,
            horaLlegada.toString(),
            folioPEmergencia
        )
    }




    //Cuerpo implementation

    private fun resultToCuerpo(row: ResultRow) = Cuerpo(
        idCuerpo = row[Cuerpos.idCuerpo],
        nombreCuerpo = row[Cuerpos.nombreCuerpo],
        provincia = row[Cuerpos.provincia],
        region = row[Cuerpos.region],
        comuna = row[Cuerpos.comuna]
    )

    override suspend fun allCuerpos(): List<Cuerpo> = dbQuery {
        Cuerpos.selectAll().map(::resultToCuerpo)
    }

    override suspend fun getCuerpo(idCuerpo: Int): Cuerpo? = dbQuery {
        Cuerpos.select { Cuerpos.idCuerpo eq idCuerpo }
            .mapNotNull(::resultToCuerpo)
            .singleOrNull()
    }

    override suspend fun createCuerpo(
        idCuerpo: Int,
        nombreCuerpo: String,
        provincia: String,
        region: String,
        comuna: String
    ): Cuerpo = dbQuery {
        val insertStatement = Cuerpos.insert {
            it[Cuerpos.idCuerpo] = idCuerpo
            it[Cuerpos.nombreCuerpo] = nombreCuerpo
            it[Cuerpos.provincia] = provincia
            it[Cuerpos.region] = region
            it[Cuerpos.comuna] = comuna
        }
        val generatedId = insertStatement.resultedValues?.get(0)?.get(Cuerpos.idCuerpo)
            ?: throw IllegalStateException("Insert failed")
        Cuerpo(generatedId, nombreCuerpo, provincia, region, comuna)
    }

    override suspend fun deleteCuerpo(idCuerpo: Int): Boolean = dbQuery {
        Cuerpos.deleteWhere { Cuerpos.idCuerpo eq idCuerpo } > 0
    }

    override suspend fun updateCuerpo(
        idCuerpo: Int,
        nombreCuerpo: String,
        provincia: String,
        region: String,
        comuna: String
    ): Cuerpo = dbQuery {
        val rowsUpdated = Cuerpos.update({ Cuerpos.idCuerpo eq idCuerpo }) {
            it[Cuerpos.nombreCuerpo] = nombreCuerpo
            it[Cuerpos.provincia] = provincia
            it[Cuerpos.region] = region
            it[Cuerpos.comuna] = comuna
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Cuerpo con id $idCuerpo no encontrado")
        }
        Cuerpo(idCuerpo, nombreCuerpo, provincia, region, comuna)
    }





    // Compania implementation


    private fun resultToCompania(row: ResultRow) = Compania(
        idCompania = row[Companias.idCompania],
        nombreCia = row[Companias.nombreCia],
        direccionCia = row[Companias.direccionCia],
        especialidad = row[Companias.especialidad],
        idCuerpo = row[Companias.idCuerpo]
    )

    override suspend fun allCompanias(): List<Compania> = dbQuery {
        Companias.selectAll().map(::resultToCompania)
    }

    override suspend fun getCompania(idCompania: Int): Compania? = dbQuery {
        Companias.select { Companias.idCompania eq idCompania }
            .mapNotNull(::resultToCompania)
            .singleOrNull()
    }

    override suspend fun createCompania(
        idCompania: Int,
        nombreCia: String,
        direccionCia: String,
        especialidad: String,
        idCuerpo: Int
    ): Compania = dbQuery {
        val insertStatement = Companias.insert {
            it[Companias.idCompania] = idCompania
            it[Companias.nombreCia] = nombreCia
            it[Companias.direccionCia] = direccionCia
            it[Companias.especialidad] = especialidad
            it[Companias.idCuerpo] = idCuerpo
        }
        val generatedId = insertStatement.resultedValues?.get(0)?.get(Companias.idCompania)
            ?: throw IllegalStateException("Insert failed")
        Compania(generatedId, nombreCia, direccionCia, especialidad, idCuerpo)
    }

    override suspend fun deleteCompania(idCompania: Int): Boolean = dbQuery {
        Companias.deleteWhere { Companias.idCompania eq idCompania } > 0
    }

    override suspend fun updateCompania(
        idCompania: Int,
        nombreCia: String,
        direccionCia: String,
        especialidad: String,
        idCuerpo: Int
    ): Compania = dbQuery {
        val rowsUpdated = Companias.update({ Companias.idCompania eq idCompania }) {
            it[Companias.nombreCia] = nombreCia
            it[Companias.direccionCia] = direccionCia
            it[Companias.especialidad] = especialidad
            it[Companias.idCuerpo] = idCuerpo
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Compania con id $idCompania no encontrada")
        }
        Compania(idCompania, nombreCia, direccionCia, especialidad, idCuerpo)
    }






    // Usuario implementation


    private fun resultToUsuario(row: ResultRow) = Usuario(
        idUsuario = row[Usuarios.idUsuario],
        nombreUsuario = row[Usuarios.nombreUsuario],
        contrasena = row[Usuarios.contrasena],
        rol = row[Usuarios.rol]
    )

    override suspend fun allUsuarios(): List<Usuario> = dbQuery {
        Usuarios.selectAll().map(::resultToUsuario)
    }

    override suspend fun getUsuario(idUsuario: Int): Usuario? = dbQuery {
        Usuarios.select { Usuarios.idUsuario eq idUsuario }
            .mapNotNull(::resultToUsuario)
            .singleOrNull()
    }

    override suspend fun createUsuario(
        idUsuario: Int,
        nombreUsuario: String,
        contrasena: String,
        rol: String
    ): Usuario = dbQuery {
        val insertStatement = Usuarios.insert {
            it[Usuarios.idUsuario] = idUsuario
            it[Usuarios.nombreUsuario] = nombreUsuario
            it[Usuarios.contrasena] = contrasena
            it[Usuarios.rol] = rol
        }
        val generatedId = insertStatement.resultedValues?.get(0)?.get(Usuarios.idUsuario)
            ?: throw IllegalStateException("Insert failed")
        Usuario(generatedId, nombreUsuario, contrasena, rol)
    }

    override suspend fun deleteUsuario(idUsuario: Int): Boolean = dbQuery {
        Usuarios.deleteWhere { Usuarios.idUsuario eq idUsuario } > 0
    }

    override suspend fun updateUsuario(
        idUsuario: Int,
        nombreUsuario: String,
        contrasena: String,
        rol: String
    ): Usuario = dbQuery {
        val rowsUpdated = Usuarios.update({ Usuarios.idUsuario eq idUsuario }) {
            it[Usuarios.nombreUsuario] = nombreUsuario
            it[Usuarios.contrasena] = contrasena
            it[Usuarios.rol] = rol
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Usuario con id $idUsuario no encontrado")
        }
        Usuario(idUsuario, nombreUsuario, contrasena, rol)
    }


    // Inmueble implementation


    private fun resultToInmueble(row: ResultRow) = Inmueble(
        idInmueble = row[Inmuebles.idInmueble],
        direccion = row[Inmuebles.direccion],
        tipoInmueble = row[Inmuebles.tipoInmueble],
        estadoInmueble = row[Inmuebles.estadoInmueble],
        folioPEmergencia = row[Inmuebles.folioPEmergencia]
    )

    override suspend fun allInmuebles(): List<Inmueble> = dbQuery {
        Inmuebles.selectAll().map(::resultToInmueble)
    }

    override suspend fun getInmueble(idInmueble: Int): Inmueble? = dbQuery {
        Inmuebles.select { Inmuebles.idInmueble eq idInmueble }
            .mapNotNull(::resultToInmueble)
            .singleOrNull()
    }

    override suspend fun createInmueble(
        idInmueble: Int,
        direccion: String,
        tipoInmueble: String,
        estadoInmueble: String,
        folioPEmergencia: Int
    ): Inmueble = dbQuery {
        val insertStatement = Inmuebles.insert {
            it[Inmuebles.idInmueble] = idInmueble
            it[Inmuebles.direccion] = direccion
            it[Inmuebles.tipoInmueble] = tipoInmueble
            it[Inmuebles.estadoInmueble] = estadoInmueble
            it[Inmuebles.folioPEmergencia] = folioPEmergencia
        }
        val generatedId = insertStatement.resultedValues?.get(0)?.get(Inmuebles.idInmueble)
            ?: throw IllegalStateException("Insert failed")
        Inmueble(generatedId, direccion, tipoInmueble, estadoInmueble, folioPEmergencia)
    }

    override suspend fun deleteInmueble(idInmueble: Int): Boolean = dbQuery {
        Inmuebles.deleteWhere { Inmuebles.idInmueble eq idInmueble } > 0
    }

    override suspend fun updateInmueble(
        idInmueble: Int,
        direccion: String,
        tipoInmueble: String,
        estadoInmueble: String,
        folioPEmergencia: Int
    ): Inmueble = dbQuery {
        val rowsUpdated = Inmuebles.update({ Inmuebles.idInmueble eq idInmueble }) {
            it[Inmuebles.direccion] = direccion
            it[Inmuebles.tipoInmueble] = tipoInmueble
            it[Inmuebles.estadoInmueble] = estadoInmueble
            it[Inmuebles.folioPEmergencia] = folioPEmergencia
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Inmueble con id $idInmueble no encontrado")
        }
        Inmueble(idInmueble, direccion, tipoInmueble, estadoInmueble, folioPEmergencia)
    }

    // Victima implementation
    private fun resultToVictima(row: ResultRow) = Victima(
        rutVictima = row[Victimas.rutVictima],
        nombreVictima = row[Victimas.nombreVictima],
        edadVictima = row[Victimas.edadVictima],
        folioPEmergencia = row[Victimas.folioPEmergencia]
    )

    override suspend fun allVictimas(): List<Victima> = dbQuery {
        Victimas.selectAll().map(::resultToVictima)
    }

    override suspend fun getVictima(rutVictima: String): Victima? = dbQuery {
        Victimas.select { Victimas.rutVictima eq rutVictima }
            .mapNotNull(::resultToVictima)
            .singleOrNull()
    }

    override suspend fun createVictima(
        rutVictima: String,
        nombreVictima: String,
        edadVictima: Int,
        folioPEmergencia: Int
    ): Victima = dbQuery {
        val insertStatement = Victimas.insert {
            it[Victimas.rutVictima] = rutVictima
            it[Victimas.nombreVictima] = nombreVictima
            it[Victimas.edadVictima] = edadVictima
            it[Victimas.folioPEmergencia] = folioPEmergencia
        }
        Victima(rutVictima, nombreVictima, edadVictima, folioPEmergencia)
    }

    override suspend fun deleteVictima(rutVictima: String): Boolean = dbQuery {
        Victimas.deleteWhere { Victimas.rutVictima eq rutVictima } > 0
    }

    override suspend fun updateVictima(
        rutVictima: String,
        nombreVictima: String,
        edadVictima: Int,
        folioPEmergencia: Int
    ): Victima = dbQuery {
        val rowsUpdated = Victimas.update({ Victimas.rutVictima eq rutVictima }) {
            it[Victimas.nombreVictima] = nombreVictima
            it[Victimas.edadVictima] = edadVictima
            it[Victimas.folioPEmergencia] = folioPEmergencia
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Victima con RUT $rutVictima no encontrada")
        }
        Victima(rutVictima, nombreVictima, edadVictima, folioPEmergencia)
    }



    // Vehiculo implementation


    private fun resultToVehiculo(row: ResultRow) = Vehiculo(
        patente = row[Vehiculos.patente],
        marca = row[Vehiculos.marca],
        modelo = row[Vehiculos.modelo],
        tipoVehiculo = row[Vehiculos.tipoVehiculo],
        folioPEmergencia = row[Vehiculos.folioPEmergencia]
    )

    override suspend fun allVehiculos(): List<Vehiculo> = dbQuery {
        Vehiculos.selectAll().map(::resultToVehiculo)
    }

    override suspend fun getVehiculo(patente: String): Vehiculo? = dbQuery {
        Vehiculos.select { Vehiculos.patente eq patente }
            .mapNotNull(::resultToVehiculo)
            .singleOrNull()
    }

    override suspend fun createVehiculo(
        patente: String,
        marca: String,
        modelo: String,
        tipoVehiculo: String,
        folioPEmergencia: Int
    ): Vehiculo = dbQuery {
        val insertStatement = Vehiculos.insert {
            it[Vehiculos.patente] = patente
            it[Vehiculos.marca] = marca
            it[Vehiculos.modelo] = modelo
            it[Vehiculos.tipoVehiculo] = tipoVehiculo
            it[Vehiculos.folioPEmergencia] = folioPEmergencia
        }
        Vehiculo(patente, marca, modelo, tipoVehiculo, folioPEmergencia)
    }

    override suspend fun deleteVehiculo(patente: String): Boolean = dbQuery {
        Vehiculos.deleteWhere { Vehiculos.patente eq patente } > 0
    }

    override suspend fun updateVehiculo(
        patente: String,
        marca: String,
        modelo: String,
        tipoVehiculo: String,
        folioPEmergencia: Int
    ): Vehiculo = dbQuery {
        val rowsUpdated = Vehiculos.update({ Vehiculos.patente eq patente }) {
            it[Vehiculos.marca] = marca
            it[Vehiculos.modelo] = modelo
            it[Vehiculos.tipoVehiculo] = tipoVehiculo
            it[Vehiculos.folioPEmergencia] = folioPEmergencia
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Vehiculo con patente $patente no encontrado")
        }
        Vehiculo(patente, marca, modelo, tipoVehiculo, folioPEmergencia)
    }

    // Emergencia implementation

    private fun resultToEmergencia(row: ResultRow) = Emergencia(
        idEmergencia = row[Emergencias.idEmergencia],
        claveEmergencia = row[Emergencias.claveEmergencia],
        cuadrante = row[Emergencias.cuadrante],
        direccion = row[Emergencias.direccion]
    )

    override suspend fun allEmergencias(): List<Emergencia> = dbQuery {
        Emergencias.selectAll().map(::resultToEmergencia)
    }

    override suspend fun getEmergencia(idEmergencia: Int): Emergencia? = dbQuery {
        Emergencias.select { Emergencias.idEmergencia eq idEmergencia }
            .mapNotNull(::resultToEmergencia)
            .singleOrNull()
    }

    override suspend fun createEmergencia(
        idEmergencia: Int,
        claveEmergencia: String,
        cuadrante: String,
        direccion: String
    ): Emergencia = dbQuery {
        val insertStatement = Emergencias.insert {
            it[Emergencias.idEmergencia] = idEmergencia
            it[Emergencias.claveEmergencia] = claveEmergencia
            it[Emergencias.cuadrante] = cuadrante
            it[Emergencias.direccion] = direccion
        }
        val generatedId = insertStatement.resultedValues?.get(0)?.get(Emergencias.idEmergencia)
            ?: throw IllegalStateException("Insert failed")
        Emergencia(generatedId, claveEmergencia, cuadrante, direccion)
    }

    override suspend fun deleteEmergencia(idEmergencia: Int): Boolean = dbQuery {
        Emergencias.deleteWhere { Emergencias.idEmergencia eq idEmergencia } > 0
    }

    override suspend fun updateEmergencia(
        idEmergencia: Int,
        claveEmergencia: String,
        cuadrante: String,
        direccion: String
    ): Emergencia = dbQuery {
        val rowsUpdated = Emergencias.update({ Emergencias.idEmergencia eq idEmergencia }) {
            it[Emergencias.claveEmergencia] = claveEmergencia
            it[Emergencias.cuadrante] = cuadrante
            it[Emergencias.direccion] = direccion
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Emergencia con id $idEmergencia no encontrada")
        }
        Emergencia(idEmergencia, claveEmergencia, cuadrante, direccion)
    }



    // MaterialP implementation

    private fun resultToMaterialP(row: ResultRow) = MaterialP(
        idMaterialP = row[MaterialesP.idMaterialP],
        numeroONU = row[MaterialesP.numeroONU],
        clasificacion = row[MaterialesP.clasificacion],
        nombre = row[MaterialesP.nombre],
        folioPEmergencia = row[MaterialesP.folioPEmergencia]
    )

    override suspend fun allMaterialesP(): List<MaterialP> = dbQuery {
        MaterialesP.selectAll().map(::resultToMaterialP)
    }

    override suspend fun getMaterialP(idMaterialP: Int): MaterialP? = dbQuery {
        MaterialesP.select { MaterialesP.idMaterialP eq idMaterialP }
            .mapNotNull(::resultToMaterialP)
            .singleOrNull()
    }

    override suspend fun createMaterialP(
        idMaterialP: Int,
        numeroONU: String,
        clasificacion: String,
        nombre: String,
        folioPEmergencia: Int
    ): MaterialP = dbQuery {
        val insertStatement = MaterialesP.insert {
            it[MaterialesP.idMaterialP] = idMaterialP
            it[MaterialesP.numeroONU] = numeroONU
            it[MaterialesP.clasificacion] = clasificacion
            it[MaterialesP.nombre] = nombre
            it[MaterialesP.folioPEmergencia] = folioPEmergencia
        }
        val generatedId = insertStatement.resultedValues?.get(0)?.get(MaterialesP.idMaterialP)
            ?: throw IllegalStateException("Insert failed")
        MaterialP(generatedId, numeroONU, clasificacion, nombre, folioPEmergencia)
    }

    override suspend fun deleteMaterialP(idMaterialP: Int): Boolean = dbQuery {
        MaterialesP.deleteWhere { MaterialesP.idMaterialP eq idMaterialP } > 0
    }

    override suspend fun updateMaterialP(
        idMaterialP: Int,
        numeroONU: String,
        clasificacion: String,
        nombre: String,
        folioPEmergencia: Int
    ): MaterialP = dbQuery {
        val rowsUpdated = MaterialesP.update({ MaterialesP.idMaterialP eq idMaterialP }) {
            it[MaterialesP.numeroONU] = numeroONU
            it[MaterialesP.clasificacion] = clasificacion
            it[MaterialesP.nombre] = nombre
            it[MaterialesP.folioPEmergencia] = folioPEmergencia
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("MaterialP con id $idMaterialP no encontrado")
        }
        MaterialP(idMaterialP, numeroONU, clasificacion, nombre, folioPEmergencia)
    }

    // ParteEmergencia implementation

    private fun resultToParteEmergencia(row: ResultRow) = ParteEmergencia(
        folioPEmergencia = row[ParteEmergencias.folioPEmergencia],
        tipoEmergencia = row[ParteEmergencias.tipoEmergencia],
        horaInicio = row[ParteEmergencias.horaInicio],
        horaFin = row[ParteEmergencias.horaFin],
        fechaEmergencia = row[ParteEmergencias.fechaEmergencia],
        preInforme = row[ParteEmergencias.preInforme],
        oficial = row[ParteEmergencias.oficial],
        idEmergencia = row[ParteEmergencias.idEmergencia],
        folioPAsistencia = row[ParteEmergencias.folioPAsistencia]
    )

    override suspend fun allParteEmergencias(): List<ParteEmergencia> = dbQuery {
        ParteEmergencias.selectAll().map(::resultToParteEmergencia)
    }

    override suspend fun getParteEmergencia(folioPEmergencia: Int): ParteEmergencia? = dbQuery {
        ParteEmergencias.select { ParteEmergencias.folioPEmergencia eq folioPEmergencia }
            .mapNotNull(::resultToParteEmergencia)
            .singleOrNull()
    }

    override suspend fun createParteEmergencia(
        folioPEmergencia: Int,
        tipoEmergencia: String,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        fechaEmergencia: LocalDate,
        preInforme: String?,
        oficial: String,
        idEmergencia: Int,
        folioPAsistencia: Int
    ): ParteEmergencia {
        var generatedFolio = 0
        dbQuery {
            generatedFolio = (ParteEmergencias.insert {
                it[ParteEmergencias.folioPEmergencia] = folioPEmergencia
                it[ParteEmergencias.tipoEmergencia] = tipoEmergencia
                it[ParteEmergencias.horaInicio] = horaInicio
                it[ParteEmergencias.horaFin] = horaFin
                it[ParteEmergencias.fechaEmergencia] = fechaEmergencia
                it[ParteEmergencias.preInforme] = preInforme
                it[ParteEmergencias.oficial] = oficial
                it[ParteEmergencias.idEmergencia] = idEmergencia
                it[ParteEmergencias.folioPAsistencia] = folioPAsistencia
            } get ParteEmergencias.folioPEmergencia)!!
        }
        return ParteEmergencia(
            generatedFolio, tipoEmergencia, horaInicio, horaFin, fechaEmergencia, preInforme, oficial, idEmergencia, folioPAsistencia
        )
    }

    override suspend fun deleteParteEmergencia(folioPEmergencia: Int): Boolean = dbQuery {
        ParteEmergencias.deleteWhere { ParteEmergencias.folioPEmergencia eq folioPEmergencia } > 0
    }

    override suspend fun updateParteEmergencia(
        folioPEmergencia: Int,
        tipoEmergencia: String,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        fechaEmergencia: LocalDate,
        preInforme: String?,
        oficial: String,
        idEmergencia: Int,
        folioPAsistencia: Int
    ): ParteEmergencia {
        val rowsUpdated = dbQuery {
            ParteEmergencias.update({ ParteEmergencias.folioPEmergencia eq folioPEmergencia }) {
                it[ParteEmergencias.tipoEmergencia] = tipoEmergencia
                it[ParteEmergencias.horaInicio] = horaInicio
                it[ParteEmergencias.horaFin] = horaFin
                it[ParteEmergencias.fechaEmergencia] = fechaEmergencia
                it[ParteEmergencias.preInforme] = preInforme
                it[ParteEmergencias.oficial] = oficial
                it[ParteEmergencias.idEmergencia] = idEmergencia
                it[ParteEmergencias.folioPAsistencia] = folioPAsistencia
            }
        }

        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Parte de Emergencia con folio $folioPEmergencia no encontrado")
        }

        return ParteEmergencia(
            folioPEmergencia,
            tipoEmergencia,
            horaInicio,
            horaFin,
            fechaEmergencia,
            preInforme,
            oficial,
            idEmergencia,
            folioPAsistencia
        )
    }

    // ParteAsistencia implementation

    private fun resultToParteAsistencia(row: ResultRow) = ParteAsistencia(
        folioPAsistencia = row[ParteAsistencias.folioPAsistencia],
        tipoLlamado = row[ParteAsistencias.tipoLlamado],
        aCargoDelCuerpo = row[ParteAsistencias.aCargoDelCuerpo],
        aCargoDeLaCompania = row[ParteAsistencias.aCargoDeLaCompania],
        fechaAsistencia = row[ParteAsistencias.fechaAsistencia],
        horaInicio = row[ParteAsistencias.horaInicio],
        horaFin = row[ParteAsistencias.horaFin],
        direccion = row[ParteAsistencias.direccion],
        totalAsistencia = row[ParteAsistencias.totalAsistencia],
        observaciones = row[ParteAsistencias.observaciones],
        materialMayorAsistencia = row[ParteAsistencias.materialMayorAsistencia]
    )

    override suspend fun allParteAsistencias(): List<ParteAsistencia> = dbQuery {
            ParteAsistencias.selectAll().map(::resultToParteAsistencia)
    }

    override suspend fun getParteAsistencia(folioPAsistencia: Int): ParteAsistencia? = dbQuery {
        ParteAsistencias.select { ParteAsistencias.folioPAsistencia eq folioPAsistencia }
            .mapNotNull(::resultToParteAsistencia)
            .singleOrNull()

    }

    override suspend fun createParteAsistencia(
        folioPAsistencia: Int,
        tipoLlamado: String,
        aCargoDelCuerpo: String,
        aCargoDeLaCompania: String,
        fechaAsistencia: LocalDate,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        direccion: String,
        totalAsistencia: Int,
        observaciones: String?,
        materialMayorAsistencia: String?
    ): ParteAsistencia {
        var generatedFolio = 0
        dbQuery {
            generatedFolio = (ParteAsistencias.insert {
                it[ParteAsistencias.folioPAsistencia] = folioPAsistencia
                it[ParteAsistencias.tipoLlamado] = tipoLlamado
                it[ParteAsistencias.aCargoDelCuerpo] = aCargoDelCuerpo
                it[ParteAsistencias.aCargoDeLaCompania] = aCargoDeLaCompania
                it[ParteAsistencias.fechaAsistencia] = fechaAsistencia
                it[ParteAsistencias.horaInicio] = horaInicio
                it[ParteAsistencias.horaFin] = horaFin
                it[ParteAsistencias.direccion] = direccion
                it[ParteAsistencias.totalAsistencia] = totalAsistencia
                it[ParteAsistencias.observaciones] = observaciones
                it[ParteAsistencias.materialMayorAsistencia] = materialMayorAsistencia
            } get ParteAsistencias.folioPAsistencia)!!
        }
        return ParteAsistencia(
            generatedFolio, tipoLlamado, aCargoDelCuerpo, aCargoDeLaCompania, fechaAsistencia, horaInicio, horaFin, direccion, totalAsistencia, observaciones, materialMayorAsistencia
        )
    }

    override suspend fun deleteParteAsistencia(folioPAsistencia: Int): Boolean = dbQuery {
        ParteAsistencias.deleteWhere { ParteAsistencias.folioPAsistencia eq folioPAsistencia } > 0
    }

    override suspend fun updateParteAsistencia(
        folioPAsistencia: Int,
        tipoLlamado: String,
        aCargoDelCuerpo: String,
        aCargoDeLaCompania: String,
        fechaAsistencia: LocalDate,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        direccion: String,
        totalAsistencia: Int,
        observaciones: String?,
        materialMayorAsistencia: String?
    ): ParteAsistencia {
        val rowsUpdate = dbQuery {
            ParteAsistencias.update({ ParteAsistencias.folioPAsistencia eq folioPAsistencia }) {
                it[ParteAsistencias.tipoLlamado] = tipoLlamado
                it[ParteAsistencias.aCargoDelCuerpo] = aCargoDelCuerpo
                it[ParteAsistencias.aCargoDeLaCompania] = aCargoDeLaCompania
                it[ParteAsistencias.fechaAsistencia] = fechaAsistencia
                it[ParteAsistencias.horaInicio] = horaInicio
                it[ParteAsistencias.horaFin] = horaFin
                it[ParteAsistencias.direccion] = direccion
                it[ParteAsistencias.totalAsistencia] = totalAsistencia
                it[ParteAsistencias.observaciones] = observaciones
                it[ParteAsistencias.materialMayorAsistencia] = materialMayorAsistencia
            }
        }
        if (rowsUpdate == 0) {
            throw IllegalArgumentException("Parte de Asistencia con folio $folioPAsistencia no encontrado")
        }
        return ParteAsistencia(
            folioPAsistencia,
            tipoLlamado,
            aCargoDelCuerpo,
            aCargoDeLaCompania,
            fechaAsistencia,
            horaInicio,
            horaFin,
            direccion,
            totalAsistencia,
            observaciones,
            materialMayorAsistencia
        )
    }


}


