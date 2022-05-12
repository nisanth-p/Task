package com.triangle.task.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class UserPages(
    @SerializedName("support")

    val ad: Support,

    @SerializedName("data")

    val myData: List<DataItem>,

    @SerializedName("page")

    val page: Int,

    @SerializedName("per_page")

    val per_page: Int,

    @SerializedName("total")

    val total: Int,

    @SerializedName("total_pages")

    val total_pages: Int
)

data class Support(
    @SerializedName("text")

    val text: String,

    @SerializedName("url")

    val url: String
)

data class DataItem(
    @SerializedName("email")

    val email: String,

    @SerializedName("first_name")

    val firstName: String,

    @SerializedName("id")

    val id: Int,

    @SerializedName("last_name")

    val lastName: String,

    @SerializedName("avatar")

    val avatar: String,
)