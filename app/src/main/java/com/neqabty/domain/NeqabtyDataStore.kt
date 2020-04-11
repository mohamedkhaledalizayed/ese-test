package com.neqabty.domain

import com.neqabty.domain.entities.*
import io.reactivex.Observable
import java.io.File

interface NeqabtyDataStore {
    fun getAppVersion(): Observable<AppVersionEntity>
    fun getSyndicates(): Observable<List<SyndicateEntity>>
    fun geSyndicateById(id: String): Observable<SyndicateEntity>
    fun geSubSyndicatesById(id: String): Observable<List<SyndicateEntity>>
    fun getNews(id: String): Observable<List<NewsEntity>>
    fun getTrips(id: String): Observable<List<TripEntity>>
    fun getTripDetails(id: String): Observable<TripEntity>
    fun getAllDoctors(): Observable<List<DoctorEntity>>
    fun getAllAreas(): Observable<List<AreaEntity>>
    fun getAllGoverns(): Observable<List<GovernEntity>>
    fun getAllDegrees(): Observable<List<DegreeEntity>>
    fun getAllSpecializations(): Observable<List<SpecializationEntity>>
    fun getProviderDetails(id: String, type: String): Observable<ProviderEntity>
    fun getProvidersByType(
            providerTypeId: String,
            govId: String,
            areaId: String,
            professionID: String?,
            degreeID: String?
    ): Observable<List<ProviderEntity>>

    fun getAllProviderTypes(type: String): Observable<List<ProviderTypeEntitiy>>
    fun getNotificationsCount(userNumber: Int): Observable<NotificationsCountEntity>
    fun getNotifications(serviceID: Int, type: Int, userNumber: Int): Observable<List<NotificationEntity>>
    fun getNotificationDetails(serviceID: Int, type: Int, userNumber: Int, requestID: Int): Observable<NotificationEntity>
    fun sendMedicalRequest(
            mainSyndicateId: Int,
            subSyndicateId: Int,
            userNumber: String,
            email: String,
            phone: String,
            profession: Int,
            degree: Int,
            area: Int,
            doctor: Int,
            providerType: Int,
            provider: Int,
            name: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?,
            doc5: File?
    ): Observable<Unit>

    fun bookTrip(
            mainSyndicateId: Int,
            userNumber: String,
            phone: String,
            tripID: Int,
            regimentID: Int,
            regimentDate: String,
            housingType: String,
            numChild: Int,
            ages: String,
            name: String,
            personsList: List<PersonEntity>,
            docsNumber: Int,
            personsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?
    ): Observable<Unit>

    fun getAllServices(): Observable<List<ServiceEntity>>
    fun inquirePayment(userNumber: Int, serviceID: Int, requestID: String, amount: String): Observable<MemberEntity>
    fun encrypt(userName: String, password: String, description: String): Observable<EncryptionEntity>
    fun sendDecryptionKey(requestNumber: String, decryptionKey: String): Observable<DecryptionEntity>
    fun validateUserForClaiming(userNumber: String): Observable<ClaimingValidationEntity>
    fun updateUserDataInquiry(userNumber: String): Observable<InquireUpdateUserDataEntity>
    fun inquireEngineeringRecords(userNumber: String): Observable<RegisteryEntity>
    fun verifyUser(userNumber: String, mobileNumber: String): Observable<VerifyUserDataEntity>
    fun updateUserData(userNumber: String, fullName: String, nationalID: String, gender: String, userID: String): Observable<UpdateUserDataEntity>
    fun requestEngineeringRecords(
            name: String,
            phone: String,
            typeId: String,
            mainSyndicate: String,
            userNumber: String,
            lastRenewYear: String,
            statusID: Int,
            isOwner: Int,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?): Observable<Unit>


    fun loginVisitor(mobile: String, token: String): Observable<UserEntity>
    fun loginUser(mobile: String, userNumber: String, token: String): Observable<UserEntity>
    fun signup(
            email: String,
            fName: String,
            lName: String,
            mobile: String,
            govId: String,
            mainSyndicateId: String,
            subSyndicateId: String,
            password: String
    ): Observable<UserEntity>

    fun getComplaintTypes(): Observable<List<ComplaintTypeEntity>>
    fun createComplaint(name: String, phone: String, type: String, body: String, token: String, memberNumber: String): Observable<Unit>
}