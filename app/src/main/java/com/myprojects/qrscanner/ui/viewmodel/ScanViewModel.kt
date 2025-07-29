package com.myprojects.qrscanner.ui.viewmodel;

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.myprojects.qrscanner.data.AppDatabase
import com.myprojects.qrscanner.data.model.Scan
import kotlinx.coroutines.launch

public class ScanViewModel (application: Application) : AndroidViewModel(application) {

    private val scanDao = AppDatabase.getDatabase(application).scanDao()
    val allScans: LiveData<List<Scan>> = scanDao.getAll()

    fun insert(scan: Scan) {
        viewModelScope.launch {
            scanDao.insert(scan)
        }
    }
}
