package com.codetest.repository

import com.codetest.common.Resource
import com.codetest.feature.model.Location
import com.codetest.feature.model.LocationModel
import com.codetest.feature.model.LocationRequest
import com.codetest.feature.model.Status
import com.codetest.feature.repository.AppRepository

class MockRepository : AppRepository {

    var returnErrorResponse: Boolean = false

    override suspend fun getLocations(apiKey: String): Resource<LocationModel> {
        return if (returnErrorResponse) {
            Resource.error(null, "Something went wrong")
        } else {
            val model = LocationModel(arrayListOf(Location(1, "id", "India", "20", Status.CLOUDY)))
            Resource.success(model)
        }
    }

    override suspend fun addLocation(apiKey: String, locationRequest: LocationRequest): Resource<Location> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocation(apiKey: String, location: Location): Resource<Location>? {
        TODO("Not yet implemented")
    }
}