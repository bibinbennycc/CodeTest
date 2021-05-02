package com.codetest.network

import com.codetest.feature.model.Location
import com.codetest.feature.model.LocationModel
import com.codetest.feature.model.LocationRequest
import retrofit2.http.*

interface ApiService {

    @GET
    suspend fun getLocations(@Header("X-Api-Key") apiKey: String,@Url url: String): LocationModel

    @POST("locations")
    suspend fun addLocation(@Header("X-Api-Key") apiKey: String,@Body location: LocationRequest): Location

    @DELETE("locations/{id}")
    suspend fun deleteLocation(@Header("X-Api-Key") apiKey: String,@Path("id") locationId: String)
}