package com.neqabty.data.repositories

import com.neqabty.data.api.WebService
import com.neqabty.data.api.requests.*
import com.neqabty.data.mappers.*
import com.neqabty.domain.NeqabtyDataStore
import com.neqabty.domain.entities.*
import com.neqabty.presentation.di.DI
import io.reactivex.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton

class RemoteNeqabtyDataStore @Inject constructor(@Named(DI.authorized) private val api: WebService, @Named(DI.unAuthorized) private val unauthorizedApi: WebService) : NeqabtyDataStore {

    override fun createComplaint(
            name: String,
            phone: String,
            catId: String,
            subCatId: String,
            body: String,
            token: String,
            memberNumber: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?
    ): Observable<Unit> {
        var file1: MultipartBody.Part? = null
        var file2: MultipartBody.Part? = null
        var file3: MultipartBody.Part? = null
        var file4: MultipartBody.Part? = null

        doc1?.let {
            val doc1RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc1)
            file1 = MultipartBody.Part.createFormData("doc1", doc1?.name, doc1RequestFile)
        }
        doc2?.let {
            val doc2RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc2)
            file2 = MultipartBody.Part.createFormData("doc2", doc2?.name, doc2RequestFile)
        }
        doc3?.let {
            val doc3RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc3)
            file3 = MultipartBody.Part.createFormData("doc3", doc3?.name, doc3RequestFile)
        }
        doc4?.let {
            val doc4RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc4)
            file4 = MultipartBody.Part.createFormData("doc4", doc4?.name, doc4RequestFile)
        }
        return api.sendComplaint(ComplaintRequest(name, phone, catId, subCatId, body, token, memberNumber, docsNumber), file1, file2, file3, file4).map { result ->
            result.data ?: Unit
        }
    }

    private val questionnaireDataEntityMapper = QuestionnaireDataEntityMapper()
    override fun getQuestionnaires(userNumber: String): Observable<QuestionnaireEntity> {
        return api.getQuestionnaires(QuestionnaireRequest(userNumber)).flatMap { questionnaire ->
            Observable.just(questionnaireDataEntityMapper.mapFrom(questionnaire.data!!))
        }
    }

    private val questionnaireVoteDataEntityMapper = QuestionnaireVoteDataEntityMapper()
    override fun voteQuestionnaire(userNumber: String, questionnaireId: Int, answerId: Int): Observable<QuestionnaireVoteEntity> {
        return api.voteQuestionnaire(QuestionnaireVoteRequest(userNumber, questionnaireId,answerId)).flatMap { voteData ->
            Observable.just(questionnaireVoteDataEntityMapper.mapFrom(voteData.data!!))
        }
    }

    private val complaintTypeDataEntityMapper = ComplaintTypeDataEntityMapper()
    override fun getComplaintTypes(): Observable<List<ComplaintTypeEntity>> {
        return api.getComplaintTypes().map { types ->
            types.map { complaintTypeDataEntityMapper.mapFrom(it) }
        }
    }

    override fun getComplaintSubTypes(id: String): Observable<List<ComplaintTypeEntity>> {
        return api.getComplaintSubTypes(ComplaintSubtypeRequest(id)).map { types ->
            types.data?.map { complaintTypeDataEntityMapper.mapFrom(it) }
        }
    }

    private val inquireUpdateUserDataEntityMapper = InquireUpdateUserDataEntityMapper()
    override fun updateUserDataInquiry(userNumber: String): Observable<InquireUpdateUserDataEntity> {
        return api.updateUserDataInquiry(InquireUpdateUserDataRequest(userNumber)).flatMap { userDataResponse ->
            Observable.just(inquireUpdateUserDataEntityMapper.mapFrom(userDataResponse.data!!))
        }
    }

    private val verifyUserDataEntityMapper = VerifyUserDataEntityMapper()
    override fun verifyUser(userNumber: String, mobileNumber: String): Observable<VerifyUserDataEntity> {
        return api.sendVerifySMS(SendSMSRequest(mobileNumber)).map { result ->
            verifyUserDataEntityMapper.mapFrom(result)
        }
    }

    private val updateUserDataEntityMapper = UpdateUserDataEntityMapper()
    override fun updateUserData(
            userNumber: String,
            name: String,
            nationalID: String,
            mobile: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?
    ): Observable<UpdateUserDataEntity> {

        var file1: MultipartBody.Part? = null
        var file2: MultipartBody.Part? = null
        var file3: MultipartBody.Part? = null

        doc1?.let {
            val doc1RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc1)
            file1 = MultipartBody.Part.createFormData("doc1", doc1?.name, doc1RequestFile)
        }
        doc2?.let {
            val doc2RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc2)
            file2 = MultipartBody.Part.createFormData("doc2", doc2?.name, doc2RequestFile)
        }
        doc3?.let {
            val doc3RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc3)
            file3 = MultipartBody.Part.createFormData("doc3", doc3?.name, doc3RequestFile)
        }

        return api.updateUserData(UpdateUserDataRequest(userNumber, name, nationalID, mobile, docsNumber), file1, file2, file3).map { userData ->
            updateUserDataEntityMapper.mapFrom(userData)
        }
    }

    private val registeryDataEntityMapper = RegisteryDataEntityMapper()

    override fun inquireEngineeringRecords(userNumber: String): Observable<RegisteryEntity> {
        return api.engineeringRecordsInquiry(EngineeringRecordsInquiryRequest(userNumber)).map { registeryData ->
            registeryData.data?.msg = registeryData.arMsg
            registeryDataEntityMapper.mapFrom(registeryData.data!!)
        }
    }

    override fun requestEngineeringRecords(
            name: String,
            phone: String,
            typeId: String,
            mainSyndicate: String,
            userNumber: String,
            lastRenewYear: String,
            statusID: Int,
            isOwner: Int,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?,
            doc5: File?
    ): Observable<Unit> {
        var file1: MultipartBody.Part? = null
        var file2: MultipartBody.Part? = null
        var file3: MultipartBody.Part? = null
        var file4: MultipartBody.Part? = null
        var file5: MultipartBody.Part? = null

        doc1?.let {
            val doc1RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc1)
            file1 = MultipartBody.Part.createFormData("doc1", doc1?.name, doc1RequestFile)
        }
        doc2?.let {
            val doc2RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc2)
            file2 = MultipartBody.Part.createFormData("doc2", doc2?.name, doc2RequestFile)
        }
        doc3?.let {
            val doc3RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc3)
            file3 = MultipartBody.Part.createFormData("doc3", doc3?.name, doc3RequestFile)
        }
        doc4?.let {
            val doc4RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc4)
            file4 = MultipartBody.Part.createFormData("doc4", doc4?.name, doc4RequestFile)
        }
        doc5?.let {
            val doc5RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc5)
            file5 = MultipartBody.Part.createFormData("doc5", doc5?.name, doc5RequestFile)
        }
        return api.engineeringRecordsRequest(EngineeringRecordsRequest(name, phone, typeId, mainSyndicate, userNumber, lastRenewYear, statusID, isOwner, docsNumber), file1, file2, file3, file4, file5).map { result ->
            result.data ?: Unit
        }
    }

    override fun bookTrip(
            mainSyndicateId: Int,
            userNumber: String,
            phone: String,
            tripID: Int,
            regimentID: Int,
            regimentDate: String,
            housingType: String,
            numChild: Int,
            ages: String,
            name: String,
            personsList: List<PersonEntity>,
            docsNumber: Int,
            personsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?,
            doc5: File?,
            doc6: File?,
            doc7: File?,
            doc8: File?,
            doc9: File?,
            doc10: File?
    ): Observable<Unit> {

        var file1: MultipartBody.Part? = null
        var file2: MultipartBody.Part? = null
        var file3: MultipartBody.Part? = null
        var file4: MultipartBody.Part? = null
        var file5: MultipartBody.Part? = null
        var file6: MultipartBody.Part? = null
        var file7: MultipartBody.Part? = null
        var file8: MultipartBody.Part? = null
        var file9: MultipartBody.Part? = null
        var file10: MultipartBody.Part? = null
        var personsRequestsList: List<PersonRequest> = personsList.map { personEntity -> PersonRequest(personEntity.name, personEntity.relationship, personEntity.birthDate, personEntity.ageOnTrip) }

        doc1?.let {
            val doc1RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc1)
            file1 = MultipartBody.Part.createFormData("doc1", doc1?.name, doc1RequestFile)
        }
        doc2?.let {
            val doc2RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc2)
            file2 = MultipartBody.Part.createFormData("doc2", doc2?.name, doc2RequestFile)
        }
        doc3?.let {
            val doc3RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc3)
            file3 = MultipartBody.Part.createFormData("doc3", doc3?.name, doc3RequestFile)
        }
        doc4?.let {
            val doc4RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc4)
            file4 = MultipartBody.Part.createFormData("doc4", doc4?.name, doc4RequestFile)
        }
        doc5?.let {
            val doc5RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc5)
            file5 = MultipartBody.Part.createFormData("doc5", doc5?.name, doc5RequestFile)
        }
        doc6?.let {
            val doc6RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc6)
            file6 = MultipartBody.Part.createFormData("doc6", doc6?.name, doc6RequestFile)
        }
        doc7?.let {
            val doc7RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc7)
            file7 = MultipartBody.Part.createFormData("doc7", doc7?.name, doc7RequestFile)
        }
        doc8?.let {
            val doc8RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc8)
            file8 = MultipartBody.Part.createFormData("doc8", doc8?.name, doc8RequestFile)
        }
        doc9?.let {
            val doc9RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc9)
            file9 = MultipartBody.Part.createFormData("doc9", doc9?.name, doc9RequestFile)
        }
        doc10?.let {
            val doc10RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc10)
            file10 = MultipartBody.Part.createFormData("doc10", doc10?.name, doc10RequestFile)
        }

        return api.bookTrip(BookTripRequest(mainSyndicateId, userNumber, phone, tripID, regimentID, regimentDate, housingType, numChild, ages, name, docsNumber, personsNumber, personsRequestsList), file1, file2, file3, file4, file5, file6, file7, file8, file9, file10).map { result ->
            result.data ?: Unit
        }
    }

    private val appConfigDataEntityMapper = AppConfigDataEntityMapper()

    override fun getAppConfig(): Observable<AppConfigEntity> {
        return unauthorizedApi.getAppConfig().map { config -> appConfigDataEntityMapper.mapFrom(config.data!!) }
    }

    private val medicalLetterDataEntityMapper = MedicalLetterDataEntityMapper()

    override fun getMedicalLetters(mobileNumber: String, benID: String, start: Int, end: Int, orderBy: String, dir: String): Observable<MedicalLetterEntity> {
        return api.getMedicalLetters(mobileNumber, benID, start, end, orderBy, dir).flatMap { letterInfo ->
            Observable.just(medicalLetterDataEntityMapper.mapFrom(letterInfo))
        }
    }

    private val medicalLetterItemDataEntityMapper = MedicalLetterItemDataEntityMapper()

    override fun getMedicalLetterByID(mobileNumber: String, userNumber: String, id: String): Observable<MedicalLetterEntity.LetterItem> {
        return api.getMedicalLetterByID(mobileNumber, userNumber, id).flatMap { letterItemInfo ->
            Observable.just(medicalLetterItemDataEntityMapper.mapFrom(letterItemInfo))
        }
    }

    private val adDataEntityMapper = AdDataEntityMapper()

    override fun getAds(sectionId: Int): Observable<List<AdEntity>> {
        return api.getAds(AdsRequest()).map { adsList ->
            adsList.data?.map { adDataEntityMapper.mapFrom(it)}
        }
    }

    private val liteFollowersListDataEntityMapper = LiteFollowersListDataEntityMapper()

    override fun getLiteFollowersListData(mobileNumber: String, userNumber: String): Observable<List<LiteFollowersListEntity>> {
        return api.getLiteFollowersList(mobileNumber, userNumber).map { follower ->
            follower.map { liteFollowersListDataEntityMapper.mapFrom(it) }
        }
    }

    private val medicalRenewalDataEntityMapper = MedicalRenewalDataEntityMapper()

    override fun getMedicalRenewalData(mobileNumber: String, userNumber: String): Observable<MedicalRenewalEntity> {
        return api.getMedicalRenewData(mobileNumber, userNumber).flatMap { renewalInfo ->
            Observable.just(medicalRenewalDataEntityMapper.mapFrom(renewalInfo))
        }
    }

    private val medicalRenewalPaymentDataEntityMapper = MedicalRenewalPaymentDataEntityMapper()

    override fun inquireMedicalRenewalPayment(isInquire: Boolean, mobileNumber: String, userNumber: String, locationType: Int, address: String, mobile: String): Observable<MedicalRenewalPaymentEntity> {
        return if(isInquire) api.inquireHealthCare(mobileNumber, userNumber).flatMap { renewalPaymentInfo ->
            Observable.just(medicalRenewalPaymentDataEntityMapper.mapFrom(renewalPaymentInfo))
        } else api.getMedicalRenewPaymentData(mobileNumber, userNumber, locationType, address, mobile).flatMap { renewalPaymentInfo ->
            Observable.just(medicalRenewalPaymentDataEntityMapper.mapFrom(renewalPaymentInfo))
        }
//        return api.getMedicalRenewPaymentData(mobileNumber, userNumber, locationType, address, mobile).flatMap { renewalPaymentInfo ->
//            Observable.just(medicalRenewalPaymentDataEntityMapper.mapFrom(renewalPaymentInfo))
//        }
    }

    private val medicalRenewalEntityDataMapper = MedicalRenewalEntityDataMapper()
    private val medicalRenewalUpdateDataEntityMapper = MedicalRenewalUpdateDataEntityMapper()

    override fun updateMedicalRenewalData(mobileNumber: String, medicalRenewalData: MedicalRenewalEntity): Observable<MedicalRenewalUpdateEntity> {
        return api.updateMedicalRenewPaymentData(mobileNumber, medicalRenewalEntityDataMapper.mapFrom(medicalRenewalData)).flatMap { updateInfoResult ->
            Observable.just(medicalRenewalUpdateDataEntityMapper.mapFrom(updateInfoResult))
        }
    }

    private val claimingValidationDataEntityMapper = ClaimingValidationDataEntityMapper()

    override fun validateUserForClaiming(userNumber: String): Observable<ClaimingValidationEntity> {
        return api.validateUser(ClaimingValidationRequest(userNumber)).flatMap { user ->
            user.data?.message = user.arMsg
//            user.data?.engineer?.code = user.data?.code!!
            Observable.just(claimingValidationDataEntityMapper.mapFrom(user.data!!))
        }
    }

    private val paymentHistoryDataEntityMapper = PaymentHistoryDataEntityMapper()

    override fun getPaymentHistory(userNumber: String): Observable<List<PaymentHistoryEntity>> {
        return api.getPaymentsHistory(PaymentsHistoryRequest(userNumber)).map { result ->
            result.data!!.map { paymentHistoryDataEntityMapper.mapFrom(it) }
        }
    }

    private val committeesLookupsDataEntityMapper = CommitteesLookupsDataEntityMapper()

    override fun getCommitteesLookups(): Observable<CommitteesLookupEntity> {
        return api.getCommitteesLookups().flatMap { lookups ->
            Observable.just(committeesLookupsDataEntityMapper.mapFrom(lookups.data!!))
        }
    }

    override fun sendCommitteesRequest(
        name: String,
        userNumber: String,
        mobile: String,
        email: String,
        nationalId: String,
        address: String,
        university: String,
        degree: String,
        maritalStatus: String,
        committeesIds: List<Int>,
        sectionId: Int,
        syndicateId: Int,
        department: String,
        section: String,
        currentJob: String,
        details: String,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?
    ): Observable<String> {

        var file1: MultipartBody.Part? = null
        var file2: MultipartBody.Part? = null
        var file3: MultipartBody.Part? = null

        doc1?.let {
            val doc1RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc1)
            file1 = MultipartBody.Part.createFormData("doc1", doc1?.name, doc1RequestFile)
        }
        doc2?.let {
            val doc2RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc2)
            file2 = MultipartBody.Part.createFormData("doc2", doc2?.name, doc2RequestFile)
        }
        doc3?.let {
            val doc3RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc3)
            file3 = MultipartBody.Part.createFormData("doc3", doc3?.name, doc3RequestFile)
        }

            return api.sendCommitteesRequest(CommitteesRequest(name, userNumber, mobile, email, nationalId, address, university, degree, maritalStatus, committeesIds, sectionId, syndicateId, department, section, currentJob, details, docsNumber), file1, file2, file3).map { result ->
            result.data ?: ""
        }
    }

    private val serviceDataEntityMapper = ServiceDataEntityMapper()

    override fun getAllServices(typeID: Int): Observable<List<ServiceEntity>> {
        return api.getAllServices(ServicesRequest(typeID)).map { services ->
            services.data?.map { serviceDataEntityMapper.mapFrom(it) }
        }
    }

    private val serviceTypeDataEntityMapper = ServiceTypeDataEntityMapper()

    override fun getAllServiceTypes(): Observable<List<ServiceTypeEntity>> {
        return api.getAllServiceTypes().map { serviceTypes ->
            serviceTypes.data?.map { serviceTypeDataEntityMapper.mapFrom(it) }
        }
    }

    private val memberDataEntityMapper = MemberDataEntityMapper()

    override fun inquirePayment(isInquire: Boolean, mobileNumber: String, userNumber: String, serviceID: Int, requestID: String, amount: String, locationType: Int, address: String, mobile: String): Observable<MedicalRenewalPaymentEntity> {
        return if (isInquire) api.paymentInquiry(mobileNumber, userNumber, serviceID, requestID, amount).flatMap { renewalData ->
            Observable.just(medicalRenewalPaymentDataEntityMapper.mapFrom(renewalData))
        } else api.addRenewalRequest(mobileNumber, userNumber, serviceID, requestID, amount, locationType, address, mobile).flatMap { renewalData ->
            Observable.just(medicalRenewalPaymentDataEntityMapper.mapFrom(renewalData))
        }
    }

    private val encryptionDataEntityMapper = EncryptionDataEntityMapper()

    override fun encrypt(userName: String, password: String, description: String): Observable<EncryptionEntity> {
        return api.paymentEncryption(EncryptionRequest(userName, password, description)).flatMap { data ->
            Observable.just(encryptionDataEntityMapper.mapFrom(data))
        }
    }

    private val decryptionDataEntityMapper = DecryptionDataEntityMapper()

    override fun sendDecryptionKey(requestNumber: String, decryptionKey: String): Observable<DecryptionEntity> {
        return api.sendDecryptionKey(DecryptionRequest(requestNumber, decryptionKey)).flatMap { result ->
            Observable.just(decryptionDataEntityMapper.mapFrom(result.data!!))
        }
    }

    private val notificationsCountDataEntityMapper = NotificationsCountDataEntityMapper()

    override fun getNotificationsCount(userNumber: String): Observable<NotificationsCountEntity> {
        return api.getNotificationsCount(NotificationRequest(userNumber = userNumber)).map { count ->
            notificationsCountDataEntityMapper.mapFrom(count)
        }
    }

    private val notificationDataEntityMapper = NotificationDataEntityMapper()

    override fun getNotifications(serviceID: Int, type: Int, userNumber: String): Observable<List<NotificationEntity>> {
        return api.getNotifications(NotificationRequest(userNumber)).map { notifications ->
            notifications.map { notificationDataEntityMapper.mapFrom(it) }
        }
    }

    override fun getNotificationDetails(serviceID: Int, type: Int, userNumber: String, requestID: String): Observable<NotificationEntity> {
        return api.getNotificationDetails(requestID, type).flatMap { notification ->
            Observable.just(notificationDataEntityMapper.mapFrom(notification))
        }
    }

    override fun sendMedicalRequest(
            mainSyndicateId: Int,
            subSyndicateId: Int,
            userNumber: String,
            email: String,
            phone: String,
            profession: Int,
            degree: Int,
            gov: Int,
            area: Int,
            doctor: Int,
            providerType: Int,
            provider: Int,
            name: String,
            oldbenid: String,
            details: String,
            followerName: String,
            followerRelation: String,
            docsNumber: Int,
            doc1: File?,
            doc2: File?,
            doc3: File?,
            doc4: File?,
            doc5: File?
    ): Observable<Unit> {
        var file1: MultipartBody.Part? = null
        var file2: MultipartBody.Part? = null
        var file3: MultipartBody.Part? = null
        var file4: MultipartBody.Part? = null
        var file5: MultipartBody.Part? = null

        doc1?.let {
            val doc1RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc1)
            file1 = MultipartBody.Part.createFormData("doc1", doc1?.name, doc1RequestFile)
        }
        doc2?.let {
            val doc2RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc2)
            file2 = MultipartBody.Part.createFormData("doc2", doc2?.name, doc2RequestFile)
        }
        doc3?.let {
            val doc3RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc3)
            file3 = MultipartBody.Part.createFormData("doc3", doc3?.name, doc3RequestFile)
        }
        doc4?.let {
            val doc4RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc4)
            file4 = MultipartBody.Part.createFormData("doc4", doc4?.name, doc4RequestFile)
        }
        doc5?.let {
            val doc5RequestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), doc5)
            file5 = MultipartBody.Part.createFormData("doc5", doc5?.name, doc5RequestFile)
        }

        return api.sendMedicalRequest(MedicalRequest(mainSyndicateId, subSyndicateId, userNumber, email, phone, profession, degree, gov, area, doctor, providerType, provider, name, oldbenid, details, followerName, followerRelation, docsNumber), file1, file2, file3, file4, file5).map { result ->
            result.data ?: Unit
        }
    }

    private val onlinePharmacyURLDataEntityMapper = OnlinePharmacyURLDataEntityMapper()

    override fun getOnlinePharmacyURL(userNumber: String): Observable<OnlinePharmacyEntity> {
        return api.getOnlinePharmacyURL(OnlinePharmacyRequest(userNumber)).map { response ->
            onlinePharmacyURLDataEntityMapper.mapFrom(response.data!!)
        }
    }

    private val doctorsReservationDataEntityMapper = DoctorsReservationDataEntityMapper()

    override fun getDoctorsReservationData(mobileNumber: String): Observable<DoctorsReservationEntity> {
        return api.getDoctorsReservationData(DoctorsReservationRequest(mobileNumber)).map { response ->
            doctorsReservationDataEntityMapper.mapFrom(response.data!!)
        }
    }

    private val providerTypeDataEntityMapper = ProviderTypeDataEntityMapper()

    override fun getAllProviderTypes(type: String): Observable<List<ProviderTypeEntitiy>> {
        return api.getAllProviderTypes(ProviderTypeRequest(type)).map { types ->
            types.data?.map { providerTypeDataEntityMapper.mapFrom(it) }
        }
    }

    private val providerDataEntityMapper = ProviderDataEntityMapper()

    override fun getProviderDetails(id: String, type: String): Observable<ProviderEntity> {
        return api.getProviderDetails(ProviderDetailsRequest(id, type)).flatMap { provider ->
            Observable.just(providerDataEntityMapper.mapFrom(provider.data!!))
        }
    }

    override fun getProvidersByType(
            providerTypeId: String,
            govId: String,
            areaId: String,
            providerName: String?,
            professionID: String?,
            degreeID: String?
    ): Observable<List<ProviderEntity>> {
        return api.getProvidersById(ProviderRequest(providerTypeId, govId, areaId, providerName, professionID, degreeID)).map { providers ->
            providers.data?.map { providerDataEntityMapper.mapFrom(it) }
        }
    }

    private val medicalDirectoryLookupsDataEntityMapper = MedicalDirectoryLookupsDataEntityMapper()

    override fun getMedicalDirectoryLookups(mobileNumber: String): Observable<MedicalDirectoryLookupsEntity> {
        return api.getMedicalDirectoryLookups(mobileNumber).map { lookups ->
            medicalDirectoryLookupsDataEntityMapper.mapFrom(lookups)
        }
    }

    private val medicalDirectoryProviderDataEntityMapper = MedicalDirectoryProviderDataEntityMapper()

    override fun getMedicalDirectoryProviders(
        mobileNumber: String,
        providerTypeId: String,
        govId: String,
        areaId: String,
        providerName: String,
        specializationId: String
    ): Observable<List<MedicalDirectoryProviderEntity>> {
        return api.getMedicalDirectoryProviders(mobileNumber, providerTypeId, govId, areaId, providerName, specializationId).map { providers ->
            providers.map { medicalDirectoryProviderDataEntityMapper.mapFrom(it) }
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

    private val governDataEntityMapper = GovernDataEntityMapper()

    override fun getAllGoverns(): Observable<List<GovernEntity>> {
        return api.getAllGoverns().map { governs ->
            governs.map { governDataEntityMapper.mapFrom(it) }
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

    private val syndicateBranchDataEntityMapper = SyndicateBranchDataEntityMapper()

    override fun getSyndicateBranches(): Observable<List<SyndicateBranchEntity>> {
        return api.getSyndicateBranches().map { branches ->
            branches.map { syndicateBranchDataEntityMapper.mapFrom(it) }
        }
    }

    private val newsDataEntityMapper = NewsDataEntityMapper()

    override fun getNews(id: String): Observable<List<NewsEntity>> {
        return api.getAllNews(NewsRequest(id)).map { news ->
            news.data?.map { newsDataEntityMapper.mapFrom(it) }
        }
    }

    private val tripsDataEntityMapper = TripsDataEntityMapper()

    override fun getTrips(id: String): Observable<List<TripEntity>> {
        return api.getAllTrips(TripsRequest(id)).map { trips ->
            trips.data?.map { tripsDataEntityMapper.mapFrom(it) }
        }
    }

    override fun getTripDetails(id: String): Observable<TripEntity> {
        return api.getTripDetails(TripDetailsRequest(id)).flatMap { tripDetails ->
            Observable.just(tripsDataEntityMapper.mapFrom(tripDetails.data!!))
        }
    }

    private val syndicateDataEntityMapper = SyndicateDataEntityMapper()

    override fun getSyndicates(): Observable<List<SyndicateEntity>> {
        return api.getAllSyndicates().map { results ->
            results.map { syndicateDataEntityMapper.mapFrom(it) }
        }
    }

    private val userDataEntityMapper = UserDataEntityMapper()

    override fun signup(
            userNumber: String,
            mobile: String,
            natID: String,
            newFirebaseToken: String,
            oldFirebaseToken: String
    ): Observable<UserEntity> {
        return api.signup(SignupRequest(userNumber, mobile, natID, newFirebaseToken, oldFirebaseToken)).flatMap { userDataResponse ->
            Observable.just(userDataEntityMapper.mapFrom(userDataResponse.data!!))
        }
    }

    override fun sendSMS(mobileNumber: String): Observable<Unit> {
        return api.sendSMS(SendSMSRequest(mobileNumber)).map { smsResponse ->
            smsResponse ?: Unit
        }
    }

    override fun activateAccount(mobile: String, verificationCode: String, password: String): Observable<UserEntity> {
        return api.activateAccount(ActivateAccountRequest(mobile, verificationCode, password)).flatMap { userDataResponse ->
            Observable.just(userDataEntityMapper.mapFrom(userDataResponse.data!!))
        }
    }

    override fun login(actionType: String, mobile: String, userNumber: String, newToken: String, oldToken: String, password: String): Observable<UserEntity> {
        return api.login(LoginRequest(actionType, mobile, userNumber, newToken, oldToken, password)).map { userData ->
            userDataEntityMapper.mapFrom(userData.data!!)
        }
    }

    override fun forgetPassword(mobile: String, userNumber: String, natId: String): Observable<String> {
        return api.forgetPassword(ForgetPasswordRequest(mobile, userNumber, natId)).flatMap { response ->
            Observable.just(response.arMsg)
        }
    }

    override fun changePassword(mobile: String, currentPassword: String, newPassword: String): Observable<String> {
        return api.changePassword(ChangePasswordRequest(mobile, currentPassword, newPassword)).flatMap { response ->
            Observable.just(response.arMsg)
        }
    }

    override fun setNewPassword(mobile: String, verificationCode: String, newPassword: String): Observable<String> {
        return api.setNewPassword(SetNewPasswordRequest(mobile, verificationCode, newPassword)).flatMap { response ->
            Observable.just(response.arMsg)
        }
    }

    private val trackShipmentDataEntityMapper = TrackShipmentDataEntityMapper()

    override fun trackShipment(userNumber: String): Observable<List<TrackShipmentEntity>> {
        return api.trackShipment(TrackShipmentRequest(userNumber)).map { response ->
            response.data?.map { trackShipmentDataEntityMapper.mapFrom(it)}
        }
    }

    private val changeUserMobileDataEntityMapper = ChangeUserMobileDataEntityMapper()

    override fun changeUserMobile(userNumber: String, natID: String, newMobile: String, oldMobile: String): Observable<ChangeUserMobileEntity> {
        return api.changeUserMobile(ChangeUserMobileRequest(userNumber, natID, newMobile, oldMobile)).map { response ->
            response.data!!.msg = response.arMsg
            changeUserMobileDataEntityMapper.mapFrom(response.data!!)
        }
    }
}