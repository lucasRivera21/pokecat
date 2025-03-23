package com.example.pokecat.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cat")
data class CatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weight: String,
    @SerializedName("name_id") val nameId: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    @SerializedName("img_name") val imgName: String? = null,
    val color: String,
    val isFounded: Int = 0
)
