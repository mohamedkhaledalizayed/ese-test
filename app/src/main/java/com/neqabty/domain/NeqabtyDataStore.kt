package com.neqabty.domain

import com.neqabty.domain.entities.*
import io.reactivex.Observable

interface NeqabtyDataStore {
    fun getSyndicates(): Observable<List<SyndicateEntity>>
    fun geSyndicateById(id : String): Observable<SyndicateEntity>
    fun geSubSyndicatesById(id : String): Observable<List<SyndicateEntity>>
    fun getNews(id :String): Observable<List<NewsEntity>>
    fun getTrips(): Observable<List<TripEntity>>
    fun getAllDoctors(): Observable<List<DoctorEntity>>
    fun getAllAreas(): Observable<List<AreaEntity>>
    fun getAllDegrees(): Observable<List<DegreeEntity>>
    fun getAllSpecializations(): Observable<List<SpecializationEntity>>
    fun getAllProviders(type : String): Observable<List<ProviderEntity>>
    fun getAllProviderTypes(): Observable<List<ProviderTypeEntitiy>>
    fun registerUser(mobile: String, mainSyndicateId: Int, subSyndicateId: Int, token : String):Observable<Unit>

    fun login(mobile: String, password: String, token: String): Observable<UserEntity>
    fun signup(email: String, fName: String, lName: String, mobile: String, govId: String, mainSyndicateId: String, subSyndicateId: String, password: String): Observable<UserEntity>

}