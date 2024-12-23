package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R
import com.netomi.chat.model.messages.SurveyField
import com.netomi.chat.survey.*
import java.util.UUID

class NCWSurveyBottomSheet(
    private val surveyField: SurveyField,
    private val conversationId: String,
    private val botRefId: String,
    private val onSubmitSurveyRequest: (SubmitSurveyRequest) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var recyclerSuggestion: RecyclerView
    private lateinit var rowSuggestion: ConstraintLayout
    private lateinit var rowResolveIssue: ConstraintLayout
    private lateinit var tvIssueTitle: TextView
    private lateinit var rowAdditionalFeedback: ConstraintLayout
    private lateinit var tvFeedbackTitle: TextView
    private lateinit var tvSuggestionTitle: TextView
    private lateinit var edtAdditionalFeedback: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var suggestionAdapter: SuggestionAdapter

    private var selectedRating: Int = 0
    private var feedbackValue: String = ""
    private var selectedRadioValue: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_bottom_sheet
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.layout_bottom_sheet_survey, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        setupRadioGroup()
        setupSubmitButton()
        configureRatingSection()
    }

    private fun initializeViews(view: View) {
        with(view) {
            recyclerSuggestion = findViewById(R.id.recyclerSuggestion)
            rowSuggestion = findViewById(R.id.rowSuggestion)
            rowResolveIssue = findViewById(R.id.rowResolveIssue)
            tvIssueTitle = findViewById(R.id.tvIssueTitle)
            rowAdditionalFeedback = findViewById(R.id.rowAdditionalFeedback)
            tvFeedbackTitle = findViewById(R.id.tvFeedbackTitle)
            tvSuggestionTitle = findViewById(R.id.tvSuggestionTitle)
            edtAdditionalFeedback = findViewById(R.id.edtAdditionFeedback)
            radioGroup = findViewById(R.id.radioGroup)
        }
    }

    private fun setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedRadioValue = view?.findViewById<RadioButton>(checkedId)?.text.toString()
        }
    }

    private fun setupSubmitButton() {
        view?.findViewById<Button>(R.id.submitButton)?.setOnClickListener {
            val suggestionTitle = if (feedbackValue == "POSITIVE")
                surveyField.payload?.positiveSuggestionMap?.title
            else
                surveyField.payload?.negativeSuggestionMap?.title

            val selectedSuggestions = suggestionAdapter.getSelectedOptions()
            val issueResolved = selectedRadioValue == "Yes"

            Log.e("selectedSuggestions", "selectedSuggestions $selectedSuggestions")

            val submitSurveyRequest = SubmitSurveyRequest(
                botRefId = botRefId,
                requestBody = RequestBody(
                    eventData = EventData(
                        eventInfo = EventInfo(
                            surveyId = surveyField.surveyId ?: "",
                            feedbackValue = feedbackValue,
                            requestId = UUID.randomUUID().toString(),
                            submitSurveyInfo = SubmitSurveyInfo(
                                rating = selectedRating,
                                suggestions = selectedSuggestions,
                                suggestionTitle = suggestionTitle ?: "",
                                issueResolved = issueResolved,
                                additionalFeedback = edtAdditionalFeedback.text.toString(),
                                triggerType = surveyField.triggerType ?: ""
                            )
                        )
                    ),
                    conversationId = conversationId,
                    botReferenceId = botRefId,
                    timestamp = System.currentTimeMillis()
                )
            )

            onSubmitSurveyRequest(submitSurveyRequest)
        }
    }

    private fun configureRatingSection() {
        val rowRating = view?.findViewById<ConstraintLayout>(R.id.rowRating)
        val recyclerRating = view?.findViewById<RecyclerView>(R.id.recyclerRating)
        val tvTitle = view?.findViewById<TextView>(R.id.tvTitle)

        if (surveyField.payload?.feedbackMessage?.enabled == true) {
            rowRating?.visibility = View.VISIBLE
            tvTitle?.text = surveyField.payload.feedbackMessage.text

            recyclerRating?.apply {
                layoutManager = GridLayoutManager(context, 5)
                adapter = context?.let {
                    NCWRatingAdapter(
                        it,
                        surveyField.payload?.surveyRatingTypeEnabledInfo?.upperBound ?: 3,
                        surveyField.payload.ratingTypeEnabled ?: "STAR"
                    ) { rating ->
                        selectedRating = rating
                        showOptionList(rating)
                    }
                }
            }
        } else {
            rowRating?.visibility = View.GONE
        }
    }

    private fun showOptionList(selectedRating: Int) {
        val criteria = surveyField.payload?.surveyRatingTypeEnabledInfo?.criteria ?: 3
        val isPositiveFeedback = selectedRating >= criteria

        feedbackValue = if (isPositiveFeedback) "POSITIVE" else "NEGATIVE"
        tvSuggestionTitle.text = if (isPositiveFeedback)
            surveyField.payload?.positiveSuggestionMap?.title
        else
            surveyField.payload?.negativeSuggestionMap?.title

        val optionsList = if (isPositiveFeedback)
            surveyField.payload?.positiveSuggestionMap?.options
        else
            surveyField.payload?.negativeSuggestionMap?.options

        suggestionAdapter = SuggestionAdapter(optionsList ?: emptyList())
        recyclerSuggestion.layoutManager = GridLayoutManager(context, 2)
        recyclerSuggestion.adapter = suggestionAdapter

        rowSuggestion.visibility = View.VISIBLE
        configureResolutionQuestion()
        configureAdditionalFeedback()
    }

    private fun configureResolutionQuestion() {
        if (surveyField.payload?.resolutionQuestion?.enabled == true) {
            rowResolveIssue.visibility = View.VISIBLE
            tvIssueTitle.text = surveyField.payload.resolutionQuestion.text
        } else {
            rowResolveIssue.visibility = View.GONE
        }
    }

    private fun configureAdditionalFeedback() {
        if (surveyField.payload?.additionalFeedback?.enabled == true) {
            rowAdditionalFeedback.visibility = View.VISIBLE
            tvFeedbackTitle.text = surveyField.payload.additionalFeedback.text
        } else {
            rowAdditionalFeedback.visibility = View.GONE
        }
    }
}
