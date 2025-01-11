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
        idCuerpo = row[Cuerpo.idCuerpo],
        nombreCuerpo = row[Cuerpo.nombreCuerpo],
        provincia = row[Cuerpo.provincia],
        region = row[Cuerpo.region],
        comuna = row[Cuerpo.comuna]
    )

    override suspend fun allCuerpos(): List<Cuerpos> = transaction {
        Cuerpo.selectAll().map(::resultToCuerpo)
    }

    override suspend fun getCuerpo(idCuerpo: Int): Cuerpos? = transaction {
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
        Cuerpo.deleteWhere { Cuerpo.idCuerpo eq idCuerpo } > 0
    }

    override suspend fun updateCuerpo(
        idCuerpo: Int,
        nombreCuerpo: String,
        provincia: String,
        region: String,
        comuna: String
    ): Cuerpos = transaction {
        val rowsUpdated = Cuerpo.update({ Cuerpo.idCuerpo eq idCuerpo }) {
            it[this.nombreCuerpo] = nombreCuerpo
            it[this.provincia] = provincia
            it[this.region] = region
            it[this.comuna] = comuna
        }

        if (rowsUpdated == 0) throw IllegalArgumentException("Cuerpo con id $idCuerpo no encontrado")

        Cuerpos(
            idCuerpo = idCuerpo,
            nombreCuerpo = nombreCuerpo,
            provincia = provincia,
            region = region,
            comuna = comuna
        )
    }


    // Compania implementation

    private fun resultToCompania(row: ResultRow) = Companias(
        idCompania = row[Compania.idCompania],
        nombreCia = row[Compania.nombreCia],
        direccionCia = row[Compania.direccionCia],
        especialidad = row[Compania.especialidad],
        idCuerpo = row[Compania.idCuerpo]
    )

    override suspend fun allCompanias(): List<Companias> = transaction {
        Compania.selectAll().map(::resultToCompania)
    }

    override suspend fun getCompania(idCompania: Int): Companias? = transaction {
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

    override suspend fun deleteCompania(idCompania: Int): Boolean = transaction {
        Compania.deleteWhere { Compania.idCompania eq idCompania } > 0
    }

    override suspend fun updateCompania(
        idCompania: Int,
        nombreCia: String,
        direccionCia: String,
        especialidad: String,
        idCuerpo: Int?
    ): Companias = transaction {
        val rowsUpdated = Compania.update({ Compania.idCompania eq idCompania }) {
            it[this.nombreCia] = nombreCia
            it[this.direccionCia] = direccionCia
            it[this.especialidad] = especialidad
            it[this.idCuerpo] = idCuerpo
        }

        if (rowsUpdated == 0) throw IllegalArgumentException("Compañía con id $idCompania no encontrada")

        Companias(
            idCompania = idCompania,
            nombreCia = nombreCia,
            direccionCia = direccionCia,
            especialidad = especialidad,
            idCuerpo = idCuerpo
        )
    }


    // Usuario implementation

    private fun resultToUsuario(row: ResultRow) = Usuarios(
        idUsuario = row[Usuario.idUsuario],
        nombreUsuario = row[Usuario.nombreUsuario],
        contrasena = row[Usuario.contrasena]
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
        contrasena: String
    ): Usuarios = transaction {
        val insertStatement = Usuario.insert {
            it[this.nombreUsuario] = nombreUsuario
            it[this.contrasena] = contrasena
        }

        val idUsuario = insertStatement.resultedValues?.get(0)?.get(Usuario.idUsuario)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        Usuarios(
            idUsuario = idUsuario,
            nombreUsuario = nombreUsuario,
            contrasena = contrasena
        )
    }


    override suspend fun deleteUsuario(idUsuario: Int): Boolean = dbQuery {
        Usuario.deleteWhere { Usuario.idUsuario eq idUsuario } > 0
    }

    override suspend fun updateUsuario(
        idUsuario: Int,
        nombreUsuario: String,
        contrasena: String
    ): Usuarios {
        val rowsUpdated = transaction {
            Usuario.update({ Usuario.idUsuario eq idUsuario }) {
                it[this.nombreUsuario] = nombreUsuario
                it[this.contrasena] = contrasena
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Usuario con id $idUsuario no encontrado")
        }
        return Usuarios(idUsuario, nombreUsuario, contrasena)
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
        idVoluntario = row[Voluntario.idVoluntario],
        nombreVol = row[Voluntario.nombreVol],
        fechaNac = row[Voluntario.fechaNac],
        direccion = row[Voluntario.direccion],
        numeroContacto = row[Voluntario.numeroContacto],
        tipoSangre = row[Voluntario.tipoSangre],
        enfermedades = row[Voluntario.enfermedades],
        alergias = row[Voluntario.alergias],
        fechaIngreso = row[Voluntario.fechaIngreso],
        claveRadial = row[Voluntario.claveRadial],
        rutVoluntario = row[Voluntario.rutVoluntario],
        idCompania = row[Voluntario.idCompania],
        idUsuario = row[Voluntario.idUsuario],
        idCargo = row[Voluntario.idCargo],
        apellidop = row[Voluntario.apellidop],
        apellidom = row[Voluntario.apellidom],
        activo = row[Voluntario.activo]
    )

    override suspend fun allVoluntarios(): List<Voluntarios> = transaction {
        Voluntario.selectAll().map(::resultToVoluntario)
    }

    override suspend fun getVoluntario(idVoluntario: Int): Voluntarios? = transaction {
        Voluntario.select { Voluntario.idVoluntario eq idVoluntario }
            .mapNotNull(::resultToVoluntario)
            .singleOrNull()
    }

    override suspend fun createVoluntario(
        nombreVol: String,
        fechaNac: LocalDate,
        direccion: String,
        numeroContacto: String,
        tipoSangre: String?,
        enfermedades: String,
        alergias: String,
        fechaIngreso: LocalDate,
        claveRadial: String,
        rutVoluntario: String,
        idCompania: Int,
        idUsuario: Int?,
        idCargo: Int,
        apellidop: String,
        apellidom: String,
        activo: Boolean
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
            it[this.rutVoluntario] = rutVoluntario
            it[this.idCompania] = idCompania
            it[this.idUsuario] = idUsuario
            it[this.idCargo] = idCargo
            it[this.apellidop] = apellidop
            it[this.apellidom] = apellidom
            it[this.activo] = activo
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
            rutVoluntario = rutVoluntario,
            idCompania = idCompania,
            idUsuario = idUsuario,
            idCargo = idCargo,
            apellidop = apellidop,
            apellidom = apellidom,
            activo = activo
        )
    }

    override suspend fun deleteVoluntario(idVoluntario: Int): Boolean = transaction {
        Voluntario.deleteWhere { Voluntario.idVoluntario eq idVoluntario } > 0
    }

    override suspend fun updateVoluntario(
        idVoluntario: Int,
        nombreVol: String,
        fechaNac: LocalDate,
        direccion: String,
        numeroContacto: String,
        tipoSangre: String?,
        enfermedades: String,
        alergias: String,
        fechaIngreso: LocalDate,
        claveRadial: String,
        rutVoluntario: String,
        idCompania: Int,
        idUsuario: Int?,
        idCargo: Int,
        apellidop: String,
        apellidom: String,
        activo: Boolean
    ): Voluntarios {
        val rowsUpdated = transaction {
            Voluntario.update({ Voluntario.idVoluntario eq idVoluntario }) {
                it[this.nombreVol] = nombreVol
                it[this.fechaNac] = fechaNac
                it[this.direccion] = direccion
                it[this.numeroContacto] = numeroContacto
                it[this.tipoSangre] = tipoSangre
                it[this.enfermedades] = enfermedades
                it[this.alergias] = alergias
                it[this.fechaIngreso] = fechaIngreso
                it[this.claveRadial] = claveRadial
                it[this.rutVoluntario] = rutVoluntario
                it[this.idCompania] = idCompania
                it[this.idUsuario] = idUsuario
                it[this.idCargo] = idCargo
                it[this.apellidop] = apellidop
                it[this.apellidom] = apellidom
                it[this.activo] = activo
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Voluntario con id $idVoluntario no encontrado")
        return Voluntarios(
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
            rutVoluntario = rutVoluntario,
            idCompania = idCompania,
            idUsuario = idUsuario,
            idCargo = idCargo,
            apellidop = apellidop,
            apellidom = apellidom,
            activo = activo
        )
    }

    suspend fun getIdVoluntarioByRut(rutVoluntario: String): Int = transaction {
        Voluntario
            .select { Voluntario.rutVoluntario eq rutVoluntario }
            .mapNotNull { it[Voluntario.idVoluntario] }
            .singleOrNull()
            ?: throw IllegalArgumentException("Voluntario con rut $rutVoluntario no encontrado")
    }

    override suspend fun getVoluntarioByIdUsuario(idUsuario: Int): Voluntarios? = transaction {
        Voluntario.select { Voluntario.idUsuario eq idUsuario }
            .mapNotNull(::resultToVoluntario)
            .singleOrNull()
    }

    // VoluntarioWithRelations implementation
    override suspend fun getVoluntarioWithRelations(idVoluntario: Int): Triple<Companias?, Usuarios?, Cargos?> {
        // Primero obtiene el voluntario dentro de un bloque transaction
        val voluntario = transaction {
            Voluntario.select { Voluntario.idVoluntario eq idVoluntario }
                .singleOrNull()
                ?.let { resultToVoluntario(it) } // Aseguramos que resultToVoluntario se invoque correctamente
        } ?: throw IllegalArgumentException("Voluntario con ID $idVoluntario no encontrado")

        // Llama a las funciones suspendidas fuera del bloque transaction
        val compania = voluntario.idCompania.let { getCompania(it) } // let es seguro porque idCompania es no nulo
        val usuario = voluntario.idUsuario?.let { getUsuario(it) } // Esto maneja el caso donde idUsuario sea nulo
        val cargo = voluntario.idCargo.let { getCargo(it) }

        // Retorna las relaciones
        return Triple(compania, usuario, cargo)
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


    // ClaveEmergencias implementation

    private fun resultToClaveEmergencia(row: ResultRow) = ClaveEmergencias(
        idClaveEmergencia = row[ClaveEmergencia.idClaveEmergencia],
        nombreClaveEmergencia = row[ClaveEmergencia.nombreClaveEmergencia]
    )

    override suspend fun allClaveEmergencias(): List<ClaveEmergencias> = transaction {
        ClaveEmergencia.selectAll().map(::resultToClaveEmergencia)
    }

    override suspend fun getClaveEmergencia(idClaveEmergencia: Int): ClaveEmergencias? = transaction {
        ClaveEmergencia.select { ClaveEmergencia.idClaveEmergencia eq idClaveEmergencia }
            .mapNotNull(::resultToClaveEmergencia)
            .singleOrNull()
    }

    override suspend fun createClaveEmergencia(
        nombreClaveEmergencia: String
    ): ClaveEmergencias = transaction {
        val insertStatement = ClaveEmergencia.insert {
            it[this.nombreClaveEmergencia] = nombreClaveEmergencia
        }

        val idClaveEmergencia = insertStatement.resultedValues?.get(0)?.get(ClaveEmergencia.idClaveEmergencia)
            ?: throw IllegalStateException("No se pudo obtener el ID generado")

        ClaveEmergencias(
            idClaveEmergencia = idClaveEmergencia,
            nombreClaveEmergencia = nombreClaveEmergencia
        )
    }

    override suspend fun deleteClaveEmergencia(idClaveEmergencia: Int): Boolean = transaction {
        ClaveEmergencia.deleteWhere { ClaveEmergencia.idClaveEmergencia eq idClaveEmergencia } > 0
    }

    override suspend fun updateClaveEmergencia(
        idClaveEmergencia: Int,
        nombreClaveEmergencia: String
    ): ClaveEmergencias = transaction {
        val rowsUpdated = ClaveEmergencia.update({ ClaveEmergencia.idClaveEmergencia eq idClaveEmergencia }) {
            it[this.nombreClaveEmergencia] = nombreClaveEmergencia
        }

        if (rowsUpdated == 0) {
            throw IllegalArgumentException("ClaveEmergencia con id $idClaveEmergencia no encontrada")
        }

        ClaveEmergencias(idClaveEmergencia, nombreClaveEmergencia)
    }


    // ParteEmergencia implementation

    private fun resultToParteEmergencia(row: ResultRow) = Partes_emergencia(
        folioPEmergencia = row[Parte_emergencia.folioPEmergencia],
        horaInicio = row[Parte_emergencia.horaInicio],
        horaFin = row[Parte_emergencia.horaFin],
        fechaEmergencia = row[Parte_emergencia.fechaEmergencia],
        preInforme = row[Parte_emergencia.preInforme],
        llamarEmpresaQuimica = row[Parte_emergencia.llamarEmpresaQuimica],
        descripcionMaterialP = row[Parte_emergencia.descripcionMaterialP],
        direccionEmergencia = row[Parte_emergencia.direccionEmergencia],
        idOficial = row[Parte_emergencia.idOficial],
        idClaveEmergencia = row[Parte_emergencia.idClaveEmergencia],
        folioPAsistencia = row[Parte_emergencia.folioPAsistencia]
    )

    override suspend fun allPartesEmergencia(): List<Partes_emergencia> = transaction {
        Parte_emergencia.selectAll().map(::resultToParteEmergencia)
    }

    override suspend fun getParteEmergencia(folioPEmergencia: Int): Partes_emergencia? = transaction {
        Parte_emergencia.select { Parte_emergencia.folioPEmergencia eq folioPEmergencia }
            .mapNotNull(::resultToParteEmergencia)
            .singleOrNull()
    }

    override suspend fun createParteEmergencia(
        horaInicio: LocalTime,
        horaFin: LocalTime,
        fechaEmergencia: LocalDate,
        preInforme: String,
        llamarEmpresaQuimica: Boolean,
        descripcionMaterialP: String,
        direccionEmergencia: String,
        idOficial: Int,
        idClaveEmergencia: Int,
        folioPAsistencia: Int?,
        idMaterialP: Int? // Nuevo parámetro opcional
    ): Partes_emergencia = transaction {
        // Insertar el Parte de Emergencia
        val insertStatement = Parte_emergencia.insert {
            it[this.horaInicio] = horaInicio
            it[this.horaFin] = horaFin
            it[this.fechaEmergencia] = fechaEmergencia
            it[this.preInforme] = preInforme
            it[this.llamarEmpresaQuimica] = llamarEmpresaQuimica
            it[this.descripcionMaterialP] = descripcionMaterialP
            it[this.direccionEmergencia] = direccionEmergencia
            it[this.idOficial] = idOficial
            it[this.idClaveEmergencia] = idClaveEmergencia
            it[this.folioPAsistencia] = folioPAsistencia
        }

        val folioPEmergencia = insertStatement.resultedValues?.get(0)?.get(Parte_emergencia.folioPEmergencia)
            ?: throw IllegalStateException("No se pudo obtener el folio generado")

        // Si se proporciona idMaterialP, inserta en la tabla intermedia
        if (idMaterialP != null) {
            ParteEmergenciaMaterial.insert {
                it[this.folioPEmergencia] = folioPEmergencia
                it[this.idMaterialP] = idMaterialP
            }
        }

        // Retornar el Parte de Emergencia creado
        Partes_emergencia(
            folioPEmergencia = folioPEmergencia,
            horaInicio = horaInicio,
            horaFin = horaFin,
            fechaEmergencia = fechaEmergencia,
            preInforme = preInforme,
            llamarEmpresaQuimica = llamarEmpresaQuimica,
            descripcionMaterialP = descripcionMaterialP,
            direccionEmergencia = direccionEmergencia,
            idOficial = idOficial,
            idClaveEmergencia = idClaveEmergencia,
            folioPAsistencia = folioPAsistencia
        )
    }


    override suspend fun deleteParteEmergencia(folioPEmergencia: Int): Boolean = transaction {
        Parte_emergencia.deleteWhere { Parte_emergencia.folioPEmergencia eq folioPEmergencia } > 0
    }

    override suspend fun updateParteEmergencia(
        folioPEmergencia: Int,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        fechaEmergencia: LocalDate,
        preInforme: String,
        llamarEmpresaQuimica: Boolean,
        descripcionMaterialP: String,
        direccionEmergencia: String,
        idOficial: Int,
        idClaveEmergencia: Int,
        folioPAsistencia: Int?,
        idMaterialP: Int? // Nuevo parámetro opcional
    ): Partes_emergencia = transaction {
        val rowsUpdated = Parte_emergencia.update({ Parte_emergencia.folioPEmergencia eq folioPEmergencia }) {
            it[this.horaInicio] = horaInicio
            it[this.horaFin] = horaFin
            it[this.fechaEmergencia] = fechaEmergencia
            it[this.preInforme] = preInforme
            it[this.llamarEmpresaQuimica] = llamarEmpresaQuimica
            it[this.descripcionMaterialP] = descripcionMaterialP
            it[this.direccionEmergencia] = direccionEmergencia
            it[this.idOficial] = idOficial
            it[this.idClaveEmergencia] = idClaveEmergencia
            it[this.folioPAsistencia] = folioPAsistencia
        }
        // Si se proporciona idMaterialP, inserta en la tabla intermedia
        if (idMaterialP != null) {
            ParteEmergenciaMaterial.update {
                it[this.folioPEmergencia] = folioPEmergencia
                it[this.idMaterialP] = idMaterialP
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergencia con folio $folioPEmergencia no encontrado")

        Partes_emergencia(
            folioPEmergencia,
            horaInicio,
            horaFin,
            fechaEmergencia,
            preInforme,
            llamarEmpresaQuimica,
            descripcionMaterialP,
            direccionEmergencia,
            idOficial,
            idClaveEmergencia,
            folioPAsistencia
        )
    }

    override suspend fun getParteEmergenciaWithRelations(folioPEmergencia: Int): Pair<List<Moviles>, List<Voluntarios>> {
        // Primero obtiene el Parte de Emergencia dentro de un bloque transaction
        val parteEmergencia = transaction {
            Parte_emergencia.select { Parte_emergencia.folioPEmergencia eq folioPEmergencia }
                .singleOrNull()
                ?.let { resultToParteEmergencia(it) } // Aseguramos que resultToParteEmergencia se invoque correctamente
        } ?: throw IllegalArgumentException("Parte de Emergencia con folio $folioPEmergencia no encontrado")
        // Llama a las funciones suspendidas fuera del bloque transaction
        val moviles = getMovilesPorParteEmergencia(folioPEmergencia)
        val voluntarios = getVoluntariosPorParteEmergencia(folioPEmergencia)

        // Retorna las relaciones
        return Pair(moviles, voluntarios)
    }


    // ParteAsistencia implementation

    private fun resultToParteAsistencia(row: ResultRow) = Partes_asistencia(
        folioPAsistencia = row[Parte_asistencia.folioPAsistencia],
        aCargoDelCuerpo = row[Parte_asistencia.aCargoDelCuerpo],
        aCargoDeLaCompania = row[Parte_asistencia.aCargoDeLaCompania],
        fechaAsistencia = row[Parte_asistencia.fechaAsistencia],
        horaInicio = row[Parte_asistencia.horaInicio],
        horaFin = row[Parte_asistencia.horaFin],
        direccionAsistencia = row[Parte_asistencia.direccionAsistencia],
        totalAsistencia = row[Parte_asistencia.totalAsistencia],
        observaciones = row[Parte_asistencia.observaciones],
        idTipoLlamado = row[Parte_asistencia.idTipoLlamado]
    )

    override suspend fun allPartesAsistencia(): List<Partes_asistencia> = transaction {
        Parte_asistencia.selectAll().map(::resultToParteAsistencia)
    }

    override suspend fun getParteAsistencia(folioPAsistencia: Int): Partes_asistencia? = transaction {
        Parte_asistencia.select { Parte_asistencia.folioPAsistencia eq folioPAsistencia }
            .mapNotNull(::resultToParteAsistencia)
            .singleOrNull()
    }

    override suspend fun createParteAsistencia(
        aCargoDelCuerpo: Int,
        aCargoDeLaCompania: Int,
        fechaAsistencia: LocalDate,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        direccionAsistencia: String,
        totalAsistencia: Int,
        observaciones: String,
        idTipoLlamado: Int
    ): Partes_asistencia = transaction {
        val insertStatement = Parte_asistencia.insert {
            it[this.aCargoDelCuerpo] = aCargoDelCuerpo
            it[this.aCargoDeLaCompania] = aCargoDeLaCompania
            it[this.fechaAsistencia] = fechaAsistencia
            it[this.horaInicio] = horaInicio
            it[this.horaFin] = horaFin
            it[this.direccionAsistencia] = direccionAsistencia
            it[this.totalAsistencia] = totalAsistencia
            it[this.observaciones] = observaciones
            it[this.idTipoLlamado] = idTipoLlamado
        }

        val folioPAsistencia = insertStatement.resultedValues?.get(0)?.get(Parte_asistencia.folioPAsistencia)
            ?: throw IllegalStateException("No se pudo obtener el folio generado")

        Partes_asistencia(
            folioPAsistencia = folioPAsistencia,
            aCargoDelCuerpo = aCargoDelCuerpo,
            aCargoDeLaCompania = aCargoDeLaCompania,
            fechaAsistencia = fechaAsistencia,
            horaInicio = horaInicio,
            horaFin = horaFin,
            direccionAsistencia = direccionAsistencia,
            totalAsistencia = totalAsistencia,
            observaciones = observaciones,
            idTipoLlamado = idTipoLlamado
        )
    }

    override suspend fun deleteParteAsistencia(folioPAsistencia: Int): Boolean = transaction {
        Parte_asistencia.deleteWhere { Parte_asistencia.folioPAsistencia eq folioPAsistencia } > 0
    }

    override suspend fun updateParteAsistencia(
        folioPAsistencia: Int,
        aCargoDelCuerpo: Int,
        aCargoDeLaCompania: Int,
        fechaAsistencia: LocalDate,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        direccionAsistencia: String,
        totalAsistencia: Int,
        observaciones: String,
        idTipoLlamado: Int
    ): Partes_asistencia = transaction {
        val rowsUpdated = Parte_asistencia.update({ Parte_asistencia.folioPAsistencia eq folioPAsistencia }) {
            it[this.aCargoDelCuerpo] = aCargoDelCuerpo
            it[this.aCargoDeLaCompania] = aCargoDeLaCompania
            it[this.fechaAsistencia] = fechaAsistencia
            it[this.horaInicio] = horaInicio
            it[this.horaFin] = horaFin
            it[this.direccionAsistencia] = direccionAsistencia
            it[this.totalAsistencia] = totalAsistencia
            it[this.observaciones] = observaciones
            it[this.idTipoLlamado] = idTipoLlamado
        }

        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistencia con folio $folioPAsistencia no encontrado")

        Partes_asistencia(
            folioPAsistencia,
            aCargoDelCuerpo,
            aCargoDeLaCompania,
            fechaAsistencia,
            horaInicio,
            horaFin,
            direccionAsistencia,
            totalAsistencia,
            observaciones,
            idTipoLlamado
        )
    }

    override suspend fun getParteAsistenciaWithRelations(folioPAsistencia: Int): Triple<TipoCitacion?, List<Moviles>, List<Voluntarios>> {
        // Primero obtiene el Parte de Asistencia dentro de un bloque transaction
        val parteAsistencia = transaction {
            Parte_asistencia.select { Parte_asistencia.folioPAsistencia eq folioPAsistencia }
                .singleOrNull()
                ?.let { resultToParteAsistencia(it) } // Aseguramos que resultToParteAsistencia se invoque correctamente
        } ?: throw IllegalArgumentException("Parte de Asistencia con folio $folioPAsistencia no encontrado")

        // Llama a las funciones suspendidas fuera del bloque transaction
        val tipoCitacion =
            parteAsistencia.idTipoLlamado.let { getTipoCitacion(it) } // let es seguro porque idTipoLlamado es no nulo
        val moviles = getMovilesPorParteAsistencia(folioPAsistencia)
        val voluntarios = getVoluntariosPorParteAsistencia(folioPAsistencia)

        // Retorna las relaciones
        return Triple(tipoCitacion, moviles, voluntarios)
    }

    override suspend fun getParteAsistenciaResponse(folioPAsistencia: Int): ParteAsistenciaResponse? {
        val parteAsistencia = transaction {
            Parte_asistencia
                .select { Parte_asistencia.folioPAsistencia eq folioPAsistencia }
                .singleOrNull()
                ?.let { resultToParteAsistencia(it) }
        } ?: return null

        // Llama a las funciones suspendidas fuera del bloque transaction
        val encargadoCuerpo = getVoluntario(parteAsistencia.aCargoDelCuerpo)
        val encargadoCompania = getVoluntario(parteAsistencia.aCargoDeLaCompania)
        val tipoLlamado = getTipoCitacion(parteAsistencia.idTipoLlamado)
        val voluntarios = getVoluntariosPorParteAsistencia(folioPAsistencia)
        val moviles = getMovilesPorParteAsistencia(folioPAsistencia)

        return ParteAsistenciaResponse(
            folioPAsistencia = parteAsistencia.folioPAsistencia,
            aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
            encargadoCuerpo = encargadoCuerpo,
            aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
            encargadoCompania = encargadoCompania,
            fechaAsistencia = parteAsistencia.fechaAsistencia,
            horaInicio = parteAsistencia.horaInicio,
            horaFin = parteAsistencia.horaFin,
            direccionAsistencia = parteAsistencia.direccionAsistencia,
            totalAsistencia = parteAsistencia.totalAsistencia,
            observaciones = parteAsistencia.observaciones,
            idTipoLlamado = parteAsistencia.idTipoLlamado,
            tipoLlamado = tipoLlamado,
            voluntarios = voluntarios,
            moviles = moviles
        )
    }



    // MaterialP implementation

    private fun resultToMaterialP(row: ResultRow) = MaterialesP(
        idMaterialP = row[MaterialP.idMaterialP],
        clasificacion = row[MaterialP.clasificacion],
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
        clasificacion: String,
    ): MaterialesP = transaction {
        val insertStatement = MaterialP.insert {
            it[this.clasificacion] = clasificacion
        }

        val idMaterialP = insertStatement.resultedValues?.get(0)?.get(MaterialP.idMaterialP)
            ?: throw IllegalStateException("No se pudo obtener el id generado")

        MaterialesP(
            idMaterialP = idMaterialP,
            clasificacion = clasificacion,
        )
    }


    override suspend fun deleteMaterialP(idMaterialP: Int): Boolean = transaction {
        MaterialP.deleteWhere { MaterialP.idMaterialP eq idMaterialP } > 0
    }

    override suspend fun updateMaterialP(
        idMaterialP: Int,
        clasificacion: String,
    ): MaterialesP = transaction {
        val rowsUpdated = MaterialP.update({ MaterialP.idMaterialP eq idMaterialP }) {
            it[this.clasificacion] = clasificacion
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Material peligroso con ID $idMaterialP no encontrado")
        MaterialesP(idMaterialP, clasificacion)
    }


    // Movil implementation

    private fun resultToMovil(row: ResultRow) = Moviles(
        idMovil = row[Movil.idMovil],
        nomenclatura = row[Movil.nomenclatura],
        especialidad = row[Movil.especialidad]
    )

    override suspend fun allMoviles(): List<Moviles> = transaction {
        Movil.selectAll().map(::resultToMovil)
    }

    override suspend fun getMovil(idMovil: Int): Moviles? = transaction {
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

    override suspend fun deleteMovil(idMovil: Int): Boolean = transaction {
        Movil.deleteWhere { Movil.idMovil eq idMovil } > 0
    }

    override suspend fun updateMovil(
        idMovil: Int,
        nomenclatura: String,
        especialidad: String
    ): Moviles = transaction {
        val rowsUpdated = Movil.update({ Movil.idMovil eq idMovil }) {
            it[this.nomenclatura] = nomenclatura
            it[this.especialidad] = especialidad
        }

        if (rowsUpdated == 0) throw IllegalArgumentException("Movil con id $idMovil no encontrado")

        Moviles(
            idMovil = idMovil,
            nomenclatura = nomenclatura,
            especialidad = especialidad
        )
    }


    // ParteEmergenciaVoluntario implementation
    private fun resultToParteEmergenciaVoluntario(row: ResultRow) = PartesEmergenciaVoluntarios(
        folioPEmergencia = row[ParteEmergenciaVoluntario.folioPEmergencia],
        idVoluntario = row[ParteEmergenciaVoluntario.idVoluntario]
    )

    override suspend fun allParteEmergenciaVoluntarios(): List<PartesEmergenciaVoluntarios> = transaction {
        ParteEmergenciaVoluntario.selectAll().map(::resultToParteEmergenciaVoluntario)
    }

    override suspend fun getParteEmergenciaVoluntario(idParteVoluntario: Int): PartesEmergenciaVoluntarios? =
        transaction {
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
        val rowsUpdated =
            ParteEmergenciaVoluntario.update({ ParteEmergenciaVoluntario.idParteVoluntario eq idParteVoluntario }) {
                it[this.folioPEmergencia] = folioPEmergencia
                it[this.idVoluntario] = idVoluntario
            }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergenciaVoluntario con id $idParteVoluntario no encontrado")
        PartesEmergenciaVoluntarios(idParteVoluntario, folioPEmergencia, idVoluntario)
    }

    override suspend fun getVoluntariosPorParteEmergencia(folioPEmergencia: Int): List<Voluntarios> = transaction {
        (ParteEmergenciaVoluntario innerJoin Voluntario)
            .select { ParteEmergenciaVoluntario.folioPEmergencia eq folioPEmergencia }
            .map { row ->
                Voluntarios(
                    idVoluntario = row[Voluntario.idVoluntario],
                    nombreVol = row[Voluntario.nombreVol],
                    fechaNac = row[Voluntario.fechaNac],
                    direccion = row[Voluntario.direccion],
                    numeroContacto = row[Voluntario.numeroContacto],
                    tipoSangre = row[Voluntario.tipoSangre],
                    enfermedades = row[Voluntario.enfermedades],
                    alergias = row[Voluntario.alergias],
                    fechaIngreso = row[Voluntario.fechaIngreso],
                    claveRadial = row[Voluntario.claveRadial],
                    rutVoluntario = row[Voluntario.rutVoluntario],
                    idCompania = row[Voluntario.idCompania],
                    idUsuario = row[Voluntario.idUsuario],
                    idCargo = row[Voluntario.idCargo],
                    apellidop = row[Voluntario.apellidop],
                    apellidom = row[Voluntario.apellidom],
                    activo = row[Voluntario.activo]


                )
            }
    }


    // ParteAsistenciaVoluntario implementation

    private fun resultToParteAsistenciaVoluntario(row: ResultRow) = PartesAsistenciaVoluntarios(
        folioPAsistencia = row[ParteAsistenciaVoluntario.folioPAsistencia],
        idVoluntario = row[ParteAsistenciaVoluntario.idVoluntario]
    )

    override suspend fun allParteAsistenciaVoluntarios(): List<PartesAsistenciaVoluntarios> = transaction {
        ParteAsistenciaVoluntario.selectAll().map(::resultToParteAsistenciaVoluntario)
    }

    override suspend fun getParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): PartesAsistenciaVoluntarios? =
        transaction {
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

        val idParteAsistenciaVoluntario =
            insertStatement.resultedValues?.get(0)?.get(ParteAsistenciaVoluntario.idParteAsistenciaVoluntario)
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
        val rowsUpdated =
            ParteAsistenciaVoluntario.update({ ParteAsistenciaVoluntario.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario }) {
                it[this.folioPAsistencia] = folioPAsistencia
                it[this.idVoluntario] = idVoluntario
            }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistenciaVoluntario con id $idParteAsistenciaVoluntario no encontrado")
        PartesAsistenciaVoluntarios(idParteAsistenciaVoluntario, folioPAsistencia, idVoluntario)
    }

    override suspend fun getVoluntariosPorParteAsistencia(folioPAsistencia: Int): List<Voluntarios> = transaction {
        (ParteAsistenciaVoluntario innerJoin Voluntario)
            .select { ParteAsistenciaVoluntario.folioPAsistencia eq folioPAsistencia }
            .map { row ->
                Voluntarios(
                    idVoluntario = row[Voluntario.idVoluntario],
                    nombreVol = row[Voluntario.nombreVol],
                    fechaNac = row[Voluntario.fechaNac],
                    direccion = row[Voluntario.direccion],
                    numeroContacto = row[Voluntario.numeroContacto],
                    tipoSangre = row[Voluntario.tipoSangre],
                    enfermedades = row[Voluntario.enfermedades],
                    alergias = row[Voluntario.alergias],
                    fechaIngreso = row[Voluntario.fechaIngreso],
                    claveRadial = row[Voluntario.claveRadial],
                    rutVoluntario = row[Voluntario.rutVoluntario],
                    idCompania = row[Voluntario.idCompania],
                    idUsuario = row[Voluntario.idUsuario],
                    idCargo = row[Voluntario.idCargo],
                    apellidop = row[Voluntario.apellidop],
                    apellidom = row[Voluntario.apellidom],
                    activo = row[Voluntario.activo]
                )
            }
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

        val idParteEmergenciaMovil =
            insertStatement.resultedValues?.get(0)?.get(ParteEmergenciaMovil.idParteEmergenciaMovil)
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
        val rowsUpdated =
            ParteEmergenciaMovil.update({ ParteEmergenciaMovil.idParteEmergenciaMovil eq idParteEmergenciaMovil }) {
                it[this.folioPEmergencia] = folioPEmergencia
                it[this.idMovil] = idMovil
            }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergenciaMovil con id $idParteEmergenciaMovil no encontrado")
        PartesEmergenciaMoviles(idParteEmergenciaMovil, folioPEmergencia, idMovil)
    }


    override suspend fun getParteEmergenciaMovilByFolio(folioPEmergencia: Int): List<PartesEmergenciaMoviles> =
        transaction {
            ParteEmergenciaMovil
                .select { ParteEmergenciaMovil.folioPEmergencia eq folioPEmergencia }
                .map {
                    PartesEmergenciaMoviles(
                        idParteEmergenciaMovil = it[ParteEmergenciaMovil.idParteEmergenciaMovil],
                        folioPEmergencia = it[ParteEmergenciaMovil.folioPEmergencia],
                        idMovil = it[ParteEmergenciaMovil.idMovil]
                    )
                }
        }

    override suspend fun getMovilesPorParteEmergencia(folioPEmergencia: Int): List<Moviles> = transaction {
        (ParteEmergenciaMovil innerJoin Movil)
            .select { ParteEmergenciaMovil.folioPEmergencia eq folioPEmergencia }
            .map { row ->
                Moviles(
                    idMovil = row[Movil.idMovil],
                    nomenclatura = row[Movil.nomenclatura],
                    especialidad = row[Movil.especialidad]
                )
            }
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

        val idParteAsistenciaMovil =
            insertStatement.resultedValues?.get(0)?.get(ParteAsistenciaMovil.idParteAsistenciaMovil)
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
        val rowsUpdated =
            ParteAsistenciaMovil.update({ ParteAsistenciaMovil.idParteAsistenciaMovil eq idParteAsistenciaMovil }) {
                it[this.folioPAsistencia] = folioPAsistencia
                it[this.idMovil] = idMovil
            }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistenciaMovil con id $idParteAsistenciaMovil no encontrado")
        PartesAsistenciaMoviles(idParteAsistenciaMovil, folioPAsistencia, idMovil)
    }

    override suspend fun getMovilesPorParteAsistencia(folioPAsistencia: Int): List<Moviles> = transaction {
        (ParteAsistenciaMovil innerJoin Movil)
            .select { ParteAsistenciaMovil.folioPAsistencia eq folioPAsistencia }
            .map { row ->
                Moviles(
                    idMovil = row[Movil.idMovil],
                    nomenclatura = row[Movil.nomenclatura],
                    especialidad = row[Movil.especialidad]
                )
            }
    }

    // ParteEmergenciaMaterial implementation

    private fun resultToParteEmergenciaMaterial(row: ResultRow) = PartesEmergenciaMateriales(
        idparteemergenciamaterialp = row[ParteEmergenciaMaterial.idParteAsistenciaMaterialP],
        folioPEmergencia = row[ParteEmergenciaMaterial.folioPEmergencia],
        idMaterialP = row[ParteEmergenciaMaterial.idMaterialP]
    )

    override suspend fun allParteEmergenciaMaterial(): List<PartesEmergenciaMateriales> = transaction {
        ParteEmergenciaMaterial.selectAll().map(::resultToParteEmergenciaMaterial)
    }

    override suspend fun getParteEmergenciaMaterial(idparteemergenciamaterialp: Int): PartesEmergenciaMateriales? =
        transaction {
            ParteEmergenciaMaterial.select { ParteEmergenciaMaterial.idParteAsistenciaMaterialP eq idparteemergenciamaterialp }
                .mapNotNull(::resultToParteEmergenciaMaterial)
                .singleOrNull()
        }

    override suspend fun createParteEmergenciaMaterial(
        folioPEmergencia: Int,
        idMaterialP: Int
    ): PartesEmergenciaMateriales = transaction {
        val insertStatement = ParteEmergenciaMaterial.insert {
            it[this.folioPEmergencia] = folioPEmergencia
            it[this.idMaterialP] = idMaterialP
        }

        val idparteemergenciamaterialp =
            insertStatement.resultedValues?.get(0)?.get(ParteEmergenciaMaterial.idParteAsistenciaMaterialP)
                ?: throw IllegalStateException("No se pudo obtener el ID del parte emergencia material generado")

        PartesEmergenciaMateriales(
            idparteemergenciamaterialp = idparteemergenciamaterialp,
            folioPEmergencia = folioPEmergencia,
            idMaterialP = idMaterialP
        )
    }

    override suspend fun deleteParteEmergenciaMaterial(idparteemergenciamaterialp: Int): Boolean = transaction {
        ParteEmergenciaMaterial.deleteWhere { ParteEmergenciaMaterial.idParteAsistenciaMaterialP eq idparteemergenciamaterialp } > 0
    }

    override suspend fun updateParteEmergenciaMaterial(
        idparteemergenciamaterialp: Int,
        folioPEmergencia: Int,
        idMaterialP: Int
    ): PartesEmergenciaMateriales = transaction {
        val rowsUpdated =
            ParteEmergenciaMaterial.update({ ParteEmergenciaMaterial.idParteAsistenciaMaterialP eq idparteemergenciamaterialp }) {
                it[this.folioPEmergencia] = folioPEmergencia
                it[this.idMaterialP] = idMaterialP
            }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergenciaMaterial con id $idparteemergenciamaterialp no encontrado")
        PartesEmergenciaMateriales(idparteemergenciamaterialp, folioPEmergencia, idMaterialP)
    }

    // Cargo implementation

    private fun resultToCargo(row: ResultRow) = Cargos(
        idCargo = row[Cargo.idCargo],
        nombreCarg = row[Cargo.nombreCarg]
    )

    override suspend fun allCargos(): List<Cargos> = transaction {
        Cargo.selectAll().map(::resultToCargo)
    }

    override suspend fun getCargo(idCargo: Int): Cargos? = transaction {
        Cargo.select { Cargo.idCargo eq idCargo }
            .mapNotNull(::resultToCargo)
            .singleOrNull()
    }

    override suspend fun createCargo(idCargo: Int, nombreCarg: String): Cargos = transaction {
        val insertStatement = Cargo.insert {
            it[this.idCargo] = idCargo
            it[this.nombreCarg] = nombreCarg
        }

        val insertedId = insertStatement.resultedValues?.get(0)?.get(Cargo.idCargo)
            ?: throw IllegalStateException("No se pudo obtener el ID insertado")

        Cargos(
            idCargo = insertedId,
            nombreCarg = nombreCarg
        )
    }

    override suspend fun deleteCargo(idCargo: Int): Boolean = transaction {
        Cargo.deleteWhere { Cargo.idCargo eq idCargo } > 0
    }

    override suspend fun updateCargo(idCargo: Int, nombreCarg: String): Cargos = transaction {
        val rowsUpdated = Cargo.update({ Cargo.idCargo eq idCargo }) {
            it[this.nombreCarg] = nombreCarg
        }

        if (rowsUpdated == 0) throw IllegalArgumentException("Cargo con id $idCargo no encontrado")

        Cargos(
            idCargo = idCargo,
            nombreCarg = nombreCarg
        )
    }


    // TipoCitacion implementation

    private fun resultToTipoCitacion(row: ResultRow) = TipoCitacion(
        idTipoLlamado = row[Tipo_citacion.idTipoLlamado],
        nombreTipoLlamado = row[Tipo_citacion.nombreTipoLlamado]
    )

    override suspend fun allTipoCitaciones(): List<TipoCitacion> = transaction {
        Tipo_citacion.selectAll().map(::resultToTipoCitacion)
    }

    override suspend fun getTipoCitacion(idTipoLlamado: Int): TipoCitacion? = transaction {
        Tipo_citacion.select { Tipo_citacion.idTipoLlamado eq idTipoLlamado }
            .mapNotNull(::resultToTipoCitacion)
            .singleOrNull()
    }

    override suspend fun createTipoCitacion(nombreTipoLlamado: String): TipoCitacion = transaction {
        val insertStatement = Tipo_citacion.insert {
            it[this.nombreTipoLlamado] = nombreTipoLlamado
        }

        val idTipoLlamado = insertStatement.resultedValues?.get(0)?.get(Tipo_citacion.idTipoLlamado)
            ?: throw IllegalStateException("No se pudo obtener el ID insertado")

        TipoCitacion(
            idTipoLlamado = idTipoLlamado,
            nombreTipoLlamado = nombreTipoLlamado
        )
    }

    override suspend fun deleteTipoCitacion(idTipoLlamado: Int): Boolean = transaction {
        Tipo_citacion.deleteWhere { Tipo_citacion.idTipoLlamado eq idTipoLlamado } > 0
    }

    override suspend fun updateTipoCitacion(idTipoLlamado: Int, nombreTipoLlamado: String): TipoCitacion = transaction {
        val rowsUpdated = Tipo_citacion.update({ Tipo_citacion.idTipoLlamado eq idTipoLlamado }) {
            it[this.nombreTipoLlamado] = nombreTipoLlamado
        }

        if (rowsUpdated == 0) throw IllegalArgumentException("TipoCitacion con id $idTipoLlamado no encontrado")

        TipoCitacion(
            idTipoLlamado = idTipoLlamado,
            nombreTipoLlamado = nombreTipoLlamado
        )
    }


    //Funciones extras


}


