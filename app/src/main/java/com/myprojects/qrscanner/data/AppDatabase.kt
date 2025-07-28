package com.myprojects.qrscanner.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Scan::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scanDao(): ScanDao
}