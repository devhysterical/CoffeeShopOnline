package com.example.coffeeshoponline.helper

import android.content.Context
import com.example.coffeeshoponline.model.ItemsModel

// Lớp quản lý giỏ hàng cho ứng dụng Coffee Shop.
class ManagmentCart(val context: Context) {

    // Sử dụng TinyDB (một lớp helper lưu trữ dữ liệu dưới dạng SharedPreferences).
    private val tinyDB = TinyDB(context)

    // Hàm thêm sản phẩm vào giỏ hàng.
    fun insertItems(item: ItemsModel) {
        // Lấy danh sách sản phẩm hiện có trong giỏ hàng.
        var listItem = getListCart()

        // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng hay chưa.
        val existAlready = listItem.any { it.title == item.title }
        val index = listItem.indexOfFirst { it.title == item.title }

        if (existAlready) {
            // Nếu sản phẩm đã tồn tại, cập nhật số lượng sản phẩm trong giỏ hàng.
            listItem[index].numberInCart = item.numberInCart
        } else {
            // Nếu sản phẩm chưa tồn tại, thêm sản phẩm mới vào danh sách.
            listItem.add(item)
        }

        // Cập nhật danh sách giỏ hàng vào TinyDB.
        tinyDB.putListObject("CartList", listItem)

//        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show()
        // (Dòng này bị comment lại, có thể dùng để hiển thị thông báo khi thêm sản phẩm thành công).
    }

    // Hàm lấy danh sách sản phẩm trong giỏ hàng.
    fun getListCart(): ArrayList<ItemsModel> {
        // Lấy danh sách sản phẩm từ TinyDB, trả về danh sách trống nếu không có dữ liệu.
        return tinyDB.getListObject("CartList") ?: arrayListOf()
    }

    // Hàm giảm số lượng sản phẩm tại một vị trí cụ thể trong giỏ hàng.
    fun minusItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (listItems[position].numberInCart == 1) {
            // Nếu số lượng sản phẩm là 1, xóa sản phẩm khỏi danh sách.
            listItems.removeAt(position)
        } else {
            // Nếu số lượng lớn hơn 1, giảm số lượng sản phẩm.
            listItems[position].numberInCart--
        }

        // Cập nhật danh sách sản phẩm trong TinyDB.
        tinyDB.putListObject("CartList", listItems)

        // Gọi callback để cập nhật giao diện sau khi thay đổi số lượng.
        listener.onChanged()
    }

    // Hàm tăng số lượng sản phẩm tại một vị trí cụ thể trong giỏ hàng.
    fun plusItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        // Tăng số lượng sản phẩm tại vị trí đã chọn.
        listItems[position].numberInCart++

        // Cập nhật danh sách sản phẩm trong TinyDB.
        tinyDB.putListObject("CartList", listItems)

        // Gọi callback để cập nhật giao diện sau khi thay đổi số lượng.
        listener.onChanged()
    }

    // Hàm tính tổng tiền của giỏ hàng.
    fun getTotalFee(): Double {
        val listItem = getListCart() // Lấy danh sách sản phẩm trong giỏ hàng.
        var fee = 0.0 // Biến lưu tổng tiền.

        // Duyệt qua từng sản phẩm để tính tổng giá trị.
        for (item in listItem) {
            fee += item.price * item.numberInCart // Tổng tiền = giá * số lượng.
        }

        return fee // Trả về tổng tiền.
    }
}
