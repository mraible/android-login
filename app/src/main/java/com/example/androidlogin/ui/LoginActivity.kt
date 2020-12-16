package com.example.androidlogin.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlogin.OktaLoginApplication
import com.example.androidlogin.OktaManager
import com.okta.oidc.*
import com.okta.oidc.util.AuthorizationException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val oktaManager: OktaManager by lazy { (application as OktaLoginApplication).oktaManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOktaCallback()
        setupViews()
    }

    private fun setupOktaCallback() {
        oktaManager.registerWebAuthCallback(getAuthCallback(), this)  // <1>
    }

    private fun setupViews() {
        signInButton.setOnClickListener {
            val payload = AuthenticationPayload.Builder().build()
            oktaManager.signIn(this, payload)  // <2>
        }
    }

    private fun getAuthCallback(): ResultCallback<AuthorizationStatus, AuthorizationException> {
        return object : ResultCallback<AuthorizationStatus, AuthorizationException> {
            override fun onSuccess(result: AuthorizationStatus) {  // <3>
                when (result) {
                    AuthorizationStatus.AUTHORIZED -> {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }
                    AuthorizationStatus.SIGNED_OUT -> showShortToast("Signed out")  // <4>
                    AuthorizationStatus.CANCELED -> showShortToast("Canceled")
                    AuthorizationStatus.ERROR -> showShortToast("Error")
                    AuthorizationStatus.EMAIL_VERIFICATION_AUTHENTICATED -> showShortToast("Email verification authenticated")
                    AuthorizationStatus.EMAIL_VERIFICATION_UNAUTHENTICATED -> showShortToast("Email verification unauthenticated")
                }
            }

            override fun onCancel() {
                showShortToast("Canceled")
            }

            override fun onError(msg: String?, exception: AuthorizationException?) {
                showShortToast("Error: $msg")
            }
        }
    }
}
