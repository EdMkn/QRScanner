package com.myprojects.qrscanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myprojects.qrscanner.data.dao.ScanDao
import com.myprojects.qrscanner.data.model.Scan

@Database(entities = [Scan::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scanDao(): ScanDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "qr_database"
                ).build()
            }
            return INSTANCE!!
        }
    }
}