package com.learning.learningbydoing.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.learning.learningbydoing.R
import com.learning.learningbydoing.databinding.ActivityLoginBinding
import com.learning.learningbydoing.presentation.login.LoginActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayAndGoToLogin()
    }

    private fun delayAndGoToLogin() {
        Handler(Looper.getMainLooper())
                .postDelayed({
                    startActivity<LoginActivity>()
                    finish()
                }, 1200)
    }
}