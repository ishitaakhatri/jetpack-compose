package my.first.widget

sealed class ListUiState {
    object Loading : ListUiState()
    object Error : ListUiState()
    data class Success(val data: List<ListWidgetConfig>) : ListUiState()
}