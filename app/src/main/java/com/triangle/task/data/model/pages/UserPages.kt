package com.triangle.task.data.model.pages

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPages(
    @SerializedName("support")
    @Json(name = "support")
    val ad: Support,

    @SerializedName("data")
    @Json(name = "data")
    val myData: List<DataItem>,

    @SerializedName("page")
    @Json(name = "page")
    val page: Int,

    @SerializedName("per_page")
    @Json(name = "per_page")
    val per_page: Int,

    @SerializedName("total")
    @Json(name = "total")
    val total: Int,

    @SerializedName("total_pages")
    @Json(name = "total_pages")
    val total_pages: Int
)

data class Support(
    @SerializedName("text")
    @Json(name = "text")
    val text: String,

    @SerializedName("url")
    @Json(name = "url")
    val url: String
)

data class DataItem(
    @SerializedName("email")
    @Json(name = "email")
    val email: String,

    @SerializedName("first_name")
    @Json(name = "first_name")
    val firstName: String,

    @SerializedName("id")
    @Json(name = "id")
    val id: Int,

    @SerializedName("last_name")
    @Json(name = "last_name")
    val lastName: String,

    @SerializedName("avatar")
    @Json(name = "avatar")
    val avatar: String,
)
