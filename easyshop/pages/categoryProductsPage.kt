package com.example.easyshop.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import com.example.easyshop.Components.productItemView
import com.example.easyshop.Model.productModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun CategoryProductsPage(categoryId : String?) {
    var productslist = remember{ mutableStateOf<List<productModel>>(emptyList()) }

    LaunchedEffect(key1 = Unit){
        Firebase.firestore.collection("data").document("Stock")
            .collection("products")
            .whereEqualTo("category",categoryId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val resultList = it.result.documents.mapNotNull { doc->
                        doc.toObject(productModel::class.java)
                    }
                    productslist.value = resultList
                }
            }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        items(productslist.value.chunked(2)){rowitems->
            Row {
                rowitems.forEach {
                    productItemView(product = it, modifier = Modifier.weight(1f))
                }
                if (rowitems.size == 1){
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}