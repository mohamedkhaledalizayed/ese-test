package com.neqabty.healthcare.retirement.data.datasource



import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.retirement.data.api.RetirementApi
import com.neqabty.healthcare.retirement.data.model.inheritor.InheritorModel
import com.neqabty.healthcare.retirement.data.model.pension.PensionModel
import com.neqabty.healthcare.retirement.data.model.validation.ValidationModel
import retrofit2.Response
import javax.inject.Inject


class RetirementDS @Inject constructor(private val retirementApi: RetirementApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun checkValidation(userNumber: String, nationalId: String): Response<ValidationModel> {
        return retirementApi.checkValidation(token = preferencesHelper.token, userNumber, nationalId)
    }

    suspend fun getPensionInfo(id: String): Response<PensionModel> {
        return retirementApi.getPensionInfo(token = preferencesHelper.token, id)
    }

    suspend fun getInheritor(id: String): Response<InheritorModel> {
        return retirementApi.getInheritor(token = preferencesHelper.token, id)
    }

}