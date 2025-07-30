package com.myprojects.qrscanner

import android.app.Application
import android.util.Log
import com.myprojects.qrscanner.utils.ThemeManager

/**
 * Application class for the QR Scanner app.
 * 
 * This class handles global application initialization, including
 * theme setup and other app-wide configurations.
 * 
 * @author Edouard Makon
 * @since 1.0
 */
class QRScannerApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Apply saved theme before any activities are created
        val themeMode = ThemeManager.getThemeMode(this)
        Log.d("QRScannerApp", "Applying theme: $themeMode")
        
        // Force apply the theme
        ThemeManager.applyTheme(themeMode)
        
        // Set the theme mode again to ensure it's applied
        ThemeManager.setThemeMode(this, themeMode)
    }
} 