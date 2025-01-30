package com.dfe.ex_p2_danielafuentesescobar.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dfe.ex_p2_danielafuentesescobar.entities.Medicion

@Dao
interface medicionDao {

    @Query("SELECT * FROM mediciones ORDER BY medicion")
    fun getAll():List<Medicion>

    @Insert
    fun insert(medicion: com.dfe.ex_p2_danielafuentesescobar.Medicion)

    @Delete
    fun delete(medicion: Medicion)
}
