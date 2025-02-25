package com.jay.ekacaretask.model.datamodel.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Articles(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val urlOfImage: String,
    val url: String
)