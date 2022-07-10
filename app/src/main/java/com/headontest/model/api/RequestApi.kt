package com.adviserall22spdaslld.model.response

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RequestWebService {
    private lateinit var api: RequestApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://40.88.151.148")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        api = retrofit.create(RequestApi::class.java)

    }

    @Throws(Exception::class)
    suspend fun getRequest(): Responses {
        return api.getRequest()
    }

    interface RequestApi {
        @Throws(Exception::class)
        @GET("/api/v1/get_Home_Assignment_Data")
        suspend fun getRequest(): Responses
    }

}