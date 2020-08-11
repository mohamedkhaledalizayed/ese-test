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

    @GET("api/v1/SubSyndicate")
    fun getAllSyndicates(): Observable<List<SyndicateData>>

    @POST("api/v2/Syndicates/Main/By_Id")
    fun getSyndicateById(@Body syndicateRequest: SyndicateRequest): Observable<ApiResponse<SyndicateData>>

    @POST("api/v2/Syndicates/Sub/All/byMain")
    fun getSubSyndicatesById(@Body subSyndicateRequest: SubSyndicateRequest): Observable<ApiResponse<List<SyndicateData>>>

    @POST("api/v1/countnotification")
    fun getNotificationsCount(@Body notificationRequest: NotificationRequest): Observable<NotificationsCountData>

    @POST("api/v1/listnotification")
    fun getNotifications(@Body notificationRequest: NotificationRequest): Observable<List<NotificationData>>

    @GET("api/v1/shownotification/{notificationId}")
    fun getNotificationDetails(
        @Path("notificationId") notificationId: Int,
        @Query("notification_type") notificationType: Int
    ): Observable<NotificationData>

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

    @Multipart
    @POST("/api/v2/trips/request")
    fun bookTrip(
        @Part("json_request") bookTripRequest: BookTripRequest,
        @Part doc1: MultipartBody.Part?,
        @Part doc2: MultipartBody.Part?,
        @Part doc3: MultipartBody.Part?,
        @Part doc4: MultipartBody.Part?
    ): Observable<ApiResponse<Unit>>

    @POST("api/v1/transactions/generate-hash")
    fun getTransactionHash(@Body validationRequest: ValidationRequest): Observable<MemberData>

    @GET("api/v1/services")
    fun getAllServices(): Observable<ResponseWrapper<List<List<ServiceData>>>>

    @GET("api/v1/service")
    fun paymentInquiry(
        @Query("oldRefID") userNumber: Int,
        @Query("serviceID") serviceID: Int,
        @Query("requestID") requestID: String,
        @Query("amount") amount: String
    ): Observable<ResponseWrapper<MemberData>>

    @POST("api/v1/encrypt")
    fun paymentEncryption(@Body encryptionRequest: EncryptionRequest): Observable<EncryptionData>

    @POST("api/v1/service/response")
    fun sendDecryptionKey(@Body decryptionRequest: DecryptionRequest): Observable<ResponseWrapper<DecryptionData>>

    @POST("api/v2/medical/Beneficiary")
    fun validateUser(@Body claimingValidationRequest: ClaimingValidationRequest): Observable<ApiResponse<ClaimingValidationData>>

    @POST("api/v1/Engineer/Data")
    fun updateUserDataInquiry(@Body inquireUpdateUserDataRequest: InquireUpdateUserDataRequest): Observable<ApiResponse<InquireUpdateUserData>>

    @POST("api/Engineer/sendsms")
    fun sendVerifySMS(@Body sendSMSRequest: SendSMSRequest): Observable<VerifyUserData>

    @Multipart
    @POST("api/v1/RecieveEngineerRquest")
    fun updateUserData(
        @Part("json_request") updateUserDataRequest: UpdateUserDataRequest,
        @Part doc1: MultipartBody.Part?,
        @Part doc2: MultipartBody.Part?,
        @Part doc3: MultipartBody.Part?
    ): Observable<UpdateUserData>

    @POST("api/EnginneringRecordsRegistry")
    fun engineeringRecordsInquiry(@Body engineeringRecordsInquiryRequest: EngineeringRecordsInquiryRequest): Observable<ApiResponse<RegisteryData>>

    @Multipart
    @POST("api/EnginneringRecordsRegistry/request")
    fun engineeringRecordsRequest(
        @Part("json_request") engineeringRecordsRequest: EngineeringRecordsRequest,
        @Part doc1: MultipartBody.Part?,
        @Part doc2: MultipartBody.Part?,
        @Part doc3: MultipartBody.Part?
    ): Observable<ApiResponse<Unit>>

    @POST("api/v1/login/member/mobile")
    fun loginVisitor(@Body loginVisitorRequest: LoginVisitorRequest): Observable<ApiResponse<UserData>>

    @POST("api/v1/upgrade/member/client")
    fun loginUser(@Body loginRequest: LoginRequest): Observable<ApiResponse<UserData>>

    @POST("api/Auth/Login")
    fun login(@Body loginRequest: LoginRequest): Observable<ApiResponse<UserData>>

    @POST("api/Auth/SignUp")
    fun signup(@Body signupRequest: SignupRequest): Observable<ApiResponse<UserData>>

    @GET("api/complaintServices")
    fun getComplaintTypes(): Observable<List<ComplaintTypeData>>

    @POST("api/complaints/request")
    fun sendComplaint(@Body complaintRequest: ComplaintRequest): Observable<ApiResponse<Unit>>

    @Multipart
    @POST("api/v1/RecieveCovid19Rquest")
    fun createCoronaRequest(
        @Part("json_request") coronaRequest: CoronaRequest,
        @Part doc1: MultipartBody.Part?,
        @Part doc2: MultipartBody.Part?,
        @Part doc3: MultipartBody.Part?,
        @Part doc4: MultipartBody.Part?,
        @Part doc5: MultipartBody.Part?
    ): Observable<ApiResponse<Unit>>
}
