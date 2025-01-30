package com.dfe.ex_p2_danielafuentesescobar.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.TypeConverters


enum class tipoServicio { AGUA, LUZ, GAS }

@Entity(tableName = "mediciones")
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val medicion: String,
    val fecha: String,
    val servicio: tipoServicio
    )






