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
    override fun createCoronaRequest(userNumber: String, phone: String, type: String, job: String, work: String, treatmentDestination: String, treatmentDestinationAddress: String, family: Int, injury: String, docsNumber: Int, doc1: File?, doc2: File?, doc3: File?, doc4: File?, doc5: File?): Observable<Unit> {
        return remoteDataStore.createCoronaRequest(userNumber, phone, type, job, work, treatmentDestination, treatmentDestinationAddress, family, injury, docsNumber, doc1, doc2, doc3, doc4, doc5)
    }

    override fun createComplaint(name: String, phone: String, type: String, body: String, token: String, memberNumber: String): Observable<Unit> {
        return remoteDataStore.createComplaint(name, phone, type, body, token, memberNumber)
    }

    override fun getComplaintTypes(): Observable<List<ComplaintTypeEntity>> {
        return remoteDataStore.getComplaintTypes()
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

    override fun updateUserData(userNumber: String, fullName: String, nationalID: String, gender: String, userID: String): Observable<UpdateUserDataEntity> {
        return remoteDataStore.updateUserData(userNumber, fullName, nationalID, gender, userID)
    }

    override fun inquireEngineeringRecords(userNumber: String): Observable<RegisteryEntity> {
        return remoteDataStore.inquireEngineeringRecords(userNumber)
    }

    override fun requestEngineeringRecords(name: String, phone: String, typeId: String, mainSyndicate: String, userNumber: String, lastRenewYear: String, statusID: Int, isOwner: Int, docsNumber: Int, doc1: File?, doc2: File?, doc3: File?): Observable<Unit> {
        return remoteDataStore.requestEngineeringRecords(name, phone, typeId, mainSyndicate, userNumber, lastRenewYear, statusID, isOwner, docsNumber, doc1, doc2, doc3)
    }

    override fun bookTrip(mainSyndicateId: Int, userNumber: String, phone: String, tripID: Int, regimentID: Int, regimentDate: String, housingType: String, numChild: Int, ages: String, name: String, personsList: List<PersonEntity>, docsNumber: Int, personsNumber: Int, doc1: File?, doc2: File?, doc3: File?, doc4: File?): Observable<Unit> {
        return remoteDataStore.bookTrip(mainSyndicateId, userNumber, phone, tripID, regimentID, regimentDate, housingType, numChild, ages, name, personsList, docsNumber, personsNumber, doc1, doc2, doc3, doc4)
    }

    override fun getAppVersion(): Observable<AppVersionEntity> {
        return remoteDataStore.getAppVersion()
    }

    override fun getTripDetails(id: String): Observable<TripEntity> {
        return remoteDataStore.getTripDetails(id)
    }

    override fun getAllServices(): Observable<List<ServiceEntity>> {
        return remoteDataStore.getAllServices()
    }

    override fun inquirePayment(userNumber: Int, serviceID: Int, requestID: String, amount: String): Observable<MemberEntity> {
        return remoteDataStore.inquirePayment(userNumber, serviceID, requestID, amount)
    }

    override fun encrypt(userName: String, password: String, description: String): Observable<EncryptionEntity> {
        return remoteDataStore.encrypt(userName, password, description)
    }

    override fun sendDecryptionKey(requestNumber: String, decryptionKey: String): Observable<DecryptionEntity> {
        return remoteDataStore.sendDecryptionKey(requestNumber, decryptionKey)
    }

    override fun getNotificationDetails(serviceID: Int, type: Int, userNumber: Int, requestID: Int): Observable<NotificationEntity> {
        return remoteDataStore.getNotificationDetails(serviceID, type, userNumber, requestID)
    }

    override fun getNotifications(serviceID: Int, type: Int, userNumber: Int): Observable<List<NotificationEntity>> {
        return remoteDataStore.getNotifications(serviceID, type, userNumber)
    }

    override fun getNotificationsCount(userNumber: String): Observable<NotificationsCountEntity> {
        return remoteDataStore.getNotificationsCount(userNumber.toInt())
    }

    override fun sendMedicalRequest(
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
            oldbenid: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?,
            doc5: File?
    ): Observable<Unit> {
        return remoteDataStore.sendMedicalRequest(mainSyndicateId, subSyndicateId, userNumber, email, phone, profession, degree, area, doctor, providerType, provider, name, oldbenid,docsNumber, doc1, doc2, doc3, doc4, doc5)
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
            professionID: String?,
            degreeID: String?
    ): Observable<List<ProviderEntity>> {
        return remoteDataStore.getProvidersByType(providerTypeId, govId, areaId, professionID, degreeID)
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
            email: String,
            fName: String,
            lName: String,
            mobile: String,
            govId: String,
            mainSyndicateId: String,
            subSyndicateId: String,
            password: String
    ): Observable<UserEntity> {

        return remoteDataStore.signup(email, fName, lName, mobile, govId, mainSyndicateId, subSyndicateId, password)
                .doOnNext { user ->
                    saveUser(user)
                }
    }


    override fun loginUser(mobile: String, userNumber: String, token: String): Observable<UserEntity> {
        return remoteDataStore.loginUser(mobile, userNumber, token)
    }

    override fun loginVisitor(mobile: String, token: String): Observable<UserEntity> {
        return remoteDataStore.loginVisitor(mobile, token)
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