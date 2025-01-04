package com.example.dao


import com.example.dao.DatabaseSingleton.dbQuery
import com.example.models.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.transactions.transaction


class DAOFacadeImpl : DAOFacade {



    // Institucion implementation

    private fun resultToInstitucion(row: ResultRow) = Instituciones(
        idInstitucion = row[Institucion.idInstitucion],
        nombreInstitucion = row[Institucion.nombreInstitucion],
        tipoInstitucion = row[Institucion.tipoInstitucion],
        nombrePersonaCargo = row[Institucion.nombrePersonaCargo],
        horaLlegada = row[Institucion.horaLlegada],
        folioPEmergencia = row[Institucion.folioPEmergencia]
    )

    override suspend fun allInstituciones(): List<Instituciones> = transaction {
        Institucion.selectAll().map(::resultToInstitucion)
    }

    override suspend fun getInstitucion(idInstitucion: Int): Instituciones? = transaction {
        Institucion.select { Institucion.idInstitucion eq idInstitucion }
            .mapNotNull(::resultToInstitucion)
            .singleOrNull()
    }

    override suspend fun createInstitucion(
        nombreInstitucion: String,
        tipoInstitucion: String,
        nombrePersonaCargo: String,
        horaLlegada: LocalTime,
        folioPEmergencia: Int?
    ): Instituciones = transaction {
        val insertStatement = Institucion.insert {
            it[this.nombreInstitucion] = nombreInstitucion
            it[this.tipoInstitucion] = tipoInstitucion
            it[this.nombrePersonaCargo] = nombrePersonaCargo
            it[this.horaLlegada] = horaLlegada
            it[this.folioPEmergencia] = folioPEmergencia
        }

        val idInstitucion = insertStatement.resultedValues?.get(0)?.get(Institucion.idInstitucion)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Instituciones(
            idInstitucion = idInstitucion,
            nombreInstitucion = nombreInstitucion,
            tipoInstitucion = tipoInstitucion,
            nombrePersonaCargo = nombrePersonaCargo,
            horaLlegada = horaLlegada,
            folioPEmergencia = folioPEmergencia
        )
    }

    override suspend fun deleteInstitucion(idInstitucion: Int): Boolean = transaction {
        Institucion.deleteWhere { Institucion.idInstitucion eq idInstitucion } > 0
    }

    override suspend fun updateInstitucion(
        idInstitucion: Int,
        nombreInstitucion: String,
        tipoInstitucion: String,
        nombrePersonaCargo: String,
        horaLlegada: LocalTime,
        folioPEmergencia: Int?
    ): Instituciones = transaction {
        val rowsUpdated = Institucion.update({ Institucion.idInstitucion eq idInstitucion }) {
            it[this.nombreInstitucion] = nombreInstitucion
            it[this.tipoInstitucion] = tipoInstitucion
            it[this.nombrePersonaCargo] = nombrePersonaCargo
            it[this.horaLlegada] = horaLlegada
            it[this.folioPEmergencia] = folioPEmergencia
        }

        if (rowsUpdated == 0) throw IllegalArgumentException("Institución con id $idInstitucion no encontrada")

        Instituciones(
            nombreInstitucion = nombreInstitucion,
            tipoInstitucion = tipoInstitucion,
            nombrePersonaCargo = nombrePersonaCargo,
            horaLlegada = horaLlegada,
            folioPEmergencia = folioPEmergencia
        )
    }

    // Cuerpo implementation

    private fun resultToCuerpo(row: ResultRow) = Cuerpos(
        nombreCuerpo = row[Cuerpo.nombreCuerpo],
        provincia = row[Cuerpo.provincia],
        region = row[Cuerpo.region],
        comuna = row[Cuerpo.comuna]
    )

    override suspend fun allCuerpos(): List<Cuerpos> = dbQuery {
        Cuerpo.selectAll().map(::resultToCuerpo)
    }

    override suspend fun getCuerpo(idCuerpo: Int): Cuerpos? = dbQuery {
        Cuerpo.select { Cuerpo.idCuerpo eq idCuerpo }
            .mapNotNull(::resultToCuerpo)
            .singleOrNull()
    }

    override suspend fun createCuerpo(
        nombreCuerpo: String,
        provincia: String,
        region: String,
        comuna: String
    ): Cuerpos = transaction {
        val insertStatement = Cuerpo.insert {
            it[this.nombreCuerpo] = nombreCuerpo
            it[this.provincia] = provincia
            it[this.region] = region
            it[this.comuna] = comuna
        }

        val idCuerpo = insertStatement.resultedValues?.get(0)?.get(Cuerpo.idCuerpo)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Cuerpos(
            idCuerpo = idCuerpo,
            nombreCuerpo = nombreCuerpo,
            provincia = provincia,
            region = region,
            comuna = comuna
        )
    }

    override suspend fun deleteCuerpo(idCuerpo: Int): Boolean = transaction {
        val rowsDeleted = Cuerpo.deleteWhere { Cuerpo.idCuerpo eq idCuerpo }
        rowsDeleted > 0
    }

    override suspend fun updateCuerpo(
        idCuerpo: Int,
        nombreCuerpo: String,
        provincia: String,
        region: String,
        comuna: String
    ): Cuerpos = transaction {
        // Actualizar el cuerpo en la base de datos
        val rowsUpdated = Cuerpo.update({ Cuerpo.idCuerpo eq idCuerpo }) {
            it[Cuerpo.nombreCuerpo] = nombreCuerpo
            it[Cuerpo.provincia] = provincia
            it[Cuerpo.region] = region
            it[Cuerpo.comuna] = comuna
        }

        // Si no se actualizó ningún registro, lanzar una excepción
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Cuerpo con id $idCuerpo no encontrado")
        }

        // Retornar el objeto actualizado
        Cuerpos(idCuerpo, nombreCuerpo, provincia, region, comuna)
    }


    // Compania implementation

    private fun resultToCompania(row: ResultRow) = Companias(
        nombreCia = row[Compania.nombreCia],
        direccionCia = row[Compania.direccionCia],
        especialidad = row[Compania.especialidad],
        idCuerpo = row[Compania.idCuerpo]
    )

    override suspend fun allCompanias(): List<Companias> = dbQuery {
        Compania.selectAll().map(::resultToCompania)
    }

    override suspend fun getCompania(idCompania: Int): Companias? = dbQuery {
        Compania.select { Compania.idCompania eq idCompania }
            .mapNotNull(::resultToCompania)
            .singleOrNull()
    }

    override suspend fun createCompania(
        nombreCia: String,
        direccionCia: String,
        especialidad: String,
        idCuerpo: Int?
    ): Companias = transaction {
        val insertStatement = Compania.insert {
            it[this.nombreCia] = nombreCia
            it[this.direccionCia] = direccionCia
            it[this.especialidad] = especialidad
            it[this.idCuerpo] = idCuerpo
        }

        val idCompania = insertStatement.resultedValues?.get(0)?.get(Compania.idCompania)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Companias(
            idCompania = idCompania,
            nombreCia = nombreCia,
            direccionCia = direccionCia,
            especialidad = especialidad,
            idCuerpo = idCuerpo
        )
    }

    override suspend fun deleteCompania(idCompania: Int): Boolean = dbQuery {
        Compania.deleteWhere { Compania.idCompania eq idCompania } > 0
    }

    override suspend fun updateCompania(
        idCompania: Int,
        nombreCia: String,
        direccionCia: String,
        especialidad: String,
        idCuerpo: Int?
    ): Companias {
        val rowsUpdated = dbQuery {
            Compania.update({ Compania.idCompania eq idCompania }) {
                it[Compania.nombreCia] = nombreCia
                it[Compania.direccionCia] = direccionCia
                it[Compania.especialidad] = especialidad
                it[Compania.idCuerpo] = idCuerpo
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Compañía con id $idCompania no encontrada")
        }
        return Companias(idCompania, nombreCia, direccionCia, especialidad, idCuerpo)
    }

    // Usuario implementation

    private fun resultToUsuario(row: ResultRow) = Usuarios(
        nombreUsuario = row[Usuario.nombreUsuario],
        contrasena = row[Usuario.contrasena],
        idRol = row[Usuario.idRol] // idRol es requerido
    )

    override suspend fun allUsuarios(): List<Usuarios> = dbQuery {
        Usuario.selectAll().map(::resultToUsuario)
    }

    override suspend fun getUsuario(idUsuario: Int): Usuarios? = dbQuery {
        Usuario.select { Usuario.idUsuario eq idUsuario }
            .mapNotNull(::resultToUsuario)
            .singleOrNull()
    }

    override suspend fun createUsuario(
        nombreUsuario: String,
        contrasena: String,
        idRol: Int
    ): Usuarios = transaction {
        val insertStatement = Usuario.insert {
            it[this.nombreUsuario] = nombreUsuario
            it[this.contrasena] = contrasena
            it[this.idRol] = idRol
        }

        val idUsuario = insertStatement.resultedValues?.get(0)?.get(Usuario.idUsuario)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Usuarios(
            idUsuario = idUsuario,
            nombreUsuario = nombreUsuario,
            contrasena = contrasena,
            idRol = idRol
        )
    }


    override suspend fun deleteUsuario(idUsuario: Int): Boolean = dbQuery {
        Usuario.deleteWhere { Usuario.idUsuario eq idUsuario } > 0
    }

    override suspend fun updateUsuario(
        idUsuario: Int,
        nombreUsuario: String,
        contrasena: String,
        idRol: Int // idRol es requerido
    ): Usuarios {
        val rowsUpdated = dbQuery {
            Usuario.update({ Usuario.idUsuario eq idUsuario }) {
                it[Usuario.nombreUsuario] = nombreUsuario
                it[Usuario.contrasena] = contrasena
                it[Usuario.idRol] = idRol
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Usuario con id $idUsuario no encontrado")
        }
        return Usuarios(idUsuario, nombreUsuario, contrasena, idRol)
    }

    override suspend fun loginUsuario(nombreUsuario: String, contrasena: String): Usuarios? {
        return transaction {
            Usuario.select {
                (Usuario.nombreUsuario eq nombreUsuario) and (Usuario.contrasena eq contrasena)
            }.mapNotNull(::resultToUsuario).singleOrNull()
        }
    }


    // Voluntario implementation

    private fun resultToVoluntario(row: ResultRow) = Voluntarios(
        nombreVol = row[Voluntario.nombreVol],
        fechaNac = row[Voluntario.fechaNac],
        direccion = row[Voluntario.direccion],
        numeroContacto = row[Voluntario.numeroContacto],
        tipoSangre = row[Voluntario.tipoSangre],
        enfermedades = row[Voluntario.enfermedades],
        alergias = row[Voluntario.alergias],
        fechaIngreso = row[Voluntario.fechaIngreso],
        claveRadial = row[Voluntario.claveRadial],
        cargoVoluntario = row[Voluntario.cargoVoluntario],
        rutVoluntario = row[Voluntario.rutVoluntario],
        idCompania = row[Voluntario.idCompania],
        idUsuario = row[Voluntario.idUsuario]
    )

    override suspend fun allVoluntarios(): List<Voluntarios> = dbQuery {
        Voluntario.selectAll().map(::resultToVoluntario)
    }

    override suspend fun getVoluntario(idVoluntario: Int): Voluntarios? = dbQuery {
        Voluntario.select { Voluntario.idVoluntario eq idVoluntario }
            .mapNotNull(::resultToVoluntario)
            .singleOrNull()
    }

    override suspend fun createVoluntario(
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
        rutVoluntario: String,
        idCompania: Int,
        idUsuario: Int?
    ): Voluntarios = transaction {
        val insertStatement = Voluntario.insert {
            it[this.nombreVol] = nombreVol
            it[this.fechaNac] = fechaNac
            it[this.direccion] = direccion
            it[this.numeroContacto] = numeroContacto
            it[this.tipoSangre] = tipoSangre
            it[this.enfermedades] = enfermedades
            it[this.alergias] = alergias
            it[this.fechaIngreso] = fechaIngreso
            it[this.claveRadial] = claveRadial
            it[this.cargoVoluntario] = cargoVoluntario
            it[this.rutVoluntario] = rutVoluntario
            it[this.idCompania] = idCompania
            it[this.idUsuario] = idUsuario
        }

        val idVoluntario = insertStatement.resultedValues?.get(0)?.get(Voluntario.idVoluntario)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Voluntarios(
            idVoluntario = idVoluntario,
            nombreVol = nombreVol,
            fechaNac = fechaNac,
            direccion = direccion,
            numeroContacto = numeroContacto,
            tipoSangre = tipoSangre,
            enfermedades = enfermedades,
            alergias = alergias,
            fechaIngreso = fechaIngreso,
            claveRadial = claveRadial,
            cargoVoluntario = cargoVoluntario,
            rutVoluntario = rutVoluntario,
            idCompania = idCompania,
            idUsuario = idUsuario
        )
    }

    override suspend fun deleteVoluntario(idVoluntario: Int): Boolean = dbQuery {
        Voluntario.deleteWhere { Voluntario.idVoluntario eq idVoluntario } > 0
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
        rutVoluntario: String,
        idCompania: Int,
        idUsuario: Int?

    ): Voluntarios {
        val rowsUpdated = dbQuery {
            Voluntario.update({ Voluntario.idVoluntario eq idVoluntario }) {
                it[Voluntario.nombreVol] = nombreVol
                it[Voluntario.fechaNac] = fechaNac
                it[Voluntario.direccion] = direccion
                it[Voluntario.numeroContacto] = numeroContacto
                it[Voluntario.tipoSangre] = tipoSangre
                it[Voluntario.enfermedades] = enfermedades
                it[Voluntario.alergias] = alergias
                it[Voluntario.fechaIngreso] = fechaIngreso
                it[Voluntario.claveRadial] = claveRadial
                it[Voluntario.cargoVoluntario] = cargoVoluntario
                it[Voluntario.rutVoluntario] = rutVoluntario
                it[Voluntario.idCompania] = idCompania
                it[Voluntario.idUsuario] = idUsuario
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Voluntario con id $idVoluntario no encontrado")
        return Voluntarios( idVoluntario, nombreVol, fechaNac, direccion, numeroContacto, tipoSangre, enfermedades, alergias, fechaIngreso, claveRadial, cargoVoluntario,rutVoluntario, idCompania, idUsuario)
    }

    suspend fun getIdVoluntarioByRut(rutVoluntario: String): Int {
        return dbQuery {
            Voluntario
                .select { Voluntario.rutVoluntario eq rutVoluntario }
                .mapNotNull { it[Voluntario.idVoluntario] }
                .singleOrNull()
                ?: throw IllegalArgumentException("Voluntario con rut $rutVoluntario no encontrado")
        }
    }

    // inmueble implementation

    private fun resultToInmueble(row: ResultRow) = Inmuebles(
        direccion = row[Inmueble.direccion],
        tipoInmueble = row[Inmueble.tipoInmueble],
        estadoInmueble = row[Inmueble.estadoInmueble],
        folioPEmergencia = row[Inmueble.folioPEmergencia]
    )

    override suspend fun allInmuebles(): List<Inmuebles> = transaction {
        Inmueble.selectAll().map(::resultToInmueble)
    }

    override suspend fun getInmueble(idInmueble: Int): Inmuebles? = transaction {
        Inmueble.select { Inmueble.idInmueble eq idInmueble }
            .mapNotNull(::resultToInmueble)
            .singleOrNull()
    }

    override suspend fun createInmueble(
        direccion: String,
        tipoInmueble: String,
        estadoInmueble: String,
        folioPEmergencia: Int?
    ): Inmuebles = transaction {
        val insertStatement = Inmueble.insert {
            it[this.direccion] = direccion
            it[this.tipoInmueble] = tipoInmueble
            it[this.estadoInmueble] = estadoInmueble
            it[this.folioPEmergencia] = folioPEmergencia
        }

        val idInmueble = insertStatement.resultedValues?.get(0)?.get(Inmueble.idInmueble)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Inmuebles(
            idInmueble = idInmueble,
            direccion = direccion,
            tipoInmueble = tipoInmueble,
            estadoInmueble = estadoInmueble,
            folioPEmergencia = folioPEmergencia
        )
    }

    override suspend fun deleteInmueble(idInmueble: Int): Boolean = transaction {
        Inmueble.deleteWhere { Inmueble.idInmueble eq idInmueble } > 0
    }

    override suspend fun updateInmueble(
        idInmueble: Int,
        direccion: String,
        tipoInmueble: String,
        estadoInmueble: String,
        folioPEmergencia: Int?
    ): Inmuebles = transaction {
        val rowsUpdated = Inmueble.update({ Inmueble.idInmueble eq idInmueble }) {
            it[this.direccion] = direccion
            it[this.tipoInmueble] = tipoInmueble
            it[this.estadoInmueble] = estadoInmueble
            it[this.folioPEmergencia] = folioPEmergencia
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Inmueble con id $idInmueble no encontrado")
        }
        Inmuebles(idInmueble, direccion, tipoInmueble, estadoInmueble, folioPEmergencia)
    }

    // Victima implementation

    private fun resultToVictima(row: ResultRow) = Victimas(
        rutVictima = row[Victima.rutVictima],
        nombreVictima = row[Victima.nombreVictima],
        edadVictima = row[Victima.edadVictima],
        descripcion = row[Victima.descripcion],
        folioPEmergencia = row[Victima.folioPEmergencia]
    )

    override suspend fun allVictimas(): List<Victimas> = transaction {
        Victima.selectAll().map(::resultToVictima)
    }

    override suspend fun getVictima(idVictima: Int): Victimas? = transaction {
        Victima.select { Victima.idVictima eq idVictima }
            .mapNotNull(::resultToVictima)
            .singleOrNull()
    }

    override suspend fun createVictima(
        rutVictima: String,
        nombreVictima: String,
        edadVictima: Int,
        descripcion: String,
        folioPEmergencia: Int
    ): Victimas = transaction {
        val insertStatement = Victima.insert {
            it[this.rutVictima] = rutVictima
            it[this.nombreVictima] = nombreVictima
            it[this.edadVictima] = edadVictima
            it[this.descripcion] = descripcion
            it[this.folioPEmergencia] = folioPEmergencia
        }

        val idVictima = insertStatement.resultedValues?.get(0)?.get(Victima.idVictima)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Victimas(
            idVictima = idVictima,
            rutVictima = rutVictima,
            nombreVictima = nombreVictima,
            edadVictima = edadVictima,
            descripcion = descripcion,
            folioPEmergencia = folioPEmergencia
        )
    }

    override suspend fun deleteVictima(idVictima: Int): Boolean = transaction {
        Victima.deleteWhere { Victima.idVictima eq idVictima } > 0
    }

    override suspend fun updateVictima(
        idVictima: Int,
        rutVictima: String,
        nombreVictima: String,
        edadVictima: Int,
        descripcion: String,
        folioPEmergencia: Int
    ): Victimas = transaction {
        val rowsUpdated = Victima.update({ Victima.idVictima eq idVictima }) {
            it[this.rutVictima] = rutVictima
            it[this.nombreVictima] = nombreVictima
            it[this.edadVictima] = edadVictima
            it[this.descripcion] = descripcion
            it[this.folioPEmergencia] = folioPEmergencia
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Víctima con id $idVictima no encontrada")
        Victimas(idVictima, rutVictima, nombreVictima, edadVictima, descripcion, folioPEmergencia)
    }

    // Vehiculo implementation

    private fun resultToVehiculo(row: ResultRow) = Vehiculos(
        patente = row[Vehiculo.patente],
        marca = row[Vehiculo.marca],
        modelo = row[Vehiculo.modelo],
        tipoVehiculo = row[Vehiculo.tipoVehiculo],
        folioPEmergencia = row[Vehiculo.folioPEmergencia]
    )

    override suspend fun allVehiculos(): List<Vehiculos> = transaction {
        Vehiculo.selectAll().map(::resultToVehiculo)
    }

    override suspend fun getVehiculo(idVehiculo: Int): Vehiculos? = transaction {
        Vehiculo.select { Vehiculo.idVehiculo eq idVehiculo }
            .mapNotNull(::resultToVehiculo)
            .singleOrNull()
    }

    override suspend fun createVehiculo(
        patente: String,
        marca: String,
        modelo: String,
        tipoVehiculo: String,
        folioPEmergencia: Int?
    ): Vehiculos = transaction {
        val insertStatement = Vehiculo.insert {
            it[this.patente] = patente
            it[this.marca] = marca
            it[this.modelo] = modelo
            it[this.tipoVehiculo] = tipoVehiculo
            it[this.folioPEmergencia] = folioPEmergencia
        }

        val idVehiculo = insertStatement.resultedValues?.get(0)?.get(Vehiculo.idVehiculo)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Vehiculos(
            idVehiculo = idVehiculo,
            patente = patente,
            marca = marca,
            modelo = modelo,
            tipoVehiculo = tipoVehiculo,
            folioPEmergencia = folioPEmergencia
        )
    }


    override suspend fun deleteVehiculo(idVehiculo: Int): Boolean = transaction {
        Vehiculo.deleteWhere { Vehiculo.idVehiculo eq idVehiculo } > 0
    }

    override suspend fun updateVehiculo(
        idVehiculo: Int,
        patente: String,
        marca: String,
        modelo: String,
        tipoVehiculo: String,
        folioPEmergencia: Int?
    ): Vehiculos = transaction {
        val rowsUpdated = Vehiculo.update({ Vehiculo.idVehiculo eq idVehiculo }) {
            it[this.patente] = patente
            it[this.marca] = marca
            it[this.modelo] = modelo
            it[this.tipoVehiculo] = tipoVehiculo
            it[this.folioPEmergencia] = folioPEmergencia
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Vehículo con id $idVehiculo no encontrado")
        Vehiculos(idVehiculo, patente, marca, modelo, tipoVehiculo, folioPEmergencia)
    }


    // Emergencia implementation

    private fun resultToEmergencia(row: ResultRow) = Emergencias(
        claveEmergencia = row[Emergencia.claveEmergencia],
        cuadrante = row[Emergencia.cuadrante],
        direccionEmergencia = row[Emergencia.direccionEmergencia],
        folioPEmergencia = row[Emergencia.folioPEmergencia]
    )

    override suspend fun allEmergencias(): List<Emergencias> = dbQuery {
        Emergencia.selectAll().map(::resultToEmergencia)
    }

    override suspend fun getEmergencia(idEmergencia: Int): Emergencias? = dbQuery {
        Emergencia.select { Emergencia.idEmergencia eq idEmergencia }
            .mapNotNull(::resultToEmergencia)
            .singleOrNull()
    }

    override suspend fun createEmergencia(
        claveEmergencia: String,
        cuadrante: String,
        direccionEmergencia: String,
        folioPEmergencia: Int
    ): Emergencias = transaction {
        val insertStatement = Emergencia.insert {
            it[this.claveEmergencia] = claveEmergencia
            it[this.cuadrante] = cuadrante
            it[this.direccionEmergencia] = direccionEmergencia
            it[this.folioPEmergencia] = folioPEmergencia
        }

        val idEmergencia = insertStatement.resultedValues?.get(0)?.get(Emergencia.idEmergencia)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Emergencias(
            idEmergencia = idEmergencia,
            claveEmergencia = claveEmergencia,
            cuadrante = cuadrante,
            direccionEmergencia = direccionEmergencia,
            folioPEmergencia = folioPEmergencia
        )
    }


    override suspend fun deleteEmergencia(idEmergencia: Int): Boolean = transaction {
        val rowsDeleted = Emergencia.deleteWhere { Emergencia.idEmergencia eq idEmergencia }
        rowsDeleted > 0
    }

    override suspend fun updateEmergencia(
        idEmergencia: Int,
        claveEmergencia: String,
        cuadrante: String,
        direccionEmergencia: String,
        folioPEmergencia: Int
    ): Emergencias = transaction {
        // Actualizar la emergencia en la base de datos
        val rowsUpdated = Emergencia.update({ Emergencia.idEmergencia eq idEmergencia }) {
            it[Emergencia.claveEmergencia] = claveEmergencia
            it[Emergencia.cuadrante] = cuadrante
            it[Emergencia.direccionEmergencia] = direccionEmergencia
            it[Emergencia.folioPEmergencia] = folioPEmergencia
        }

        // Si no se actualizó ningún registro, lanzar una excepción
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Emergencia con id $idEmergencia no encontrada")
        }

        // Retornar el objeto actualizado
        Emergencias(idEmergencia, claveEmergencia, cuadrante, direccionEmergencia, folioPEmergencia)
    }


    // ParteEmergencia implementation

    private fun resultToParteEmergencia(row: ResultRow) = Partes_emergencia(
        folioPEmergencia = row[Parte_emergencia.folioPEmergencia],
        tipoEmergencia = row[Parte_emergencia.tipoEmergencia],
        horaInicio = row[Parte_emergencia.horaInicio],
        horaFin = row[Parte_emergencia.horaFin],
        fechaEmergencia = row[Parte_emergencia.fechaEmergencia],
        preInforme = row[Parte_emergencia.preInforme],
        oficial = row[Parte_emergencia.oficial],
        folioPAsistencia = row[Parte_emergencia.folioPAsistencia]
    )

    override suspend fun allParteEmergencias(): List<Partes_emergencia> = dbQuery {
        Parte_emergencia.selectAll().map(::resultToParteEmergencia)
    }

    override suspend fun getParteEmergencia(folioPEmergencia: Int): Partes_emergencia? = dbQuery {
        Parte_emergencia.select { Parte_emergencia.folioPEmergencia eq folioPEmergencia }
            .mapNotNull(::resultToParteEmergencia)
            .singleOrNull()
    }

    override suspend fun createParteEmergencia(
        tipoEmergencia: String,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        fechaEmergencia: LocalDate,
        preInforme: String,
        oficial: String,
        folioPAsistencia: Int?
    ): Partes_emergencia = transaction {
        val insertStatement = Parte_emergencia.insert {
            it[Parte_emergencia.tipoEmergencia] = tipoEmergencia
            it[Parte_emergencia.horaInicio] = horaInicio
            it[Parte_emergencia.horaFin] = horaFin
            it[Parte_emergencia.fechaEmergencia] = fechaEmergencia
            it[Parte_emergencia.preInforme] = preInforme
            it[Parte_emergencia.oficial] = oficial
            it[Parte_emergencia.folioPAsistencia] = folioPAsistencia
        }

        val folioPEmergencia = insertStatement.resultedValues?.get(0)?.get(Parte_emergencia.folioPEmergencia)
            ?: throw IllegalStateException("No se pudo obtener el folio generado")

        Partes_emergencia(
            folioPEmergencia = folioPEmergencia,
            tipoEmergencia = tipoEmergencia,
            horaInicio = horaInicio,
            horaFin = horaFin,
            fechaEmergencia = fechaEmergencia,
            preInforme = preInforme,
            oficial = oficial,
            folioPAsistencia = folioPAsistencia
        )
    }

    override suspend fun deleteParteEmergencia(folioPEmergencia: Int): Boolean = dbQuery {
        Parte_emergencia.deleteWhere { Parte_emergencia.folioPEmergencia eq folioPEmergencia } > 0
    }

    override suspend fun updateParteEmergencia(
        folioPEmergencia: Int,
        tipoEmergencia: String,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        fechaEmergencia: LocalDate,
        preInforme: String,
        oficial: String,
        folioPAsistencia: Int?
    ): Partes_emergencia {
        val rowsUpdated = dbQuery {
            Parte_emergencia.update({ Parte_emergencia.folioPEmergencia eq folioPEmergencia }) {
                it[Parte_emergencia.tipoEmergencia] = tipoEmergencia
                it[Parte_emergencia.horaInicio] = horaInicio
                it[Parte_emergencia.horaFin] = horaFin
                it[Parte_emergencia.fechaEmergencia] = fechaEmergencia
                it[Parte_emergencia.preInforme] = preInforme
                it[Parte_emergencia.oficial] = oficial
                it[Parte_emergencia.folioPAsistencia] = folioPAsistencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergencia con folio $folioPEmergencia no encontrado")
        return Partes_emergencia(folioPEmergencia, tipoEmergencia, horaInicio, horaFin, fechaEmergencia, preInforme, oficial, folioPAsistencia)
    }

    //parte_asistencia implementation

    private fun resultToParteAsistencia(row: ResultRow) = Partes_asistencia(
        tipoLlamado = row[Parte_asistencia.tipoLlamado],
        aCargoDelCuerpo = row[Parte_asistencia.aCargoDelCuerpo],
        aCargoDeLaCompania = row[Parte_asistencia.aCargoDeLaCompania],
        fechaAsistencia = row[Parte_asistencia.fechaAsistencia],
        horaInicio = row[Parte_asistencia.horaInicio],
        horaFin = row[Parte_asistencia.horaFin],
        direccionAsistencia = row[Parte_asistencia.direccionAsistencia],
        totalAsistencia = row[Parte_asistencia.totalAsistencia],
        observaciones = row[Parte_asistencia.observaciones],
    )

    override suspend fun allParteAsistencias(): List<Partes_asistencia> = dbQuery {
        Parte_asistencia.selectAll().map(::resultToParteAsistencia)
    }

    override suspend fun getParteAsistencia(folioPAsistencia: Int): Partes_asistencia? = dbQuery {
        Parte_asistencia.select { Parte_asistencia.folioPAsistencia eq folioPAsistencia }
            .mapNotNull(::resultToParteAsistencia)
            .singleOrNull()
    }

    override suspend fun createParteAsistencia(
        tipoLlamado: String,
        aCargoDelCuerpo: String,
        aCargoDeLaCompania: String,
        fechaAsistencia: LocalDate,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        direccionAsistencia: String,
        totalAsistencia: Int,
        observaciones: String,
    ): Partes_asistencia = transaction {
        val insertStatement = Parte_asistencia.insert {
            it[this.tipoLlamado] = tipoLlamado
            it[this.aCargoDelCuerpo] = aCargoDelCuerpo
            it[this.aCargoDeLaCompania] = aCargoDeLaCompania
            it[this.fechaAsistencia] = fechaAsistencia
            it[this.horaInicio] = horaInicio
            it[this.horaFin] = horaFin
            it[this.direccionAsistencia] = direccionAsistencia
            it[this.totalAsistencia] = totalAsistencia
            it[this.observaciones] = observaciones
        }

        val folioPAsistencia = insertStatement.resultedValues?.get(0)?.get(Parte_asistencia.folioPAsistencia)
            ?: throw IllegalStateException("No se pudo obtener el folio generado")

        Partes_asistencia(
            folioPAsistencia = folioPAsistencia,
            tipoLlamado = tipoLlamado,
            aCargoDelCuerpo = aCargoDelCuerpo,
            aCargoDeLaCompania = aCargoDeLaCompania,
            fechaAsistencia = fechaAsistencia,
            horaInicio = horaInicio,
            horaFin = horaFin,
            direccionAsistencia = direccionAsistencia,
            totalAsistencia = totalAsistencia,
            observaciones = observaciones,
        )
    }

    // Añadir una función para verificar si existe un móvil
    suspend fun movilExists(idMovil: Int): Boolean = transaction {
        Movil.select { Movil.idMovil eq idMovil }.count() > 0
    }

    override suspend fun deleteParteAsistencia(folioPAsistencia: Int): Boolean = dbQuery {
        Parte_asistencia.deleteWhere { Parte_asistencia.folioPAsistencia eq folioPAsistencia } > 0
    }

    override suspend fun updateParteAsistencia(
        folioPAsistencia: Int,
        tipoLlamado: String,
        aCargoDelCuerpo: String,
        aCargoDeLaCompania: String,
        fechaAsistencia: LocalDate,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        direccionAsistencia: String,
        totalAsistencia: Int,
        observaciones: String,
    ): Partes_asistencia {
        val rowsUpdated = dbQuery {
            Parte_asistencia.update({ Parte_asistencia.folioPAsistencia eq folioPAsistencia }) {
                it[Parte_asistencia.tipoLlamado] = tipoLlamado
                it[Parte_asistencia.aCargoDelCuerpo] = aCargoDelCuerpo
                it[Parte_asistencia.aCargoDeLaCompania] = aCargoDeLaCompania
                it[Parte_asistencia.fechaAsistencia] = fechaAsistencia
                it[Parte_asistencia.horaInicio] = horaInicio
                it[Parte_asistencia.horaFin] = horaFin
                it[Parte_asistencia.direccionAsistencia] = direccionAsistencia
                it[Parte_asistencia.totalAsistencia] = totalAsistencia
                it[Parte_asistencia.observaciones] = observaciones
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistencia con folio $folioPAsistencia no encontrado")
        return Partes_asistencia(folioPAsistencia, tipoLlamado, aCargoDelCuerpo, aCargoDeLaCompania, fechaAsistencia, horaInicio, horaFin, direccionAsistencia, totalAsistencia, observaciones)
    }

    // MaterialP implementation

    private fun resultToMaterialP(row: ResultRow) = MaterialesP(
        llamarEmpresaQuimica = row[MaterialP.llamarEmpresaQuimica],
        clasificacion = row[MaterialP.clasificacion],
        nombreMaP = row[MaterialP.nombreMaP],
        folioPEmergencia = row[MaterialP.folioPEmergencia]
    )

    override suspend fun allMaterialesP(): List<MaterialesP> = transaction {
        MaterialP.selectAll().map(::resultToMaterialP)
    }

    override suspend fun getMaterialP(idMaterialP: Int): MaterialesP? = transaction {
        MaterialP.select { MaterialP.idMaterialP eq idMaterialP }
            .mapNotNull(::resultToMaterialP)
            .singleOrNull()
    }

    override suspend fun createMaterialP(
        llamarEmpresaQuimica: Boolean,
        clasificacion: String,
        nombreMaP: String,
        folioPEmergencia: Int?
    ): MaterialesP = transaction {
        val insertStatement = MaterialP.insert {
            it[this.llamarEmpresaQuimica] = llamarEmpresaQuimica
            it[this.clasificacion] = clasificacion
            it[this.nombreMaP] = nombreMaP
            it[this.folioPEmergencia] = folioPEmergencia
        }

        val idMaterialP = insertStatement.resultedValues?.get(0)?.get(MaterialP.idMaterialP)
            ?: throw IllegalStateException("No se pudo obtener el id generado")

        MaterialesP(
            idMaterialP = idMaterialP,
            llamarEmpresaQuimica = llamarEmpresaQuimica,
            clasificacion = clasificacion,
            nombreMaP = nombreMaP,
            folioPEmergencia = folioPEmergencia
        )
    }


    override suspend fun deleteMaterialP(idMaterialP: Int): Boolean = transaction {
        MaterialP.deleteWhere { MaterialP.idMaterialP eq idMaterialP } > 0
    }

    override suspend fun updateMaterialP(
        idMaterialP: Int,
        llamarEmpresaQuimica: Boolean,
        clasificacion: String,
        nombreMaP: String,
        folioPEmergencia: Int?
    ): MaterialesP = transaction {
        val rowsUpdated = MaterialP.update({ MaterialP.idMaterialP eq idMaterialP }) {
            it[this.llamarEmpresaQuimica] = llamarEmpresaQuimica
            it[this.clasificacion] = clasificacion
            it[this.nombreMaP] = nombreMaP
            it[this.folioPEmergencia] = folioPEmergencia
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Material peligroso con ID $idMaterialP no encontrado")
        MaterialesP(idMaterialP, llamarEmpresaQuimica, clasificacion, nombreMaP, folioPEmergencia)
    }


    // Movil implementation

    private fun resultToMovil(row: ResultRow) = Moviles(
        nomenclatura = row[Movil.nomenclatura],
        especialidad = row[Movil.especialidad],
    )

    override suspend fun allMoviles(): List<Moviles> = dbQuery {
        Movil.selectAll().map(::resultToMovil)
    }

    override suspend fun getMovil(idMovil: Int): Moviles? = dbQuery {
        Movil.select { Movil.idMovil eq idMovil }
            .mapNotNull(::resultToMovil)
            .singleOrNull()
    }

    override suspend fun createMovil(
        nomenclatura: String,
        especialidad: String
    ): Moviles = transaction {
        val insertStatement = Movil.insert {
            it[this.nomenclatura] = nomenclatura
            it[this.especialidad] = especialidad
        }

        val idMovil = insertStatement.resultedValues?.get(0)?.get(Movil.idMovil)
            ?: throw IllegalStateException("No se pudo obtener el ID del móvil generado")

        Moviles(
            idMovil = idMovil,
            nomenclatura = nomenclatura,
            especialidad = especialidad
        )
    }



    override suspend fun deleteMovil(idMovil: Int): Boolean = dbQuery {
        Movil.deleteWhere { Movil.idMovil eq idMovil } > 0
    }

    override suspend fun updateMovil(
        idMovil: Int,
        nomenclatura: String,
        especialidad: String,
    ): Moviles {
        val rowsUpdated = dbQuery {
            Movil.update({ Movil.idMovil eq idMovil }) {
                it[Movil.nomenclatura] = nomenclatura
                it[Movil.especialidad] = especialidad
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Movil con id $idMovil no encontrado")
        return Moviles(idMovil, nomenclatura, especialidad)
    }

    // ParteEmergenciaVoluntario implementation
    private fun resultToParteEmergenciaVoluntario(row: ResultRow) = PartesEmergenciaVoluntarios(
        folioPEmergencia = row[ParteEmergenciaVoluntario.folioPEmergencia],
        idVoluntario = row[ParteEmergenciaVoluntario.idVoluntario]
    )

    override suspend fun allParteEmergenciaVoluntarios(): List<PartesEmergenciaVoluntarios> = transaction {
        ParteEmergenciaVoluntario.selectAll().map(::resultToParteEmergenciaVoluntario)
    }

    override suspend fun getParteEmergenciaVoluntario(idParteVoluntario: Int): PartesEmergenciaVoluntarios? = transaction {
        ParteEmergenciaVoluntario.select { ParteEmergenciaVoluntario.idParteVoluntario eq idParteVoluntario }
            .mapNotNull(::resultToParteEmergenciaVoluntario)
            .singleOrNull()
    }

    override suspend fun createParteEmergenciaVoluntario(
        folioPEmergencia: Int,
        idVoluntario: Int
    ): PartesEmergenciaVoluntarios = transaction {
        val insertStatement = ParteEmergenciaVoluntario.insert {
            it[this.folioPEmergencia] = folioPEmergencia
            it[this.idVoluntario] = idVoluntario
        }

        val idParteVoluntario = insertStatement.resultedValues?.get(0)?.get(ParteEmergenciaVoluntario.idParteVoluntario)
            ?: throw IllegalStateException("No se pudo obtener el ID del parte emergencia voluntario generado")

        PartesEmergenciaVoluntarios(
            idParteVoluntario = idParteVoluntario,
            folioPEmergencia = folioPEmergencia,
            idVoluntario = idVoluntario
        )
    }


    override suspend fun deleteParteEmergenciaVoluntario(idParteVoluntario: Int): Boolean = transaction {
        ParteEmergenciaVoluntario.deleteWhere { ParteEmergenciaVoluntario.idParteVoluntario eq idParteVoluntario } > 0
    }

    override suspend fun updateParteEmergenciaVoluntario(
        idParteVoluntario: Int,
        folioPEmergencia: Int,
        idVoluntario: Int
    ): PartesEmergenciaVoluntarios = transaction {
        val rowsUpdated = ParteEmergenciaVoluntario.update({ ParteEmergenciaVoluntario.idParteVoluntario eq idParteVoluntario }) {
            it[this.folioPEmergencia] = folioPEmergencia
            it[this.idVoluntario] = idVoluntario
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergenciaVoluntario con id $idParteVoluntario no encontrado")
        PartesEmergenciaVoluntarios(idParteVoluntario, folioPEmergencia, idVoluntario)
    }

    // ParteAsistenciaVoluntario implementation

    private fun resultToParteAsistenciaVoluntario(row: ResultRow) = PartesAsistenciaVoluntarios(
        folioPAsistencia = row[ParteAsistenciaVoluntario.folioPAsistencia],
        idVoluntario = row[ParteAsistenciaVoluntario.idVoluntario]
    )

    override suspend fun allParteAsistenciaVoluntarios(): List<PartesAsistenciaVoluntarios> = transaction {
        ParteAsistenciaVoluntario.selectAll().map(::resultToParteAsistenciaVoluntario)
    }

    override suspend fun getParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): PartesAsistenciaVoluntarios? = transaction {
        ParteAsistenciaVoluntario.select { ParteAsistenciaVoluntario.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario }
            .mapNotNull(::resultToParteAsistenciaVoluntario)
            .singleOrNull()
    }

    override suspend fun createParteAsistenciaVoluntario(
        folioPAsistencia: Int,
        idVoluntario: Int
    ): PartesAsistenciaVoluntarios = transaction {
        val insertStatement = ParteAsistenciaVoluntario.insert {
            it[this.folioPAsistencia] = folioPAsistencia
            it[this.idVoluntario] = idVoluntario
        }

        val idParteAsistenciaVoluntario = insertStatement.resultedValues?.get(0)?.get(ParteAsistenciaVoluntario.idParteAsistenciaVoluntario)
            ?: throw IllegalStateException("No se pudo obtener el ID del parte asistencia voluntario generado")

        PartesAsistenciaVoluntarios(
            idParteAsistenciaVoluntario = idParteAsistenciaVoluntario,
            folioPAsistencia = folioPAsistencia,
            idVoluntario = idVoluntario
        )
    }


    override suspend fun deleteParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): Boolean = transaction {
        ParteAsistenciaVoluntario.deleteWhere { ParteAsistenciaVoluntario.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario } > 0
    }

    override suspend fun updateParteAsistenciaVoluntario(
        idParteAsistenciaVoluntario: Int,
        folioPAsistencia: Int,
        idVoluntario: Int
    ): PartesAsistenciaVoluntarios = transaction {
        val rowsUpdated = ParteAsistenciaVoluntario.update({ ParteAsistenciaVoluntario.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario }) {
            it[this.folioPAsistencia] = folioPAsistencia
            it[this.idVoluntario] = idVoluntario
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistenciaVoluntario con id $idParteAsistenciaVoluntario no encontrado")
        PartesAsistenciaVoluntarios(idParteAsistenciaVoluntario, folioPAsistencia, idVoluntario)
    }

    //ParteEmergenciaMovil implementation

    private fun resultToParteEmergenciaMovil(row: ResultRow) = PartesEmergenciaMoviles(
        folioPEmergencia = row[ParteEmergenciaMovil.folioPEmergencia],
        idMovil = row[ParteEmergenciaMovil.idMovil]
    )

    override suspend fun allParteEmergenciaMovil(): List<PartesEmergenciaMoviles> = transaction {
        ParteEmergenciaMovil.selectAll().map(::resultToParteEmergenciaMovil)
    }

    override suspend fun getParteEmergenciaMovil(idParteEmergenciaMovil: Int): PartesEmergenciaMoviles? = transaction {
        ParteEmergenciaMovil.select { ParteEmergenciaMovil.idParteEmergenciaMovil eq idParteEmergenciaMovil }
            .mapNotNull(::resultToParteEmergenciaMovil)
            .singleOrNull()
    }

    override suspend fun createParteEmergenciaMovil(
        folioPEmergencia: Int,
        idMovil: Int
    ): PartesEmergenciaMoviles = transaction {
        val insertStatement = ParteEmergenciaMovil.insert {
            it[this.folioPEmergencia] = folioPEmergencia
            it[this.idMovil] = idMovil
        }

        val idParteEmergenciaMovil = insertStatement.resultedValues?.get(0)?.get(ParteEmergenciaMovil.idParteEmergenciaMovil)
            ?: throw IllegalStateException("No se pudo obtener el ID del parte emergencia móvil generado")

        PartesEmergenciaMoviles(
            idParteEmergenciaMovil = idParteEmergenciaMovil,
            folioPEmergencia = folioPEmergencia,
            idMovil = idMovil
        )
    }


    override suspend fun deleteParteEmergenciaMovil(idParteEmergenciaMovil: Int): Boolean = transaction {
        ParteEmergenciaMovil.deleteWhere { ParteEmergenciaMovil.idParteEmergenciaMovil eq idParteEmergenciaMovil } > 0
    }

    override suspend fun updateParteEmergenciaMovil(
        idParteEmergenciaMovil: Int,
        folioPEmergencia: Int,
        idMovil: Int
    ): PartesEmergenciaMoviles = transaction {
        val rowsUpdated = ParteEmergenciaMovil.update({ ParteEmergenciaMovil.idParteEmergenciaMovil eq idParteEmergenciaMovil }) {
            it[this.folioPEmergencia] = folioPEmergencia
            it[this.idMovil] = idMovil
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergenciaMovil con id $idParteEmergenciaMovil no encontrado")
        PartesEmergenciaMoviles(idParteEmergenciaMovil, folioPEmergencia, idMovil)
    }

    //ParteAsistenciaMovil implementation

    private fun resultToParteAsistenciaMovil(row: ResultRow) = PartesAsistenciaMoviles(
        folioPAsistencia = row[ParteAsistenciaMovil.folioPAsistencia],
        idMovil = row[ParteAsistenciaMovil.idMovil]
    )

    override suspend fun allParteAsistenciaMovil(): List<PartesAsistenciaMoviles> = transaction {
        ParteAsistenciaMovil.selectAll().map(::resultToParteAsistenciaMovil)
    }

    override suspend fun getParteAsistenciaMovil(idParteAsistenciaMovil: Int): PartesAsistenciaMoviles? = transaction {
        ParteAsistenciaMovil.select { ParteAsistenciaMovil.idParteAsistenciaMovil eq idParteAsistenciaMovil }
            .mapNotNull(::resultToParteAsistenciaMovil)
            .singleOrNull()
    }

    override suspend fun createParteAsistenciaMovil(
        folioPAsistencia: Int,
        idMovil: Int
    ): PartesAsistenciaMoviles = transaction {
        val insertStatement = ParteAsistenciaMovil.insert {
            it[this.folioPAsistencia] = folioPAsistencia
            it[this.idMovil] = idMovil
        }

        val idParteAsistenciaMovil = insertStatement.resultedValues?.get(0)?.get(ParteAsistenciaMovil.idParteAsistenciaMovil)
            ?: throw IllegalStateException("No se pudo obtener el ID del parte asistencia móvil generado")

        PartesAsistenciaMoviles(
            idParteAsistenciaMovil = idParteAsistenciaMovil,
            folioPAsistencia = folioPAsistencia,
            idMovil = idMovil
        )
    }


    override suspend fun deleteParteAsistenciaMovil(idParteAsistenciaMovil: Int): Boolean = transaction {
        ParteAsistenciaMovil.deleteWhere { ParteAsistenciaMovil.idParteAsistenciaMovil eq idParteAsistenciaMovil } > 0
    }

    override suspend fun updateParteAsistenciaMovil(
        idParteAsistenciaMovil: Int,
        folioPAsistencia: Int,
        idMovil: Int
    ): PartesAsistenciaMoviles = transaction {
        val rowsUpdated = ParteAsistenciaMovil.update({ ParteAsistenciaMovil.idParteAsistenciaMovil eq idParteAsistenciaMovil }) {
            it[this.folioPAsistencia] = folioPAsistencia
            it[this.idMovil] = idMovil
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistenciaMovil con id $idParteAsistenciaMovil no encontrado")
        PartesAsistenciaMoviles(idParteAsistenciaMovil, folioPAsistencia, idMovil)
    }


    //Funciones extras



}


