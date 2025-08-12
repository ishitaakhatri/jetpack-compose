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

    }

}
