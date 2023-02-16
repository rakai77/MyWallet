package com.example.mywallet

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mywallet.databinding.ActivitySplashBinding
import com.example.mywallet.ui.authentication.AuthenticationActivity
import com.example.mywallet.ui.authentication.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fromBottomAnim = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        binding.ivSplash.startAnimation(fromBottomAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getToken.collect {
                        if (it.isNotEmpty()) {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                        } else {
                            startActivity(
                                Intent(
                                    this@SplashActivity,
                                    AuthenticationActivity::class.java
                                )
                            )
                            finish()
                        }
                    }
                }
            }
        }, 3000)
    }
}