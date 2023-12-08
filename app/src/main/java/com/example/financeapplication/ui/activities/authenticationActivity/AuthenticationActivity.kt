package com.example.financeapplication.ui.activities.authenticationActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.financeapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

    }
}