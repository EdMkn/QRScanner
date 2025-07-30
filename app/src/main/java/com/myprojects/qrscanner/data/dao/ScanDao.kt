package com.myprojects.qrscanner.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myprojects.qrscanner.data.model.Scan

/**
 * Data Access Object (DAO) interface for managing QR code scan data in the Room database.
 * 
 * This interface defines the database operations for the Scan entity, including
 * inserting new scans and retrieving all scans with proper ordering. The DAO
 * provides a clean abstraction layer between the database and the application logic.
 * 
 * Room automatically generates the implementation of this interface at compile time.
 * 
 * @author Edouard Makon
 * @since 1.0
 */
@Dao
interface ScanDao {
    
    /**
     * Inserts a new scan entry into the database.
     * 
     * This method adds a new QR code scan to the local database. The scan object
     * should contain the QR code content and will automatically get a timestamp
     * and auto-generated ID.
     * 
     * @param scan The Scan object to insert into the database.
     * @return The row ID of the newly inserted scan (as a suspend function result).
     * 
     * @throws SQLiteException if the database operation fails
     */
    @Insert
    suspend fun insert(scan: Scan)

    /**
     * Retrieves all scan entries from the database, ordered by timestamp in descending order.
     * 
     * This method returns a LiveData object containing all scans, with the most recent
     * scans appearing first. The LiveData will automatically update the UI when the
     * underlying data changes.
     * 
     * @return LiveData containing a list of all scans, ordered by timestamp (newest first).
     *         Returns an empty list if no scans exist.
     */
    @Query("SELECT * FROM scans ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<Scan>>
}