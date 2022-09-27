package com.example.woloxapp

import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
import androidx.appcompat.app.AppCompatActivity
import com.example.woloxapp.ui.login.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        with(LayoutParams()) { getWindow().setFlags(FLAG_TRANSLUCENT_NAVIGATION, FLAG_TRANSLUCENT_NAVIGATION) }
        // TODO add authManager
        val auth = true
        if (auth) supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, LoginFragment()).commit()
    }
}
