package com.tom.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "secret:" + secretNumber.secret)
    }

    fun check(view: View) {
        val n = number.text.toString().toInt()
        println("number: $n")
        Log.d("MainActivity", "number:" + n)
        val diff = secretNumber.validate(n)
        var message = "Yes, you get it"
        if (diff < 0) {
            message = "Bigger"
        } else if (diff > 0) {
            message = "Smaller"
        }
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton("ok", null)
            .show()
    }
}