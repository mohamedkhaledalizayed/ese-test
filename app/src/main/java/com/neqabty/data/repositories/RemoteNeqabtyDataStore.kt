package com.neqabty.data.repositories

import android.util.Log
import com.neqabty.data.api.WebService
import com.neqabty.data.api.requests.*
import com.neqabty.data.mappers.*
import com.neqabty.domain.NeqabtyDataStore
import com.neqabty.domain.entities.*
import com.neqabty.testing.OpenForTesting
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@OpenForTesting
class RemoteNeqabtyDataStore @Inject constructor(private val api: WebService) : NeqabtyDataStore {
    override fun sendMedicalRequest(mainSyndicateId: Int, subSyndicateId: Int, userNumber: String, email: String, phone: String, profession: Int, degree: Int, area: Int, doctor: Int, docsNumber: Int, doc1: File?, doc2: File?, doc3: File?, doc4: File?, doc5: File?): Observable<Unit> {
        var file1: MultipartBody.Part? = null
        var file2: MultipartBody.Part? = null
        var file3: MultipartBody.Part? = null
        var file4: MultipartBody.Part? = null
        var file5: MultipartBody.Part? = null

        doc1?.let {
            val doc1RequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), doc1)
            file1 = MultipartBody.Part.createFormData("doc1", doc1?.name, doc1RequestFile)
        }
        doc2?.let {
            val doc2RequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), doc2)
            file2 = MultipartBody.Part.createFormData("doc2", doc2?.name, doc2RequestFile)
        }
        doc3?.let {
            val doc3RequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), doc3)
            file3 = MultipartBody.Part.createFormData("doc3", doc3?.name, doc3RequestFile)
        }
        doc4?.let {
            val doc4RequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), doc4)
            file4 = MultipartBody.Part.createFormData("doc4", doc4?.name, doc4RequestFile)
        }
        doc5?.let {
            val doc5RequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), doc5)
            file5 = MultipartBody.Part.createFormData("doc5", doc5?.name, doc5RequestFile)
        }

        return api.sendMedicalRequest(MedicalRequest(mainSyndicateId, subSyndicateId, userNumber, email, phone, profession, degree, area, doctor, docsNumber), file1, file2, file3, file4, file5).map { result ->
            result.data ?: Unit
        }
    }

    private val providerTypeDataEntityMapper = ProviderTypeDataEntityMapper()

    override fun getAllProviderTypes(): Observable<List<ProviderTypeEntitiy>> {
        return api.getAllProviderTypes().map { types ->
            types.data?.map { providerTypeDataEntityMapper.mapFrom(it) }
        }
    }

    override fun registerUser(mobile: String, mainSyndicateId: Int, subSyndicateId: Int, token: String): Observable<Unit> {
        return api.registerUser(RegisterRequest(mobile, mainSyndicateId, subSyndicateId, token)).map { result ->
            result.data ?: Unit
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
        return api.getSyndicateById(SyndicateRequest(id)).flatMap { syndicate ->
            Observable.just(syndicateDataEntityMapper.mapFrom(syndicate.data!!))
        }
    }

    private val newsDataEntityMapper = com.neqabty.data.mappers.NewsDataEntityMapper()

    override fun getNews(id: String): Observable<List<NewsEntity>> {
        return api.getAllNews(NewsRequest(id)).map { news ->
            news.data?.map { newsDataEntityMapper.mapFrom(it) }
        }
    }

    private val tripsDataEntityMapper = com.neqabty.data.mappers.TripsDataEntityMapper()

    override fun getTrips(id : String): Observable<List<TripEntity>> {
        return api.getAllTrips(TripsRequest(id)).map { trips ->
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
        return api.signup(SignupRequest(email, fName, lName, mobile, govId, mainSyndicateId, subSyndicateId, password)).flatMap { userDataResponse ->
            Log.e("TAG", userDataResponse.toString())
            Observable.just(userDataMapper.mapFrom(userDataResponse.data!!))
        }
    }

    override fun login(mobile: String, password: String, token: String): Observable<UserEntity> {
//        return api.login(ApiResponse<LoginRequest>().createRequest(LoginRequest(mobile,password , token))).flatMap { userData ->
        return api.login(LoginRequest(mobile, password, token)).flatMap { userDataResponse ->
            Log.e("TAG", userDataResponse.toString())
            Observable.just(userDataMapper.mapFrom(userDataResponse.data!!))
        }
    }

}