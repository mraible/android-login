package com.example.androidlogin.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlogin.OktaLoginApplication
import com.example.androidlogin.OktaManager

class SplashActivity : AppCompatActivity() {

    private val oktaManager: OktaManager by lazy { (application as OktaLoginApplication).oktaManager }
    // <1>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIfUserIsAuthenticated()
    }

    private fun checkIfUserIsAuthenticated() {
        val intent = if (oktaManager.isAuthenticated()) {
            Intent(this, HomeActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
