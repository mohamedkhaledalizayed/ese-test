package com.neqabty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neqabty.data.entities.ProviderData

@Database(
    entities = [ProviderData::class],
    version = 2,
    exportSchema = false
)
abstract class NeqabtyDb : RoomDatabase() {
    abstract fun providerDao(): ProviderDao
//    abstract fun historyDao(): HistoryDao
}
