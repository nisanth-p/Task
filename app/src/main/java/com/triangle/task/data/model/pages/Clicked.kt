package com.triangle.task.data.model.pages

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json


data class Clicked(var position: Int,
                   var isChecked: Boolean,
                   @SerializedName("data")
                   @Json(name = "data")
                   val myData: List<DataItem>)
