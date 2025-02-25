package com.jay.ekacaretask.view.screen

import kotlinx.serialization.Serializable


@Serializable
data class Web(
    val url:String,
    val title:String,
    val urlToImage:String,
    val description:String
)
