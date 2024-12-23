package com.example.coffeeshoponline.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeshoponline.databinding.ViewholderOfferBinding
import com.example.coffeeshoponline.model.ItemsModel

// Adapter cho RecyclerView hiển thị danh sách các ưu đãi (Offers).
class OffersAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    private var context: Context? = null // Biến lưu ngữ cảnh của adapter.

    // Tạo ViewHolder cho từng mục trong danh sách ưu đãi.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersAdapter.ViewHolder {
        context = parent.context // Lấy ngữ cảnh từ ViewGroup cha.
        val binding = ViewholderOfferBinding.inflate(
            LayoutInflater.from(context), parent, false
        ) // Inflate layout từ file XML ViewholderOfferBinding.
        return ViewHolder(binding) // Trả về một ViewHolder mới.
    }

    // Gán dữ liệu vào từng mục trong danh sách ưu đãi.
    @SuppressLint("SetTextI18n") // Ignore cảnh báo liên quan đến việc ghép chuỗi với kiểu dữ liệu số.
    override fun onBindViewHolder(holder: OffersAdapter.ViewHolder, position: Int) {
        val item = items[position] // Lấy đối tượng ưu đãi tại vị trí hiện tại.

        // Hiển thị tiêu đề của ưu đãi.
        holder.binding.titleTxt.text = item.title
        // Hiển thị giá của ưu đãi.
        holder.binding.priceTxt.text = "$" + item.price.toString()

        // Sử dụng thư viện Glide để tải và hiển thị hình ảnh từ URL.
        Glide.with(holder.itemView.context)
            .load(item.picUrl[0]) // URL của hình ảnh.
            .into(holder.binding.shapeableImageView) // Hiển thị hình ảnh vào ImageView.

        // Xử lý sự kiện nhấn vào một mục trong danh sách ưu đãi.
        holder.itemView.setOnClickListener {
            // Hiện tại, chưa có hành động nào được định nghĩa.
        }
    }

    // Trả về tổng số mục trong danh sách ưu đãi.
    override fun getItemCount(): Int = items.size

    // Lớp ViewHolder để quản lý các View của từng mục trong danh sách ưu đãi.
    inner class ViewHolder(val binding: ViewholderOfferBinding) :
        RecyclerView.ViewHolder(binding.root) // Ánh xạ root view từ ViewBinding.
}
