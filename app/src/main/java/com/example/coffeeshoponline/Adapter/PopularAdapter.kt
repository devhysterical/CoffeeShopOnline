package com.example.coffeeshoponline.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeshoponline.activity.DetailedActivity
import com.example.coffeeshoponline.databinding.ViewholderPopularBinding
import com.example.coffeeshoponline.model.ItemsModel

// Adapter cho RecyclerView hiển thị danh sách các mặt hàng phổ biến (Popular Items).
class PopularAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private var context: Context? = null // Biến lưu ngữ cảnh của adapter.

    // Tạo ViewHolder cho từng mục trong danh sách mặt hàng phổ biến.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        context = parent.context // Lấy ngữ cảnh từ ViewGroup cha.
        val binding = ViewholderPopularBinding.inflate(
            LayoutInflater.from(context), parent, false
        ) // Inflate layout từ file XML ViewholderPopularBinding.
        return ViewHolder(binding) // Trả về một ViewHolder mới.
    }

    // Gán dữ liệu vào từng mục trong danh sách mặt hàng phổ biến.
    @SuppressLint("SetTextI18n") // Ignore cảnh báo liên quan đến việc ghép chuỗi với kiểu dữ liệu số.
    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        val item = items[position] // Lấy đối tượng mặt hàng tại vị trí hiện tại.

        // Gán dữ liệu vào các thành phần trong Viewholder:
        holder.binding.titleTxt.text = item.title // Hiển thị tên sản phẩm.
        holder.binding.priceTxt.text = "$" + item.price.toString() // Hiển thị giá sản phẩm.
        holder.binding.ratingBar.rating = item.rating.toFloat() // Hiển thị đánh giá của sản phẩm.
        holder.binding.extraTxt.text = item.extra // Hiển thị thông tin bổ sung của sản phẩm.

        // Sử dụng Glide để tải và hiển thị hình ảnh từ URL.
        Glide.with(holder.itemView.context)
            .load(item.picUrl[0]) // URL của hình ảnh.
            .into(holder.binding.shapeableImageView) // Hiển thị hình ảnh vào ImageView.

        // Xử lý sự kiện nhấn vào một mục trong danh sách.
        holder.itemView.setOnClickListener {
            // Tạo Intent để mở DetailedActivity.
            val intent = Intent(holder.itemView.context, DetailedActivity::class.java)
            intent.putExtra("object", item) // Truyền dữ liệu của mặt hàng được nhấn qua Intent.
            holder.itemView.context.startActivity(intent) // Bắt đầu Activity hiển thị chi tiết.
        }
    }

    // Trả về tổng số mục trong danh sách mặt hàng phổ biến.
    override fun getItemCount(): Int = items.size

    // Lớp ViewHolder để quản lý các View của từng mục trong danh sách mặt hàng phổ biến.
    inner class ViewHolder(val binding: ViewholderPopularBinding) :
        RecyclerView.ViewHolder(binding.root) // Ánh xạ root view từ ViewBinding.
}
