package com.example.coffeeshoponline.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeeshoponline.model.CategoryModel
import com.example.coffeeshoponline.model.ItemsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// ViewModel quản lý dữ liệu chính cho ứng dụng Coffee Shop.
class MainViewModel : ViewModel() {

    // Khởi tạo FirebaseDatabase instance.
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    // MutableLiveData để lưu trữ danh sách danh mục, sản phẩm phổ biến và ưu đãi.
    private val _category = MutableLiveData<MutableList<CategoryModel>>() // Dữ liệu danh mục.
    private val _popular = MutableLiveData<MutableList<ItemsModel>>() // Dữ liệu sản phẩm phổ biến.
    private val _offer = MutableLiveData<MutableList<ItemsModel>>() // Dữ liệu ưu đãi.

    // LiveData để quan sát dữ liệu từ View.
    val category: LiveData<MutableList<CategoryModel>> = _category
    val popular: LiveData<MutableList<ItemsModel>> = _popular
    val offer: LiveData<MutableList<ItemsModel>> = _offer

    // Hàm tải dữ liệu danh mục từ Firebase.
    fun loadCategory() {
        val ref = firebaseDatabase.getReference("Category") // Tham chiếu đến node "Category" trong Firebase.
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>() // Tạo danh sách lưu dữ liệu danh mục.

                // Lặp qua tất cả các child trong node "Category".
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(CategoryModel::class.java) // Chuyển đổi dữ liệu sang đối tượng CategoryModel.
                    if (list != null) {
                        lists.add(list) // Thêm danh mục vào danh sách nếu không null.
                    }
                }
                _category.value = lists // Cập nhật giá trị của LiveData.
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình tải dữ liệu (hiện tại để trống).
            }
        })
    }

    // Hàm tải dữ liệu sản phẩm phổ biến từ Firebase.
    fun loadPopular() {
        val ref = firebaseDatabase.getReference("Items") // Tham chiếu đến node "Items" trong Firebase.
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>() // Tạo danh sách lưu dữ liệu sản phẩm phổ biến.

                // Lặp qua tất cả các child trong node "Items".
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java) // Chuyển đổi dữ liệu sang đối tượng ItemsModel.
                    if (list != null) {
                        lists.add(list) // Thêm sản phẩm vào danh sách nếu không null.
                    }
                }
                _popular.value = lists // Cập nhật giá trị của LiveData.
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình tải dữ liệu (hiện tại để trống).
            }
        })
    }

    // Hàm tải dữ liệu ưu đãi từ Firebase.
    fun loadOffer() {
        val ref = firebaseDatabase.getReference("Offers") // Tham chiếu đến node "Offers" trong Firebase.
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>() // Tạo danh sách lưu dữ liệu ưu đãi.

                // Lặp qua tất cả các child trong node "Offers".
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java) // Chuyển đổi dữ liệu sang đối tượng ItemsModel.
                    if (list != null) {
                        lists.add(list) // Thêm ưu đãi vào danh sách nếu không null.
                    }
                }
                _offer.value = lists // Cập nhật giá trị của LiveData.
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình tải dữ liệu (hiện tại để trống).
            }
        })
    }
}
