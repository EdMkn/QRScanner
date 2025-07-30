package com.myprojects.qrscanner.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.myprojects.qrscanner.R

class GenerateActivity : ComponentActivity() {
    private lateinit var inputEditText: EditText
    private lateinit var generateButton: Button
    private lateinit var qrImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate)

        inputEditText = findViewById(R.id.editTextInput)
        generateButton = findViewById(R.id.buttonGenerate)
        qrImageView = findViewById(R.id.imageViewQR)

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun generateQRCode(text: String): Bitmap {
        val size = 512
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

}