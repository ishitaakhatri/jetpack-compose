package com.example.musicappui

import androidx.annotation.DrawableRes

data class Lib(@DrawableRes val Icon : Int, val name : String)


val library = listOf<Lib>(

    Lib(R.drawable.baseline_playlist_play_24,"PlayList"),
    Lib(R.drawable.baseline_assignment_ind_24,"Artists"),
    Lib(R.drawable.baseline_album_24,"Album"),
    Lib(R.drawable.baseline_headphones_24,"Songs"),
    Lib(R.drawable.baseline_music_video_24,"Genre")
)