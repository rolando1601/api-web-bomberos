package com.example.dao


import com.example.dao.DatabaseSingleton.dbQuery
import com.example.models.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*
import kotlinx.datetime.LocalDateTime


class DAOFacadeImpl : DAOFacade {



    // Institucion implementation

    private fun resultToInstitucion(row: ResultRow) = Instituciones(
        nombreInstitucion = row[Institucion.nombreInstitucion],
        tipoInstitucion = row[Institucion.tipoInstitucion],
        nombrePersonaCargo = row[Institucion.nombrePersonaCargo],
        horaLlegada = row[Institucion.horaLlegada],
        folioPEmergencia = row[Institucion.folioPEmergencia]
    )

    override suspend fun allInstituciones(): List<Instituciones> = dbQuery {
        Institucion.selectAll().map(::resultToInstitucion)
    }

    override suspend fun getInstitucion(idInstitucion: Int): Instituciones? = dbQuery {
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
    ): Instituciones {
        dbQuery {
            Institucion.insert {
                it[Institucion.nombreInstitucion] = nombreInstitucion
                it[Institucion.tipoInstitucion] = tipoInstitucion
                it[Institucion.nombrePersonaCargo] = nombrePersonaCargo
                it[Institucion.horaLlegada] = horaLlegada
                it[Institucion.folioPEmergencia] = folioPEmergencia
            }
        }
        return Instituciones( nombreInstitucion, tipoInstitucion, nombrePersonaCargo, horaLlegada, folioPEmergencia)
    }

    override suspend fun deleteInstitucion(idInstitucion: Int): Boolean = dbQuery {
        Institucion.deleteWhere { Institucion.idInstitucion eq idInstitucion } > 0
    }

    override suspend fun updateInstitucion(
        idInstitucion: Int,
        nombreInstitucion: String,
        tipoInstitucion: String,
        nombrePersonaCargo: String,
        horaLlegada: LocalTime,
        folioPEmergencia: Int?
    ): Instituciones {
        val rowsUpdated = dbQuery {
            Institucion.update({ Institucion.idInstitucion eq idInstitucion }) {
                it[Institucion.nombreInstitucion] = nombreInstitucion
                it[Institucion.tipoInstitucion] = tipoInstitucion
                it[Institucion.nombrePersonaCargo] = nombrePersonaCargo
                it[Institucion.horaLlegada] = horaLlegada
                it[Institucion.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Institución con id $idInstitucion no encontrada")
        }
        return Instituciones(nombreInstitucion, tipoInstitucion, nombrePersonaCargo, horaLlegada, folioPEmergencia)
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
    ): Cuerpos {
        dbQuery {
            Cuerpo.insert {

                it[Cuerpo.nombreCuerpo] = nombreCuerpo
                it[Cuerpo.provincia] = provincia
                it[Cuerpo.region] = region
                it[Cuerpo.comuna] = comuna
            }
        }
        return Cuerpos( nombreCuerpo, provincia, region, comuna)
    }

    override suspend fun deleteCuerpo(idCuerpo: Int): Boolean = dbQuery {
        Cuerpo.deleteWhere { Cuerpo.idCuerpo eq idCuerpo } > 0
    }

    override suspend fun updateCuerpo(
        idCuerpo: Int,
        nombreCuerpo: String,
        provincia: String,
        region: String,
        comuna: String
    ): Cuerpos {
        val rowsUpdated = dbQuery {
            Cuerpo.update({ Cuerpo.idCuerpo eq idCuerpo }) {
                it[Cuerpo.nombreCuerpo] = nombreCuerpo
                it[Cuerpo.provincia] = provincia
                it[Cuerpo.region] = region
                it[Cuerpo.comuna] = comuna
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Cuerpo con id $idCuerpo no encontrado")
        }
        return Cuerpos( nombreCuerpo, provincia, region, comuna)
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
    ): Companias {
        dbQuery {
            Compania.insert {
                it[Compania.nombreCia] = nombreCia
                it[Compania.direccionCia] = direccionCia
                it[Compania.especialidad] = especialidad
                it[Compania.idCuerpo] = idCuerpo
            }
        }
        return Companias(nombreCia, direccionCia, especialidad, idCuerpo)
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
        return Companias( nombreCia, direccionCia, especialidad, idCuerpo)
    }

    // Usuario implementation

    private fun resultToUsuario(row: ResultRow) = Usuarios(
        nombreUsuario = row[Usuario.nombreUsuario],
        contrasena = row[Usuario.contrasena],
        rol = row[Usuario.rol]
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
        rol: String
    ): Usuarios {
        dbQuery {
            Usuario.insert {
                it[Usuario.nombreUsuario] = nombreUsuario
                it[Usuario.contrasena] = contrasena
                it[Usuario.rol] = rol
            }
        }
        return Usuarios( nombreUsuario, contrasena, rol)
    }

    override suspend fun deleteUsuario(idUsuario: Int): Boolean = dbQuery {
        Usuario.deleteWhere { Usuario.idUsuario eq idUsuario } > 0
    }

    override suspend fun updateUsuario(
        idUsuario: Int,
        nombreUsuario: String,
        contrasena: String,
        rol: String
    ): Usuarios {
        val rowsUpdated = dbQuery {
            Usuario.update({ Usuario.idUsuario eq idUsuario }) {
                it[Usuario.nombreUsuario] = nombreUsuario
                it[Usuario.contrasena] = contrasena
                it[Usuario.rol] = rol
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Usuario con id $idUsuario no encontrado")
        }
        return Usuarios( nombreUsuario, contrasena, rol)
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

    ): Voluntarios {
        dbQuery {
            Voluntario.insert {
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
        return Voluntarios( nombreVol, fechaNac, direccion, numeroContacto, tipoSangre, enfermedades, alergias, fechaIngreso, claveRadial, cargoVoluntario,rutVoluntario, idCompania, idUsuario)
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
        return Voluntarios( nombreVol, fechaNac, direccion, numeroContacto, tipoSangre, enfermedades, alergias, fechaIngreso, claveRadial, cargoVoluntario,rutVoluntario, idCompania, idUsuario)
    }

    // inmueble implementation

    private fun resultToInmueble(row: ResultRow) = Inmuebles(
        direccion = row[Inmueble.direccion],
        tipoInmueble = row[Inmueble.tipoInmueble],
        estadoInmueble = row[Inmueble.estadoInmueble],
        folioPEmergencia = row[Inmueble.folioPEmergencia]
    )

    override suspend fun allInmuebles(): List<Inmuebles> = dbQuery {
        Inmueble.selectAll().map(::resultToInmueble)
    }

    override suspend fun getInmueble(idInmueble: Int): Inmuebles? = dbQuery {
        Inmueble.select { Inmueble.idInmueble eq idInmueble }
            .mapNotNull(::resultToInmueble)
            .singleOrNull()
    }

    override suspend fun createInmueble(

        direccion: String,
        tipoInmueble: String,
        estadoInmueble: String,
        folioPEmergencia: Int?
    ): Inmuebles {
        dbQuery {
            Inmueble.insert {

                it[Inmueble.direccion] = direccion
                it[Inmueble.tipoInmueble] = tipoInmueble
                it[Inmueble.estadoInmueble] = estadoInmueble
                it[Inmueble.folioPEmergencia] = folioPEmergencia
            }
        }
        return Inmuebles( direccion, tipoInmueble, estadoInmueble, folioPEmergencia)
    }

    override suspend fun deleteInmueble(idInmueble: Int): Boolean = dbQuery {
        Inmueble.deleteWhere { Inmueble.idInmueble eq idInmueble } > 0
    }

    override suspend fun updateInmueble(
        idInmueble: Int,
        direccion: String,
        tipoInmueble: String,
        estadoInmueble: String,
        folioPEmergencia: Int?
    ): Inmuebles {
        val rowsUpdated = dbQuery {
            Inmueble.update({ Inmueble.idInmueble eq idInmueble }) {
                it[Inmueble.direccion] = direccion
                it[Inmueble.tipoInmueble] = tipoInmueble
                it[Inmueble.estadoInmueble] = estadoInmueble
                it[Inmueble.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Inmueble con id $idInmueble no encontrado")
        return Inmuebles( direccion, tipoInmueble, estadoInmueble, folioPEmergencia)
    }

    // Victima implementation

    private fun resultToVictima(row: ResultRow) = Victimas(
        rutVictima = row[Victima.rutVictima],
        nombreVictima = row[Victima.nombreVictima],
        edadVictima = row[Victima.edadVictima],
        descripcion = row[Victima.descripcion],
        folioPEmergencia = row[Victima.folioPEmergencia]
    )

    override suspend fun allVictimas(): List<Victimas> = dbQuery {
        Victima.selectAll().map(::resultToVictima)
    }

    override suspend fun getVictima(idVictima: Int): Victimas? = dbQuery {
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
    ): Victimas {
        dbQuery {
            Victima.insert {
                it[Victima.rutVictima] = rutVictima
                it[Victima.nombreVictima] = nombreVictima
                it[Victima.edadVictima] = edadVictima
                it[Victima.descripcion] = descripcion
                it[Victima.folioPEmergencia] = folioPEmergencia
            }
        }
        return Victimas( rutVictima, nombreVictima, edadVictima, descripcion, folioPEmergencia)
    }

    override suspend fun deleteVictima(idVictima: Int): Boolean = dbQuery {
        Victima.deleteWhere { Victima.idVictima eq idVictima } > 0
    }

    override suspend fun updateVictima(
        idVictima: Int,
        rutVictima: String,
        nombreVictima: String,
        edadVictima: Int,
        descripcion: String,
        folioPEmergencia: Int
    ): Victimas {
        val rowsUpdated = dbQuery {
            Victima.update({ Victima.idVictima eq idVictima }) {
                it[Victima.rutVictima] = rutVictima
                it[Victima.nombreVictima] = nombreVictima
                it[Victima.edadVictima] = edadVictima
                it[Victima.descripcion] = descripcion
                it[Victima.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Víctima con id $idVictima no encontrada")
        return Victimas( rutVictima, nombreVictima, edadVictima, descripcion, folioPEmergencia)
    }

    // Vehiculo implementation

    private fun resultToVehiculo(row: ResultRow) = Vehiculos(
        patente = row[Vehiculo.patente],
        marca = row[Vehiculo.marca],
        modelo = row[Vehiculo.modelo],
        tipoVehiculo = row[Vehiculo.tipoVehiculo],
        folioPEmergencia = row[Vehiculo.folioPEmergencia]
    )

    override suspend fun allVehiculos(): List<Vehiculos> = dbQuery {
        Vehiculo.selectAll().map(::resultToVehiculo)
    }

    override suspend fun getVehiculo(idVehiculo: Int): Vehiculos? = dbQuery {
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
    ): Vehiculos {
        dbQuery {
            Vehiculo.insert {
                it[Vehiculo.patente] = patente
                it[Vehiculo.marca] = marca
                it[Vehiculo.modelo] = modelo
                it[Vehiculo.tipoVehiculo] = tipoVehiculo
                it[Vehiculo.folioPEmergencia] = folioPEmergencia
            }
        }
        return Vehiculos( patente, marca, modelo, tipoVehiculo, folioPEmergencia)
    }

    override suspend fun deleteVehiculo(idVehiculo: Int): Boolean = dbQuery {
        Vehiculo.deleteWhere { Vehiculo.idVehiculo eq idVehiculo } > 0
    }

    override suspend fun updateVehiculo(
        idVehiculo: Int,
        patente: String,
        marca: String,
        modelo: String,
        tipoVehiculo: String,
        folioPEmergencia: Int?
    ): Vehiculos {
        val rowsUpdated = dbQuery {
            Vehiculo.update({ Vehiculo.idVehiculo eq idVehiculo }) {
                it[Vehiculo.patente] = patente
                it[Vehiculo.marca] = marca
                it[Vehiculo.modelo] = modelo
                it[Vehiculo.tipoVehiculo] = tipoVehiculo
                it[Vehiculo.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Vehículo con id $idVehiculo no encontrado")
        return Vehiculos( patente, marca, modelo, tipoVehiculo, folioPEmergencia)
    }


    // Emergencia implementation

    private fun resultToEmergencia(row: ResultRow) = Emergencias(

        claveEmergencia = row[Emergencia.claveEmergencia],
        cuadrante = row[Emergencia.cuadrante],
        direccionEmergencia = row[Emergencia.direccionEmergencia]
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
        direccionEmergencia: String
    ): Emergencias {
        dbQuery {
            Emergencia.insert {
                it[Emergencia.claveEmergencia] = claveEmergencia
                it[Emergencia.cuadrante] = cuadrante
                it[Emergencia.direccionEmergencia] = direccionEmergencia
            }
        }
        return Emergencias( claveEmergencia, cuadrante, direccionEmergencia)
    }

    override suspend fun deleteEmergencia(idEmergencia: Int): Boolean = dbQuery {
        Emergencia.deleteWhere { Emergencia.idEmergencia eq idEmergencia } > 0
    }

    override suspend fun updateEmergencia(
        idEmergencia: Int,
        claveEmergencia: String,
        cuadrante: String,
        direccionEmergencia: String
    ): Emergencias {
        val rowsUpdated = dbQuery {
            Emergencia.update({ Emergencia.idEmergencia eq idEmergencia }) {
                it[Emergencia.claveEmergencia] = claveEmergencia
                it[Emergencia.cuadrante] = cuadrante
                it[Emergencia.direccionEmergencia] = direccionEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Emergencia con id $idEmergencia no encontrada")
        return Emergencias( claveEmergencia, cuadrante, direccionEmergencia)
    }

    // ParteEmergencia implementation

    private fun resultToParteEmergencia(row: ResultRow) = Partes_emergencia(
        tipoEmergencia = row[Parte_emergencia.tipoEmergencia],
        horaInicio = row[Parte_emergencia.horaInicio],
        horaFin = row[Parte_emergencia.horaFin],
        fechaEmergencia = row[Parte_emergencia.fechaEmergencia],
        preInforme = row[Parte_emergencia.preInforme],
        oficial = row[Parte_emergencia.oficial],
        idEmergencia = row[Parte_emergencia.idEmergencia],
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
        idEmergencia: Int,
        folioPAsistencia: Int?
    ): Partes_emergencia {
        dbQuery {
            Parte_emergencia.insert {
                it[Parte_emergencia.tipoEmergencia] = tipoEmergencia
                it[Parte_emergencia.horaInicio] = horaInicio
                it[Parte_emergencia.horaFin] = horaFin
                it[Parte_emergencia.fechaEmergencia] = fechaEmergencia
                it[Parte_emergencia.preInforme] = preInforme
                it[Parte_emergencia.oficial] = oficial
                it[Parte_emergencia.idEmergencia] = idEmergencia
                it[Parte_emergencia.folioPAsistencia] = folioPAsistencia
            }
        }
        return Partes_emergencia( tipoEmergencia, horaInicio, horaFin, fechaEmergencia, preInforme, oficial, idEmergencia, folioPAsistencia)
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
        idEmergencia: Int,
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
                it[Parte_emergencia.idEmergencia] = idEmergencia
                it[Parte_emergencia.folioPAsistencia] = folioPAsistencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergencia con folio $folioPEmergencia no encontrado")
        return Partes_emergencia( tipoEmergencia, horaInicio, horaFin, fechaEmergencia, preInforme, oficial, idEmergencia, folioPAsistencia)
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
        idMovil = row[Parte_asistencia.idMovil]
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
        idMovil: Int?
    ): Partes_asistencia {
        dbQuery {
            Parte_asistencia.insert {
                it[Parte_asistencia.tipoLlamado] = tipoLlamado
                it[Parte_asistencia.aCargoDelCuerpo] = aCargoDelCuerpo
                it[Parte_asistencia.aCargoDeLaCompania] = aCargoDeLaCompania
                it[Parte_asistencia.fechaAsistencia] = fechaAsistencia
                it[Parte_asistencia.horaInicio] = horaInicio
                it[Parte_asistencia.horaFin] = horaFin
                it[Parte_asistencia.direccionAsistencia] = direccionAsistencia
                it[Parte_asistencia.totalAsistencia] = totalAsistencia
                it[Parte_asistencia.observaciones] = observaciones
                it[Parte_asistencia.idMovil] = idMovil
            }
        }
        return Partes_asistencia( tipoLlamado, aCargoDelCuerpo, aCargoDeLaCompania, fechaAsistencia, horaInicio, horaFin, direccionAsistencia, totalAsistencia, observaciones, idMovil)
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
        idMovil: Int
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
                it[Parte_asistencia.idMovil] = idMovil
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistencia con folio $folioPAsistencia no encontrado")
        return Partes_asistencia( tipoLlamado, aCargoDelCuerpo, aCargoDeLaCompania, fechaAsistencia, horaInicio, horaFin, direccionAsistencia, totalAsistencia, observaciones, idMovil)
    }

                // MaterialP implementation

    private fun resultToMaterialP(row: ResultRow) = MaterialesP(
        llamarEmpresaQuimica = row[MaterialP.llamarEmpresaQuimica],
        clasificacion = row[MaterialP.clasificacion],
        nombreMaP = row[MaterialP.nombreMaP],
        folioPEmergencia = row[MaterialP.folioPEmergencia]
    )

    override suspend fun allMaterialP(): List<MaterialesP> = dbQuery {
        MaterialP.selectAll().map(::resultToMaterialP)
    }

    override suspend fun getMaterialP(idMaterialP: Int): MaterialesP? = dbQuery {
        MaterialP.select { MaterialP.idMaterialP eq idMaterialP }
            .mapNotNull(::resultToMaterialP)
            .singleOrNull()
    }

    override suspend fun createMaterialP(
        llamarEmpresaQuimica: Boolean,
        clasificacion: String,
        nombreMaP: String,
        folioPEmergencia: Int?
    ): MaterialesP {
        dbQuery {
            MaterialP.insert {
                it[MaterialP.llamarEmpresaQuimica] = llamarEmpresaQuimica
                it[MaterialP.clasificacion] = clasificacion
                it[MaterialP.nombreMaP] = nombreMaP
                it[MaterialP.folioPEmergencia] = folioPEmergencia
            }
        }
        return MaterialesP( llamarEmpresaQuimica, clasificacion, nombreMaP, folioPEmergencia)
    }

    override suspend fun deleteMaterialP(idMaterialP: Int): Boolean = dbQuery {
        MaterialP.deleteWhere { MaterialP.idMaterialP eq idMaterialP } > 0
    }

    override suspend fun updateMaterialP(
        idMaterialP: Int,
        llamarEmpresaQuimica: Boolean,
        clasificacion: String,
        nombreMaP: String,
        folioPEmergencia: Int?
    ): MaterialesP {
        val rowsUpdated = dbQuery {
            MaterialP.update({ MaterialP.idMaterialP eq idMaterialP }) {
                it[MaterialP.llamarEmpresaQuimica] = llamarEmpresaQuimica
                it[MaterialP.clasificacion] = clasificacion
                it[MaterialP.nombreMaP] = nombreMaP
                it[MaterialP.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("MaterialP con id $idMaterialP no encontrado")
        return MaterialesP( llamarEmpresaQuimica, clasificacion, nombreMaP, folioPEmergencia)
    }


    // Movil implementation

    private fun resultToMovil(row: ResultRow) = Moviles(
        nomenclatura = row[Movil.nomenclatura],
        especialidad = row[Movil.especialidad],
        folioPEmergencia = row[Movil.folioPEmergencia]
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
        especialidad: String,
        folioPEmergencia: Int?
    ): Moviles {
        dbQuery {
            Movil.insert {
                it[Movil.nomenclatura] = nomenclatura
                it[Movil.especialidad] = especialidad
                it[Movil.folioPEmergencia] = folioPEmergencia
            }
        }
        return Moviles( nomenclatura, especialidad, folioPEmergencia)
    }

    override suspend fun deleteMovil(idMovil: Int): Boolean = dbQuery {
        Movil.deleteWhere { Movil.idMovil eq idMovil } > 0
    }

    override suspend fun updateMovil(
        idMovil: Int,
        nomenclatura: String,
        especialidad: String,
        folioPEmergencia: Int?
    ): Moviles {
        val rowsUpdated = dbQuery {
            Movil.update({ Movil.idMovil eq idMovil }) {
                it[Movil.nomenclatura] = nomenclatura
                it[Movil.especialidad] = especialidad
                it[Movil.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Movil con id $idMovil no encontrado")
        return Moviles( nomenclatura, especialidad, folioPEmergencia)
    }

    // ParteEmergenciaVoluntario implementation
    private fun resultToParteEmergenciaVoluntario(row: ResultRow) = PartesEmergenciaVoluntarios(
        folioPEmergencia = row[ParteEmergenciaVoluntario.folioPEmergencia],
        idVoluntario = row[ParteEmergenciaVoluntario.idVoluntario]
    )
    override suspend fun allParteEmergenciaVoluntarios(): List<PartesEmergenciaVoluntarios> = dbQuery {
        ParteEmergenciaVoluntario.selectAll().map(::resultToParteEmergenciaVoluntario)
    }

    override suspend fun getParteEmergenciaVoluntario(idParteVoluntario: Int): PartesEmergenciaVoluntarios? = dbQuery {
        ParteEmergenciaVoluntario.select { ParteEmergenciaVoluntario.idParteVoluntario eq idParteVoluntario }
            .mapNotNull(::resultToParteEmergenciaVoluntario)
            .singleOrNull()
    }

    override suspend fun createParteEmergenciaVoluntario(
        folioPEmergencia: Int,
        idVoluntario: Int
    ): PartesEmergenciaVoluntarios {
        dbQuery {
            ParteEmergenciaVoluntario.insert {
                it[ParteEmergenciaVoluntario.folioPEmergencia] = folioPEmergencia
                it[ParteEmergenciaVoluntario.idVoluntario] = idVoluntario
            }
        }
        return PartesEmergenciaVoluntarios( folioPEmergencia, idVoluntario)
    }

    override suspend fun deleteParteEmergenciaVoluntario(idParteVoluntario: Int): Boolean = dbQuery {
        ParteEmergenciaVoluntario.deleteWhere { ParteEmergenciaVoluntario.idParteVoluntario eq idParteVoluntario } > 0

    }

    override suspend fun updateParteEmergenciaVoluntario(
        idParteVoluntario: Int,
        folioPEmergencia: Int,
        idVoluntario: Int
    ): PartesEmergenciaVoluntarios {
        val rowsUpdated = dbQuery {
            ParteEmergenciaVoluntario.update({ ParteEmergenciaVoluntario.idParteVoluntario eq idParteVoluntario }) {
                it[ParteEmergenciaVoluntario.folioPEmergencia] = folioPEmergencia
                it[ParteEmergenciaVoluntario.idVoluntario] = idVoluntario
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergenciaVoluntario con id $idParteVoluntario no encontrado")
        return PartesEmergenciaVoluntarios( folioPEmergencia, idVoluntario)
    }

    // ParteAsistenciaVoluntario implementation

    private fun resultToParteAsistenciaVoluntario(row: ResultRow) = PartesAsistenciaVoluntarios(
        folioPAsistencia = row[ParteAsistenciaVoluntario.folioPAsistencia],
        idVoluntario = row[ParteAsistenciaVoluntario.idVoluntario]
    )
    override suspend fun allParteAsistenciaVoluntarios(): List<PartesAsistenciaVoluntarios> = dbQuery {
        ParteAsistenciaVoluntario.selectAll().map(::resultToParteAsistenciaVoluntario)
    }

    override suspend fun getParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): PartesAsistenciaVoluntarios? = dbQuery {
        ParteAsistenciaVoluntario.select { ParteAsistenciaVoluntario.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario }
            .mapNotNull(::resultToParteAsistenciaVoluntario)
            .singleOrNull()
    }

    override suspend fun createParteAsistenciaVoluntario(
        folioPAsistencia: Int,
        idVoluntario: Int
    ): PartesAsistenciaVoluntarios {
        dbQuery {
            ParteAsistenciaVoluntario.insert {
                it[ParteAsistenciaVoluntario.folioPAsistencia] = folioPAsistencia
                it[ParteAsistenciaVoluntario.idVoluntario] = idVoluntario
            }
        }
        return PartesAsistenciaVoluntarios( folioPAsistencia, idVoluntario)
    }

    override suspend fun deleteParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): Boolean = dbQuery {
        ParteAsistenciaVoluntario.deleteWhere { ParteAsistenciaVoluntario.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario } > 0

    }

    override suspend fun updateParteAsistenciaVoluntario(
        idParteAsistenciaVoluntario: Int,
        folioPAsistencia: Int,
        idVoluntario: Int
    ): PartesAsistenciaVoluntarios {
        val rowsUpdated = dbQuery {
            ParteAsistenciaVoluntario.update({ ParteAsistenciaVoluntario.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario }) {
                it[ParteAsistenciaVoluntario.folioPAsistencia] = folioPAsistencia
                it[ParteAsistenciaVoluntario.idVoluntario] = idVoluntario
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistenciaVoluntario con id $idParteAsistenciaVoluntario no encontrado")
        return PartesAsistenciaVoluntarios( folioPAsistencia, idVoluntario)
    }

}


