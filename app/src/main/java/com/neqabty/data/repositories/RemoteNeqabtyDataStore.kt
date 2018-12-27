package com.neqabty.data.repositories

import android.util.Log
import com.neqabty.data.api.WebService
import com.neqabty.data.api.requests.ProviderRequest
import com.neqabty.data.api.requests.RegisterRequest
import com.neqabty.data.mappers.*
import com.neqabty.domain.NeqabtyDataStore
import com.neqabty.domain.entities.*
import com.neqabty.testing.OpenForTesting
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class RemoteNeqabtyDataStore @Inject constructor(private val api: WebService) : NeqabtyDataStore {
    private val providerTypeDataEntityMapper = ProviderTypeDataEntityMapper()

    override fun getAllProviderTypes(): Observable<List<ProviderTypeEntitiy>> {
        return api.getAllProviderTypes().map { types ->
            types.data?.map { providerTypeDataEntityMapper.mapFrom(it) }
        }
    }

    override fun registerUser(mobile: String, mainSyndicateId: Int, subSyndicateId: Int, token: String): Observable<Unit> {
        return api.registerUser(RegisterRequest(mobile, mainSyndicateId, subSyndicateId, token)).map { result ->
            result.data?: Unit
        }
    }


    private val providerDataEntityMapper = ProviderDataEntityMapper()

    override fun getAllProviders(type: String): Observable<List<ProviderEntity>> {
        return api.getAllProvidersById(ProviderRequest(type)).map { providers ->
            providers.data?.map { providerDataEntityMapper.mapFrom(it) }
        }
    }

    private val doctorDataEntityMapper = DoctorDataEntityMapper()

    override fun getAllDoctors(): Observable<List<DoctorEntity>> {
        return api.getAllDoctors().map { doctors ->
            doctors.data?.map { doctorDataEntityMapper.mapFrom(it) }
        }
    }

    private val degreeDataEntityMapper = DegreeDataEntityMapper()

    override fun getAllDegrees(): Observable<List<DegreeEntity>> {
        return api.getAllDegrees().map { degrees ->
            degrees.data?.map { degreeDataEntityMapper.mapFrom(it) }
        }
    }

    private val areaDataEntityMapper = AreaDataEntityMapper()

    override fun getAllAreas(): Observable<List<AreaEntity>> {
        return api.getAllAreas().map { areas ->
            areas.data?.map { areaDataEntityMapper.mapFrom(it) }
        }
    }

    private val specializationDataEntityMapper = SpecializationDataEntityMapper()

    override fun getAllSpecializations(): Observable<List<SpecializationEntity>> {
        return api.getAllSpecializations().map { specializations ->
            specializations.data?.map { specializationDataEntityMapper.mapFrom(it) }
        }
    }

    override fun geSubSyndicatesById(id: String): Observable<List<SyndicateEntity>> {
        return api.getSubSyndicatesById(com.neqabty.data.api.requests.SubSyndicateRequest(id)).map { subSyndicates ->
            subSyndicates.data?.map { syndicateDataEntityMapper.mapFrom(it) }
        }
    }

    override fun geSyndicateById(id: String): Observable<SyndicateEntity> {
        return api.getSyndicateById(com.neqabty.data.api.requests.SyndicateRequest(id)).flatMap { syndicate ->
            Observable.just(syndicateDataEntityMapper.mapFrom(syndicate.data!!))
        }
    }

    private val newsDataEntityMapper = com.neqabty.data.mappers.NewsDataEntityMapper()

    override fun getNews(id: String): Observable<List<NewsEntity>> {
        return api.getAllNews(com.neqabty.data.api.requests.NewsRequest(id)).map { news ->
            news.data?.map { newsDataEntityMapper.mapFrom(it) }
        }
    }

    private val tripsDataEntityMapper = com.neqabty.data.mappers.TripsDataEntityMapper()

    override fun getTrips(): Observable<List<TripEntity>> {
        return api.getAllTrips().map { trips ->
            trips.data?.map { tripsDataEntityMapper.mapFrom(it) }
        }
    }

    private val syndicateDataEntityMapper = com.neqabty.data.mappers.SyndicateDataEntityMapper()

    override fun getSyndicates(): Observable<List<SyndicateEntity>> {
        return api.getAllSyndicates().map { results ->
            results.data?.map { syndicateDataEntityMapper.mapFrom(it) }
        }
    }

    private val userDataMapper = com.neqabty.data.mappers.UserDataEntityMapper()

    override fun signup(email: String, fName: String, lName: String, mobile: String, govId: String, mainSyndicateId: String, subSyndicateId: String, password: String): Observable<UserEntity> {
        return api.signup(com.neqabty.data.api.requests.SignupRequest(email, fName, lName, mobile, govId, mainSyndicateId, subSyndicateId, password)).flatMap { userDataResponse ->
            Log.e("TAG", userDataResponse.toString())
            Observable.just(userDataMapper.mapFrom(userDataResponse.data!!))
        }
    }

    override fun login(mobile: String, password: String, token: String): Observable<UserEntity> {
//        return api.login(ApiResponse<LoginRequest>().createRequest(LoginRequest(mobile,password , token))).flatMap { userData ->
        return api.login(com.neqabty.data.api.requests.LoginRequest(mobile, password, token)).flatMap { userDataResponse ->
            Log.e("TAG", userDataResponse.toString())
            Observable.just(userDataMapper.mapFrom(userDataResponse.data!!))
        }
    }

}