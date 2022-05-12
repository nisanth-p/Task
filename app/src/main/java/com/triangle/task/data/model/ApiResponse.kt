package com.triangle.task.data.model

import com.google.gson.annotations.SerializedName
import com.triangle.task.data.model.pages.UserPages

data class ApiResponse(
    @SerializedName("data")
    val datas: List<UserPages>
)