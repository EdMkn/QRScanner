package com.myprojects.qrscanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myprojects.qrscanner.data.dao.ScanDao
import com.myprojects.qrscanner.data.model.Scan

/**
 * Main database class for the QR Scanner application.
 * 
 * This Room database manages all local data storage for the application,
 * currently including QR code scan history. The database uses Room's
 * automatic schema generation and provides a singleton pattern for
 * database access across the application.
 * 
 * The database is configured with:
 * - Entity: Scan (QR code scan entries)
 * - Version: 1 (initial schema version)
 * - Automatic schema generation enabled
 * 
 * @author Edouard Makon
 * @since 1.0
 */
@Database(entities = [Scan::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    
    /**
     * Provides access to the ScanDao for database operations on scan data.
     * 
     * @return ScanDao instance for performing scan-related database operations.
     */
    abstract fun scanDao(): ScanDao

    companion object {
        /**
         * Singleton instance of the database.
         * This ensures only one database connection is maintained throughout the app lifecycle.
         */
        private var INSTANCE: AppDatabase? = null

        /**
         * Gets the singleton instance of the database.
         * 
         * This method implements the singleton pattern to ensure only one database
         * instance exists throughout the application lifecycle. The database is created
         * lazily when first accessed.
         * 
         * @param context The application context used to create the database.
         * @return The singleton AppDatabase instance.
         * 
         * @throws IllegalStateException if the database creation fails
         */
        fun getDatabase(context: Context): AppDatabase {
            // Return existing instance if available
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            
            // Create new instance if none exists
            synchronized(this) {
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