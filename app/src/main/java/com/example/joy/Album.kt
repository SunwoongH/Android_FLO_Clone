package com.example.joy

data class Album(
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
    var song: ArrayList<Song>? = null // 수록곡
)
