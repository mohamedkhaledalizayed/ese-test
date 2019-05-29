package com.neqabty.data.api

import com.neqabty.data.api.requests.*
import com.neqabty.data.entities.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * REST API access points
 */
interface WebService {

    @GET("api/min-version")
    fun getAppVersion(): Observable<AppVersionData>

    @GET("api/v2/syndicates/All")
    fun getAllSyndicates(): Observable<ApiResponse<List<SyndicateData>>>

    @POST("api/Syndicates/Main/By_Id")
    fun getSyndicateById(@Body syndicateRequest: SyndicateRequest): Observable<ApiResponse<SyndicateData>>

    @POST("api/v2/Syndicates/Sub/All/byMain")
    fun getSubSyndicatesById(@Body subSyndicateRequest: SubSyndicateRequest): Observable<ApiResponse<List<SyndicateData>>>

    @POST("api/v2/medical/request/code")
    fun getNotifications(@Body notificationRequest: NotificationRequest): Observable<List<NotificationData>>

    @POST("api/v2/medical/request_details")
    fun getNotificationDetails(@Body notificationDetailsRequest: NotificationDetailsRequest): Observable<NotificationData>

    @POST("api/v2/news/main_syndicate")
    fun getAllNews(@Body newsRequest: NewsRequest): Observable<ApiResponse<List<NewsData>>>

    @POST("api/v2/trips")
    fun getAllTrips(@Body tripsRequest: TripsRequest): Observable<ApiResponse<List<TripData>>>

    @POST("api/v2/trips")
    fun getTripDetails(@Body tripDetailsRequest: TripDetailsRequest): Observable<ApiResponse<TripData>>

    @GET("api/v2/medical/areas")
    fun getAllAreas(): Observable<ApiResponse<List<AreaData>>>

    @GET("api/Governorates")
    fun getAllGoverns(): Observable<List<GovernData>>

    @GET("api/v2/medical/doctors")
    fun getAllDoctors(): Observable<ApiResponse<List<DoctorData>>>

    @GET("api/v2/medical/professions")
    fun getAllSpecializations(): Observable<ApiResponse<List<SpecializationData>>>

    @GET("api/v2/medical/degrees")
    fun getAllDegrees(): Observable<ApiResponse<List<DegreeData>>>

    @POST("api/v3/medical/providerTypes")
    fun getAllProviderTypes(@Body providerTypeRequest: ProviderTypeRequest): Observable<ApiResponse<List<ProviderTypeData>>>

    @POST("api/v3/medical/providerDetails")
    fun getProviderDetails(@Body providerDetailsRequest: ProviderDetailsRequest): Observable<ApiResponse<ProviderData>>

    @POST("api/v3/medical/providers")
    fun getProvidersById(@Body providerRequest: ProviderRequest): Observable<ApiResponse<List<ProviderData>>>

    @POST("api/v2/users/signup")
    fun registerUser(@Body registerRequest: RegisterRequest): Observable<ApiResponse<Unit>>

    @Multipart
    @POST("api/v2/medical/request")
    fun sendMedicalRequest(
        @Part("json_request") medicalRequest: MedicalRequest,
        @Part doc1: MultipartBody.Part?,
        @Part doc2: MultipartBody.Part?,
        @Part doc3: MultipartBody.Part?,
        @Part doc4: MultipartBody.Part?,
        @Part doc5: MultipartBody.Part?
    ): Observable<ApiResponse<Unit>>

    @POST("http://18.188.152.62:3000/neqabty/inquiry")
    fun validateUser(@Body validationRequest: ValidationRequest): Observable<MemberData>

    @POST("api/Auth/Login")
    fun login(@Body loginRequest: LoginRequest): Observable<ApiResponse<UserData>>

    @POST("api/Auth/SignUp")
    fun signup(@Body signupRequest: SignupRequest): Observable<ApiResponse<UserData>>
}
