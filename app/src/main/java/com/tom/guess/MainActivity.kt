package com.tom.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: GuessViewModel
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)
        viewModel.counter.observe(this, Observer { data ->
            counter.setText(data.toString())
        })
        viewModel.result.observe(this, Observer { result ->
            var message = when(result) {
                GameResult.BIGGER -> getString(R.string.bigger)
                GameResult.SMALLER -> getString(R.string.smaller)
                GameResult.NUMBER_RIGHT -> getString(R.string.yes_you_got_it)
            }
            AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
        })
    }

    fun check(view: View) {
        val n = number.text.toString().toInt()
        viewModel.guess(n)
//        val n = number.text.toString().toInt()
//        println("number: $n")
//        Log.d(TAG, "number:" + n)
//        val diff = secretNumber.validate(n)
//        var message = getString(R.string.yes_you_got_it)
//        if (diff < 0) {
//            message = getString(R.string.bigger)
//        } else if (diff > 0) {
//            message = getString(R.string.smaller)
//        }
//        counter.setText(secretNumber.count.toString())
////        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//        AlertDialog.Builder(this)
//            .setTitle(getString(R.string.dialog_title))
//            .setMessage(message)
//            .setPositiveButton(getString(R.string.ok), null)
//            .show()
    }

    fun playAgain(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Replay game")
            .setMessage("Are you sure?")
            .setPositiveButton(R.string.ok, { dialog, which ->
                viewModel.reset()
                number.setText("")
            })
            .setNeutralButton("Cancel", null)
            .show()
    }
}