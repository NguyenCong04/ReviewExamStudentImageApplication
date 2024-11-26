package com.congntph34559.fpoly.reviewexamstudentimageapplication

data class SuggestSearchModel(

    val lat: String,
    val lon: String,
    val display_name: String
) {
    constructor() : this("", "", "")
}