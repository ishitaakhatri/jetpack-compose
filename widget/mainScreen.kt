package my.first.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DashboardScreen(viewModel: DashBoardViewModel.DashboardViewModel){

    val widgets = viewModel.widgets

    LazyColumn{
        items(widgets) { widget->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                when (widget.type) {
                    WidgetType.banner -> BannerWidget(widget.instanceId)
                    WidgetType.list -> ListWidget(widget.instanceId)
                }
            }
        }
    }
}
