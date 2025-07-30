package com.myprojects.qrscanner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myprojects.qrscanner.R
import com.myprojects.qrscanner.data.AppDatabase
import com.myprojects.qrscanner.data.dao.ScanDao
import com.myprojects.qrscanner.ui.adapter.ScanAdapter
import com.myprojects.qrscanner.ui.viewmodel.ScanViewModel

class HistoryActivity : ComponentActivity() {

    private lateinit var adapter: ScanAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ScanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ScanAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Initialiser le ViewModel
        viewModel = ViewModelProvider(this)[ScanViewModel::class.java]


        // Observer les données
        viewModel.allScans.observe(this) { scans ->
            adapter.updateData(scans)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
