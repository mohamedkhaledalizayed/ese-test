package com.neqabty.presentation.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.neqabty.MyApp
import com.neqabty.data.api.WebService
import com.neqabty.data.db.NeqabtyDb
import com.neqabty.data.repositories.CachedNeqabtyDataStore
import com.neqabty.data.repositories.MemoryNeqabtyCache
import com.neqabty.data.repositories.NeqabtyRepositoryImpl
import com.neqabty.data.repositories.RemoteNeqabtyDataStore
import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.ASyncTransformer
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    fun provideContext(application: MyApp): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
//            this.followRedirects(true)
        }.build()

        return Retrofit.Builder()
                .baseUrl("http://lvai0dfr-site.htempurl.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//LiveDataCallAdapterFactory()
                .client(client)
                .build()
    }

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): WebService {
        return retrofit.create(WebService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): NeqabtyDb {
        return Room
                .databaseBuilder(app, NeqabtyDb::class.java, "weather.db")
                .fallbackToDestructiveMigration().allowMainThreadQueries()
                .build()
    }

//    @Singleton
//    @Provides
//    fun provideWeatherDao(db: NeqabtyDb): WeatherDao {
//        return db.weatherDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideHistoryDao(db: NeqabtyDb): HistoryDao {
//        return db.historyDao()
//    }


    @Provides
    @Singleton
    fun provideWeatherRepository(api: WebService,
                                 @Named(DI.inMemoryCache) cache: NeqabtyCache): NeqabtyRepository {

        val cachedWeatherDataStore = CachedNeqabtyDataStore(cache)
        val remoteWeatherDataStore = RemoteNeqabtyDataStore(api)
        return NeqabtyRepositoryImpl(cachedWeatherDataStore, remoteWeatherDataStore)
    }

    @Singleton
    @Provides
    @Named(DI.inMemoryCache)
    fun provideInMemoryWeatherCache(): NeqabtyCache {
        return MemoryNeqabtyCache()
    }

    @Singleton
    @Provides
    fun provideLoginUser(neqabtyRepository: NeqabtyRepository): LoginUser {
        return com.neqabty.domain.usecases.LoginUser(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideSignupUser(neqabtyRepository: NeqabtyRepository): SignupUser {
        return com.neqabty.domain.usecases.SignupUser(ASyncTransformer(), neqabtyRepository)
    }


    @Singleton
    @Provides
    fun provideGetUserRegistered(neqabtyRepository: NeqabtyRepository): GetUserRegistered {
        return com.neqabty.domain.usecases.GetUserRegistered(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllSyndicates(neqabtyRepository: NeqabtyRepository): GetAllSyndicates {
        return com.neqabty.domain.usecases.GetAllSyndicates(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllNews(neqabtyRepository: NeqabtyRepository): GetAllNews {
        return com.neqabty.domain.usecases.GetAllNews(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllTrips(neqabtyRepository: NeqabtyRepository): GetAllTrips {
        return com.neqabty.domain.usecases.GetAllTrips(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetSyndicate(neqabtyRepository: NeqabtyRepository): GetSyndicate {
        return com.neqabty.domain.usecases.GetSyndicate(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetSubSyndicates(neqabtyRepository: NeqabtyRepository): GetSubSyndicates {
        return com.neqabty.domain.usecases.GetSubSyndicates(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllAreas(neqabtyRepository: NeqabtyRepository): GetAllAreas {
        return com.neqabty.domain.usecases.GetAllAreas(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllDoctors(neqabtyRepository: NeqabtyRepository): GetAllDoctors {
        return com.neqabty.domain.usecases.GetAllDoctors(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllSpecializations(neqabtyRepository: NeqabtyRepository): GetAllSpecializations {
        return com.neqabty.domain.usecases.GetAllSpecializations(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllDegrees(neqabtyRepository: NeqabtyRepository): GetAllDegrees {
        return com.neqabty.domain.usecases.GetAllDegrees(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllProviders(neqabtyRepository: NeqabtyRepository): GetAllProviders {
        return com.neqabty.domain.usecases.GetAllProviders(ASyncTransformer(), neqabtyRepository)
    }
}
