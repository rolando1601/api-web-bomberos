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

    private fun resultToInstitucion(row: ResultRow) = Institucion(
        idInstitucion = row[Instituciones.idInstitucion],
        nombreInstitucion = row[Instituciones.nombreInstitucion],
        tipoInstitucion = row[Instituciones.tipoInstitucion],
        nombrePersonaCargo = row[Instituciones.nombrePersonaCargo],
        horaLlegada = row[Instituciones.horaLlegada],
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
        folioPEmergencia: Int?
    ): Institucion {
        dbQuery {
            Instituciones.insert {
                it[Instituciones.idInstitucion] = idInstitucion
                it[Instituciones.nombreInstitucion] = nombreInstitucion
                it[Instituciones.tipoInstitucion] = tipoInstitucion
                it[Instituciones.nombrePersonaCargo] = nombrePersonaCargo
                it[Instituciones.horaLlegada] = horaLlegada
                it[Instituciones.folioPEmergencia] = folioPEmergencia
            }
        }
        return Institucion(idInstitucion, nombreInstitucion, tipoInstitucion, nombrePersonaCargo, horaLlegada, folioPEmergencia)
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
        folioPEmergencia: Int?
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
            throw IllegalArgumentException("Institución con id $idInstitucion no encontrada")
        }
        return Institucion(idInstitucion, nombreInstitucion, tipoInstitucion, nombrePersonaCargo, horaLlegada, folioPEmergencia)
    }


    // Cuerpo implementation

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
    ): Cuerpo {
        dbQuery {
            Cuerpos.insert {
                it[Cuerpos.idCuerpo] = idCuerpo
                it[Cuerpos.nombreCuerpo] = nombreCuerpo
                it[Cuerpos.provincia] = provincia
                it[Cuerpos.region] = region
                it[Cuerpos.comuna] = comuna
            }
        }
        return Cuerpo(idCuerpo, nombreCuerpo, provincia, region, comuna)
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
    ): Cuerpo {
        val rowsUpdated = dbQuery {
            Cuerpos.update({ Cuerpos.idCuerpo eq idCuerpo }) {
                it[Cuerpos.nombreCuerpo] = nombreCuerpo
                it[Cuerpos.provincia] = provincia
                it[Cuerpos.region] = region
                it[Cuerpos.comuna] = comuna
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Cuerpo con id $idCuerpo no encontrado")
        }
        return Cuerpo(idCuerpo, nombreCuerpo, provincia, region, comuna)
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
    ): Compania {
        dbQuery {
            Companias.insert {
                it[Companias.idCompania] = idCompania
                it[Companias.nombreCia] = nombreCia
                it[Companias.direccionCia] = direccionCia
                it[Companias.especialidad] = especialidad
                it[Companias.idCuerpo] = idCuerpo
            }
        }
        return Compania(idCompania, nombreCia, direccionCia, especialidad, idCuerpo)
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
    ): Compania {
        val rowsUpdated = dbQuery {
            Companias.update({ Companias.idCompania eq idCompania }) {
                it[Companias.nombreCia] = nombreCia
                it[Companias.direccionCia] = direccionCia
                it[Companias.especialidad] = especialidad
                it[Companias.idCuerpo] = idCuerpo
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Compañía con id $idCompania no encontrada")
        }
        return Compania(idCompania, nombreCia, direccionCia, especialidad, idCuerpo)
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
    ): Usuario {
        dbQuery {
            Usuarios.insert {
                it[Usuarios.idUsuario] = idUsuario
                it[Usuarios.nombreUsuario] = nombreUsuario
                it[Usuarios.contrasena] = contrasena
                it[Usuarios.rol] = rol
            }
        }
        return Usuario(idUsuario, nombreUsuario, contrasena, rol)
    }

    override suspend fun deleteUsuario(idUsuario: Int): Boolean = dbQuery {
        Usuarios.deleteWhere { Usuarios.idUsuario eq idUsuario } > 0
    }

    override suspend fun updateUsuario(
        idUsuario: Int,
        nombreUsuario: String,
        contrasena: String,
        rol: String
    ): Usuario {
        val rowsUpdated = dbQuery {
            Usuarios.update({ Usuarios.idUsuario eq idUsuario }) {
                it[Usuarios.nombreUsuario] = nombreUsuario
                it[Usuarios.contrasena] = contrasena
                it[Usuarios.rol] = rol
            }
        }
        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Usuario con id $idUsuario no encontrado")
        }
        return Usuario(idUsuario, nombreUsuario, contrasena, rol)
    }

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
        rutVoluntario = row[Voluntarios.rutVoluntario],
        idCompania = row[Voluntarios.idCompania],
        idUsuario = row[Voluntarios.idUsuario]
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
        rutVoluntario: String,
        idCompania: Int,
        idUsuario: Int?

    ): Voluntario {
        dbQuery {
            Voluntarios.insert {
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
                it[Voluntarios.rutVoluntario] = rutVoluntario
                it[Voluntarios.idCompania] = idCompania
                it[Voluntarios.idUsuario] = idUsuario
            }
        }
        return Voluntario(idVoluntario, nombreVol, fechaNac, direccion, numeroContacto, tipoSangre, enfermedades, alergias, fechaIngreso, claveRadial, cargoVoluntario,rutVoluntario, idCompania, idUsuario)
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
        rutVoluntario: String,
        idCompania: Int,
        idUsuario: Int?

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
                it[Voluntarios.rutVoluntario] = rutVoluntario
                it[Voluntarios.idCompania] = idCompania
                it[Voluntarios.idUsuario] = idUsuario
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Voluntario con id $idVoluntario no encontrado")
        return Voluntario(idVoluntario, nombreVol, fechaNac, direccion, numeroContacto, tipoSangre, enfermedades, alergias, fechaIngreso, claveRadial, cargoVoluntario,rutVoluntario, idCompania, idUsuario)
    }

    // inmueble implementation

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
        folioPEmergencia: Int?
    ): Inmueble {
        dbQuery {
            Inmuebles.insert {
                it[Inmuebles.idInmueble] = idInmueble
                it[Inmuebles.direccion] = direccion
                it[Inmuebles.tipoInmueble] = tipoInmueble
                it[Inmuebles.estadoInmueble] = estadoInmueble
                it[Inmuebles.folioPEmergencia] = folioPEmergencia
            }
        }
        return Inmueble(idInmueble, direccion, tipoInmueble, estadoInmueble, folioPEmergencia)
    }

    override suspend fun deleteInmueble(idInmueble: Int): Boolean = dbQuery {
        Inmuebles.deleteWhere { Inmuebles.idInmueble eq idInmueble } > 0
    }

    override suspend fun updateInmueble(
        idInmueble: Int,
        direccion: String,
        tipoInmueble: String,
        estadoInmueble: String,
        folioPEmergencia: Int?
    ): Inmueble {
        val rowsUpdated = dbQuery {
            Inmuebles.update({ Inmuebles.idInmueble eq idInmueble }) {
                it[Inmuebles.direccion] = direccion
                it[Inmuebles.tipoInmueble] = tipoInmueble
                it[Inmuebles.estadoInmueble] = estadoInmueble
                it[Inmuebles.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Inmueble con id $idInmueble no encontrado")
        return Inmueble(idInmueble, direccion, tipoInmueble, estadoInmueble, folioPEmergencia)
    }

    // Victima implementation

    private fun resultToVictima(row: ResultRow) = Victima(
        idVictima = row[Victimas.idVictima],
        rutVictima = row[Victimas.rutVictima],
        nombreVictima = row[Victimas.nombreVictima],
        edadVictima = row[Victimas.edadVictima],
        descripcion = row[Victimas.descripcion],
        folioPEmergencia = row[Victimas.folioPEmergencia]
    )

    override suspend fun allVictimas(): List<Victima> = dbQuery {
        Victimas.selectAll().map(::resultToVictima)
    }

    override suspend fun getVictima(idVictima: Int): Victima? = dbQuery {
        Victimas.select { Victimas.idVictima eq idVictima }
            .mapNotNull(::resultToVictima)
            .singleOrNull()
    }

    override suspend fun createVictima(
        idVictima: Int,
        rutVictima: String,
        nombreVictima: String,
        edadVictima: Int,
        descripcion: String,
        folioPEmergencia: Int
    ): Victima {
        dbQuery {
            Victimas.insert {
                it[Victimas.idVictima] = idVictima
                it[Victimas.rutVictima] = rutVictima
                it[Victimas.nombreVictima] = nombreVictima
                it[Victimas.edadVictima] = edadVictima
                it[Victimas.descripcion] = descripcion
                it[Victimas.folioPEmergencia] = folioPEmergencia
            }
        }
        return Victima(idVictima, rutVictima, nombreVictima, edadVictima, descripcion, folioPEmergencia)
    }

    override suspend fun deleteVictima(idVictima: Int): Boolean = dbQuery {
        Victimas.deleteWhere { Victimas.idVictima eq idVictima } > 0
    }

    override suspend fun updateVictima(
        idVictima: Int,
        rutVictima: String,
        nombreVictima: String,
        edadVictima: Int,
        descripcion: String,
        folioPEmergencia: Int
    ): Victima {
        val rowsUpdated = dbQuery {
            Victimas.update({ Victimas.idVictima eq idVictima }) {
                it[Victimas.rutVictima] = rutVictima
                it[Victimas.nombreVictima] = nombreVictima
                it[Victimas.edadVictima] = edadVictima
                it[Victimas.descripcion] = descripcion
                it[Victimas.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Víctima con id $idVictima no encontrada")
        return Victima(idVictima, rutVictima, nombreVictima, edadVictima, descripcion, folioPEmergencia)
    }

    // Vehiculo implementation

    private fun resultToVehiculo(row: ResultRow) = Vehiculo(
        idVehiculo = row[Vehiculos.idVehiculo],
        patente = row[Vehiculos.patente],
        marca = row[Vehiculos.marca],
        modelo = row[Vehiculos.modelo],
        tipoVehiculo = row[Vehiculos.tipoVehiculo],
        folioPEmergencia = row[Vehiculos.folioPEmergencia]
    )

    override suspend fun allVehiculos(): List<Vehiculo> = dbQuery {
        Vehiculos.selectAll().map(::resultToVehiculo)
    }

    override suspend fun getVehiculo(idVehiculo: Int): Vehiculo? = dbQuery {
        Vehiculos.select { Vehiculos.idVehiculo eq idVehiculo }
            .mapNotNull(::resultToVehiculo)
            .singleOrNull()
    }

    override suspend fun createVehiculo(
        idVehiculo: Int,
        patente: String,
        marca: String,
        modelo: String,
        tipoVehiculo: String,
        folioPEmergencia: Int
    ): Vehiculo {
        dbQuery {
            Vehiculos.insert {
                it[Vehiculos.idVehiculo] = idVehiculo
                it[Vehiculos.patente] = patente
                it[Vehiculos.marca] = marca
                it[Vehiculos.modelo] = modelo
                it[Vehiculos.tipoVehiculo] = tipoVehiculo
                it[Vehiculos.folioPEmergencia] = folioPEmergencia
            }
        }
        return Vehiculo(idVehiculo, patente, marca, modelo, tipoVehiculo, folioPEmergencia)
    }

    override suspend fun deleteVehiculo(idVehiculo: Int): Boolean = dbQuery {
        Vehiculos.deleteWhere { Vehiculos.idVehiculo eq idVehiculo } > 0
    }

    override suspend fun updateVehiculo(
        idVehiculo: Int,
        patente: String,
        marca: String,
        modelo: String,
        tipoVehiculo: String,
        folioPEmergencia: Int
    ): Vehiculo {
        val rowsUpdated = dbQuery {
            Vehiculos.update({ Vehiculos.idVehiculo eq idVehiculo }) {
                it[Vehiculos.patente] = patente
                it[Vehiculos.marca] = marca
                it[Vehiculos.modelo] = modelo
                it[Vehiculos.tipoVehiculo] = tipoVehiculo
                it[Vehiculos.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Vehículo con id $idVehiculo no encontrado")
        return Vehiculo(idVehiculo, patente, marca, modelo, tipoVehiculo, folioPEmergencia)
    }


    // Emergencia implementation

    private fun resultToEmergencia(row: ResultRow) = Emergencia(
        idEmergencia = row[Emergencias.idEmergencia],
        claveEmergencia = row[Emergencias.claveEmergencia],
        cuadrante = row[Emergencias.cuadrante],
        direccionEmergencia = row[Emergencias.direccionEmergencia]
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
        direccionEmergencia: String
    ): Emergencia {
        dbQuery {
            Emergencias.insert {
                it[Emergencias.idEmergencia] = idEmergencia
                it[Emergencias.claveEmergencia] = claveEmergencia
                it[Emergencias.cuadrante] = cuadrante
                it[Emergencias.direccionEmergencia] = direccionEmergencia
            }
        }
        return Emergencia(idEmergencia, claveEmergencia, cuadrante, direccionEmergencia)
    }

    override suspend fun deleteEmergencia(idEmergencia: Int): Boolean = dbQuery {
        Emergencias.deleteWhere { Emergencias.idEmergencia eq idEmergencia } > 0
    }

    override suspend fun updateEmergencia(
        idEmergencia: Int,
        claveEmergencia: String,
        cuadrante: String,
        direccionEmergencia: String
    ): Emergencia {
        val rowsUpdated = dbQuery {
            Emergencias.update({ Emergencias.idEmergencia eq idEmergencia }) {
                it[Emergencias.claveEmergencia] = claveEmergencia
                it[Emergencias.cuadrante] = cuadrante
                it[Emergencias.direccionEmergencia] = direccionEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Emergencia con id $idEmergencia no encontrada")
        return Emergencia(idEmergencia, claveEmergencia, cuadrante, direccionEmergencia)
    }

    // ParteEmergencia implementation

    private fun resultToParteEmergencia(row: ResultRow) = Parte_emergencia(
        folioPEmergencia = row[Partes_emergencia.folioPEmergencia],
        tipoEmergencia = row[Partes_emergencia.tipoEmergencia],
        horaInicio = row[Partes_emergencia.horaInicio],
        horaFin = row[Partes_emergencia.horaFin],
        fechaEmergencia = row[Partes_emergencia.fechaEmergencia],
        preInforme = row[Partes_emergencia.preInforme],
        oficial = row[Partes_emergencia.oficial],
        idEmergencia = row[Partes_emergencia.idEmergencia],
        folioPAsistencia = row[Partes_emergencia.folioPAsistencia]
    )

    override suspend fun allParteEmergencias(): List<Parte_emergencia> = dbQuery {
        Partes_emergencia.selectAll().map(::resultToParteEmergencia)
    }

    override suspend fun getParteEmergencia(folioPEmergencia: Int): Parte_emergencia? = dbQuery {
        Partes_emergencia.select { Partes_emergencia.folioPEmergencia eq folioPEmergencia }
            .mapNotNull(::resultToParteEmergencia)
            .singleOrNull()
    }

    override suspend fun createParteEmergencia(
        folioPEmergencia: Int,
        tipoEmergencia: String,
        horaInicio: LocalTime,
        horaFin: LocalTime,
        fechaEmergencia: LocalDate,
        preInforme: String,
        oficial: String,
        idEmergencia: Int,
        folioPAsistencia: Int?
    ): Parte_emergencia {
        dbQuery {
            Partes_emergencia.insert {
                it[Partes_emergencia.folioPEmergencia] = folioPEmergencia
                it[Partes_emergencia.tipoEmergencia] = tipoEmergencia
                it[Partes_emergencia.horaInicio] = horaInicio
                it[Partes_emergencia.horaFin] = horaFin
                it[Partes_emergencia.fechaEmergencia] = fechaEmergencia
                it[Partes_emergencia.preInforme] = preInforme
                it[Partes_emergencia.oficial] = oficial
                it[Partes_emergencia.idEmergencia] = idEmergencia
                it[Partes_emergencia.folioPAsistencia] = folioPAsistencia
            }
        }
        return Parte_emergencia(folioPEmergencia, tipoEmergencia, horaInicio, horaFin, fechaEmergencia, preInforme, oficial, idEmergencia, folioPAsistencia)
    }

    override suspend fun deleteParteEmergencia(folioPEmergencia: Int): Boolean = dbQuery {
        Partes_emergencia.deleteWhere { Partes_emergencia.folioPEmergencia eq folioPEmergencia } > 0
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
    ): Parte_emergencia {
        val rowsUpdated = dbQuery {
            Partes_emergencia.update({ Partes_emergencia.folioPEmergencia eq folioPEmergencia }) {
                it[Partes_emergencia.tipoEmergencia] = tipoEmergencia
                it[Partes_emergencia.horaInicio] = horaInicio
                it[Partes_emergencia.horaFin] = horaFin
                it[Partes_emergencia.fechaEmergencia] = fechaEmergencia
                it[Partes_emergencia.preInforme] = preInforme
                it[Partes_emergencia.oficial] = oficial
                it[Partes_emergencia.idEmergencia] = idEmergencia
                it[Partes_emergencia.folioPAsistencia] = folioPAsistencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergencia con folio $folioPEmergencia no encontrado")
        return Parte_emergencia(folioPEmergencia, tipoEmergencia, horaInicio, horaFin, fechaEmergencia, preInforme, oficial, idEmergencia, folioPAsistencia)
    }

    //parte_asistencia implementation

    private fun resultToParteAsistencia(row: ResultRow) = Parte_asistencia(
        folioPAsistencia = row[Partes_asistencia.folioPAsistencia],
        tipoLlamado = row[Partes_asistencia.tipoLlamado],
        aCargoDelCuerpo = row[Partes_asistencia.aCargoDelCuerpo],
        aCargoDeLaCompania = row[Partes_asistencia.aCargoDeLaCompania],
        fechaAsistencia = row[Partes_asistencia.fechaAsistencia],
        horaInicio = row[Partes_asistencia.horaInicio],
        horaFin = row[Partes_asistencia.horaFin],
        direccionAsistencia = row[Partes_asistencia.direccionAsistencia],
        totalAsistencia = row[Partes_asistencia.totalAsistencia],
        observaciones = row[Partes_asistencia.observaciones],
        idMovil = row[Partes_asistencia.idMovil]
    )

    override suspend fun allParteAsistencias(): List<Parte_asistencia> = dbQuery {
        Partes_asistencia.selectAll().map(::resultToParteAsistencia)
    }

    override suspend fun getParteAsistencia(folioPAsistencia: Int): Parte_asistencia? = dbQuery {
        Partes_asistencia.select { Partes_asistencia.folioPAsistencia eq folioPAsistencia }
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
        direccionAsistencia: String,
        totalAsistencia: Int,
        observaciones: String,
        idMovil: Int
    ): Parte_asistencia {
        dbQuery {
            Partes_asistencia.insert {
                it[Partes_asistencia.folioPAsistencia] = folioPAsistencia
                it[Partes_asistencia.tipoLlamado] = tipoLlamado
                it[Partes_asistencia.aCargoDelCuerpo] = aCargoDelCuerpo
                it[Partes_asistencia.aCargoDeLaCompania] = aCargoDeLaCompania
                it[Partes_asistencia.fechaAsistencia] = fechaAsistencia
                it[Partes_asistencia.horaInicio] = horaInicio
                it[Partes_asistencia.horaFin] = horaFin
                it[Partes_asistencia.direccionAsistencia] = direccionAsistencia
                it[Partes_asistencia.totalAsistencia] = totalAsistencia
                it[Partes_asistencia.observaciones] = observaciones
                it[Partes_asistencia.idMovil] = idMovil
            }
        }
        return Parte_asistencia(folioPAsistencia, tipoLlamado, aCargoDelCuerpo, aCargoDeLaCompania, fechaAsistencia, horaInicio, horaFin, direccionAsistencia, totalAsistencia, observaciones, idMovil)
    }

    override suspend fun deleteParteAsistencia(folioPAsistencia: Int): Boolean = dbQuery {
        Partes_asistencia.deleteWhere { Partes_asistencia.folioPAsistencia eq folioPAsistencia } > 0
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
    ): Parte_asistencia {
        val rowsUpdated = dbQuery {
            Partes_asistencia.update({ Partes_asistencia.folioPAsistencia eq folioPAsistencia }) {
                it[Partes_asistencia.tipoLlamado] = tipoLlamado
                it[Partes_asistencia.aCargoDelCuerpo] = aCargoDelCuerpo
                it[Partes_asistencia.aCargoDeLaCompania] = aCargoDeLaCompania
                it[Partes_asistencia.fechaAsistencia] = fechaAsistencia
                it[Partes_asistencia.horaInicio] = horaInicio
                it[Partes_asistencia.horaFin] = horaFin
                it[Partes_asistencia.direccionAsistencia] = direccionAsistencia
                it[Partes_asistencia.totalAsistencia] = totalAsistencia
                it[Partes_asistencia.observaciones] = observaciones
                it[Partes_asistencia.idMovil] = idMovil
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistencia con folio $folioPAsistencia no encontrado")
        return Parte_asistencia(folioPAsistencia, tipoLlamado, aCargoDelCuerpo, aCargoDeLaCompania, fechaAsistencia, horaInicio, horaFin, direccionAsistencia, totalAsistencia, observaciones, idMovil)
    }

                // MaterialP implementation

    private fun resultToMaterialP(row: ResultRow) = MaterialP(
        idMaterialP = row[MaterialesP.idMaterialP],
        llamarEmpresaQuimica = row[MaterialesP.llamarEmpresaQuimica],
        clasificacion = row[MaterialesP.clasificacion],
        nombreMaP = row[MaterialesP.nombreMaP],
        folioPEmergencia = row[MaterialesP.folioPEmergencia]
    )

    override suspend fun allMaterialP(): List<MaterialP> = dbQuery {
        MaterialesP.selectAll().map(::resultToMaterialP)
    }

    override suspend fun getMaterialP(idMaterialP: Int): MaterialP? = dbQuery {
        MaterialesP.select { MaterialesP.idMaterialP eq idMaterialP }
            .mapNotNull(::resultToMaterialP)
            .singleOrNull()
    }

    override suspend fun createMaterialP(
        idMaterialP: Int,
        llamarEmpresaQuimica: Boolean,
        clasificacion: String,
        nombreMaP: String,
        folioPEmergencia: Int?
    ): MaterialP {
        dbQuery {
            MaterialesP.insert {
                it[MaterialesP.idMaterialP] = idMaterialP
                it[MaterialesP.llamarEmpresaQuimica] = llamarEmpresaQuimica
                it[MaterialesP.clasificacion] = clasificacion
                it[MaterialesP.nombreMaP] = nombreMaP
                it[MaterialesP.folioPEmergencia] = folioPEmergencia
            }
        }
        return MaterialP(idMaterialP, llamarEmpresaQuimica, clasificacion, nombreMaP, folioPEmergencia)
    }

    override suspend fun deleteMaterialP(idMaterialP: Int): Boolean = dbQuery {
        MaterialesP.deleteWhere { MaterialesP.idMaterialP eq idMaterialP } > 0
    }

    override suspend fun updateMaterialP(
        idMaterialP: Int,
        llamarEmpresaQuimica: Boolean,
        clasificacion: String,
        nombreMaP: String,
        folioPEmergencia: Int?
    ): MaterialP {
        val rowsUpdated = dbQuery {
            MaterialesP.update({ MaterialesP.idMaterialP eq idMaterialP }) {
                it[MaterialesP.llamarEmpresaQuimica] = llamarEmpresaQuimica
                it[MaterialesP.clasificacion] = clasificacion
                it[MaterialesP.nombreMaP] = nombreMaP
                it[MaterialesP.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("MaterialP con id $idMaterialP no encontrado")
        return MaterialP(idMaterialP, llamarEmpresaQuimica, clasificacion, nombreMaP, folioPEmergencia)
    }


    // Movil implementation

    private fun resultToMovil(row: ResultRow) = Movil(
        idMovil = row[Moviles.idMovil],
        nomenclatura = row[Moviles.nomenclatura],
        especialidad = row[Moviles.especialidad],
        folioPEmergencia = row[Moviles.folioPEmergencia]
    )

    override suspend fun allMoviles(): List<Movil> = dbQuery {
        Moviles.selectAll().map(::resultToMovil)
    }

    override suspend fun getMovil(idMovil: Int): Movil? = dbQuery {
        Moviles.select { Moviles.idMovil eq idMovil }
            .mapNotNull(::resultToMovil)
            .singleOrNull()
    }

    override suspend fun createMovil(
        idMovil: Int,
        nomenclatura: String,
        especialidad: String,
        folioPEmergencia: Int?
    ): Movil {
        dbQuery {
            Moviles.insert {
                it[Moviles.idMovil] = idMovil
                it[Moviles.nomenclatura] = nomenclatura
                it[Moviles.especialidad] = especialidad
                it[Moviles.folioPEmergencia] = folioPEmergencia
            }
        }
        return Movil(idMovil, nomenclatura, especialidad, folioPEmergencia)
    }

    override suspend fun deleteMovil(idMovil: Int): Boolean = dbQuery {
        Moviles.deleteWhere { Moviles.idMovil eq idMovil } > 0
    }

    override suspend fun updateMovil(
        idMovil: Int,
        nomenclatura: String,
        especialidad: String,
        folioPEmergencia: Int?
    ): Movil {
        val rowsUpdated = dbQuery {
            Moviles.update({ Moviles.idMovil eq idMovil }) {
                it[Moviles.nomenclatura] = nomenclatura
                it[Moviles.especialidad] = especialidad
                it[Moviles.folioPEmergencia] = folioPEmergencia
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("Movil con id $idMovil no encontrado")
        return Movil(idMovil, nomenclatura, especialidad, folioPEmergencia)
    }

    // ParteEmergenciaVoluntario implementation
    private fun resultToParteEmergenciaVoluntario(row: ResultRow) = ParteEmergenciaVoluntario(
        idParteVoluntario = row[PartesEmergenciaVoluntarios.idParteVoluntario],
        folioPEmergencia = row[PartesEmergenciaVoluntarios.folioPEmergencia],
        idVoluntario = row[PartesEmergenciaVoluntarios.idVoluntario]
    )
    override suspend fun allParteEmergenciaVoluntarios(): List<ParteEmergenciaVoluntario> = dbQuery {
        PartesEmergenciaVoluntarios.selectAll().map(::resultToParteEmergenciaVoluntario)
    }

    override suspend fun getParteEmergenciaVoluntario(idParteVoluntario: Int): ParteEmergenciaVoluntario? = dbQuery {
        PartesEmergenciaVoluntarios.select { PartesEmergenciaVoluntarios.idParteVoluntario eq idParteVoluntario }
            .mapNotNull(::resultToParteEmergenciaVoluntario)
            .singleOrNull()
    }

    override suspend fun createParteEmergenciaVoluntario(
        idParteVoluntario: Int,
        folioPEmergencia: Int,
        idVoluntario: Int
    ): ParteEmergenciaVoluntario {
        dbQuery {
            PartesEmergenciaVoluntarios.insert {
                it[PartesEmergenciaVoluntarios.idParteVoluntario] = idParteVoluntario
                it[PartesEmergenciaVoluntarios.folioPEmergencia] = folioPEmergencia
                it[PartesEmergenciaVoluntarios.idVoluntario] = idVoluntario
            }
        }
        return ParteEmergenciaVoluntario(idParteVoluntario, folioPEmergencia, idVoluntario)
    }

    override suspend fun deleteParteEmergenciaVoluntario(idParteVoluntario: Int): Boolean = dbQuery {
        PartesEmergenciaVoluntarios.deleteWhere { PartesEmergenciaVoluntarios.idParteVoluntario eq idParteVoluntario } > 0

    }

    override suspend fun updateParteEmergenciaVoluntario(
        idParteVoluntario: Int,
        folioPEmergencia: Int,
        idVoluntario: Int
    ): ParteEmergenciaVoluntario {
        val rowsUpdated = dbQuery {
            PartesEmergenciaVoluntarios.update({ PartesEmergenciaVoluntarios.idParteVoluntario eq idParteVoluntario }) {
                it[PartesEmergenciaVoluntarios.folioPEmergencia] = folioPEmergencia
                it[PartesEmergenciaVoluntarios.idVoluntario] = idVoluntario
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteEmergenciaVoluntario con id $idParteVoluntario no encontrado")
        return ParteEmergenciaVoluntario(idParteVoluntario, folioPEmergencia, idVoluntario)
    }

    // ParteAsistenciaVoluntario implementation

    private fun resultToParteAsistenciaVoluntario(row: ResultRow) = ParteAsistenciaVoluntario(
        idParteAsistenciaVoluntario = row[PartesAsistenciaVoluntarios.idParteAsistenciaVoluntario],
        folioPAsistencia = row[PartesAsistenciaVoluntarios.folioPAsistencia],
        idVoluntario = row[PartesAsistenciaVoluntarios.idVoluntario]
    )
    override suspend fun allParteAsistenciaVoluntarios(): List<ParteAsistenciaVoluntario> = dbQuery {
        PartesAsistenciaVoluntarios.selectAll().map(::resultToParteAsistenciaVoluntario)
    }

    override suspend fun getParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): ParteAsistenciaVoluntario? = dbQuery {
        PartesAsistenciaVoluntarios.select { PartesAsistenciaVoluntarios.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario }
            .mapNotNull(::resultToParteAsistenciaVoluntario)
            .singleOrNull()
    }

    override suspend fun createParteAsistenciaVoluntario(
        idParteAsistenciaVoluntario: Int,
        folioPAsistencia: Int,
        idVoluntario: Int
    ): ParteAsistenciaVoluntario {
        dbQuery {
            PartesAsistenciaVoluntarios.insert {
                it[PartesAsistenciaVoluntarios.idParteAsistenciaVoluntario] = idParteAsistenciaVoluntario
                it[PartesAsistenciaVoluntarios.folioPAsistencia] = folioPAsistencia
                it[PartesAsistenciaVoluntarios.idVoluntario] = idVoluntario
            }
        }
        return ParteAsistenciaVoluntario(idParteAsistenciaVoluntario, folioPAsistencia, idVoluntario)
    }

    override suspend fun deleteParteAsistenciaVoluntario(idParteAsistenciaVoluntario: Int): Boolean = dbQuery {
        PartesAsistenciaVoluntarios.deleteWhere { PartesAsistenciaVoluntarios.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario } > 0

    }

    override suspend fun updateParteAsistenciaVoluntario(
        idParteAsistenciaVoluntario: Int,
        folioPAsistencia: Int,
        idVoluntario: Int
    ): ParteAsistenciaVoluntario {
        val rowsUpdated = dbQuery {
            PartesAsistenciaVoluntarios.update({ PartesAsistenciaVoluntarios.idParteAsistenciaVoluntario eq idParteAsistenciaVoluntario }) {
                it[PartesAsistenciaVoluntarios.folioPAsistencia] = folioPAsistencia
                it[PartesAsistenciaVoluntarios.idVoluntario] = idVoluntario
            }
        }
        if (rowsUpdated == 0) throw IllegalArgumentException("ParteAsistenciaVoluntario con id $idParteAsistenciaVoluntario no encontrado")
        return ParteAsistenciaVoluntario(idParteAsistenciaVoluntario, folioPAsistencia, idVoluntario)
    }

}


