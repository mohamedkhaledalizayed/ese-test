package com.neqabty.data.db

import androidx.room.*
import com.neqabty.data.entities.ProviderData

/**
 * Interface for database access for User related operations.
 */
@Dao
interface ProviderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProvider(providerData: ProviderData)

    @Query("SELECT * FROM providerdata")
    fun getProviders(): List<ProviderData>

    @Query("SELECT * FROM providerdata WHERE id = :providerID")
    fun isFavorite(providerID: Int): List<ProviderData>

    @Delete
    fun removeProvider(providerData: ProviderData)
}
