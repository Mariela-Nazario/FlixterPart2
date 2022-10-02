package com.mnazari2.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mnazari2.moviesapp.R.id

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id.content, MovieFragment(), null).commit()
    }
}