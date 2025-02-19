package com.example.getupdated.data.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetUpdatedResponse(
    val copyright: String,
    val num_results: Int,
    val results: List<Articles>,
    val status: String
)
@Parcelize
data class Articles(
    val `abstract`: String,
    val byline: String,
    val id: Long,
    val media: List<Media>?,
    val published_date: String,
    val section: String,
    val subsection: String,
    val title: String,
    val url: String
):Parcelable
@Parcelize
data class Media(
    val approved_for_syndication: Int,
    val caption: String,
    val copyright: String,
    @SerializedName("media-metadata")
    val  media_Metadata: List<MediaMetadata>,
    val subtype: String,
    val type: String
):Parcelable
@Parcelize
data class MediaMetadata(
    val format: String,
    val height: Int,
    val url: String,
    val width: Int
):Parcelable