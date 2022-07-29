package com.learning.learningbydoing.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.learning.learningbydoing.R
import com.learning.learningbydoing.databinding.ActivityLoginBinding
import com.learning.learningbydoing.presentation.main.MainActivity
import com.learning.learningbydoing.presentation.register.RegisterActivity
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        onAction()
    }

    private fun onAction() {
        loginBinding.apply {
            btnLogin.setOnClickListener {
                startActivity<MainActivity>()
            }

            btnRegister.setOnClickListener {
                startActivity<RegisterActivity>()
            }
        }
    }


}