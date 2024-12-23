package com.example.coffeeshoponline.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshoponline.adapter.CategoryAdapter
import com.example.coffeeshoponline.adapter.OffersAdapter
import com.example.coffeeshoponline.adapter.PopularAdapter
import com.example.coffeeshoponline.databinding.ActivityMainBinding
import com.example.coffeeshoponline.viewmodel.MainViewModel

// MainActivity là màn hình chính của ứng dụng Coffee Shop Online.
class MainActivity : BaseActivity() {

    // Khởi tạo ViewModel cho việc xử lý logic và dữ liệu của MainActivity.
    private val viewModel = MainViewModel()

    // Sử dụng View Binding để thao tác với giao diện từ layout activity_main.xml.
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Phương thức onCreate được gọi khi Activity được khởi tạo.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // Gán layout từ View Binding cho Activity.

        // Loại bỏ background mặc định của thanh điều hướng dưới cùng (bottom navigation bar).
        binding.bottomNavigation.background = null

        // Khởi tạo các danh mục, mục phổ biến và ưu đãi.
        initCategory()
        initPopular()
        initOffer()

        // Xử lý sự kiện menu dưới cùng.
        bottomMenu()
    }

    // Hàm xử lý sự kiện khi người dùng nhấn vào nút "Cart" trong menu dưới cùng.
    private fun bottomMenu() {
        binding.cartBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, CartActivity::class.java) // Chuyển đến màn hình CartActivity.
            startActivity(intent) // Khởi chạy CartActivity.
        }
    }

    // Hàm khởi tạo danh sách các ưu đãi (Offers).
    private fun initOffer() {
        binding.progressBarOffer.visibility = View.VISIBLE // Hiển thị ProgressBar khi tải dữ liệu ưu đãi.
        viewModel.offer.observe(this, Observer { offers ->
            // Cài đặt LayoutManager và Adapter cho RecyclerView ưu đãi.
            binding.recyclerViewOffer.layoutManager =
                LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, // Hiển thị danh sách ngang.
                    false
                )
            binding.recyclerViewOffer.adapter = OffersAdapter(offers) // Cập nhật dữ liệu ưu đãi cho Adapter.
            binding.progressBarOffer.visibility = View.GONE // Ẩn ProgressBar sau khi tải xong dữ liệu.
        })
        viewModel.loadOffer() // Gọi hàm trong ViewModel để tải dữ liệu ưu đãi.
    }

    // Hàm khởi tạo danh sách các mục phổ biến (Popular).
    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE // Hiển thị ProgressBar khi tải dữ liệu mục phổ biến.
        viewModel.popular.observe(this, Observer { popularItems ->
            // Cài đặt LayoutManager và Adapter cho RecyclerView mục phổ biến.
            binding.recyclerViewPopular.layoutManager =
                LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, // Hiển thị danh sách ngang.
                    false
                )
            binding.recyclerViewPopular.adapter = PopularAdapter(popularItems) // Cập nhật dữ liệu mục phổ biến cho Adapter.
            binding.progressBarPopular.visibility = View.GONE // Ẩn ProgressBar sau khi tải xong dữ liệu.
        })
        viewModel.loadPopular() // Gọi hàm trong ViewModel để tải dữ liệu mục phổ biến.
    }

    // Hàm khởi tạo danh sách các danh mục (Category).
    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE // Hiển thị ProgressBar khi tải dữ liệu danh mục.
        viewModel.category.observe(this, Observer { categories ->
            // Cài đặt LayoutManager và Adapter cho RecyclerView danh mục.
            binding.recyclerViewCategory.layoutManager =
                LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, // Hiển thị danh sách ngang.
                    false
                )
            binding.recyclerViewCategory.adapter = CategoryAdapter(categories) // Cập nhật dữ liệu danh mục cho Adapter.
            binding.progressBarCategory.visibility = View.GONE // Ẩn ProgressBar sau khi tải xong dữ liệu.
        })
        viewModel.loadCategory() // Gọi hàm trong ViewModel để tải dữ liệu danh mục.
    }
}
