package com.myprojects.qrscanner.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.myprojects.qrscanner.R
import androidx.core.graphics.set
import androidx.core.graphics.createBitmap

/**
 * Activity for generating QR codes from text input.
 * 
 * This activity provides a user interface for creating QR codes by entering text.
 * Users can input any text content, and the app will generate a high-quality
 * QR code that can be scanned by other devices or applications.
 * 
 * Features:
 * - Text input for QR code content
 * - High-quality QR code generation (512x512 pixels)
 * - Instant generation using ZXing library
 * - Visual display of generated QR code
 * - Input validation with user feedback
 * 
 * The generated QR codes can contain any text content including URLs, contact
 * information, plain text, or any other data that can be encoded in QR format.
 * 
 * @author Edouard Makon
 * @since 1.0
 */
class GenerateActivity : ComponentActivity() {
    
    /**
     * EditText for user input of QR code content.
     * Users enter the text they want to encode in the QR code here.
     */
    private lateinit var inputEditText: EditText
    
    /**
     * Button to trigger QR code generation.
     * When pressed, it validates the input and generates the QR code.
     */
    private lateinit var generateButton: Button
    
    /**
     * ImageView for displaying the generated QR code.
     * Shows the visual representation of the generated QR code.
     */
    private lateinit var qrImageView: ImageView

    /**
     * Called when the activity is first created.
     * 
     * This method initializes the activity, sets up the UI components,
     * configures the click listener for the generate button, and sets up
     * the back button handling using OnBackPressedDispatcher.
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *                          previously being shut down, this Bundle contains
     *                          the data it most recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate)

        initializeViews()
        setupClickListeners()
        setupBackPressHandling()
    }

    /**
     * Initializes the UI components by finding them in the layout.
     * 
     * This method binds the UI elements to their corresponding variables
     * for later use in the activity.
     */
    private fun initializeViews() {
        inputEditText = findViewById(R.id.editTextInput)
        generateButton = findViewById(R.id.buttonGenerate)
        qrImageView = findViewById(R.id.imageViewQR)
    }

    /**
     * Sets up click listeners for interactive UI elements.
     * 
     * This method configures the generate button to handle user interactions
     * and trigger QR code generation when pressed.
     */
    private fun setupClickListeners() {
        generateButton.setOnClickListener {
            val text = inputEditText.text.toString()
            if (text.isNotEmpty()) {
                val bitmap = generateQRCode(text)
                qrImageView.setImageBitmap(bitmap)
            } else {
                Toast.makeText(this, "Veuillez entrer un texte", Toast.LENGTH_SHORT).show()
            }
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

    /**
     * Generates a QR code bitmap from the provided text.
     * 
     * This method uses the ZXing library to create a QR code from the input text.
     * The generated QR code is a high-quality bitmap that can be displayed or saved.
     * 
     * The QR code is generated with the following specifications:
     * - Size: 512x512 pixels
     * - Format: QR_CODE
     * - Error correction: Default (L level)
     * - Colors: Black and white
     * 
     * @param text The text content to encode in the QR code.
     * @return A Bitmap containing the generated QR code image.
     * 
     * @throws IllegalArgumentException if the text is too long for QR encoding
     * @throws RuntimeException if QR code generation fails
     */
    private fun generateQRCode(text: String): Bitmap {
        val size = 512
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size)
        val bitmap = createBitmap(size, size, Bitmap.Config.RGB_565)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        return bitmap
    }
}