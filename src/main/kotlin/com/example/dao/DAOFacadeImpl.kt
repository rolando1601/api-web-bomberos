package com.example.dao

import com.example.dao.DAOFacade
import com.example.models.*
import com.example.dao.DatabaseSingleton.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class DAOFacadeImpl : DAOFacade {
    private fun resultToVoluntario(row: ResultRow) = Voluntario(
        id = row[Voluntarios.id],
        nombre = row[Voluntarios.nombre]

    )

    override suspend fun allVoluntarios(): List<Voluntario> {
        TODO("Not yet implemented")
    }

    override suspend fun getVoluntario(id: Int): Voluntario? {
        TODO("Not yet implemented")
    }

    override suspend fun createVoluntario(id: Int, nombre: String): Voluntario {
        var key = 0
        dbQuery {
            key = (Voluntarios.insert {
                it[Voluntarios.id] = id
                it[Voluntarios.nombre] = nombre
            } get Voluntarios.id)
        }
        return Voluntario(key, nombre)
    }

    override suspend fun deleteVoluntario(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateVoluntario(id: Int, nombre: String): Voluntario {
        TODO("Not yet implemented")
    }
}