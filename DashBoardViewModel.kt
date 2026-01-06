package my.first.widget

import androidx.lifecycle.ViewModel

class DashBoardViewModel {

    class DashboardViewModel : ViewModel() {

        val widgets = listOf(
            widgetData(WidgetType.banner, "pokemon"),
            widgetData(WidgetType.banner, "cars"),
            widgetData(WidgetType.banner, "bikes"),
            widgetData(WidgetType.list, "movies"),
            widgetData(WidgetType.list, "shows"),
        )
    }
}