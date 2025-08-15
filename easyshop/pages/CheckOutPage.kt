package com.example.easyshop.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyshop.AppUtil
import com.example.easyshop.GlobalNavigation
import com.example.easyshop.Model.UserModel
import com.example.easyshop.Model.productModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

@Composable
fun checkOutPage(productId: String?) {

    val userModel = remember { mutableStateOf(UserModel()) }
    val productList = remember { mutableStateListOf(productModel()) }

    val subtotal = remember { mutableStateOf(0f) }
    val discount = remember { mutableStateOf(0f) }
    val tax = remember { mutableStateOf(0f) }
    val total = remember { mutableStateOf(0f) }

    var product by remember { mutableStateOf(productModel()) }

    LaunchedEffect(productId) {
        if (!productId.isNullOrEmpty()) {
            Firebase.firestore.collection("data").document("Stock")
                .collection("products")
                .document(productId)
                .get()
                .addOnSuccessListener { doc ->
                    doc.toObject(productModel::class.java)?.let {
                        product = it
                    }
                }
        }
    }

    fun calculateAndAssign() {
        subtotal.value = 0f
        discount.value = 0f

        productList.forEach { item ->
            if (item.price.isNotEmpty()) {
                val qty = userModel.value.cartItems[item.id] ?: 0
                val price = item.price.toFloat()

                subtotal.value += price * qty

                val discountPercent = AppUtil.app.getDiscountPercentage(item)
                discount.value += (price * qty) * (discountPercent / 100f)
            }
        }

        tax.value = (subtotal.value - discount.value) * (AppUtil.app.getTaxPercentage() / 100f)

        total.value = subtotal.value - discount.value + tax.value
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.toObject(UserModel::class.java)?.let { result ->
                        userModel.value = result

                        Firebase.firestore.collection("data")
                            .document("Stock").collection("products")
                            .whereIn("id", userModel.value.cartItems.keys.toList())
                            .get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val resultProducts = task.result.toObjects(productModel::class.java)
                                    productList.addAll(resultProducts)
                                    calculateAndAssign()
                                }
                            }
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(top = 30.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Checkout",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Address", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Deliver To", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(userModel.value.name, fontWeight = FontWeight.Medium)
                Text(userModel.value.address, color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                RowCheckoutItems("Subtotal", subtotal.value, Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                RowCheckoutItems("Discount (-)", discount.value, Color(0xFF2E7D32))
                Spacer(modifier = Modifier.height(8.dp))
                RowCheckoutItems("Tax (+)", tax.value, Color(0xFFD32F2F))
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                RowCheckoutItems("Total To Pay", total.value, Color.Black, isBold = true, fontSize = 22.sp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Gray
            )
        ) {
            Text(text = "Pay Now", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun RowCheckoutItems(title: String, value: Float, valueColor: Color, isBold: Boolean = false, fontSize: TextUnit = 18.sp) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontSize = fontSize, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
        Text(
            text = "₹" + String.format("%.2f", value),
            fontSize = fontSize,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = valueColor
        )
    }
}
