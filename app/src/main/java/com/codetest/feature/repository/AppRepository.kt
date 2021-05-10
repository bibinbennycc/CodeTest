package com.codetest.feature.repository

import com.codetest.common.Resource
import com.codetest.feature.model.Location
import com.codetest.feature.model.LocationModel
import com.codetest.feature.model.LocationRequest

interface AppRepository {
    suspend fun getLocations(apiKey: String): Resource<LocationModel>
    suspend fun addLocation(apiKey: String, locationRequest: LocationRequest): Resource<Location>
    suspend fun deleteLocation(apiKey: String, location: Location): Resource<Location>?
}