package com.example.mobileapp_assignmentv1.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mobileapp_assignmentv1.R
import com.example.mobileapp_assignmentv1.activities.ListFixtures

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, ListFixtures::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
    }
