package com.neqabty.data.repositories

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.entities.*

import io.reactivex.Observable
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class NeqabtyRepositoryImpl @Inject constructor(
        private val cachedDataStore: CachedNeqabtyDataStore,
        private val remoteDataStore: RemoteNeqabtyDataStore
) : NeqabtyRepository {
    override fun createCoronaRequest(
            userNumber: String,
            phone: String,
            syndicateID: Int,
            name: String,
            type: String,
            job: String,
            work: String,
            treatmentDestination: String,
            treatmentDestinationAddress: String,
            family: Int,
            injury: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?,
            doc5: File?
    ): Observable<Unit> {
        return remoteDataStore.createCoronaRequest(userNumber, phone, syndicateID, name, type, job, work, treatmentDestination, treatmentDestinationAddress, family, injury, docsNumber, doc1, doc2, doc3, doc4, doc5)
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
        return remoteDataStore.createComplaint(name, phone, catId, subCatId, body, token, memberNumber,docsNumber, doc1, doc2, doc3, doc4)
    }

    override fun getComplaintTypes(): Observable<List<ComplaintTypeEntity>> {
        return remoteDataStore.getComplaintTypes()
    }

    override fun getComplaintSubTypes(id: String): Observable<List<ComplaintTypeEntity>> {
        return remoteDataStore.getComplaintSubTypes(id)
    }

    override fun getMedicalLetters(benID: String, start: Int, end: Int, orderBy: String, dir: String): Observable<MedicalLetterEntity> {
        return remoteDataStore.getMedicalLetters(benID, start, end, orderBy, dir)
    }

    override fun getAds(sectionId: Int): Observable<AdEntity> {
        return remoteDataStore.getAds(sectionId)
    }

    override fun getMedicalRenewalData(mobileNumber: String, userNumber: String): Observable<MedicalRenewalEntity> {
        return remoteDataStore.getMedicalRenewalData(mobileNumber, userNumber)
    }

    override fun inquireMedicalRenewalPayment(isInquire: Boolean, mobileNumber: String, userNumber: String, locationType: Int, address: String, mobile: String): Observable<MedicalRenewalPaymentEntity> {
        return remoteDataStore.inquireMedicalRenewalPayment(isInquire, mobileNumber, userNumber, locationType, address, mobile)
    }

    override fun updateMedicalRenewalData(mobileNumber: String, medicalRenewalData: MedicalRenewalEntity): Observable<MedicalRenewalUpdateEntity> {
        return remoteDataStore.updateMedicalRenewalData(mobileNumber, medicalRenewalData)
    }

    override fun validateUserForClaiming(userNumber: String): Observable<ClaimingValidationEntity> {
        return remoteDataStore.validateUserForClaiming(userNumber)
    }

    override fun updateUserDataInquiry(userNumber: String): Observable<InquireUpdateUserDataEntity> {
        return remoteDataStore.updateUserDataInquiry(userNumber)
    }

    override fun verifyUser(userNumber: String, mobileNumber: String): Observable<VerifyUserDataEntity> {
        return remoteDataStore.verifyUser(userNumber, mobileNumber)
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
        return remoteDataStore.updateUserData(userNumber, name, nationalID, mobile, docsNumber, doc1, doc2, doc3)
    }

    override fun inquireEngineeringRecords(userNumber: String): Observable<RegisteryEntity> {
        return remoteDataStore.inquireEngineeringRecords(userNumber)
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
        return remoteDataStore.requestEngineeringRecords(name, phone, typeId, mainSyndicate, userNumber, lastRenewYear, statusID, isOwner, docsNumber, doc1, doc2, doc3, doc4, doc5)
    }

    override fun bookTrip(
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
    ): Observable<Unit> {
        return remoteDataStore.bookTrip(mainSyndicateId, userNumber, phone, tripID, regimentID, regimentDate, housingType, numChild, ages, name, personsList, docsNumber, personsNumber, doc1,
                doc2,
                doc3,
                doc4,
                doc5,
                doc6,
                doc7,
                doc8,
                doc9,
                doc10)
    }

    override fun getAppConfig(): Observable<AppConfigEntity> {
        return remoteDataStore.getAppConfig()
    }

    override fun getTripDetails(id: String): Observable<TripEntity> {
        return remoteDataStore.getTripDetails(id)
    }

    override fun getAllServices(typeID: Int): Observable<List<ServiceEntity>> {
        return remoteDataStore.getAllServices(typeID)
    }

    override fun getAllServiceTypes(): Observable<List<ServiceTypeEntity>> {
        return remoteDataStore.getAllServiceTypes()
    }

    override fun inquirePayment(isInquire: Boolean, mobileNumber: String, userNumber: String, serviceID: Int, requestID: String, amount: String): Observable<MedicalRenewalPaymentEntity> {
        return remoteDataStore.inquirePayment(isInquire, mobileNumber, userNumber, serviceID, requestID, amount)
    }

    override fun encrypt(userName: String, password: String, description: String): Observable<EncryptionEntity> {
        return remoteDataStore.encrypt(userName, password, description)
    }

    override fun sendDecryptionKey(requestNumber: String, decryptionKey: String): Observable<DecryptionEntity> {
        return remoteDataStore.sendDecryptionKey(requestNumber, decryptionKey)
    }

    override fun getNotificationDetails(serviceID: Int, type: Int, userNumber: String, requestID: String): Observable<NotificationEntity> {
        return remoteDataStore.getNotificationDetails(serviceID, type, userNumber, requestID)
    }

    override fun getNotifications(serviceID: Int, type: Int, userNumber: String): Observable<List<NotificationEntity>> {
        return remoteDataStore.getNotifications(serviceID, type, userNumber)
    }

    override fun getNotificationsCount(userNumber: String): Observable<NotificationsCountEntity> {
        return remoteDataStore.getNotificationsCount(userNumber)
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
        return remoteDataStore.sendMedicalRequest(mainSyndicateId, subSyndicateId, userNumber, email, phone, profession, degree, gov, area, doctor, providerType, provider, name, oldbenid, details, followerName, followerRelation, docsNumber, doc1, doc2, doc3, doc4, doc5)
    }

    override fun getOnlinePharmacyURL(userNumber: String): Observable<OnlinePharmacyEntity> {
        return remoteDataStore.getOnlinePharmacyURL(userNumber)
    }

    override fun getAllProviderTypes(type: String): Observable<List<ProviderTypeEntitiy>> {
        return remoteDataStore.getAllProviderTypes(type)
    }

    override fun getProviderDetails(id: String, type: String): Observable<ProviderEntity> {
        return remoteDataStore.getProviderDetails(id, type)
    }

    override fun getProvidersByType(
            providerTypeId: String,
            govId: String,
            areaId: String,
            providerName: String?,
            professionID: String?,
            degreeID: String?
    ): Observable<List<ProviderEntity>> {
        return remoteDataStore.getProvidersByType(providerTypeId, govId, areaId, providerName, professionID, degreeID)
    }

    override fun getAllDoctors(): Observable<List<DoctorEntity>> {
        return remoteDataStore.getAllDoctors()
    }

    override fun getAllDegrees(): Observable<List<DegreeEntity>> {
        return remoteDataStore.getAllDegrees()
    }

    override fun getAllAreas(): Observable<List<AreaEntity>> {
        return remoteDataStore.getAllAreas()
    }

    override fun getAllGoverns(): Observable<List<GovernEntity>> {
        return remoteDataStore.getAllGoverns()
    }

    override fun getAllSpecializations(): Observable<List<SpecializationEntity>> {
        return remoteDataStore.getAllSpecializations()
    }

    override fun geSubSyndicatesById(id: String): Observable<List<SyndicateEntity>> {
        return remoteDataStore.geSubSyndicatesById(id)
    }

    override fun geSyndicateById(id: String): Observable<SyndicateEntity> {
        return remoteDataStore.geSyndicateById(id)
    }

    fun saveSyndicate(syndicate: SyndicateEntity): Observable<SyndicateEntity> {
        return cachedDataStore.saveSyndicate(syndicate)
    }

    fun saveSubSyndicates(subSyndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>> {
        return cachedDataStore.saveSyndicates(subSyndicates)
    }

    override fun getNews(id: String): Observable<List<NewsEntity>> {

        return remoteDataStore.getNews(id)
    }

    fun saveNews(news: List<NewsEntity>): Observable<List<NewsEntity>> {
        return cachedDataStore.saveNews(news)
    }

    override fun getTrips(id: String): Observable<List<TripEntity>> {

        return remoteDataStore.getTrips(id)
    }

    fun saveTrips(trips: List<TripEntity>): Observable<List<TripEntity>> {
        return cachedDataStore.saveTrips(trips)
    }

    override fun getSyndicates(): Observable<List<SyndicateEntity>> {

        return remoteDataStore.getSyndicates()
    }

    fun saveSyndicates(syndicatesList: List<SyndicateEntity>): Observable<List<SyndicateEntity>> {
        return cachedDataStore.saveSyndicates(syndicatesList)
    }

    override fun signup(
            userNumber: String,
            mobile: String,
            natID: String,
            newFirebaseToken: String,
            oldFirebaseToken: String
    ): Observable<UserEntity> {

        return remoteDataStore.signup(userNumber, mobile, natID, newFirebaseToken, oldFirebaseToken)
//                .doOnNext { user ->
//                    saveUser(user)
//                }
    }

    override fun sendSMS(mobileNumber: String): Observable<Unit> {
        return remoteDataStore.sendSMS(mobileNumber)
    }

    override fun activateAccount(mobile: String, verificationCode: String, password: String): Observable<UserEntity> {
        return remoteDataStore.activateAccount(mobile, verificationCode, password)
    }

    override fun login(actionType: String, mobile: String, userNumber: String, newToken: String, oldToken: String, password: String): Observable<UserEntity> {
        return remoteDataStore.login(actionType, mobile, userNumber, newToken, oldToken, password)
    }

    override fun forgetPassword(mobile: String, userNumber: String, natId: String): Observable<String> {
        return remoteDataStore.forgetPassword(mobile, userNumber, natId)
    }

    override fun changePassword(mobile: String, currentPassword: String, newPassword: String): Observable<String> {
        return remoteDataStore.changePassword(mobile, currentPassword, newPassword)
    }

    override fun setNewPassword(mobile: String, verificationCode: String, newPassword: String): Observable<String> {
        return remoteDataStore.setNewPassword(mobile, verificationCode, newPassword)
    }

    override fun trackShipment(userNumber: String): Observable<List<TrackShipmentEntity>> {
        return remoteDataStore.trackShipment(userNumber)
    }

    override fun changeUserMobile(userNumber: String, natID: String, newMobile: String, oldMobile: String): Observable<ChangeUserMobileEntity> {
        return remoteDataStore.changeUserMobile(userNumber, natID, newMobile, oldMobile)
    }

    fun saveUser(userEntity: UserEntity): Observable<UserEntity> {
        return cachedDataStore.saveUser(userEntity)
    }

    fun addFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        return cachedDataStore.addFavorite(providerEntity)
    }

    fun removeFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        return cachedDataStore.removeFavorite(providerEntity)
    }

    fun getFavorites(): Observable<List<ProviderEntity>> {
        return cachedDataStore.getFavorites()
    }
}