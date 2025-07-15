package com.example.mywishlistapp


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.wish
import kotlinx.coroutines.launch
import wishViewModel
import java.time.format.TextStyle

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: wishViewModel,
    navController: NavController
) {

    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val wishFlow = viewModel.getAWishByID(id).collectAsState(initial = null)

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Load the data only once when the wish arrives
    LaunchedEffect(key1 = wishFlow.value) {
        wishFlow.value?.let {
            title = it.title
            description = it.description
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                Title = if (id != 0L) stringResource(id = R.string.update_wish) else stringResource(id = R.string.add_wish),
                onBackNavClicked = { navController.popBackStack() }
            )
        },
        modifier = Modifier.padding(top = 34.dp)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            wishTextField(
                label = "Title",
                value = title,
                onValueChanged = { title = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            wishTextField(
                label = "Description",
                value = description,
                onValueChanged = { description = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    if (id != 0L) {
                        viewModel.updateWish(
                            wish(
                                id = id,
                                title = title.trim(),
                                description = description.trim()
                            )
                        )
                        snackMessage.value = "Wish has been updated"
                    } else {
                        viewModel.addWish(
                            wish(
                                title = title.trim(),
                                description = description.trim()
                            )
                        )
                        snackMessage.value = "Wish has been created"
                    }
                    navController.navigateUp()
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)

                    }
                } else {
                    snackMessage.value = "Enter Fields to create or update a wish"
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    }
                }
            }) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish),
                    style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun wishTextField(
    label: String,
    value : String,
    onValueChanged : (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label,color = Color.Black)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedSupportingTextColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            unfocusedLabelColor = Color.Black
        )
        )
}
