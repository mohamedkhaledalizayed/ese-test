package com.neqabty.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.neqabty.data.entities.UserData

@Database(
    entities = [UserData::class],
    version = 1,
    exportSchema = false
)
abstract class NeqabtyDb : RoomDatabase() {
//    abstract fun weatherDao(): WeatherDao
//    abstract fun historyDao(): HistoryDao
}
