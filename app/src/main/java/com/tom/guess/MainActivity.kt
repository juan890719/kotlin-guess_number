package com.tom.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_RECORD = 100
    private lateinit var viewModel: GuessViewModel
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: ")

        val count = getSharedPreferences("guess", MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: $count / $nick")

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
            .setPositiveButton(getString(R.string.ok), {dialog, which ->
                if (result == GameResult.NUMBER_RIGHT) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER", viewModel.count)
//                    startActivity(intent)
                    startActivityForResult(intent, REQUEST_RECORD)
                }
            })
            .show()
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    fun test() {
//        val intent = Intent(this, RecordActivity::class.java)
//        intent.putExtra("A", "abc")
//        intent.putExtra("BB", "Testing")
//        startActivity(intent)
        //
        Intent(this, RecordActivity::class.java).apply {
            putExtra("A", "abc")
            putExtra("BB", "Testing")
        }.also {
            startActivity(it)
        }
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
        replay()
    }

    private fun replay() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD) {
            if (resultCode == RESULT_OK) {
                val nickname = data?.getStringExtra("NICK")
                Log.d(TAG, "onActivityResult: ${nickname} ")
                replay()
            }
        }
    }
}