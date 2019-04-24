package com.neqabty.data.repositories

import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.NeqabtyDataStore
import com.neqabty.domain.entities.*
import com.neqabty.testing.OpenForTesting
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class CachedNeqabtyDataStore @Inject constructor(private val neqabtyCache: NeqabtyCache) : NeqabtyDataStore {
    override fun getProvidersByType(
        providerTypeId: String,
        govId: String,
        areaId: String,
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

    override fun validateUser(userNumber: String): Observable<MemberEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getNotificationDetails(id: String): Observable<NotificationEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getNotifications(userNumber: String, subSyndicateId: String): Observable<List<NotificationEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
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
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?,
        doc4: File?,
        doc5: File?
    ): Observable<Unit> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllProviderTypes(type: String): Observable<List<ProviderTypeEntitiy>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun registerUser(
        mobile: String,
        mainSyndicateId: Int,
        subSyndicateId: Int,
        token: String,
        userNumber: String
    ): Observable<Unit> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getProviderDetails(id: String, type: String): Observable<ProviderEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
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

    override fun login(mobile: String, password: String, token: String): Observable<UserEntity> {
        return neqabtyCache.getUser()
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
        return neqabtyCache.getUser()
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