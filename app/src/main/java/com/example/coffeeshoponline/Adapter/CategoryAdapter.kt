package com.example.coffeeshoponline.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshoponline.R
import com.example.coffeeshoponline.databinding.ViewholderCategoryBinding
import com.example.coffeeshoponline.model.CategoryModel

// Adapter cho RecyclerView hiển thị danh sách các danh mục sản phẩm.
class CategoryAdapter(val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var context: Context // Biến lưu trữ ngữ cảnh của adapter.
    private var selectedPosition = -1 // Vị trí của danh mục được chọn (mặc định là -1, không có mục nào được chọn).
    private var lastSelectedPosition = -1 // Vị trí của danh mục được chọn trước đó.

    // Tạo ViewHolder với layout của từng mục trong danh sách danh mục.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        context = parent.context // Lấy ngữ cảnh từ `parent`.
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(context), parent, false
        ) // Inflate layout từ file XML ViewholderCategoryBinding.
        return ViewHolder(binding)
    }

    // Gán dữ liệu cho từng mục trong danh sách danh mục.
    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val item = items[holder.adapterPosition] // Lấy đối tượng danh mục ở vị trí hiện tại.
        holder.binding.titleCat.text = item.title // Hiển thị tên danh mục.

        // Xử lý sự kiện nhấn vào danh mục.
        holder.binding.root.setOnClickListener {
            val currentPosition = holder.adapterPosition // Lấy vị trí hiện tại.
            if (currentPosition != RecyclerView.NO_POSITION) { // Kiểm tra vị trí hợp lệ.
                lastSelectedPosition = selectedPosition // Gán vị trí hiện tại cho biến `lastSelectedPosition`.
                selectedPosition = currentPosition // Cập nhật vị trí mới được chọn.
                notifyItemChanged(lastSelectedPosition) // Cập nhật giao diện cho mục trước đó.
                notifyItemChanged(selectedPosition) // Cập nhật giao diện cho mục hiện tại.
            }
        }

        // Thay đổi giao diện của mục khi được chọn hoặc không được chọn.
        if (selectedPosition == holder.adapterPosition) {
            // Nếu mục hiện tại là mục được chọn, thay đổi nền thành màu cam.
            holder.binding.titleCat.setBackgroundResource(R.drawable.orange_bg)
        } else {
            // Nếu không phải mục được chọn, thay đổi nền về mặc định.
            holder.binding.titleCat.setBackgroundResource(R.drawable.edittext_bg)
        }
    }

    // Trả về tổng số danh mục trong danh sách.
    override fun getItemCount(): Int = items.size

    // Lớp ViewHolder dùng để nắm giữ và quản lý các View trong từng mục danh mục.
    inner class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}
