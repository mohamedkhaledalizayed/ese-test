package com.neqabty.domain.common

import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.entities.*
import com.neqabty.domain.usecases.GetAllAreas
import com.neqabty.domain.usecases.GetAllDoctors
import com.neqabty.domain.usecases.GetAllSpecializations
import io.reactivex.Observable

class TestNeqabtyCache : NeqabtyCache {
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

//    override fun GetAllDoctors(): Observable<List<DoctorEntity>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun GetAllAreas(): Observable<List<AreaEntity>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun GetAllSpecializations(): Observable<List<SpecializationEntity>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override fun saveTrips(trips: List<TripEntity>): Observable<List<TripEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUser(userEntity: UserEntity): Observable<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSyndicates(): Observable<List<SyndicateEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSyndicates(syndicates: List<SyndicateEntity>): Observable<List<SyndicateEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun getUser(): Observable<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    private val weathers: HashMap<Int, WeatherEntity> = HashMap()

    override fun clear() {
//        weathers.clear()
    }

}
