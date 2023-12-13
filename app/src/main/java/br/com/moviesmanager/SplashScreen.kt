package br.com.moviesmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.moviesmanager.R
import android.os.Handler
import android.view.WindowManager
import android.content.Intent
import android.os.Looper
import br.com.moviesmanager.view.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

    }
}