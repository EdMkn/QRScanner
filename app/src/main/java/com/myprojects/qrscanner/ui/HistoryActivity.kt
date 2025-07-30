package com.myprojects.qrscanner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myprojects.qrscanner.R
import com.myprojects.qrscanner.ui.adapter.ScanAdapter
import com.myprojects.qrscanner.ui.viewmodel.ScanViewModel
import com.myprojects.qrscanner.utils.ThemeManager

/**
 * Activity for displaying the history of scanned QR codes.
 * 
 * This activity shows a chronological list of all QR codes that have been
 * scanned by the user. The list is displayed in a RecyclerView with the most
 * recent scans appearing at the top. Each scan entry shows the QR code content
 * and the timestamp when it was scanned.
 * 
 * Features:
 * - Chronological list of all scanned QR codes
 * - Real-time updates when new scans are added
 * - Formatted timestamps for each scan
 * - Clean, scrollable interface
 * - Automatic data persistence using Room database
 * 
 * The activity uses MVVM architecture with a ViewModel to manage the data
 * and a custom adapter to display the scan history efficiently.
 * 
 * @author Edouard Makon
 * @since 1.0
 */
class HistoryActivity : ComponentActivity() {

    /**
     * RecyclerView adapter for displaying scan history.
     * Manages the display of scan entries in the RecyclerView with proper formatting.
     */
    private lateinit var adapter: ScanAdapter
    
    /**
     * RecyclerView for displaying the list of scanned QR codes.
     * Provides a scrollable list interface for the scan history.
     */
    private lateinit var recyclerView: RecyclerView
    
    /**
     * ViewModel for managing scan data and database operations.
     * Handles the business logic for retrieving and managing scan history data.
     */
    private lateinit var viewModel: ScanViewModel

    /**
     * Called when the activity is first created.
     * 
     * This method initializes the activity, sets up the RecyclerView with its
     * adapter, configures the ViewModel, observes data changes to update
     * the UI automatically, and sets up the back button handling.
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *                          previously being shut down, this Bundle contains
     *                          the data it most recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        initializeViews()
        updateUIColors()
        setupRecyclerView()
        setupViewModel()
        observeData()
        setupBackPressHandling()
    }

    /**
     * Called when the activity resumes.
     * 
     * This method is called when the activity becomes visible again,
     * such as when returning from another activity.
     */
    override fun onResume() {
        super.onResume()
        // Update UI colors in case theme was changed in settings
        updateUIColors()
    }

    /**
     * Updates the UI colors based on the current theme.
     * 
     * This method sets the appropriate colors for the UI elements
     * based on whether the app is in light or dark mode.
     */
    private fun updateUIColors() {
        val isDarkMode = ThemeManager.isDarkMode(this)
        
        if (isDarkMode) {
            // Dark theme colors
            findViewById<android.view.View>(android.R.id.content).setBackgroundColor(
                ContextCompat.getColor(this, R.color.background_dark)
            )
        } else {
            // Light theme colors
            findViewById<android.view.View>(android.R.id.content).setBackgroundColor(
                ContextCompat.getColor(this, R.color.background_light)
            )
        }
    }

    /**
     * Initializes the UI components by finding them in the layout.
     * 
     * This method binds the RecyclerView to its corresponding variable
     * for later use in the activity.
     */
    private fun initializeViews() {
        recyclerView = findViewById(R.id.recyclerView)
    }

    /**
     * Sets up the RecyclerView with its adapter and layout manager.
     * 
     * This method configures the RecyclerView to display scan history items
     * in a vertical list format with proper spacing and scrolling behavior.
     */
    private fun setupRecyclerView() {
        adapter = ScanAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    /**
     * Initializes the ViewModel for data management.
     * 
     * This method creates and configures the ScanViewModel to handle
     * database operations and data retrieval for scan history.
     */
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ScanViewModel::class.java]
    }

    /**
     * Sets up data observation to automatically update the UI.
     * 
     * This method observes the LiveData from the ViewModel and updates
     * the adapter whenever the scan history data changes. This ensures
     * the UI stays synchronized with the database.
     */
    private fun observeData() {
        viewModel.allScans.observe(this) { scans ->
            adapter.updateData(scans)
        }
    }

    /**
     * Sets up the back button handling using OnBackPressedDispatcher.
     * 
     * This method configures the modern back button handling approach
     * using OnBackPressedCallback instead of the deprecated onBackPressed().
     * It ensures proper cleanup and navigation back to the main activity.
     */
    private fun setupBackPressHandling() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }
}
