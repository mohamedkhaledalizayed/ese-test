package com.neqabty.domain

import com.neqabty.domain.entities.*
import io.reactivex.Observable
import java.io.File

interface NeqabtyRepository {
    fun getSyndicates(): Observable<List<SyndicateEntity>>
    fun geSyndicateById(id: String): Observable<SyndicateEntity>
    fun geSubSyndicatesById(id: String): Observable<List<SyndicateEntity>>
    fun getNews(id: String): Observable<List<NewsEntity>>
    fun getTrips(id: String): Observable<List<TripEntity>>
    fun getAllDoctors(): Observable<List<DoctorEntity>>
    fun getAllAreas(): Observable<List<AreaEntity>>
    fun getAllDegrees(): Observable<List<DegreeEntity>>
    fun getAllSpecializations(): Observable<List<SpecializationEntity>>
    fun getAllProviders(type: String): Observable<List<ProviderEntity>>
    fun getAllProviderTypes(): Observable<List<ProviderTypeEntitiy>>
    fun registerUser(mobile: String, mainSyndicateId: Int, subSyndicateId: Int, token: String, userNumber: String): Observable<Unit>
    fun getNotifications(userNumber: String, subSyndicateId: String): Observable<List<NotificationEntity>>
    fun getNotificationDetails(id: String): Observable<NotificationEntity>
    fun sendMedicalRequest(mainSyndicateId: Int, subSyndicateId: Int, userNumber: String, email: String, phone: String, profession: Int, degree: Int, area: Int, doctor: Int, providerType: Int, provider: Int, docsNumber: Int, doc1: File?, doc2: File?, doc3: File?, doc4: File?, doc5: File?): Observable<Unit>
    fun validateUser(userNumber: String): Observable<MemberEntity>
    fun getMedicalProviders(categoryId: String): Observable<List<MedicalProviderEntity>>

    fun login(mobile: String, password: String, token: String): Observable<UserEntity>
    fun signup(email: String, fName: String, lName: String, mobile: String, govId: String, mainSyndicateId: String, subSyndicateId: String, password: String): Observable<UserEntity>
}