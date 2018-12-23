package com.neqabty.data.api

import com.neqabty.data.api.requests.*
import com.neqabty.data.entities.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * REST API access points
 */
interface WebService {

    @GET("api/v2/syndicates/All")
    fun getAllSyndicates(): Observable<ApiResponse<List<SyndicateData>>>

    @POST("api/v2/Syndicates/Main/By_Id")
    fun getSyndicateById(@Body syndicateRequest: SyndicateRequest): Observable<ApiResponse<SyndicateData>>

    @POST("api/v2/Syndicates/Sub/All/byMain")
    fun getSubSyndicatesById(@Body subSyndicateRequest: SubSyndicateRequest): Observable<ApiResponse<List<SyndicateData>>>

    @POST("api/v2/news/main_syndicate")
    fun getAllNews(@Body newsRequest: NewsRequest): Observable<ApiResponse<List<NewsData>>>

    @GET("api/Trips/All")
    fun getAllTrips(): Observable<ApiResponse<List<TripData>>>

    @GET("api/v2/medical/areas")
    fun getAllAreas(): Observable<ApiResponse<List<AreaData>>>

    @GET("api/v2/medical/doctors")
    fun getAllDoctors(): Observable<ApiResponse<List<DoctorData>>>

    @GET("api/v2/medical/professions")
    fun getAllSpecializations(): Observable<ApiResponse<List<SpecializationData>>>

    @GET("api/v2/medical/degrees")
    fun getAllDegrees(): Observable<ApiResponse<List<DegreeData>>>

    @POST("api/v2/medical/analytical_laboratories")
    fun getAllProviders(@Body providerRequest: ProviderRequest): Observable<List<ProviderData>>

    @POST("api/v2/users/signup")
    fun registerUser(@Body registerRequest: RegisterRequest): Observable<ApiResponse<Unit>>

    @POST("api/Auth/Login")
    fun login(@Body loginRequest: LoginRequest): Observable<ApiResponse<UserData>>

    @POST("api/Auth/SignUp")
    fun signup(@Body signupRequest: SignupRequest): Observable<ApiResponse<UserData>>

}
