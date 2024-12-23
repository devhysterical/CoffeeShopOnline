package com.example.coffeeshoponline.model

import android.os.Parcel
import android.os.Parcelable

// Lớp dữ liệu đại diện cho một sản phẩm trong ứng dụng Coffee Shop.
data class ItemsModel(
    var title: String = "", // Tiêu đề của sản phẩm.
    var description: String, // Mô tả sản phẩm.
    var picUrl: ArrayList<String> = ArrayList(), // Danh sách URL ảnh của sản phẩm.
    var price: Double = 0.0, // Giá sản phẩm.
    var rating: Double = 0.0, // Đánh giá của sản phẩm (từ 0 đến 5).
    var numberInCart: Int = 0, // Số lượng sản phẩm trong giỏ hàng.
    var extra: String = "" // Thông tin bổ sung về sản phẩm.
) : Parcelable { // Implement Parcelable để truyền đối tượng giữa các Activity.

    // Constructor không tham số, cần thiết để Firebase hoặc các framework khác tạo đối tượng mặc định.
    constructor() : this("", "", ArrayList(), 0.0, 0.0, 0, "")

    // Constructor để tái tạo đối tượng từ `Parcel`.
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(), // Đọc chuỗi `title` từ Parcel.
        parcel.readString().toString(), // Đọc chuỗi `description` từ Parcel.
        parcel.createStringArrayList() as ArrayList<String>, // Đọc danh sách chuỗi `picUrl` từ Parcel.
        parcel.readDouble(), // Đọc giá trị `price` từ Parcel.
        parcel.readDouble(), // Đọc giá trị `rating` từ Parcel.
        parcel.readInt(), // Đọc giá trị `numberInCart` từ Parcel.
        parcel.readString().toString() // Đọc chuỗi `extra` từ Parcel.
    )

    // Ghi dữ liệu của đối tượng vào `Parcel` để truyền đi.
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title) // Ghi chuỗi `title` vào Parcel.
        parcel.writeString(description) // Ghi chuỗi `description` vào Parcel.
        parcel.writeStringList(picUrl) // Ghi danh sách chuỗi `picUrl` vào Parcel.
        parcel.writeDouble(price) // Ghi giá trị `price` vào Parcel.
        parcel.writeDouble(rating) // Ghi giá trị `rating` vào Parcel.
        parcel.writeInt(numberInCart) // Ghi giá trị `numberInCart` vào Parcel.
        parcel.writeString(extra) // Ghi chuỗi `extra` vào Parcel.
    }

    // Hàm mô tả nội dung của `Parcelable`. Trong hầu hết các trường hợp, trả về 0.
    override fun describeContents(): Int {
        return 0
    }

    // Companion object để cung cấp các phương thức tạo đối tượng từ `Parcel` hoặc mảng.
    companion object CREATOR : Parcelable.Creator<ItemsModel> {

        // Phương thức tạo một đối tượng `ItemsModel` từ `Parcel`.
        override fun createFromParcel(parcel: Parcel): ItemsModel {
            return ItemsModel(parcel)
        }

        // Phương thức tạo một mảng `ItemsModel` với kích thước cụ thể.
        override fun newArray(size: Int): Array<ItemsModel?> {
            return arrayOfNulls(size)
        }
    }
}
