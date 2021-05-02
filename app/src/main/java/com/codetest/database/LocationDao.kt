package com.codetest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.codetest.feature.model.Location

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getAllLocations(): List<Location>

    @Insert
    fun insertLocations(locations: ArrayList<Location>)

    @Query("DELETE FROM location")
    fun clearAllLocations()

    @Delete
    fun deleteLocation(location: Location)
}