package com.neqabty.presentation.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.neqabty.MyApp
import com.neqabty.data.api.WebService
import com.neqabty.data.db.NeqabtyDb
import com.neqabty.data.db.ProviderDao
import com.neqabty.data.db.RoomHistoryCache
import com.neqabty.data.mappers.ProviderDataEntityMapper
import com.neqabty.data.mappers.ProviderEntityDataMapper
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
        }.build()

        return Retrofit.Builder()
            .baseUrl("http://eea.neqabty.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // LiveDataCallAdapterFactory()
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
            .databaseBuilder(app, NeqabtyDb::class.java, "Neqabty.db")
            .fallbackToDestructiveMigration().allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideProviderDao(db: NeqabtyDb): ProviderDao {
        return db.providerDao()
    }

//    @Singleton
//    @Provides
//    fun provideHistoryDao(db: NeqabtyDb): HistoryDao {
//        return db.historyDao()
//    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WebService,
        @Named(DI.inMemoryCache) cache: NeqabtyCache
    ): NeqabtyRepository {

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
    @Named(DI.roomCache)
    fun provideRoomCache(db: NeqabtyDb): NeqabtyCache {
        return RoomHistoryCache(db, ProviderDataEntityMapper(), ProviderEntityDataMapper())
    }

    @Singleton
    @Provides
    fun provideLoginUser(neqabtyRepository: NeqabtyRepository): LoginUser {
        return LoginUser(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideSignupUser(neqabtyRepository: NeqabtyRepository): SignupUser {
        return SignupUser(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetUserRegistered(neqabtyRepository: NeqabtyRepository): GetUserRegistered {
        return GetUserRegistered(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAppVersion(neqabtyRepository: NeqabtyRepository): GetAppVersion {
        return GetAppVersion(ASyncTransformer(), neqabtyRepository)
    }
    @Singleton
    @Provides
    fun provideGetAllSyndicates(neqabtyRepository: NeqabtyRepository): GetAllSyndicates {
        return GetAllSyndicates(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllNews(neqabtyRepository: NeqabtyRepository): GetAllNews {
        return GetAllNews(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllTrips(neqabtyRepository: NeqabtyRepository): GetAllTrips {
        return GetAllTrips(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetTripDetails(neqabtyRepository: NeqabtyRepository): GetTripDetails {
        return GetTripDetails(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetSyndicate(neqabtyRepository: NeqabtyRepository): GetSyndicate {
        return GetSyndicate(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetSubSyndicates(neqabtyRepository: NeqabtyRepository): GetSubSyndicates {
        return GetSubSyndicates(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllAreas(neqabtyRepository: NeqabtyRepository): GetAllAreas {
        return GetAllAreas(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllGoverns(neqabtyRepository: NeqabtyRepository): GetAllGoverns {
        return GetAllGoverns(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllDoctors(neqabtyRepository: NeqabtyRepository): GetAllDoctors {
        return GetAllDoctors(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllSpecializations(neqabtyRepository: NeqabtyRepository): GetAllSpecializations {
        return GetAllSpecializations(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllDegrees(neqabtyRepository: NeqabtyRepository): GetAllDegrees {
        return GetAllDegrees(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllProviders(neqabtyRepository: NeqabtyRepository): GetProviderDetails {
        return GetProviderDetails(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetProvidersByType(neqabtyRepository: NeqabtyRepository): GetProvidersByType {
        return GetProvidersByType(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllProvidersTypes(neqabtyRepository: NeqabtyRepository): GetAllProvidersTypes {
        return GetAllProvidersTypes(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetNotificationsCount(neqabtyRepository: NeqabtyRepository): GetNotificationsCount {
        return GetNotificationsCount(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetNotifications(neqabtyRepository: NeqabtyRepository): GetNotifications {
        return GetNotifications(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetNotificationDetails(neqabtyRepository: NeqabtyRepository): GetNotificationDetails {
        return GetNotificationDetails(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideSendMedicalRequest(neqabtyRepository: NeqabtyRepository): SendMedicalRequest {
        return SendMedicalRequest(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideBookTrip (neqabtyRepository: NeqabtyRepository): BookTrip {
        return BookTrip(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetTransactionHash(neqabtyRepository: NeqabtyRepository): GetTransactionHash {
        return GetTransactionHash(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideSendEngineeringRecordsRequest(neqabtyRepository: NeqabtyRepository): SendEngineeringRecordsRequest {
        return SendEngineeringRecordsRequest(ASyncTransformer(), neqabtyRepository)
    }


    @Singleton
    @Provides
    fun provideSendEngineeringRecordsInquiry(neqabtyRepository: NeqabtyRepository): SendEngineeringRecordsInquiry {
        return SendEngineeringRecordsInquiry(ASyncTransformer(), neqabtyRepository)
    }


    @Singleton
    @Provides
    fun provideUpdateUserDataInquiry(neqabtyRepository: NeqabtyRepository): GetUpdateUserDataInquiry {
        return GetUpdateUserDataInquiry(ASyncTransformer(), neqabtyRepository)
    }


    @Singleton
    @Provides
    fun provideGetUpdateUserDataDetails(neqabtyRepository: NeqabtyRepository): UpdateUserData {
        return UpdateUserData(ASyncTransformer(), neqabtyRepository)
    }


    @Singleton
    @Provides
    fun provideVerifyUpdateUserData(neqabtyRepository: NeqabtyRepository): VerifyUpdateUserData {
        return VerifyUpdateUserData(ASyncTransformer(), neqabtyRepository)
    }


    @Singleton
    @Provides
    fun provideValidateUser(neqabtyRepository: NeqabtyRepository): ValidateUser {
        return ValidateUser(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideAddFavorite(@Named(DI.roomCache) neqabtyCache: NeqabtyCache): AddFavorite {
        return AddFavorite(ASyncTransformer(), neqabtyCache)
    }

    @Singleton
    @Provides
    fun provideGetFavorites(@Named(DI.roomCache) neqabtyCache: NeqabtyCache): GetFavorites {
        return GetFavorites(ASyncTransformer(), neqabtyCache)
    }

    @Singleton
    @Provides
    fun provideCheckFavorite(@Named(DI.roomCache) neqabtyCache: NeqabtyCache): CheckFavorite {
        return CheckFavorite(ASyncTransformer(), neqabtyCache)
    }

    @Singleton
    @Provides
    fun provideRemoveFavorite(@Named(DI.roomCache) neqabtyCache: NeqabtyCache): RemoveFavorite {
        return RemoveFavorite(ASyncTransformer(), neqabtyCache)
    }
}
