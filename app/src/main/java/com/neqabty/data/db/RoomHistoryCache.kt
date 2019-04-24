package com.neqabty.data.db

import com.neqabty.data.mappers.ProviderDataEntityMapper
import com.neqabty.data.mappers.ProviderEntityDataMapper
import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.entities.*
import com.neqabty.testing.OpenForTesting
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class RoomHistoryCache @Inject constructor(
    database: NeqabtyDb,
    private val providerDataEntityMapper: ProviderDataEntityMapper,
    private val providerEntityDataMapper: ProviderEntityDataMapper
) : NeqabtyCache {
    private val providerDao: ProviderDao = database.providerDao()

    override fun addFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        providerDao.saveProvider(providerEntityDataMapper.mapFrom(providerEntity))
        return Observable.just(providerEntity)
    }

    override fun removeFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        providerDao.removeProvider(providerEntityDataMapper.mapFrom(providerEntity))
        return Observable.just(providerEntity)
    }

    override fun checkFavorite(providerEntity: ProviderEntity): Observable<Boolean> {
        return if (providerDao.isFavorite(providerEntityDataMapper.mapFrom(providerEntity).id).isEmpty())
            Observable.just(false)
        else
            Observable.just(true)
    }

    override fun getFavorites(): Observable<List<ProviderEntity>> {
        return Observable.just(providerDao.getProviders().map { providerDataEntityMapper.mapFrom(it) })
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

    override fun clear() {
//        providerDao.clear()
    }

    override fun isEmpty(): Observable<Boolean> {
        return Observable.just(true)
    }
}