package com.example.coffeeshoponline.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.coffeeshoponline.adapter.SizeAdapter
import com.example.coffeeshoponline.databinding.ActivityDetailedBinding
import com.example.coffeeshoponline.helper.ManagmentCart
import com.example.coffeeshoponline.model.ItemsModel

// DetailedActivity quản lý giao diện và logic của màn hình chi tiết sản phẩm.
class DetailedActivity : BaseActivity() {

    // Biến lưu thông tin sản phẩm đang được hiển thị.
    private lateinit var item: ItemsModel

    // Binding cho layout của Activity, giúp truy cập trực tiếp các view trong layout.
    private val binding: ActivityDetailedBinding by lazy {
        ActivityDetailedBinding.inflate(layoutInflater)
    }

    // Đối tượng quản lý giỏ hàng.
    private lateinit var managementcart: ManagmentCart

    // Phương thức onCreate, khởi tạo Activity.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Khởi tạo đối tượng quản lý giỏ hàng.
        managementcart = ManagmentCart(this)

        // Lấy dữ liệu từ intent và thiết lập giao diện.
        bundle()

        // Khởi tạo danh sách kích thước của sản phẩm.
        initSizeList()
    }

    // Phương thức hiển thị thông báo dạng Toast.
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Phương thức khởi tạo danh sách kích thước sản phẩm.
    private fun initSizeList() {
        val sizeList = ArrayList<String>() // Danh sách kích thước sản phẩm.
        sizeList.add("1") // Kích thước 1.
        sizeList.add("2") // Kích thước 2.
        sizeList.add("3") // Kích thước 3.
        sizeList.add("4") // Kích thước 4.

        // Gán adapter cho RecyclerView để hiển thị danh sách kích thước.
        binding.rvSizeList.adapter = SizeAdapter(this, sizeList)

        // Thiết lập layout cho RecyclerView theo chiều ngang.
        binding.rvSizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Danh sách màu hoặc hình ảnh sản phẩm (nếu có nhiều màu hoặc hình).
        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }

        // Sử dụng Glide để tải hình ảnh sản phẩm và áp dụng hiệu ứng bo tròn góc.
        Glide.with(this)
            .load(colorList[0]) // Tải hình ảnh đầu tiên trong danh sách.
            .apply(RequestOptions.bitmapTransform(RoundedCorners(100))) // Hiệu ứng bo tròn góc.
            .into(binding.shapeableImageView) // Hiển thị hình ảnh trong ImageView.
    }

    // Phương thức xử lý dữ liệu từ intent và thiết lập các view.
    @SuppressLint("SetTextI18n")
    private fun bundle() {
        binding.apply {
            // Lấy đối tượng sản phẩm từ intent (được truyền từ Activity trước).
            item = intent.getParcelableExtra("object")!!

            // Hiển thị thông tin sản phẩm lên giao diện.
            titleTxt.text = item.title // Tiêu đề sản phẩm.
            descriptionTxt.text = item.description // Mô tả sản phẩm.
            priceTxt.text = "$" + item.price // Giá sản phẩm.
            ratingBar.rating = item.rating.toFloat() // Đánh giá sản phẩm.

            // Xử lý sự kiện khi nhấn nút "Thêm vào giỏ hàng".
            binding.addToCart.setOnClickListener {
                // Kiểm tra nếu chưa chọn kích thước sản phẩm.
                if (binding.rvSizeList.adapter?.itemCount == 0) {
                    showToast("Vui lòng chọn kích thước trước khi thêm vào giỏ hàng!")
                } else {
                    // Cập nhật số lượng sản phẩm vào giỏ hàng.
                    item.numberInCart = Integer.valueOf(
                        numberItemTxt.text.toString()
                    )
                    managementcart.insertItems(item) // Thêm sản phẩm vào giỏ hàng.
                    showToast("Đã thêm sản phẩm vào giỏ hàng!")
                }
            }

            // Xử lý sự kiện khi nhấn nút quay lại.
            ivBack.setOnClickListener {
                finish() // Kết thúc Activity hiện tại.
            }

            // Xử lý sự kiện khi nhấn nút tăng số lượng sản phẩm.
            plusCart.setOnClickListener {
                numberItemTxt.text = (item.numberInCart + 1).toString()
                item.numberInCart++ // Tăng số lượng sản phẩm.
            }

            // Xử lý sự kiện khi nhấn nút giảm số lượng sản phẩm.
            minusCart.setOnClickListener {
                if (item.numberInCart > 0) {
                    numberItemTxt.text = (item.numberInCart - 1).toString()
                    item.numberInCart-- // Giảm số lượng sản phẩm.
                }
            }
        }
    }
}
