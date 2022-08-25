package com.hectorfortuna.rickandmorty.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    @SerializedName("count")
    val count: Long,
    @SerializedName("pages")
    val pages: Long,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String
): Parcelable
