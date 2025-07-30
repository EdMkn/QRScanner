package com.myprojects.qrscanner.ui

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import com.myprojects.qrscanner.R
import com.myprojects.qrscanner.utils.ThemeManager
import androidx.core.content.ContextCompat

/**
 * Settings activity for the QR Scanner application.
 * 
 * This activity provides user interface for configuring app settings,
 * including theme selection (light, dark, or system default).
 * 
 * Features:
 * - Theme selection (Light, Dark, System)
 * - Real-time theme preview
 * - Persistent theme settings
 * - Back navigation handling
 * 
 * @author Edouard Makon
 * @since 1.0
 */
class SettingsActivity : ComponentActivity() {
    
    /**
     * Radio group for theme selection options.
     * Contains radio buttons for Light, Dark, and System themes.
     */
    private lateinit var themeRadioGroup: RadioGroup
    
    /**
     * Radio button for light theme option.
     */
    private lateinit var lightThemeRadio: RadioButton
    
    /**
     * Radio button for dark theme option.
     */
    private lateinit var darkThemeRadio: RadioButton
    
    /**
     * Radio button for system theme option.
     */
    private lateinit var systemThemeRadio: RadioButton
    
    /**
     * Button to apply the selected theme.
     */
    private lateinit var applyButton: Button

    /**
     * Called when the activity is first created.
     * 
     * This method initializes the activity, sets up the UI components,
     * configures the theme selection, and sets up the back button handling.
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *                          previously being shut down, this Bundle contains
     *                          the data it most recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initializeViews()
        updateUIColors()
        setupThemeSelection()
        setupClickListeners()
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
        // Update UI colors in case theme was changed
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
     * This method binds the UI elements to their corresponding variables
     * for later use in the activity.
     */
    private fun initializeViews() {
        themeRadioGroup = findViewById(R.id.radioGroupTheme)
        lightThemeRadio = findViewById(R.id.radioButtonLight)
        darkThemeRadio = findViewById(R.id.radioButtonDark)
        systemThemeRadio = findViewById(R.id.radioButtonSystem)
        applyButton = findViewById(R.id.buttonApply)
    }

    /**
     * Sets up the theme selection radio group with current theme.
     * 
     * This method configures the radio buttons to reflect the current
     * theme setting and handles theme changes.
     */
    private fun setupThemeSelection() {
        val currentTheme = ThemeManager.getThemeMode(this)
        
        // Set the appropriate radio button based on current theme
        when (currentTheme) {
            ThemeManager.ThemeMode.LIGHT -> lightThemeRadio.isChecked = true
            ThemeManager.ThemeMode.DARK -> darkThemeRadio.isChecked = true
            ThemeManager.ThemeMode.SYSTEM -> systemThemeRadio.isChecked = true
        }
        
        // Handle theme selection changes
        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedTheme = when (checkedId) {
                R.id.radioButtonLight -> ThemeManager.ThemeMode.LIGHT
                R.id.radioButtonDark -> ThemeManager.ThemeMode.DARK
                R.id.radioButtonSystem -> ThemeManager.ThemeMode.SYSTEM
                else -> ThemeManager.ThemeMode.SYSTEM
            }
            
            // Apply theme immediately for preview
            ThemeManager.applyThemeAndRecreate(this, selectedTheme)
        }
    }

    /**
     * Sets up click listeners for interactive UI elements.
     * 
     * This method configures the apply button to handle user interactions
     * and provide feedback when settings are applied.
     */
    private fun setupClickListeners() {
        applyButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.theme_applied), Toast.LENGTH_SHORT).show()
            // Restart the app to apply theme changes
            ThemeManager.restartApp(this)
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