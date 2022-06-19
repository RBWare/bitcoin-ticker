package com.rbware.bitcointicker.networking

import com.google.gson.internal.LinkedTreeMap
import com.rbware.bitcointicker.api.model.Currency
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/ticker")
    suspend fun getPrices(): Response<LinkedTreeMap<String, Currency>>
}