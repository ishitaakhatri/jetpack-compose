package com.example.easyshop.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.easyshop.AppUtil
import com.example.easyshop.Model.productModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun ProductDetailPage(productId: String?) {
    var context = LocalContext.current
    var product by remember { mutableStateOf(productModel()) }

    LaunchedEffect(productId) {
        if (!productId.isNullOrEmpty()) {
            Firebase.firestore.collection("data").document("Stock")
                .collection("products")
                .document(productId)
                .get()
                .addOnSuccessListener { doc ->
                    val result = doc.toObject(productModel::class.java)
                    if (result != null) {
                        product = result
                    }
                }
        }
    }

    val scrollState = rememberScrollState()
    val isFavorited = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, top = 34.dp)
            .verticalScroll(scrollState)
    ) {
        val pagerState = rememberPagerState(initialPage = 0) { product.images.size }

        Box {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 16.dp
            ) { index ->
                AsyncImage(
                    model = product.images.getOrNull(index),
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            IconButton(
                onClick = { isFavorited.value = !isFavorited.value },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isFavorited.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorited.value) Color.Red else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        DotsIndicator(
            dotCount = product.images.size,
            pagerState = pagerState,
            type = ShiftIndicatorType(
                dotsGraphic = DotGraphic(
                    color = MaterialTheme.colorScheme.primary,
                    size = 6.dp
                )
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = product.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(12.dp))

        val actualPrice = product.actualprice.toDoubleOrNull() ?: 0.0
        val salePrice = product.price.toDoubleOrNull() ?: 0.0
        val discount = if (actualPrice > 0) ((actualPrice - salePrice) * 100 / actualPrice).toInt() else 0

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (product.actualprice.isNotBlank()) {
                Text(
                    text = "₹${product.actualprice}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            if (product.price.isNotBlank()) {
                Text(
                    text = "₹${product.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C)
                )
            }

            if (discount > 0) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFCDD2), shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "-$discount%",
                        fontSize = 12.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedButton(
            onClick = { AppUtil.app.addItemsToCart(productId,context) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add to Cart",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Add to Cart", fontWeight = FontWeight.Medium, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(20.dp))
        
        Text(text = "Product Description :", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.description, fontSize = 16.sp)
    }
}
