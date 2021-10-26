package com.example.networkingussd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countDownTimer = object : CountDownTimer(1000, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                val intent = Intent(this@MainActivity, Home::class.java)
                startActivity(intent)
                finish()
            }
        }
        countDownTimer.start()
    }
}