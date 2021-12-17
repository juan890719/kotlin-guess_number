package com.tom.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "secret:" + secretNumber.secret)

        counter.setText(secretNumber.count.toString())
    }

    fun check(view: View) {
        val n = number.text.toString().toInt()
        println("number: $n")
        Log.d(TAG, "number:" + n)
        val diff = secretNumber.validate(n)
        var message = getString(R.string.yes_you_got_it)
        if (diff < 0) {
            message = getString(R.string.bigger)
        } else if (diff > 0) {
            message = getString(R.string.smaller)
        }
        counter.setText(secretNumber.count.toString())
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    fun playAgain(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Replay game")
            .setMessage("Are you sure?")
            .setPositiveButton(R.string.ok, {dialog, which ->
                secretNumber.reset()
                counter.setText(secretNumber.count.toString())
                number.setText("")
            })
            .setNeutralButton("Cancel", null)
            .show()
    }
}