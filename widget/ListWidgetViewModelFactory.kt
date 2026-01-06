package my.first.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListWidgetViewModelFactory(
    private val instanceId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListWidgetViewModel(instanceId) as T
    }
}
