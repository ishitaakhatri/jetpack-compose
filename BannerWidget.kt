package my.first.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BannerWidget(instanceId : String){

    val banners = BannerDataGenerator.getBanner(instanceId)

    LazyRow{
        items(banners) { banner ->
            BannerItem(banner)
        }
    }
}

@Composable
fun BannerItem(banner: BannerConfig) {
    Column (
        modifier = Modifier.padding(12.dp)
    ){
        Text(banner.title)
        Text(banner.subtitle)
    }
}
