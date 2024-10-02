package com.example.age_calculator_app

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val inputBirthDate: EditText = findViewById(R.id.inputBirthDate)
        val calculateButton: Button = findViewById(R.id.calculateButton)
        val yearData: TextView = findViewById(R.id.yearData)
        val monthData: TextView = findViewById(R.id.monthData)
        val dayData: TextView = findViewById(R.id.dayData)

        calculateButton.setOnClickListener {
            val birthDateString = inputBirthDate.text.toString()

            if (birthDateString.isNotEmpty()) {
                try {
                    val birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                    if (birthDate.isAfter(LocalDate.now())) {
                        Toast.makeText(this, "Birth date cannot be after today.", Toast.LENGTH_LONG).show()
                    } else {
                        val (years, months, days) = calculateAge(birthDate)
                        yearData.text = "$years"
                        monthData.text = "$months"
                        dayData.text = "$days"
                    }
                } catch (e: DateTimeParseException) {
                    Toast.makeText(this, "Invalid date format. Please use yyyy-MM-dd.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please enter a birthdate", Toast.LENGTH_LONG).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(birthDate: LocalDate): Triple<Int, Int, Int> {
        val currentDate = LocalDate.now()
        val period = Period.between(birthDate, currentDate)

        return Triple(period.years, period.months, period.days)
    }
}