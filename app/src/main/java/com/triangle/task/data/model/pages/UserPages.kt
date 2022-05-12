package com.triangle.task.data.model.pages

import com.squareup.moshi.Json

data class UserPages(

    @Json(name = "ad")
    val ad: Support,
    @Json(name = "data")
    val myData: List<DataItem>,
    @Json(name = "page")
    val page: Int,
    @Json(name = "per_page")
    val per_page: Int,
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val total_pages: Int
)

data class Support(
    @Json(name = "text")
    val text: String,
    @Json(name = "url")
    val url: String
)

data class DataItem(
    @Json(name = "email")
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "avatar")
    val avatar: String,
)
