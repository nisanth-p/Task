package com.triangle.task.data.model

import com.google.gson.annotations.SerializedName

data class RecordsItem(

	@field:SerializedName("uId")
	val uId: String? = null,

	@field:SerializedName("uName")
	val uName: String? = null,

	@field:SerializedName("uImage")
	val uImage: String? = null
)