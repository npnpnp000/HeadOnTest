package com.headontest.model

import com.adviserall22spdaslld.model.response.Responses
import com.adviserall22spdaslld.model.response.RequestWebService

class Repository(
      val webService: RequestWebService) {


    @Throws(Exception::class)
    suspend fun getRequest() :Responses
            = webService.getRequest()

}