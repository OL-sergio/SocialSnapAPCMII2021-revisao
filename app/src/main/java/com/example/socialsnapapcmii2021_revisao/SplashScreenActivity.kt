package com.example.socialsnapapcmii2021_revisao

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = Firebase.auth

        val currentUser = auth.currentUser


        currentUser?.let {
            val intent = Intent ( this, MainActivity::class.java)
            startActivity(intent)

        } ?:run{
                val intent = Intent ( this, LoginActivity::class.java)
                startActivity(intent)

            }

    }

}