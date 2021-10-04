package com.neqabty.presentation.ui.questionnaires

import com.neqabty.presentation.entities.QuestionnaireUI

data class QuestionnaireViewState(
    var isLoading: Boolean = false,
    var message: String = "",
    var questionnaire: QuestionnaireUI? = null
)
