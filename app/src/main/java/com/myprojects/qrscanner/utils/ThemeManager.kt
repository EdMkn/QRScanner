package com.myprojects.qrscanner.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

/**
 * Theme manager utility for handling dark/light mode switching.
 * 
 * This class provides functionality to:
 * - Switch between light and dark themes
 * - Save user theme preference
 * - Apply theme based on system settings or user preference
 * - Get current theme status
 * 
 * @author Edouard Makon
 * @since 1.0
 */
object ThemeManager {
    
    private const val PREF_THEME_MODE = "theme_mode"
    private const val THEME_SYSTEM = "system"
    private const val THEME_LIGHT = "light"
    private const val THEME_DARK = "dark"
    
    /**
     * Theme mode options available in the app.
     */
    enum class ThemeMode {
        SYSTEM, LIGHT, DARK
    }
    
    /**
     * Gets the shared preferences instance for theme settings.
     * 
     * @param context The application context
     * @return SharedPreferences instance for theme settings
     */
    private fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
    
    /**
     * Gets the current theme mode from preferences.
     * 
     * @param context The application context
     * @return Current theme mode (defaults to SYSTEM)
     */
    fun getThemeMode(context: Context): ThemeMode {
        val themeString = getPreferences(context).getString(PREF_THEME_MODE, THEME_SYSTEM)
        val themeMode = when (themeString) {
            THEME_LIGHT -> ThemeMode.LIGHT
            THEME_DARK -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }
        Log.d("ThemeManager", "Current theme mode: $themeMode")
        return themeMode
    }
    
    /**
     * Sets the theme mode and saves it to preferences.
     * 
     * @param context The application context
     * @param themeMode The theme mode to set
     */
    fun setThemeMode(context: Context, themeMode: ThemeMode) {
        val themeString = when (themeMode) {
            ThemeMode.LIGHT -> THEME_LIGHT
            ThemeMode.DARK -> THEME_DARK
            ThemeMode.SYSTEM -> THEME_SYSTEM
        }
        
        Log.d("ThemeManager", "Setting theme mode to: $themeMode")
        getPreferences(context).edit().putString(PREF_THEME_MODE, themeString).apply()
        applyTheme(themeMode)
    }
    
    /**
     * Applies the specified theme mode to the app.
     * 
     * @param themeMode The theme mode to apply
     */
    fun applyTheme(themeMode: ThemeMode) {
        val nightMode = when (themeMode) {
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        Log.d("ThemeManager", "Applying night mode: $nightMode for theme: $themeMode")
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
    
    /**
     * Applies the saved theme mode from preferences.
     * 
     * @param context The application context
     */
    fun applySavedTheme(context: Context) {
        val themeMode = getThemeMode(context)
        applyTheme(themeMode)
    }
    
    /**
     * Checks if the app is currently in dark mode.
     * 
     * @param context The application context
     * @return true if dark mode is active, false otherwise
     */
    fun isDarkMode(context: Context): Boolean {
        val themeMode = getThemeMode(context)
        val isDark = when (themeMode) {
            ThemeMode.DARK -> true
            ThemeMode.LIGHT -> false
            ThemeMode.SYSTEM -> {
                val nightMode = AppCompatDelegate.getDefaultNightMode()
                nightMode == AppCompatDelegate.MODE_NIGHT_YES
            }
        }
        Log.d("ThemeManager", "Is dark mode: $isDark")
        return isDark
    }
    
    /**
     * Toggles between light and dark themes.
     * If system theme is active, switches to light mode.
     * 
     * @param context The application context
     */
    fun toggleTheme(context: Context) {
        val currentMode = getThemeMode(context)
        val newMode = when (currentMode) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
        }
        Log.d("ThemeManager", "Toggling theme from $currentMode to $newMode")
        setThemeMode(context, newMode)
    }
    
    /**
     * Restarts the app to apply theme changes.
     * 
     * @param activity The current activity
     */
    fun restartApp(activity: Activity) {
        Log.d("ThemeManager", "Restarting app to apply theme changes")
        val intent = Intent(activity, activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
        activity.finish()
    }
    
    /**
     * Forces the current activity to recreate itself to apply theme changes.
     * 
     * @param activity The current activity
     */
    fun recreateActivity(activity: Activity) {
        Log.d("ThemeManager", "Recreating activity to apply theme changes")
        activity.recreate()
    }
    
    /**
     * Applies theme and forces activity recreation for immediate effect.
     * 
     * @param context The application context
     * @param themeMode The theme mode to apply
     */
    fun applyThemeAndRecreate(context: Context, themeMode: ThemeMode) {
        setThemeMode(context, themeMode)
        if (context is Activity) {
            recreateActivity(context)
        }
    }
    
    /**
     * Gets the appropriate color for the current theme.
     * 
     * @param context The application context
     * @param lightColor The color resource ID for light theme
     * @param darkColor The color resource ID for dark theme
     * @return The color value for the current theme
     */
    fun getThemeColor(context: Context, lightColor: Int, darkColor: Int): Int {
        return if (isDarkMode(context)) {
            ContextCompat.getColor(context, darkColor)
        } else {
            ContextCompat.getColor(context, lightColor)
        }
    }
} 