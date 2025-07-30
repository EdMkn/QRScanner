package com.myprojects.qrscanner.ui.viewmodel;

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.myprojects.qrscanner.data.AppDatabase
import com.myprojects.qrscanner.data.model.Scan
import kotlinx.coroutines.launch

/**
 * ViewModel for managing QR code scan data and operations.
 * 
 * This ViewModel serves as the data management layer between the UI and the database.
 * It provides a clean API for the UI to interact with scan data, handling database
 * operations on background threads and exposing LiveData for reactive UI updates.
 * 
 * The ViewModel follows the MVVM architecture pattern and extends AndroidViewModel
 * to have access to the Application context for database operations.
 * 
 * @author Edouard Makon
 * @since 1.0
 */
public class ScanViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Data Access Object for scan operations.
     * Provides access to database operations for scan data.
     */
    private val scanDao = AppDatabase.getDatabase(application).scanDao()
    
    /**
     * LiveData containing all scan entries from the database.
     * 
     * This LiveData automatically updates the UI when the underlying database
     * changes. The scans are ordered by timestamp in descending order (newest first).
     * 
     * @return LiveData containing a list of all scans, ordered by timestamp.
     */
    val allScans: LiveData<List<Scan>> = scanDao.getAll()

    /**
     * Inserts a new scan entry into the database.
     * 
     * This method performs the database insertion on a background thread using
     * Kotlin coroutines to avoid blocking the main thread. The operation is
     * executed within the ViewModel's scope to ensure proper lifecycle management.
     * 
     * @param scan The Scan object to insert into the database.
     *              Should contain the QR code content and will automatically
     *              get a timestamp and auto-generated ID.
     * 
     * @throws SQLiteException if the database operation fails
     */
    fun insert(scan: Scan) {
        viewModelScope.launch {
            scanDao.insert(scan)
        }
    }
}
