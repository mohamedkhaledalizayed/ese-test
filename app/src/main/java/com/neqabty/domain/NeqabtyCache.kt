package com.neqabty.domain

import com.neqabty.domain.entities.*
import io.reactivex.Observable

interface NeqabtyCache {
    fun getSyndicates(): Observable<List<SyndicateEntity>>
    fun saveSyndicates(syndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>>

    fun getSyndicate(): Observable<SyndicateEntity>
    fun saveSyndicate(syndicate: SyndicateEntity): Observable<SyndicateEntity>

    fun getSubSyndicates(): Observable<List<SyndicateEntity>>
    fun saveSubSyndicates(syndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>>

    fun getNews(): Observable<List<NewsEntity>>
    fun saveNews(news: List<NewsEntity>): Observable<List<NewsEntity>>

    fun getTrips(): Observable<List<TripEntity>>
    fun saveTrips(trips: List<TripEntity>): Observable<List<TripEntity>>

    fun saveUser(userEntity: UserEntity) : Observable<UserEntity>
    fun getUser() : Observable<UserEntity>

    fun getFavorites(): Observable<List<ProviderEntity>>
    fun addFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity>
    fun removeFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity>
    fun checkFavorite(providerEntity: ProviderEntity): Observable<Boolean>

    fun isEmpty(): Observable<Boolean>
    fun clear()
}