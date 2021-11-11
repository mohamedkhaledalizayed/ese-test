package com.neqabty.presentation.ui.questionnaires

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetQuestionnaires
import com.neqabty.domain.usecases.VoteQuestionnaire
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.QuestionnaireUI
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.mappers.QuestionnaireEntityUIMapper
import com.neqabty.presentation.mappers.QuestionnaireVoteEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
        private val getQuestionnaires: GetQuestionnaires,
        private val voteQuestionnaire: VoteQuestionnaire
) : BaseViewModel() {

    private val questionnaireEntityUIMapper = QuestionnaireEntityUIMapper()
    private val questionnaireVoteEntityUIMapper = QuestionnaireVoteEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<QuestionnaireViewState> = MutableLiveData()

    init {
        viewState.value = QuestionnaireViewState(isLoading = true)
    }

    fun getQuestionnaire(userNumber: String) {
        viewState.value?.questionnaire?.let {
            onQuestionnaireReceived(it)
        } ?: addDisposable(getQuestionnaires.getQuestionnaires(userNumber)
                .flatMap {
                    it.let {
                        questionnaireEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onQuestionnaireReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onQuestionnaireReceived(questionnaire: QuestionnaireUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                questionnaire = questionnaire
        )
        viewState.value = newViewState
    }

    fun voteQuestionnaire(
        userNumber: String,
        questionnaireId: Int,
        answerId: Int
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(voteQuestionnaire.voteQuestionnaire(userNumber, questionnaireId, answerId)
                .subscribe(
                        { viewState.value = viewState.value?.copy(isLoading = false, message = it.message) },
                        { errorState.value = handleError(it) }
                )
        )
    }
}
