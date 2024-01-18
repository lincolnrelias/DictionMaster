package com.dictionmaster

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val SPLASH_SCREEN_DURATION = 3000 // 3 seconds

        setContentView(R.layout.splash_screen)
        Handler(Looper.getMainLooper()).postDelayed(Runnable { // Start your main activity
            val mainIntent = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
            startActivity(mainIntent)
            finish()
        }, SPLASH_SCREEN_DURATION.toLong())
    }
}
