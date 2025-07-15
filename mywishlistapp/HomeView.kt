package com.example.mywishlistapp

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.DummyWish
import com.example.mywishlistapp.data.wish
import wishViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: wishViewModel
) {
    val context = LocalContext.current
    val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())


    val toRemove = remember { mutableStateListOf<Long>() }

    Scaffold(
        topBar = {
            AppBarView(Title = "WishList") {
                Toast.makeText(context, "Button Clicked", Toast.LENGTH_LONG).show()
            }
        },
        modifier = Modifier.padding(top = 34.dp),

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "FButton Clicked", Toast.LENGTH_LONG).show()
                    navController.navigate(screen.AddScreen.route + "/0L")
                },
                modifier = Modifier
                    .padding(all = 20.dp)
                    .background(color = Color.Black),
                containerColor = Color.Black,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                items = wishlist.value.filter { it.id !in toRemove },
                key = { it.id }
            ) { wish ->
                val dismissState = rememberDismissState()

                LaunchedEffect(dismissState) {
                    snapshotFlow { dismissState.currentValue }
                        .collect { value ->
                            if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {

                                toRemove.add(wish.id)

                                viewModel.deleteWish(wish)

                                dismissState.reset()
                            }
                        }
                }


                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val color by animateColorAsState(
                            targetValue = if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                                Color.Red
                            } else {
                                Color.Transparent
                            },
                            label = ""
                        )
                        val alignment = Alignment.CenterEnd
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ){
                            Icon(Icons.Default.Delete,contentDescription = null, tint = Color.White)
                        }

                    },
                    dismissContent = {
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(screen.AddScreen.route + "/$id")
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun WishItem(wish : wish, onClick : () -> Unit){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .clickable {
                onClick()
            }
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column (
            modifier = Modifier.padding(16.dp)
        ){
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}