package com.neqabty.data.repositories

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.entities.*
import com.neqabty.testing.OpenForTesting
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class NeqabtyRepositoryImpl @Inject constructor(private val cachedDataStore: CachedNeqabtyDataStore,
                                                private val remoteDataStore: RemoteNeqabtyDataStore) : NeqabtyRepository {
    override fun getMedicalProviders(categoryId: String): Observable<List<MedicalProviderEntity>> {
        return remoteDataStore.getMedicalProviders(categoryId)
    }

    override fun validateUser(userNumber: String): Observable<MemberEntity> {
        return remoteDataStore.validateUser(userNumber)
    }

    override fun getNotificationDetails(id: String): Observable<NotificationEntity> {
        return remoteDataStore.getNotificationDetails(id)
    }

    override fun getNotifications(userNumber: String, subSyndicateId: String): Observable<List<NotificationEntity>> {
        return remoteDataStore.getNotifications(userNumber ,subSyndicateId)
    }

    override fun sendMedicalRequest(mainSyndicateId: Int, subSyndicateId: Int, userNumber: String, email: String, phone: String, profession: Int, degree: Int, area: Int, doctor: Int, providerType: Int, provider: Int, docsNumber: Int, doc1: File?, doc2: File?, doc3: File?, doc4: File?, doc5: File?): Observable<Unit> {
        return remoteDataStore.sendMedicalRequest(mainSyndicateId,subSyndicateId,userNumber,email,phone,profession,degree,area,doctor,providerType,provider,docsNumber,doc1, doc2, doc3, doc4, doc5)
    }

    override fun getAllProviderTypes(): Observable<List<ProviderTypeEntitiy>> {
        return remoteDataStore.getAllProviderTypes()
    }

    override fun registerUser(mobile: String, mainSyndicateId: Int, subSyndicateId: Int, token: String, userNumber:String): Observable<Unit> {
        return remoteDataStore.registerUser(mobile,mainSyndicateId,subSyndicateId,token,userNumber)
    }

    override fun getAllProviders(type:String): Observable<List<ProviderEntity>> {
        return remoteDataStore.getAllProviders(type)
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


    override fun signup(email: String, fName: String, lName: String, mobile: String, govId: String, mainSyndicateId: String, subSyndicateId: String, password: String): Observable<UserEntity> {

        return remoteDataStore.signup(email, fName, lName, mobile, govId, mainSyndicateId, subSyndicateId, password)
                .doOnNext { user ->
                    saveUser(user)
                }

    }

    override fun login(mobile: String, password: String, token: String): Observable<UserEntity> {
        return cachedDataStore.isEmpty().flatMap { empty ->
            if (!empty) {
                return@flatMap cachedDataStore.login(mobile, password, token)
            } else {
                return@flatMap remoteDataStore.login(mobile, password, token)
                        .doOnNext { user ->
                            saveUser(user)
                        }
            }
        }
    }


    fun saveUser(userEntity: UserEntity): Observable<UserEntity> {
        return cachedDataStore.saveUser(userEntity)
    }

}