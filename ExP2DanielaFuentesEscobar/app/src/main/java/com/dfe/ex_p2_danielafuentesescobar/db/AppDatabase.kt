package com.dfe.ex_p2_danielafuentesescobar.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dfe.ex_p2_danielafuentesescobar.entities.Medicion
import com.dfe.ex_p2_danielafuentesescobar.dao.medicionDao
import com.dfe.ex_p2_danielafuentesescobar.db.Converters
import androidx.room.TypeConverters

@Database(entities = [Medicion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicionDao(): medicionDao
}
