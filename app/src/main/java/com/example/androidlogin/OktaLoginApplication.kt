package com.example.androidlogin

import android.app.Application

class OktaLoginApplication : Application() {

    lateinit var oktaManager: OktaManager

    override fun onCreate() {
        super.onCreate()
        oktaManager = OktaManager(this)
    }
}

