package my.first.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ListWidget(instanceId: String) {

    val viewModel: ListWidgetViewModel = viewModel(
        key = instanceId,
        factory = ListWidgetViewModelFactory(instanceId)
    )

    when (val state = viewModel.uiState) {
        is ListUiState.Loading -> {
            Text("Loading...")
        }

        is ListUiState.Error -> {
            Text("Error has occurred")
        }

        is ListUiState.Success -> {
            Column (
                modifier = Modifier.padding(12.dp)
            ){
                state.data.forEach {
                    Text("${it.name} ${it.surname}")
                }
            }
        }

        else -> {}
    }
}
