package com.rbware.bitcointicker.api.model

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("15m")
    val fifteenM: Double,
    val last: Double,
    val buy: Double,
    val sell: Double,
    val symbol: String
) {

}