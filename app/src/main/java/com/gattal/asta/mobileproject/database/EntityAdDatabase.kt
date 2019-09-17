package com.gattal.asta.mobileproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gattal.asta.mobileproject.dao.EntityDao
import com.gattal.asta.mobileproject.modeldata.AdEntity

@Database(entities = [AdEntity::class], version = 1)
abstract class EntityAdDatabase : RoomDatabase() {
    abstract fun getEntityDao(): EntityDao

    companion object {
        @Volatile
        private var INSTANCE: EntityAdDatabase? = null

        fun getDatabase(
            context: Context
        ): EntityAdDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntityAdDatabase::class.java,
                    "ads_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}