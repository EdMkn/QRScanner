package com.myprojects.qrscanner.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myprojects.qrscanner.data.model.Scan

@Dao
interface ScanDao {
    @Insert
    suspend fun insert(scan: Scan)

    @Query("SELECT * FROM scans ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<Scan>>
}