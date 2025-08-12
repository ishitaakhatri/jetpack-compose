package com.example.easyshop.Components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerView() {

    var bannerlist by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit){
        Firebase.firestore.collection("data")
            .document("banner")
            .get()
            .addOnCompleteListener {
                bannerlist = it.result.get("urls") as List<String>
            }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val pagerstate = rememberPagerState(0) {
            bannerlist.size
        }
        HorizontalPager(
            state = pagerstate,
            pageSpacing = 24.dp
        ) {
            AsyncImage(
                model = bannerlist.get(it),
                contentDescription = "" ,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        DotsIndicator(
            dotCount = bannerlist.size,
            type = ShiftIndicatorType(dotsGraphic = DotGraphic(
                color = MaterialTheme.colorScheme.primary,
                size = 6.dp
            )),
            pagerState = pagerstate
        )
    }


}