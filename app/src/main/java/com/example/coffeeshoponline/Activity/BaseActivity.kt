package com.example.coffeeshoponline.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

// BaseActivity là lớp cơ sở dành cho các Activity trong ứng dụng,
// cung cấp các cấu hình hoặc chức năng dùng chung.
open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cấu hình cửa sổ để layout của Activity chiếm toàn bộ màn hình,
        // bao gồm cả phần phía sau thanh trạng thái và thanh điều hướng.
        // Điều này giúp tạo hiệu ứng giao diện toàn màn hình hoặc tuỳ chỉnh.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // Cờ để bỏ giới hạn layout
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS  // Cờ được áp dụng
        )
    }
}
