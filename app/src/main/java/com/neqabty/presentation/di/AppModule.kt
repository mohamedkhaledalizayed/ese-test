package com.neqabty.presentation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
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
import com.neqabty.presentation.common.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
//        val certificatePinner : CertificatePinner = CertificatePinner.Builder()
//                .add(
//                        "weapps.te.eg",
//                        "sha256/1bMfEx60y/CeKMkWEi7TiqZDppseBC3KKs220b6NXQQ="
//                ).build()

        val client: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request = chain.request()
                        val response = chain.proceed(request)

                        if (!request.url().url().toString().contains("upgrade", true)) {
                            return response
                        }

                        var newRequest = request.newBuilder()
                                // .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlZ3lwdFxcYWhtZWQuZm91YWRnb21hYSIsImV4cCI6MTU3NDA4MjkyMCwiaWF0IjoxNTc0MDY0OTIwfQ.n8CvCPxVyJnoSHcfD9ePMS48Q3nFdN3Lvqmmdi3oy947X7g99i2Bk_sReyzkyGw9appwTeht1F-dlx7IzrrxEA")
                                .header("Authorization", "Bearer " + Constants.JWT)
                                .header("Accept", "application/json")
                                .removeHeader("Content-Type")
                                .build()

                        var newResponse = chain.proceed(newRequest)
                        return newResponse
                    }
                })
                .addInterceptor(interceptor) // TODO Interceptor
//                .certificatePinner(certificatePinner)
                .build()

        return Retrofit.Builder()
                .baseUrl("http://3.131.229.146:44392/") // TEST
//                .baseUrl("http://3.131.229.146/") // PROD
//                .baseUrl("http://front.neqabty.com/")
//                .baseUrl("http://192.168.178.38/")
//            .baseUrl("https://neqabty-stage.efinance.com.eg/")
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
    fun provideLogin(neqabtyRepository: NeqabtyRepository): Login {
        return Login(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideSignupUser(neqabtyRepository: NeqabtyRepository): SignupUser {
        return SignupUser(ASyncTransformer(), neqabtyRepository)
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
    fun provideBookTrip(neqabtyRepository: NeqabtyRepository): BookTrip {
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
    fun provideGetMedicalRenewalData(neqabtyRepository: NeqabtyRepository): GetMedicalRenewalData {
        return GetMedicalRenewalData(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideMedicalRenewPaymentInquiry(neqabtyRepository: NeqabtyRepository): MedicalRenewPaymentInquiry {
        return MedicalRenewPaymentInquiry(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateMedicalRenewalData(neqabtyRepository: NeqabtyRepository): UpdateMedicalRenewalData {
        return UpdateMedicalRenewalData(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideValidateUserForClaiming(neqabtyRepository: NeqabtyRepository): ValidateUserForClaiming {
        return ValidateUserForClaiming(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllServices(neqabtyRepository: NeqabtyRepository): GetAllServices {
        return GetAllServices(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllServiceTypes(neqabtyRepository: NeqabtyRepository): GetAllServiceTypes {
        return GetAllServiceTypes(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun providePaymentInquiry(neqabtyRepository: NeqabtyRepository): PaymentInquiry {
        return PaymentInquiry(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideEncryptData(neqabtyRepository: NeqabtyRepository): EncryptData {
        return EncryptData(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideSendDecryptionKey(neqabtyRepository: NeqabtyRepository): SendDecryptionKey {
        return SendDecryptionKey(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideGetComplaintTypes(neqabtyRepository: NeqabtyRepository): GetComplaintTypes {
        return GetComplaintTypes(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideCreateComplaint(neqabtyRepository: NeqabtyRepository): CreateComplaint {
        return CreateComplaint(ASyncTransformer(), neqabtyRepository)
    }

    @Singleton
    @Provides
    fun provideCreateCoronaRequest(neqabtyRepository: NeqabtyRepository): CreateCoronaRequest {
        return CreateCoronaRequest(ASyncTransformer(), neqabtyRepository)
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
