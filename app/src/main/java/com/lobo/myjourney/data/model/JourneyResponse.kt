package com.lobo.myjourney.data.model

import com.google.gson.annotations.SerializedName

class JourneyResponse(
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("day") val day: Int
)