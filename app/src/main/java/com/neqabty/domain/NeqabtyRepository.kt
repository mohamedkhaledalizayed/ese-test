package com.neqabty.domain

import com.neqabty.domain.entities.*
import io.reactivex.Observable
import java.io.File

interface NeqabtyRepository {
    fun getAppConfig(): Observable<AppConfigEntity>
    fun getSyndicates(): Observable<List<SyndicateEntity>>
    fun geSyndicateById(id: String): Observable<SyndicateEntity>
    fun geSubSyndicatesById(id: String): Observable<List<SyndicateEntity>>
    fun getSyndicateBranches(): Observable<List<SyndicateBranchEntity>>
    fun getNews(id: String): Observable<List<NewsEntity>>
    fun getTrips(id: String): Observable<List<TripEntity>>
    fun getTripDetails(id: String): Observable<TripEntity>
    fun getMedicalDirectoryLookups(mobileNumber: String): Observable<MedicalDirectoryLookupsEntity>
    fun getMedicalDirectoryProviders(mobileNumber: String, providerTypeId: String, govId: String, areaId: String, providerName: String, specializationId: String): Observable<List<MedicalDirectoryProviderEntity>>
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
            providerName: String?,
            professionID: String?,
            degreeID: String?
    ): Observable<List<ProviderEntity>>

    fun getAllProviderTypes(type: String): Observable<List<ProviderTypeEntitiy>>

    fun getNotificationsCount(userNumber: String): Observable<NotificationsCountEntity>

    fun getNotifications(
            serviceID: Int,
            type: Int,
            userNumber: String
    ): Observable<List<NotificationEntity>>

    fun getNotificationDetails(serviceID: Int, type: Int, userNumber: String, requestID: String): Observable<NotificationEntity>
    fun sendMedicalRequest(
            mainSyndicateId: Int,
            subSyndicateId: Int,
            userNumber: String,
            email: String,
            phone: String,
            profession: Int,
            degree: Int,
            gov: Int,
            area: Int,
            doctor: Int,
            providerType: Int,
            provider: Int,
            name: String,
            oldbenid: String,
            details: String,
            followerName: String,
            followerRelation: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?,
            doc5: File?
    ): Observable<Unit>

    fun getOnlinePharmacyURL(userNumber: String): Observable<OnlinePharmacyEntity>
    fun getDoctorsReservationData(mobileNumber: String): Observable<DoctorsReservationEntity>
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
            doc4: File?,
            doc5: File?,
            doc6: File?,
            doc7: File?,
            doc8: File?,
            doc9: File?,
            doc10: File?
    ): Observable<Unit>

    fun getCommitteesLookups(): Observable<CommitteesLookupEntity>
    fun sendCommitteesRequest(
        name: String,
        userNumber: String,
        mobile: String,
        email: String,
        nationalId: String,
        address: String,
        university: String,
        degree: String,
        maritalStatus: String,
        committeesIds:  List<Int>,
        sectionId: Int,
        syndicateId: Int,
        department: String,
        section: String,
        currentJob: String,
        details: String,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?
    ): Observable<String>
    fun getProfile(mobile: String, userNumber: String): Observable<ProfileEntity>
    fun getAllServiceTypes(): Observable<List<ServiceTypeEntity>>
    fun getAllServices(typeID: Int): Observable<List<ServiceEntity>>
    fun inquirePayment(isInquire: Boolean, mobileNumber: String, userNumber: String, serviceID: Int, requestID: String, amount: String, locationType: Int, address: String, mobile: String): Observable<MedicalRenewalPaymentEntity>
    fun addRenewalRequestNew(mobileNumber: String, userNumber: String, userName: String, serviceID: Int, paymentType: String, paymentGatewayId: Int, locationType: Int, address: String, mobile: String): Observable<PaymentRequestEntity>
    fun createFawryTransaction(refrenceId: String): Observable<FawryTransactionEntity>
    fun encrypt(userName: String, password: String, description: String): Observable<EncryptionEntity>
    fun sendDecryptionKey(requestNumber: String, decryptionKey: String): Observable<DecryptionEntity>
    fun getMedicalLetters(mobileNumber: String, benID: String, start: Int, end: Int, orderBy: String, dir: String): Observable<MedicalLetterEntity>
    fun getMedicalLetterByID(mobileNumber: String, userNumber: String, id: String): Observable<MedicalLetterEntity.LetterItem>
    fun getAds(sectionId: Int): Observable<List<AdEntity>>
    fun getLiteFollowersListData(mobileNumber: String, userNumber: String): Observable<List<LiteFollowersListEntity>>
    fun getMedicalRenewalData(mobileNumber: String, userNumber: String): Observable<MedicalRenewalEntity>
    fun inquireMedicalRenewalPayment(isInquire: Boolean, mobileNumber: String, userNumber: String, locationType: Int, address: String, mobile: String): Observable<MedicalRenewalPaymentEntity>
    fun addMedicalRenewalRequestNew(mobileNumber: String, userNumber: String, userName: String, serviceID: Int, paymentType: String, paymentGatewayId: Int, locationType: Int, address: String, mobile: String): Observable<PaymentRequestEntity>
    fun updateMedicalRenewalData(mobileNumber: String, medicalRenewalData: MedicalRenewalEntity): Observable<MedicalRenewalUpdateEntity>
    fun validateUserForClaiming(userNumber: String): Observable<ClaimingValidationEntity>
    fun updateUserDataInquiry(userNumber: String): Observable<InquireUpdateUserDataEntity>
    fun verifyUser(userNumber: String, mobileNumber: String): Observable<VerifyUserDataEntity>
    fun updateUserData(
            userNumber: String,
            name: String,
            nationalID: String,
            mobile: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?
    ): Observable<UpdateUserDataEntity>

    fun inquireEngineeringRecords(userNumber: String): Observable<RegisteryEntity>
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
            doc3: File?,
            doc4: File?,
            doc5: File?
    ): Observable<Unit>

    fun login(actionType: String, mobile: String, userNumber: String, newToken: String, oldToken: String, password: String=""): Observable<UserEntity>
    fun forgetPassword(mobile: String, userNumber: String, natId: String): Observable<String>
    fun changePassword(mobile: String, currentPassword: String, newPassword: String): Observable<String>
    fun setNewPassword(mobile: String, verificationCode: String, newPassword: String): Observable<String>
    fun trackShipment(userNumber: String): Observable<List<TrackShipmentEntity>>
    fun changeUserMobile(userNumber: String, natID: String, newMobile: String, oldMobile: String): Observable<ChangeUserMobileEntity>
    fun signup(
            userNumber: String,
            mobile: String,
            natID: String,
            newFirebaseToken: String,
            oldFirebaseToken: String
    ): Observable<UserEntity>
    fun sendSMS(mobileNumber: String): Observable<Unit>
    fun activateAccount(
            mobile: String,
            verificationCode: String,
            password: String
    ): Observable<UserEntity>

    fun getComplaintTypes(): Observable<List<ComplaintTypeEntity>>
    fun getComplaintSubTypes(id: String): Observable<List<ComplaintTypeEntity>>
    fun createComplaint(name: String, phone: String, catId: String, subCatId: String, body: String, token: String, memberNumber: String,
                        docsNumber: Int,
                        doc1: File?,
                        doc2: File?,
                        doc3: File?,
                        doc4: File?): Observable<Unit>
    fun getQuestionnaires(
            userNumber: String
    ): Observable<QuestionnaireEntity>
    fun voteQuestionnaire(
            userNumber: String,
            questionnaireId: Int,
            answerId: Int
    ): Observable<QuestionnaireVoteEntity>
}