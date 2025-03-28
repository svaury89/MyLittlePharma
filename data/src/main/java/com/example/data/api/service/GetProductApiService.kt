package com.example.data.api.service

import com.example.data.api.model.ProductApi
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetProductApiService {

    @GET("product/{ean}.json")
    suspend fun getProductApiByEan(@Path("ean")ean : String  ):ProductApi

}