package my.first.widget

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListWidgetViewModel(
    private val instanceId: String
) : ViewModel() {

    var uiState by mutableStateOf<ListUiState>(ListUiState.Loading)
        private set

    init {
        loadData()
    }

    private fun loadData(){
        viewModelScope.launch {
            delay(2000)
            uiState = ListUiState.Success(
                ListDataGenerator.getListData(instanceId)
            )
        }
    }
}