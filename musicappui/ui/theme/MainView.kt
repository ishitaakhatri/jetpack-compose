package com.example.musicappui.ui.theme

import Subscription
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicappui.MainViewModel
import com.example.musicappui.R
import com.example.musicappui.Screen
import com.example.musicappui.screenInBottom
import com.example.musicappui.screensInDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainView() {

    val scaffoldState : ScaffoldState = rememberScaffoldState()
    val scope : CoroutineScope = rememberCoroutineScope()
    val viewModel : MainViewModel = viewModel()
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val modifier = if (isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()
    val controller : NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val dialogOpen = remember {
        mutableStateOf(false)
    }

    val currentScreen = remember{
        viewModel.currentScreen.value
    }
    val title = remember{
        mutableStateOf(currentScreen.title)
    }

    val RoundedCornerRadius = if(isSheetFullScreen) 0.dp else 12.dp
    val modelSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, confirmValueChange = {it != ModalBottomSheetValue.HalfExpanded})

    val bottomBar: @Composable () -> Unit = {
        if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home) {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.primary,
                elevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                screenInBottom.forEach { item ->
                    val isSelected = currentRoute == item.broute
                    val tint = if (isSelected) Color.White else Color.LightGray

                    BottomNavigationItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) controller.navigate(item.broute)
                            title.value = item.btitle
                        }, modifier = Modifier.padding(bottom = 20.dp),
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.btitle,
                                tint = tint,
                                modifier = Modifier.height(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.btitle,
                                color = tint,
                                fontSize = 12.sp
                            )
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.LightGray
                    )
                }
            }

        }
    }



    ModalBottomSheetLayout(
        sheetState = modelSheetState,
        sheetShape = RoundedCornerShape(topStart = RoundedCornerRadius, topEnd = RoundedCornerRadius),
        sheetContent = {
        MoreBottomSheet(modifier = modifier)
    },

    ){

        Scaffold(
            modifier = Modifier.padding(top = 20.dp),
            bottomBar = bottomBar,
            topBar = {
                TopAppBar(
                    title = { Text(text = title.value) },
                    actions = {
                              IconButton(onClick = {
                                  scope.launch {
                                      if(modelSheetState.isVisible){
                                          modelSheetState.hide()
                                      }else{
                                          modelSheetState.show()
                                      }
                                  }
                              }) {

                                  Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                              }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                        }
                    }
                )
            }, scaffoldState = scaffoldState,
            drawerContent = {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(screensInDrawer) { item ->
                        DrawerItem(selected = currentRoute == item.droute, item = item){
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            if(item.droute == "add_account"){
                                dialogOpen.value = true
                            }else{
                                controller.navigate(item.droute)
                                title.value = item.dtitle
                            }
                        }
                    }
                }
            }

        ) { paddingValues ->
            Navigation(navController = controller, viewModel = viewModel, pd = paddingValues)

            AccountDialog(dialogOpen = dialogOpen)
        }
    }



}

@Composable
fun MoreBottomSheet(modifier: Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ) {
        SheetOption(R.drawable.baseline_settings_24, "Settings")
        SheetOption(R.drawable.baseline_share_24, "Share")
        SheetOption(R.drawable.baseline_help_24, "Help")
    }
}

@Composable
fun SheetOption(iconRes: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.padding(end = 16.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = text,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    Divider()
}


@Composable
fun DrawerItem(
    selected : Boolean,
    item : Screen.DrawerScreen,
    onDrawerItemClicked : () -> Unit
){
    val background = if (selected) Color.DarkGray else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) Color(0xFF333333) else Color.Transparent)
            .clickable { onDrawerItemClicked() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dtitle,
            modifier = Modifier.padding(end = 12.dp),
            tint = if (selected) Color.White else MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = item.dtitle,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (selected) Color.White else MaterialTheme.colorScheme.onBackground
            )
        )
    }

}


@Composable
fun Navigation(navController: NavController,viewModel: MainViewModel,pd : PaddingValues){
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.Account.route,
        modifier = Modifier.padding(pd)
    ){

        composable(Screen.BottomScreen.Home.broute){
            Home()
        }
        composable(Screen.BottomScreen.Browse.broute){
            BrowseScreen()
        }
        composable(Screen.BottomScreen.Library.broute){
            Library()
        }


        composable(Screen.DrawerScreen.Account.route){
            AccountView()
        }
        composable(Screen.DrawerScreen.Subscription.route){
            Subscription()
        }
    }
}
