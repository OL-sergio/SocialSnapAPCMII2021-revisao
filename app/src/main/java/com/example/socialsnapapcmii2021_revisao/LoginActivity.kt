package com.example.socialsnapapcmii2021_revisao

import android.content.Intent

import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_activity.*


class LoginActivity : AppCompatActivity(){

    companion object {
        val TAG = "LoginActivity"
        val RC_SIGN_IN = 1001

    }

        private lateinit var auth: FirebaseAuth
        private var mGoogleSignInClient : GoogleSignInClient? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activity)

        auth = Firebase.auth


        textViewRegistar.setOnClickListener {
            val intent = Intent(this, RegistarActivity::class.java)
            startActivity(intent)

        }

        buttonLogin.setOnClickListener {
            auth.signInWithEmailAndPassword(
                editVextUserName.text.toString(),
                editTextPassword.text.toString())


                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signWithEmail:success")
                        val user = auth.currentUser

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Log.w(TAG, "signWithEmail:success", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }


        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken() //Falta o token
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        buttonLoginGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun  signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            }catch (e: ApiException){
                Log.w(TAG,"Google sign in failed", e)

            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener (this){ task ->

                if (task.isSuccessful){
                    Log.d (TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    val intent= Intent ( this, MainActivity::class.java)
                    startActivity(intent)

                }else {

                    Log.w(TAG, "signInWithCredential:failure",task.exception)
                    Snackbar.make(buttonLoginGoogle, "",Snackbar.LENGTH_SHORT).show()

                }

            }
    }

}