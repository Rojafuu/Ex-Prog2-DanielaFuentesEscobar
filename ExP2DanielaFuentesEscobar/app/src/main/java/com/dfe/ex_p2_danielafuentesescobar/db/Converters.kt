package com.dfe.ex_p2_danielafuentesescobar.db

import androidx.room.TypeConverter
import com.dfe.ex_p2_danielafuentesescobar.entities.tipoServicio

class Converters {

    // Convierte un TipoServicio a String
    @TypeConverter
    fun fromTipoServicio(servicio: tipoServicio): String {
        return servicio.name
    }

    // Convierte un String a TipoServicio
    @TypeConverter
    fun toTipoServicio(servicio: String): tipoServicio {
        return tipoServicio.valueOf(servicio)
    }
}
