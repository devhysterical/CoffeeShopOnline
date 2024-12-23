package com.example.coffeeshoponline.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshoponline.R
import com.example.coffeeshoponline.databinding.ViewholderSizeBinding

// Adapter cho RecyclerView hiển thị danh sách các kích thước (Size) của sản phẩm.
class SizeAdapter(val context: Context, val items: MutableList<String>) :
    RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    private var selectedPosition = -1 // Vị trí kích thước được chọn hiện tại.
    private var lastSelectedPosition = -1 // Vị trí kích thước được chọn trước đó.

    // Tạo ViewHolder cho từng mục trong danh sách kích thước.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeAdapter.ViewHolder {
        val binding = ViewholderSizeBinding.inflate(
            LayoutInflater.from(context), parent, false
        ) // Inflate layout từ file XML ViewholderSizeBinding.
        return ViewHolder(binding) // Trả về một ViewHolder mới.
    }

    // Gán dữ liệu và xử lý sự kiện cho từng mục trong danh sách kích thước.
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SizeAdapter.ViewHolder, position: Int) {
        // Xử lý sự kiện nhấn vào một kích thước.
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition // Lưu vị trí đã chọn trước đó.
            selectedPosition = position // Cập nhật vị trí được chọn hiện tại.
            notifyItemChanged(lastSelectedPosition) // Cập nhật lại trạng thái mục cũ.
            notifyItemChanged(selectedPosition) // Cập nhật lại trạng thái mục mới.
        }

        // Thay đổi giao diện nền dựa trên trạng thái được chọn.
        if (selectedPosition == position) {
            holder.binding.coffee.setBackgroundResource(R.drawable.orange_bg) // Nền màu cam nếu được chọn.
        } else {
            holder.binding.coffee.setBackgroundResource(R.drawable.size_bg) // Nền mặc định nếu không được chọn.
        }

        // Thay đổi kích thước của View (có thể là hình ảnh hoặc nút) theo vị trí trong danh sách.
        val imageSize = when (position) {
            0 -> 45.dpToPx(context) // Kích thước cho mục đầu tiên.
            1 -> 50.dpToPx(context) // Kích thước cho mục thứ hai.
            2 -> 55.dpToPx(context) // Kích thước cho mục thứ ba.
            3 -> 65.dpToPx(context) // Kích thước cho mục thứ tư.
            else -> 70.dpToPx(context) // Kích thước mặc định cho các mục còn lại.
        }

        // Cập nhật kích thước của View dựa trên giá trị được tính toán.
        val layoutParam = holder.binding.coffee.layoutParams
        layoutParam.width = imageSize // Đặt chiều rộng.
        layoutParam.height = imageSize // Đặt chiều cao.
        holder.binding.coffee.layoutParams = layoutParam // Gán lại tham số layout.
    }

    // Hàm chuyển đổi giá trị dp sang px để phù hợp với màn hình.
    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    // Trả về số lượng mục trong danh sách kích thước.
    override fun getItemCount(): Int = items.size

    // Lớp ViewHolder để quản lý các View của từng mục trong danh sách kích thước.
    inner class ViewHolder(val binding: ViewholderSizeBinding) :
        RecyclerView.ViewHolder(binding.root) // Ánh xạ root view từ ViewBinding.
}
