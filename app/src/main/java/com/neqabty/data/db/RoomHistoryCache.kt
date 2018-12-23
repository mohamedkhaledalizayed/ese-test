package com.neqabty.data.db

import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.entities.NewsEntity
import com.neqabty.domain.entities.SyndicateEntity
import com.neqabty.domain.entities.TripEntity
import com.neqabty.domain.entities.UserEntity
import com.neqabty.testing.OpenForTesting
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class RoomHistoryCache  @Inject constructor(database: NeqabtyDb) : NeqabtyCache {
    override fun getSubSyndicates(): Observable<List<SyndicateEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSubSyndicates(syndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSyndicate(): Observable<SyndicateEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSyndicate(syndicate: SyndicateEntity): Observable<SyndicateEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNews(): Observable<List<NewsEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveNews(news: List<NewsEntity>): Observable<List<NewsEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTrips(): Observable<List<TripEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTrips(trips: List<TripEntity>): Observable<List<TripEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSyndicates(): Observable<List<SyndicateEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSyndicates(syndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUser(userEntity: UserEntity): Observable<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(): Observable<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
//        historyDao.clear()
    }
    override fun isEmpty(): Observable<Boolean> {
        return Observable.just(true)
    }

}