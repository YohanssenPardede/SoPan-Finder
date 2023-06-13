package com.capstone.sopanfinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteSopan::class], version = 1)
abstract class SopanDatabase : RoomDatabase() {
    abstract fun favoriteSopanDao(): FavoriteSopanDao

    companion object {
        @Volatile
        private var INSTANCE: SopanDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): SopanDatabase {
            if (INSTANCE == null) {
                synchronized(SopanDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        SopanDatabase::class.java, "sopan_database").build()
                }
            }

            return INSTANCE as SopanDatabase
        }
    }
}
