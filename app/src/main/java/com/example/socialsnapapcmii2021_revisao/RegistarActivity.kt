package com.example.socialsnapapcmii2021_revisao

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registar_activity.*

class RegistarActivity : AppCompatActivity(){

        companion object {

            val TAG = "LoginActivity"
        }

        private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registar_activity)

        auth = Firebase.auth


        buttonLoginGoogle.setOnClickListener {

            auth.createUserWithEmailAndPassword(

                editVextUserName.text.toString(),
                editTextPassword.text.toString())
                .addOnCompleteListener (this) { task ->
                    if (task.isSuccessful) {
                       Log.d(TAG,"" )
                        val user = auth.currentUser
                        val intent = Intent ( this, MainActivity::class.java)
                        startActivity(intent)

                    }else{
                        Log.w(TAG, "", task.exception)
                        Toast.makeText(baseContext, "createUserWithEmail:failure",
                            Toast.LENGTH_SHORT).show()
                    }

                }
        }

    }

}