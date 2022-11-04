package com.example.woloxapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.woloxapp.ui.Home.tablayout.fragments.NewDetail.NewDetailFragment
import com.example.woloxapp.utils.Constants
import java.util.*

class NewDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val newId = intent.extras?.getInt(Constants.NEW_ID)
        val fragment = newId?.let { NewDetailFragment(it) }
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_new_detail)
        supportFragmentManager.commit {
            if (fragment != null) {
                replace(R.id.fragment_new_detail, fragment)
            }
        }
    }
}
