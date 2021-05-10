package com.codetest.feature.repository

import android.util.Log
import com.codetest.common.Constants
import com.codetest.common.Resource
import com.codetest.database.AppDataBase
import com.codetest.feature.model.Location
import com.codetest.feature.model.LocationModel
import com.codetest.feature.model.LocationRequest
import com.codetest.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val apiService: ApiService, private val appDataBase: AppDataBase) : AppRepository {

    override suspend fun getLocations(apiKey: String): Resource<LocationModel> {
        var response: Resource<LocationModel>
        try {
            withContext(Dispatchers.IO) {
                val result = apiService.getLocations(apiKey, Constants.locationURL)
                appDataBase.locationDao().clearAllLocations()
                appDataBase.locationDao().insertLocations(result.locations)
                response = Resource.success(result)
            }
        } catch (exception: Exception) {
            val cachedLocations = appDataBase.locationDao().getAllLocations()
            response = Resource.success(LocationModel(cachedLocations as ArrayList<Location>))
            Log.e("DB_error", exception.toString())
        }
        return response
    }

    override suspend fun addLocation(apiKey: String, locationRequest: LocationRequest): Resource<Location> {
        var response: Resource<Location>
        try {
            val location = apiService.addLocation(apiKey, locationRequest)
            appDataBase.locationDao().insertLocations(arrayListOf(location))
            response = Resource.success(location)
        } catch (exception: Exception) {
            response = Resource.error(null, message = exception.toString())
            Log.e("DB_error", exception.toString())
        }
        return response
    }

    override suspend fun deleteLocation(apiKey: String, location: Location): Resource<Location>? {
        var response: Resource<Location>? = null
        location.id?.let {
            try {
                apiService.deleteLocation(apiKey, it)
                appDataBase.locationDao().deleteLocation(location)
                response = Resource.success(location)
            } catch (exception: Exception) {
                response = Resource.error(null, message = exception.toString())
                Log.e("DB_error", exception.toString())
            }
        }
        return response
    }
}