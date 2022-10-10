package com.neqabty.data.repositories

import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.NeqabtyDataStore
import com.neqabty.domain.entities.*

import io.reactivex.Observable
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class CachedNeqabtyDataStore @Inject constructor(private val neqabtyCache: NeqabtyCache) : NeqabtyDataStore {

    override fun sendDecryptionKey(requestNumber: String, decryptionKey: String): Observable<DecryptionEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun encrypt(userName: String, password: String, description: String): Observable<EncryptionEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllServices(typeID: Int): Observable<List<ServiceEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllServiceTypes(): Observable<List<ServiceTypeEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getSyndicateServices(userNumber: String): Observable<SyndicateServicesEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun createComplaint(
            name: String,
            phone: String,
            catId: String,
            subCatId: String,
            body: String,
            token: String,
            memberNumber: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?
    ): Observable<Unit> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getQuestionnaires(userNumber: String): Observable<QuestionnaireEntity> {
        TODO("Not yet implemented")
    }

    override fun voteQuestionnaire(userNumber: String, questionnaireId: Int, answerId: Int): Observable<QuestionnaireVoteEntity> {
        TODO("Not yet implemented")
    }

    override fun getComplaintTypes(): Observable<List<ComplaintTypeEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getComplaintSubTypes(id: String): Observable<List<ComplaintTypeEntity>> {
        TODO("Not yet implemented")
    }

    override fun getMedicalLetters(mobileNumber: String, benID: String, start: Int, end: Int, orderBy: String, dir: String): Observable<MedicalLetterEntity> {
        TODO("Not yet implemented")
    }

    override fun getMedicalLetterByID(mobileNumber: String, userNumber: String, id: String): Observable<MedicalLetterEntity.LetterItem> {
        TODO("Not yet implemented")
    }

    override fun getAds(sectionId: Int): Observable<List<AdEntity>> {
        TODO("Not yet implemented")
    }

    override fun getLiteFollowersListData(mobileNumber: String, userNumber: String): Observable<List<LiteFollowersListEntity>> {
        TODO("Not yet implemented")
    }

    override fun getMedicalRenewalData(mobileNumber: String, userNumber: String): Observable<MedicalRenewalEntity> {
        TODO("Not yet implemented")
    }

    override fun inquireMedicalRenewalPayment(userNumber: String): Observable<MedicalRenewalPaymentEntity> {
        TODO("Not yet implemented")
    }

    override fun addMedicalRenewalRequest(
        mobileNumber: String,
        userNumber: String,
        userName: String,
        serviceID: Int,
        paymentType: String,
        paymentGatewayId: Int,
        locationType: Int,
        address: String,
        mobile: String
    ): Observable<PaymentRequestEntity> {
        TODO("Not yet implemented")
    }

    override fun updateMedicalRenewalData(mobileNumber: String, medicalRenewalData: MedicalRenewalEntity): Observable<MedicalRenewalUpdateEntity> {
        TODO("Not yet implemented")
    }

    override fun validateUserForClaiming(userNumber: String): Observable<ClaimingValidationEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun verifyUser(userNumber: String, mobileNumber: String): Observable<VerifyUserDataEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUserData(
            userNumber: String,
            name: String,
            nationalID: String,
            mobile: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?
    ): Observable<UpdateUserDataEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUserDataInquiry(userNumber: String): Observable<InquireUpdateUserDataEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun inquireEngineeringRecords(userNumber: String): Observable<RegisteryEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun requestEngineeringRecords(
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
    ): Observable<Unit> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getNotificationsCount(userNumber: String): Observable<NotificationsCountEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getNotifications(serviceID: Int, type: Int, userNumber: String): Observable<List<NotificationEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getNotificationDetails(serviceID: Int, type: Int, userNumber: String, requestID: String): Observable<NotificationEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun bookTrip(mainSyndicateId: Int, userNumber: String, phone: String, tripID: Int, regimentID: Int, regimentDate: String, housingType: String, numChild: Int, ages: String, name: String, personsList: List<PersonEntity>, docsNumber: Int, personsNumber: Int, doc1: File?, doc2: File?, doc3: File?, doc4: File?, doc5: File?, doc6: File?, doc7: File?, doc8: File?, doc9: File?, doc10: File?): Observable<Unit> {
        TODO("Not yet implemented")
    }

    override fun getAppConfig(): Observable<AppConfigEntity> {
        TODO("Not yet implemented")
    }

    override fun getProvidersByType(
            providerTypeId: String,
            govId: String,
            areaId: String,
            providerName: String?,
            professionID: String?,
            degreeID: String?
    ): Observable<List<ProviderEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllGoverns(): Observable<List<GovernEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getTripDetails(id: String): Observable<TripEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun inquirePayment(userNumber: String): Observable<RenewalPaymentEntity> {
        TODO("Not yet implemented")
    }

    override fun addRenewalRequest(
        mobileNumber: String,
        userNumber: String,
        userName: String,
        serviceID: Int,
        paymentType: String,
        paymentGatewayId: Int,
        locationType: Int,
        address: String,
        mobile: String
    ): Observable<PaymentRequestEntity> {
        TODO("Not yet implemented")
    }

    override fun addSyndicateServicesPaymentRequest(
        mobileNumber: String,
        userNumber: String,
        userName: String,
        serviceID: Int,
        countryID: Int,
        paymentType: String,
        paymentGatewayId: Int,
        locationType: Int,
        address: String,
        mobile: String
    ): Observable<PaymentRequestEntity> {
        TODO("Not yet implemented")
    }

    override fun sendMedicalRequest(
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
    ): Observable<Unit> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getOnlinePharmacyURL(userNumber: String): Observable<OnlinePharmacyEntity> {
        TODO("Not yet implemented")
    }

    override fun getDoctorsReservationData(mobileNumber: String): Observable<DoctorsReservationEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllProviderTypes(type: String): Observable<List<ProviderTypeEntitiy>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getProviderDetails(id: String, type: String): Observable<ProviderEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getMedicalDirectoryLookups(mobileNumber: String): Observable<MedicalDirectoryLookupsEntity> {
        TODO("Not yet implemented")
    }

    override fun getMedicalDirectoryProviders(
        mobileNumber: String,
        providerType: String,
        govId: String,
        areaId: String,
        providerName: String,
        specializationId: String
    ): Observable<List<MedicalDirectoryProviderEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllDoctors(): Observable<List<DoctorEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllDegrees(): Observable<List<DegreeEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllAreas(): Observable<List<AreaEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllSpecializations(): Observable<List<SpecializationEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun geSubSyndicatesById(id: String): Observable<List<SyndicateEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun geSyndicateById(id: String): Observable<SyndicateEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getNews(id: String): Observable<List<NewsEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getTrips(id: String): Observable<List<TripEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getSyndicates(): Observable<List<SyndicateEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    fun saveSyndicates(syndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>> {
        return neqabtyCache.saveSyndicates(syndicates)
    }

    fun saveSyndicate(syndicate: SyndicateEntity): Observable<SyndicateEntity> {
        return neqabtyCache.saveSyndicate(syndicate)
    }

    fun saveNews(news: List<NewsEntity>): Observable<List<NewsEntity>> {
        return neqabtyCache.saveNews(news)
    }

    fun saveTrips(trips: List<TripEntity>): Observable<List<TripEntity>> {
        return neqabtyCache.saveTrips(trips)
    }

    override fun login(actionType: String, mobile: String, userNumber: String, newToken: String, oldToken: String, password: String): Observable<UserEntity> {
        return neqabtyCache.getUser()
    }

    override fun forgetPassword(mobile: String, userNumber: String, natId: String): Observable<String> {
        TODO("Not yet implemented")
    }

    override fun changePassword(mobile: String, currentPassword: String, newPassword: String): Observable<String> {
        TODO("Not yet implemented")
    }

    override fun setNewPassword(mobile: String, verificationCode: String, newPassword: String): Observable<String> {
        TODO("Not yet implemented")
    }

    override fun trackShipment(userNumber: String): Observable<List<TrackShipmentEntity>> {
        TODO("Not yet implemented")
    }

    override fun changeUserMobile(userNumber: String, natID: String, newMobile: String, oldMobile: String): Observable<ChangeUserMobileEntity> {
        TODO("Not yet implemented")
    }

    override fun signup(
            userNumber: String,
            mobile: String,
            natID: String,
            newFirebaseToken: String,
            oldFirebaseToken: String
    ): Observable<UserEntity> {
        return neqabtyCache.getUser()
    }

    override fun sendSMS(mobileNumber: String): Observable<Unit> {
        TODO("Not yet implemented")
    }

    override fun activateAccount(mobile: String, verificationCode: String, password: String): Observable<UserEntity> {
        TODO("Not yet implemented")
    }

    fun saveUser(userEntity: UserEntity): Observable<UserEntity> {
        return neqabtyCache.saveUser(userEntity)
    }

    fun addFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        return neqabtyCache.addFavorite(providerEntity)
    }

    fun removeFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        return neqabtyCache.removeFavorite(providerEntity)
    }

    fun getFavorites(): Observable<List<ProviderEntity>> {
        return neqabtyCache.getFavorites()
    }

    fun isEmpty(): Observable<Boolean> {
        return neqabtyCache.isEmpty()
    }
}