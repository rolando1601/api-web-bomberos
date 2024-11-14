package com.example.dao

import com.example.models.*
import org.h2.util.json.JSONString
import java.util.Date

interface DAOFacade {
    suspend fun allVoluntarios(): List<Voluntario>
    suspend fun getVoluntario(id: Int): Voluntario?
    suspend fun createVoluntario(id: Int, nombre: String): Voluntario
    suspend fun deleteVoluntario(id: Int): Boolean
    suspend fun updateVoluntario(id: Int, nombre: String): Voluntario
}