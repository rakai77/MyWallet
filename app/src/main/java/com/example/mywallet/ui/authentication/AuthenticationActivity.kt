package com.example.mywallet.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mywallet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }
}