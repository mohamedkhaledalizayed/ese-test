package com.neqabty.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.neqabty.data.entities.ProviderData

@Database(
    entities = [ProviderData::class],
    version = 1,
    exportSchema = false
)
abstract class NeqabtyDb : RoomDatabase() {
    abstract fun providerDao(): ProviderDao
//    abstract fun historyDao(): HistoryDao
}
