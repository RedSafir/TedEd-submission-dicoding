package com.miftah.mysubmissionintermediate.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
