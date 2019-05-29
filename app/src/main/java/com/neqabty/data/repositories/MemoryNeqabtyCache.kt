package com.neqabty.data.repositories

import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.entities.*

import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class MemoryNeqabtyCache @Inject constructor() : NeqabtyCache {
    override fun checkFavorite(providerEntity: ProviderEntity): Observable<Boolean> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun addFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getFavorites(): Observable<List<ProviderEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getSubSyndicates(): Observable<List<SyndicateEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSubSyndicates(syndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getSyndicate(): Observable<SyndicateEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSyndicate(syndicate: SyndicateEntity): Observable<SyndicateEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getNews(): Observable<List<NewsEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun saveNews(news: List<NewsEntity>): Observable<List<NewsEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getTrips(): Observable<List<TripEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTrips(trips: List<TripEntity>): Observable<List<TripEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getSyndicates(): Observable<List<SyndicateEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSyndicates(syndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUser(userEntity: UserEntity): Observable<UserEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(): Observable<UserEntity> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Observable<Boolean> {
        return Observable.just(true)
    }

    override fun clear() {
//        historyList.clear()
    }
}
