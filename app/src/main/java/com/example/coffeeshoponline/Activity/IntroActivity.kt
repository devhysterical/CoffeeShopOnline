package com.example.coffeeshoponline.activity

import android.content.Intent
import android.os.Bundle
import com.example.coffeeshoponline.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {

    private val binding: ActivityIntroBinding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}