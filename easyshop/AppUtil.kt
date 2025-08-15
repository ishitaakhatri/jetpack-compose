package com.example.easyshop

import android.content.Context
import android.widget.Toast
import com.example.easyshop.Model.productModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppUtil {
    object app{
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun addItemsToCart(productId : String?, context: Context){
            val userDoc = Firebase.firestore.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            userDoc.get().addOnCompleteListener {
                if (it.isSuccessful){
                    val currentCart = it.result.get("cartItems") as? Map<String,Long> ?: emptyMap()
                    val currentQuantity = currentCart[productId]?:0
                    val updatedQuantity = currentQuantity+1

                    val updatedCart = mapOf("cartItems.$productId" to updatedQuantity)

                    userDoc.update(updatedCart)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                showToast(context,"Item Successfully Added To Cart")
                            }else{
                                showToast(context,"Failed to add item to the Cart")
                            }
                        }
                }
            }
        }


        fun addItemsToWishlist(productId: String?, context: Context) {
            if (productId.isNullOrEmpty()) {
                showToast(context, "Invalid product ID")
                return
            }

            val userDoc = Firebase.firestore.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid!!)

            userDoc.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentWishlist = it.result.get("wishlistItems") as? Map<String, Boolean> ?: emptyMap()
                    val isInList = currentWishlist[productId] ?: false

                    if (isInList) {
                        showToast(context, "Item is already in your Wishlist")
                        return@addOnCompleteListener
                    }

                    val updatedList = mapOf("wishlistItems.$productId" to true)
                    userDoc.update(updatedList)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showToast(context, "Item Successfully Added To Wishlist")
                            } else {
                                showToast(context, "Failed to add item to the Wishlist")
                            }
                        }
                }
            }
        }



        fun removeFromCart(productId : String?, context: Context, removeall : Boolean = false){
            val userDoc = Firebase.firestore.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            userDoc.get().addOnCompleteListener {
                if (it.isSuccessful){
                    val currentCart = it.result.get("cartItems") as? Map<String,Long> ?: emptyMap()
                    val currentQuantity = currentCart[productId]?:0
                    val updatedQuantity = currentQuantity-1

                    val updatedCart =
                        if (updatedQuantity <= 0 || removeall){
                            mapOf("cartItems.$productId" to FieldValue.delete())
                        }
                    else
                        mapOf("cartItems.$productId" to updatedQuantity)

                    userDoc.update(updatedCart)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                showToast(context,"Item Successfully Removed From Cart")
                            }else{
                                showToast(context,"Failed to remove item from Cart")
                            }
                        }
                }
            }
        }

        fun getDiscountPercentage(product : productModel): Float{
            val actualPrice = product.actualprice.toDoubleOrNull() ?: 0.0
            val salePrice = product.price.toDoubleOrNull() ?: 0.0
            val discount = if (actualPrice > 0) ((actualPrice - salePrice) * 100 / actualPrice).toInt() else 0
            return discount.toFloat()
        }
        fun getTaxPercentage(): Float{
            return 13.0f
        }

        fun removeFromWishlist(productId: String?, context: Context) {
            val userDoc = Firebase.firestore.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid!!)

            userDoc.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentWishlist = it.result.get("wishlistItems") as? Map<String, Boolean> ?: emptyMap()
                    if (currentWishlist.containsKey(productId)) {
                        val updatedWishlist = mapOf("wishlistItems.$productId" to FieldValue.delete())

                        userDoc.update(updatedWishlist)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showToast(context, "Item successfully removed from Wishlist")
                                } else {
                                    showToast(context, "Failed to remove item from Wishlist")
                                }
                            }
                    } else {
                        showToast(context, "Item not found in Wishlist")
                    }
                }
            }
        }



    }

}
