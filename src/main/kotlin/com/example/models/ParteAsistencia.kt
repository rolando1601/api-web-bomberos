//package com.example.models
//
//import kotlinx.datetime.LocalDate
//import kotlinx.datetime.LocalTime
//import org.jetbrains.exposed.sql.Table
//import org.jetbrains.exposed.sql.kotlin.datetime.date
//import org.jetbrains.exposed.sql.kotlin.datetime.time
//
//data class ParteAsistencia(
//    val folioPAsistencia: Int, // Clave primaria
//    val tipoLlamado: String,
//    val aCargoDelCuerpo: String,
//    val aCargoDeLaCompania: String,
//    val fechaAsistencia: LocalDate,
//    val horaInicio: LocalTime,
//    val horaFin: LocalTime,
//    val direccion: String,
//    val totalAsistencia: Int,
//    val observaciones: String?, // Nullable
//    val materialMayorAsistencia: String? // Nullable
//)
//object ParteAsistencias : Table() {
//    val folioPAsistencia = integer("folioPAsistencia").autoIncrement()
//    val tipoLlamado = varchar("tipoLlamado", 50)
//    val aCargoDelCuerpo = varchar("aCargoDelCuerpo", 100)
//    val aCargoDeLaCompania = varchar("aCargoDeLaCompania", 100)
//    val fechaAsistencia = date("fechaAsistencia")
//    val horaInicio = time("horaInicio")
//    val horaFin = time("horaFin")
//    val direccion = varchar("direccion", 255)
//    val totalAsistencia = integer("totalAsistencia")
//    val observaciones = text("observaciones").nullable() // Campo nullable
//    val materialMayorAsistencia = text("materialMayorAsistencia").nullable() // Campo nullable
//
//    override val primaryKey = PrimaryKey(folioPAsistencia) // Clave primaria
//    }