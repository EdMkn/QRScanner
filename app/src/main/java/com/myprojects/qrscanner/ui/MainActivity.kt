package com.myprojects.qrscanner.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.myprojects.qrscanner.R
import com.myprojects.qrscanner.data.model.Scan
import com.myprojects.qrscanner.ui.viewmodel.ScanViewModel

/**
 * Main activity of the QR Scanner application.
 * 
 * This activity serves as the primary interface for the QR Scanner app, providing
 * camera-based QR code scanning functionality with bottom navigation to access
 * different features of the application.
 * 
 * Features:
 * - Real-time QR code scanning using CameraX and ML Kit
 * - Bottom navigation to switch between Scanner, Generator, and History
 * - Automatic QR code detection and database storage
 * - Camera permission handling
 * - Toast notifications for successful scans
 * 
 * The activity uses MVVM architecture with a ViewModel for data management
 * and Room database for persistent storage of scan history.
 * 
 * @author Edouard Makon
 * @since 1.0
 */
class MainActivity : ComponentActivity() {

    /**
     * Camera preview view for displaying the camera feed.
     * Used by CameraX to show the live camera preview to the user.
     */
    private lateinit var previewView: PreviewView
    
    /**
     * Bottom navigation view for switching between app features.
     * Provides navigation between Scanner, Generator, and History sections.
     */
    private lateinit var bottomNavigation: BottomNavigationView
    
    /**
     * ViewModel for managing scan data and database operations.
     * Handles the business logic for scan operations and data persistence.
     */
    private lateinit var viewModel: ScanViewModel
    
    /**
     * ML Kit barcode scanner instance.
     * Used for detecting and processing QR codes from camera frames.
     */
    private val scanner = BarcodeScanning.getClient()

    /**
     * Called when the activity is first created.
     * 
     * This method initializes the activity, sets up the UI components,
     * configures the bottom navigation, and starts the camera if permissions
     * are granted.
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *                          previously being shut down, this Bundle contains
     *                          the data it most recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[ScanViewModel::class.java]

        previewView = findViewById(R.id.previewView)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Set up bottom navigation
        setupBottomNavigation()

        // Start with scanner view
        bottomNavigation.selectedItemId = R.id.navigation_scanner
    }

    /**
     * Sets up the bottom navigation with click listeners for each tab.
     * 
     * This method configures the bottom navigation bar to handle user interactions
     * and switch between different app features (Scanner, Generator, History).
     */
    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_scanner -> {
                    // Show camera preview
                    previewView.visibility = android.view.View.VISIBLE
                    if (allPermissionsGranted()) {
                        startCamera()
                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
                    }
                    true
                }
                R.id.navigation_generator -> {
                    // Hide camera and start GenerateActivity
                    previewView.visibility = android.view.View.GONE
                    val intent = Intent(this, GenerateActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_history -> {
                    // Hide camera and start HistoryActivity
                    previewView.visibility = android.view.View.GONE
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Checks if all required permissions are granted.
     * 
     * This method verifies that the camera permission has been granted by the user.
     * The camera permission is required for QR code scanning functionality.
     * 
     * @return true if camera permission is granted, false otherwise.
     */
    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    /**
     * Initializes and starts the camera for QR code scanning.
     * 
     * This method sets up CameraX with the back camera, configures the preview,
     * and starts the image analysis for QR code detection. The camera is bound
     * to the activity lifecycle for proper resource management.
     * 
     * The camera setup includes:
     * - Preview configuration for displaying camera feed
     * - Image analysis for QR code detection
     * - Camera selector for back camera
     * - Lifecycle binding for automatic resource management
     */
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            val imageAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(ContextCompat.getMainExecutor(this), QRAnalyzer())
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    /**
     * Image analyzer for detecting QR codes in camera frames.
     * 
     * This inner class implements ImageAnalysis.Analyzer to process camera frames
     * and detect QR codes using ML Kit. When a QR code is detected, it extracts
     * the content and saves it to the database via the ViewModel.
     * 
     * The analyzer runs on a background thread and processes frames efficiently
     * to provide real-time QR code detection.
     */
    inner class QRAnalyzer : ImageAnalysis.Analyzer {
        
        /**
         * Analyzes a camera frame for QR code detection.
         * 
         * This method is called for each camera frame and processes the image
         * to detect QR codes. When a QR code is found, it extracts the content
         * and saves it to the database, then shows a toast notification.
         * 
         * @param imageProxy The camera frame to analyze.
         */
        @OptIn(ExperimentalGetImage::class)
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

                scanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            val rawValue = barcode.rawValue
                            if (rawValue != null) {
                                val scan = Scan(content = rawValue)
                                viewModel.insert(scan)
                                Log.d("Scanner", "QR détecté : $rawValue")
                                // Show toast notification
                                runOnUiThread {
                                    Toast.makeText(this@MainActivity, "QR Code détecté: $rawValue", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    .addOnFailureListener {
                        Log.e("Scanner", "Erreur ML Kit", it)
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            }
        }
    }

    /**
     * Handles permission request results.
     * 
     * This method is called after the user responds to a permission request.
     * If the camera permission is granted, it starts the camera. If denied,
     * it shows a toast message informing the user that camera access is required.
     * 
     * @param requestCode The request code passed to requestPermissions().
     * @param permissions The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     */
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            Toast.makeText(this, "Permission caméra refusée", Toast.LENGTH_SHORT).show()
        }
    }
}
