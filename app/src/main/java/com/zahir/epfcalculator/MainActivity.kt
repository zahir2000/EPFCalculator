package com.zahir.epfcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var dob : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myFormat = "dd.MM.yyyy"

        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textViewSelectDob.text = sdf.format(System.currentTimeMillis())

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            textViewSelectDob.text = sdf.format(cal.time)
            dob = cal.get(Calendar.YEAR)

            textViewCalculatedAge.text = calculateAge(dob).toString()
            textViewCalculatedSaving.text  = calculateBasicSaving(calculateAge(dob)).toString()
            textViewCalculatedInvestment.text = calculateInvestment().toString()
        }

        textViewSelectDob.setOnClickListener{
            DatePickerDialog(this@MainActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        buttonReset.setOnClickListener{
            textViewCalculatedInvestment.text = ""
            textViewCalculatedSaving.text = ""
            textViewCalculatedAge.text  = ""
            textViewSelectDob.text = sdf.format(System.currentTimeMillis())
            Toast.makeText(this, "Cleared", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateInvestment(): Double {
        return calculateBasicSaving(calculateAge(dob)) * 0.3
    }

    private fun calculateBasicSaving(age: Int): Int {
        var saving = 0

        when(age){
            in 16..20 -> saving = 5000
            in 21..25 -> saving = 14000
            in 26..30 -> saving = 29000
            in 31..35 -> saving = 50000
            in 36..40 -> saving = 78000
            in 41..45 -> saving = 116000
            in 46..50 -> saving = 165000
            in 51..55 -> saving = 228000
        }

        return saving
    }

    private fun calculateAge(year: Int): Int {
        return Calendar.getInstance().get(Calendar.YEAR) - year
    }
}
