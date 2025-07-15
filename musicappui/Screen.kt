package com.example.musicappui

import androidx.annotation.DrawableRes


sealed class Screen(
    val title: String,
    val route: String
) {

    sealed class BottomScreen(
        val btitle: String,
        val broute: String,
        @DrawableRes val icon: Int
    ):Screen(btitle,broute){

        object Home : BottomScreen(
            "Home" ,
            "home" ,
            R.drawable.baseline_music_note_24
        )
        object Library : BottomScreen(
            "Library" ,
            "library" ,
            R.drawable.baseline_library_music_24
        )
        object Browse : BottomScreen(
            "Browse" ,
            "Browse" ,
            R.drawable.baseline_apps_24
        )
    }



    sealed class DrawerScreen(
        val dtitle: String,
        val droute: String,
        @DrawableRes val icon: Int
    ) : Screen(dtitle, droute) {

        object Account : DrawerScreen(
            dtitle = "Account",
            droute = "account",
            icon = R.drawable.ic_account
        )

        object Subscription : DrawerScreen(
            dtitle = "Subscription",
            droute = "subscribe",
            icon = R.drawable.baseline_library_music_24
        )

        object AddAccount : DrawerScreen(
            dtitle = "Add_Account",
            droute = "add_account",
            icon = R.drawable.baseline_person_add_alt_1_24
        )
    }
}

val screenInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Browse,
    Screen.BottomScreen.Library
)

val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount
)
