package com.example.coffeeshoponline.activity

import android.content.Intent
import android.os.Bundle
import com.example.coffeeshoponline.databinding.ActivityIntroBinding

// IntroActivity là màn hình giới thiệu (intro) ban đầu của ứng dụng.
class IntroActivity : BaseActivity() {

    // Sử dụng View Binding để dễ dàng thao tác với các thành phần giao diện trong layout.
    private val binding: ActivityIntroBinding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    // Phương thức onCreate được gọi khi Activity được khởi tạo.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // Gán layout từ View Binding cho Activity.

        // Thiết lập sự kiện click cho nút "Start" (bắt đầu).
        binding.startButton.setOnClickListener {
            // Khi người dùng nhấn nút, khởi chạy MainActivity.
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
