package br.com.moviesmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.moviesmanager.R
import br.com.moviesmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.mainTb)
        supportActionBar?.title = getString(R.string.app_name)
    }
}