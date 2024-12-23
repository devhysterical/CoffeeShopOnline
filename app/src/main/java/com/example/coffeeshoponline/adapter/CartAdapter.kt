package com.example.coffeeshoponline.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.coffeeshoponline.databinding.ViewholderCartBinding
import com.example.coffeeshoponline.helper.ChangeNumberItemsListener
import com.example.coffeeshoponline.helper.ManagmentCart
import com.example.coffeeshoponline.model.ItemsModel

// Adapter cho RecyclerView hiển thị các sản phẩm trong giỏ hàng.
class CartAdapter(
    val listItemSelected: ArrayList<ItemsModel>, // Danh sách các sản phẩm trong giỏ hàng.
    context: Context, // Ngữ cảnh để khởi tạo các tài nguyên.
    var changeNumberItemsListener: ChangeNumberItemsListener? = null // Lắng nghe sự thay đổi số lượng sản phẩm.
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    // Quản lý giỏ hàng, sử dụng để cập nhật số lượng sản phẩm và tính toán tổng tiền.
    private val managementCart = ManagmentCart(context)

    // Khởi tạo ViewHolder với layout của từng mục trong danh sách giỏ hàng.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ViewholderCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    // Gán dữ liệu cho từng mục trong danh sách giỏ hàng.
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val item = listItemSelected[position] // Lấy sản phẩm ở vị trí hiện tại.

        // Hiển thị thông tin của sản phẩm.
        holder.binding.titleTxt.text = item.title // Tên sản phẩm.
        holder.binding.feeEachItem.text = "$" + item.price.toString() // Giá mỗi sản phẩm.
        holder.binding.numberItemTxt.text = item.numberInCart.toString() // Số lượng sản phẩm.
        holder.binding.totalEachItem.text = "$${Math.round(item.numberInCart * item.price)}" // Tổng tiền cho sản phẩm này.

        // Sử dụng Glide để tải ảnh sản phẩm từ URL và hiển thị.
        Glide.with(holder.itemView.context)
            .load(item.picUrl[0]) // URL của ảnh sản phẩm.
            .apply(RequestOptions().transform(CenterCrop())) // Áp dụng hiệu ứng cắt giữa (CenterCrop).
            .into(holder.binding.cartPicture) // Hiển thị ảnh trong ImageView.

        // Xử lý khi nhấn nút "+" để tăng số lượng sản phẩm.
        holder.binding.plusCartBtn.setOnClickListener {
            managementCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onChanged() {
                    notifyDataSetChanged() // Cập nhật giao diện sau khi thay đổi số lượng.
                    changeNumberItemsListener?.onChanged() // Gọi callback để thông báo thay đổi tổng số lượng.
                }
            })
        }

        // Xử lý khi nhấn nút "-" để giảm số lượng sản phẩm.
        holder.binding.minusCartBtn.setOnClickListener {
            managementCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onChanged() {
                    notifyDataSetChanged() // Cập nhật giao diện sau khi thay đổi số lượng.
                    changeNumberItemsListener?.onChanged() // Gọi callback để thông báo thay đổi tổng số lượng.
                }
            })
        }
    }

    // Trả về tổng số sản phẩm trong danh sách giỏ hàng.
    override fun getItemCount(): Int = listItemSelected.size

    // Lớp ViewHolder dùng để nắm giữ và quản lý các View trong từng mục giỏ hàng.
    inner class ViewHolder(val binding: ViewholderCartBinding) :
        RecyclerView.ViewHolder(binding.root)
}
