package com.hectorfortuna.rickandmorty.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Results>
): Parcelable