package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.PersonEntity
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class BookTrip @Inject constructor(
    transformer: Transformer<Unit>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_MAIN_SYNDICATE = "param:mainSyndicate"
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_PHONE = "param:phone"
        private const val PARAM_TRIP_ID = "param:tripID"
        private const val PARAM_REGIMENT_ID = "param:regimentID"
        private const val PARAM_REGIMENT_DATE = "param:regimentDate"
        private const val PARAM_HOUSING_TYPE = "param:housingType"
        private const val PARAM_NUM_CHILD = "param:numChild"
        private const val PARAM_AGES = "param:ages"
        private const val PARAM_NAME = "param:name"
        private const val PARAM_PERSONS_LIST = "param:personsList"
        private const val PARAM_DOCS_COUNT = "param:docsNumber"
        private const val PARAM_PEOPLES_COUNT = "param:peoplesNumber"
        private const val PARAM_DOC1 = "param:doc1"
        private const val PARAM_DOC2 = "param:doc2"
        private const val PARAM_DOC3 = "param:doc3"
        private const val PARAM_DOC4 = "param:doc4"
        private const val PARAM_DOC5 = "param:doc5"
        private const val PARAM_DOC6 = "param:doc6"
        private const val PARAM_DOC7 = "param:doc7"
        private const val PARAM_DOC8 = "param:doc8"
        private const val PARAM_DOC9 = "param:doc9"
        private const val PARAM_DOC10 = "param:doc10"
    }

    fun bookTrip(
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
        peoplesNumber: Int,
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
        val data = HashMap<String, Any>()
        data[PARAM_MAIN_SYNDICATE] = mainSyndicateId
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_PHONE] = phone
        data[PARAM_TRIP_ID] = tripID
        data[PARAM_REGIMENT_ID] = regimentID
        data[PARAM_REGIMENT_DATE] = regimentDate
        data[PARAM_HOUSING_TYPE] = housingType
        data[PARAM_NUM_CHILD] = numChild
        data[PARAM_AGES] = ages
        data[PARAM_NAME] = name
        data[PARAM_PERSONS_LIST] = personsList
        data[PARAM_DOCS_COUNT] = docsNumber
        doc1?.let { data[PARAM_DOC1] = it }
        doc2?.let { data[PARAM_DOC2] = it }
        doc3?.let { data[PARAM_DOC3] = it }
        doc4?.let { data[PARAM_DOC4] = it }
        doc5?.let { data[PARAM_DOC5] = it }
        doc6?.let { data[PARAM_DOC6] = it }
        doc7?.let { data[PARAM_DOC7] = it }
        doc8?.let { data[PARAM_DOC8] = it }
        doc9?.let { data[PARAM_DOC9] = it }
        doc10?.let { data[PARAM_DOC10] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val mainSyndicateId = data?.get(BookTrip.PARAM_MAIN_SYNDICATE) as Int
        val userNumber = data?.get(BookTrip.PARAM_USER_NUMBER) as String
        val phone = data?.get(BookTrip.PARAM_PHONE) as String
        val tripID = data?.get(BookTrip.PARAM_TRIP_ID) as Int
        val regimentID = data?.get(BookTrip.PARAM_REGIMENT_ID) as Int
        val regimentDate = data?.get(BookTrip.PARAM_REGIMENT_DATE) as String
        val housingType = data?.get(BookTrip.PARAM_HOUSING_TYPE) as String
        val numChild = data?.get(BookTrip.PARAM_NUM_CHILD) as Int
        val ages = data?.get(BookTrip.PARAM_AGES) as String
        val name = data?.get(BookTrip.PARAM_NAME) as String
        val personsList = data?.get(BookTrip.PARAM_PERSONS_LIST) as List<PersonEntity>
        val docsNumber = data?.get(BookTrip.PARAM_DOCS_COUNT) as Int
        val peoplesNumber = data?.get(BookTrip.PARAM_DOCS_COUNT) as Int
        val doc1 = data?.get(BookTrip.PARAM_DOC1) as File?
        val doc2 = data?.get(BookTrip.PARAM_DOC2) as File?
        val doc3 = data?.get(BookTrip.PARAM_DOC3) as File?
        val doc4 = data?.get(BookTrip.PARAM_DOC4) as File?
        val doc5 = data?.get(BookTrip.PARAM_DOC5) as File?
        val doc6 = data?.get(BookTrip.PARAM_DOC6) as File?
        val doc7 = data?.get(BookTrip.PARAM_DOC7) as File?
        val doc8 = data?.get(BookTrip.PARAM_DOC8) as File?
        val doc9 = data?.get(BookTrip.PARAM_DOC9) as File?
        val doc10 = data?.get(BookTrip.PARAM_DOC10) as File?
        return neqabtyRepository.bookTrip(mainSyndicateId, userNumber, phone, tripID, regimentID, regimentDate, housingType, numChild, ages, name, personsList, docsNumber, peoplesNumber,
                doc1,
                doc2,
                doc3,
                doc4,
                doc5,
                doc6,
                doc7,
                doc8,
                doc9,
                doc10)
    }
}