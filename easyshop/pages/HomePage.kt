package com.example.easyshop.pages

import android.graphics.Paint.Style
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyshop.Components.BannerView
import com.example.easyshop.Components.CategoriesView
import com.example.easyshop.Components.HeaderView
import java.time.format.TextStyle

@Composable
fun HomePage(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
    ) {
        HeaderView()
        Spacer(modifier = Modifier.height(20.dp))
        BannerView()
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Categories", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        CategoriesView()
    }
}