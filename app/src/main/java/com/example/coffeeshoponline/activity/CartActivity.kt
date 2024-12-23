package com.example.coffeeshoponline.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshoponline.adapter.CartAdapter
import com.example.coffeeshoponline.databinding.ActivityCartBinding
import com.example.coffeeshoponline.helper.ChangeNumberItemsListener
import com.example.coffeeshoponline.helper.ManagmentCart

// CartActivity quản lý giao diện và logic của giỏ hàng trong ứng dụng.
class CartActivity : BaseActivity() {

    // Biến quản lý giỏ hàng, sử dụng để xử lý logic thêm/xóa/sửa số lượng sản phẩm.
    lateinit var management: ManagmentCart

    // Biến lưu giá trị thuế.
    private var tax: Double = 0.0

    // Binding cho layout của Activity, giúp truy cập trực tiếp các view trong layout.
    private val binding: ActivityCartBinding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }

    // Phương thức onCreate, khởi tạo Activity.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Khởi tạo đối tượng quản lý giỏ hàng.
        management = ManagmentCart(this)

        // Tính toán chi phí ban đầu cho giỏ hàng.
        calculateCart()

        // Thiết lập các sự kiện và biến cần thiết.
        setVariable()

        // Khởi tạo danh sách hiển thị các sản phẩm trong giỏ hàng.
        initCartList()
    }

    // Phương thức thiết lập RecyclerView để hiển thị danh sách sản phẩm trong giỏ hàng.
    private fun initCartList() {
        with(binding) {
            // Thiết lập layout theo chiều dọc cho RecyclerView.
            rvCartView.layoutManager =
                LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)

            // Gán adapter cho RecyclerView, lấy danh sách sản phẩm từ management.
            rvCartView.adapter = CartAdapter(
                management.getListCart(), // Danh sách sản phẩm trong giỏ hàng.
                this@CartActivity,
                // Lắng nghe sự thay đổi số lượng sản phẩm, cập nhật lại chi phí.
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateCart() // Tính toán lại giỏ hàng khi có thay đổi.
                    }
                }
            )
        }
    }

    // Phương thức thiết lập các sự kiện cho nút và các view trong layout.
    private fun setVariable() {
        // Sự kiện quay lại khi nhấn nút back.
        binding.ivBack.setOnClickListener { finish() }

        // Sự kiện khi nhấn nút tiến hành thanh toán.
        binding.proceedCheckoutBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Xác nhận thanh toán") // Tiêu đề hộp thoại.
                .setMessage("Bạn có muốn tiến hành thanh toán không?") // Nội dung hộp thoại.
                .setPositiveButton("Có") { _, _ ->
                    // Hiển thị thông báo khi thanh toán thành công.
                    showToast("Thanh toán thành công!!!")
                }
                .setNegativeButton("Không") { dialog, _ ->
                    // Đóng hộp thoại nếu người dùng chọn "Không".
                    dialog.dismiss()
                }
                .show()
        }
    }

    // Phương thức hiển thị thông báo dạng Toast.
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Phương thức tính toán tổng chi phí giỏ hàng bao gồm giá sản phẩm, thuế và phí giao hàng.
    @SuppressLint("SetTextI18n") // Để tắt cảnh báo về việc đặt text trực tiếp trong code.
    private fun calculateCart() {
        val percentTax = 0.02 // Tỷ lệ phần trăm thuế.
        val delivery = 15.0 // Phí giao hàng cố định.

        // Tính toán thuế dựa trên tổng phí sản phẩm.
        tax = Math.round((management.getTotalFee() * percentTax) * 100) / 100.0

        // Tổng chi phí bao gồm giá sản phẩm, thuế và phí giao hàng.
        val total = Math.round((management.getTotalFee() + tax + delivery) * 100) / 100.0

        // Tổng giá của sản phẩm trong giỏ hàng.
        val itemTotal = Math.round(management.getTotalFee() * 100) / 100.0

        // Cập nhật các giá trị đã tính toán lên giao diện.
        with(binding) {
            subTotalPriceTxt.text = "$$itemTotal" // Hiển thị giá sản phẩm.
            totalTaxPriceTxt.text = "$$tax" // Hiển thị thuế.
            deliveryPriceTxt.text = "$$delivery" // Hiển thị phí giao hàng.
            totalPriceTxt.text = "$$total" // Hiển thị tổng chi phí.
        }
    }
}
