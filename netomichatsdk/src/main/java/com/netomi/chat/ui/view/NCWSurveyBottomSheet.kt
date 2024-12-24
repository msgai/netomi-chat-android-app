package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R
import com.netomi.chat.model.messages.SurveyField
import com.netomi.chat.survey.EventData
import com.netomi.chat.survey.EventInfo
import com.netomi.chat.survey.RequestBody
import com.netomi.chat.survey.SubmitSurveyInfo
import com.netomi.chat.survey.SubmitSurveyRequest
import com.netomi.chat.utils.NCWAppConstant.TYPE_SUBMITTED_SURVEY
import com.netomi.chat.utils.NCWThemeUtils
import java.util.UUID

class NCWSurveyBottomSheet(
    private val requestId: String? = null,
    private val surveyField: SurveyField? = null,
    private val conversationId: String,
    private val botRefId: String,
    private val from: String,
    private val onSubmitSurveyRequest: (SubmitSurveyRequest) -> Unit,
) : BottomSheetDialogFragment() {

    private lateinit var recyclerSuggestion: RecyclerView
    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var edtAdditionalFeedback: EditText
    private lateinit var tvSuggestionTitle: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var rowSuggestion: ConstraintLayout

    private var selectedRating: Int = 0
    private var feedbackValue: String = ""
    private var selectedRadioValue: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_bottom_sheet)
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

        if (from == TYPE_SUBMITTED_SURVEY) {
            configureResolutionQuestion()
            configureAdditionalFeedback()
        }
    }

    private fun initializeViews(view: View) = with(view) {
        recyclerSuggestion = findViewById(R.id.recyclerSuggestion)
        rowSuggestion = findViewById(R.id.rowSuggestion)
        edtAdditionalFeedback = findViewById(R.id.edtAdditionFeedback)
        radioGroup = findViewById(R.id.radioGroup)
        tvSuggestionTitle = findViewById(R.id.tvSuggestionTitle)

        findViewById<View>(R.id.overlayView)?.apply {
            visibility = if (from == TYPE_SUBMITTED_SURVEY) View.VISIBLE else View.GONE
            isClickable = from == TYPE_SUBMITTED_SURVEY
        }
    }

    private fun setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedRadioValue = view?.findViewById<RadioButton>(checkedId)?.text.toString()
        }

        if (from == TYPE_SUBMITTED_SURVEY) {
            radioGroup.check(
                if (surveyField?.submitSurveyInfo?.issueResolved == true) R.id.radioYes else R.id.radioNo
            )
        }
    }

    private fun setupSubmitButton() {
        view?.findViewById<TextView>(R.id.submitButton)?.apply {
            NCWThemeUtils.createRoundedDrawable(this)
            setOnClickListener { handleSubmitClick() }
            visibility = if (from == TYPE_SUBMITTED_SURVEY) View.GONE else View.VISIBLE
        }

        view?.findViewById<TextView>(R.id.closeButton)?.apply {
            NCWThemeUtils.createRoundedDrawableClose(this)
            setOnClickListener { dismiss() }
        }
    }

    private fun handleSubmitClick() {
        val suggestionTitle = when (feedbackValue) {
            "POSITIVE" -> surveyField?.payload?.positiveSuggestionMap?.title
            else -> surveyField?.payload?.negativeSuggestionMap?.title
        }

        val selectedSuggestions = suggestionAdapter.getSelectedOptions()
        val issueResolved = selectedRadioValue == "Yes"

        onSubmitSurveyRequest(
            SubmitSurveyRequest(
                botRefId = botRefId,
                requestBody = RequestBody(
                    triggerType = "EVENT",
                    eventData = EventData(
                        eventInfo = EventInfo(
                            surveyId = surveyField?.surveyId.orEmpty(),
                            feedbackValue = feedbackValue,
                            requestId = requestId ?: UUID.randomUUID().toString(),
                            submitSurveyInfo = SubmitSurveyInfo(
                                rating = selectedRating,
                                suggestions = selectedSuggestions,
                                suggestionTitle = suggestionTitle.orEmpty(),
                                issueResolved = issueResolved,
                                additionalFeedback = edtAdditionalFeedback.text.toString(),
                                triggerType = surveyField?.triggerType ?:"EVENT"
                            )
                        )
                    ),
                    conversationId = conversationId,
                    botReferenceId = botRefId,
                    timestamp = System.currentTimeMillis()
                )
            )
        )
        dismiss()
    }

    private fun configureRatingSection() {
        val rowRating = view?.findViewById<ConstraintLayout>(R.id.rowRating)
        val recyclerRating = view?.findViewById<RecyclerView>(R.id.recyclerRating)
        val tvTitle = view?.findViewById<TextView>(R.id.tvTitle)

        if (surveyField?.payload?.feedbackMessage?.enabled == true) {
            rowRating?.visibility = View.VISIBLE
            tvTitle?.text = surveyField.payload.feedbackMessage.text
            if (tvTitle != null) {
                NCWThemeUtils.setTitleColor(tvTitle)
            }

            val ratingAdapter = context?.let {
                NCWRatingAdapter(
                    it,
                    surveyField.payload?.surveyRatingTypeEnabledInfo?.upperBound ?: 3,
                    surveyField.payload.ratingTypeEnabled ?: "STAR"
                ) { rating ->
                    selectedRating = rating
                    showOptionList(rating)
                }
            }

            recyclerRating?.apply {
                layoutManager = GridLayoutManager(context, 5)
                adapter = ratingAdapter

                if (from == TYPE_SUBMITTED_SURVEY) {
                    val rating = surveyField.submitSurveyInfo.rating
                    ratingAdapter?.selectedRating = rating
                    ratingAdapter?.notifyDataSetChanged()
                    showOptionList(rating)


                }

            }

        } else {
            rowRating?.visibility = View.GONE
        }
    }


    private fun showOptionList(selectedRating: Int) {
        Log.e("selectedRating ","selectedRating"+selectedRating)
        val criteria = surveyField?.payload?.surveyRatingTypeEnabledInfo?.criteria ?: 3
        Log.e("selectedRating ","criteria "+criteria)
        val isPositiveFeedback = selectedRating >= criteria
        Log.e("selectedRating ","isPositiveFeedback "+isPositiveFeedback)
        feedbackValue = if (isPositiveFeedback) "POSITIVE" else "NEGATIVE"
        tvSuggestionTitle.text = if (isPositiveFeedback)
            surveyField?.payload?.positiveSuggestionMap?.title
        else
            surveyField?.payload?.negativeSuggestionMap?.title
        if (tvSuggestionTitle != null) {
            NCWThemeUtils.setTitleColor(tvSuggestionTitle)
        }

        val optionsList = if (isPositiveFeedback)
            surveyField?.payload?.positiveSuggestionMap?.options
        else
            surveyField?.payload?.negativeSuggestionMap?.options

        suggestionAdapter = SuggestionAdapter(optionsList ?: emptyList())
        recyclerSuggestion.layoutManager = GridLayoutManager(context, 2)
        recyclerSuggestion.adapter = suggestionAdapter

        if (from == TYPE_SUBMITTED_SURVEY) {
            preSelectSuggestions(optionsList)
        }
        rowSuggestion.visibility = View.VISIBLE
        configureResolutionQuestion()
        configureAdditionalFeedback()
    }

    private fun preSelectSuggestions(options: List<String>?) {
        options?.forEachIndexed { index, message ->
            surveyField?.submitSurveyInfo?.suggestions?.forEach { suggestion ->
                if (message == suggestion) {
                    suggestionAdapter.selectedOptions.add(index)
                }
            }
        }
        suggestionAdapter.notifyDataSetChanged()
    }

    private fun configureResolutionQuestion() {
        view?.findViewById<ConstraintLayout>(R.id.rowResolveIssue)?.apply {
            visibility = if (surveyField?.payload?.resolutionQuestion?.enabled == true) {
                view?.findViewById<TextView>(R.id.tvIssueTitle)?.apply {
                    text = surveyField.payload.resolutionQuestion.text
                    NCWThemeUtils.setTitleColor(this)
                }
                View.VISIBLE
            } else View.GONE
        }
    }

    private fun configureAdditionalFeedback() {
        view?.findViewById<ConstraintLayout>(R.id.rowAdditionalFeedback)?.apply {
            visibility = if (surveyField?.payload?.additionalFeedback?.enabled == true) {
                view?.findViewById<TextView>(R.id.tvFeedbackTitle)?.apply {
                    text = surveyField.payload.additionalFeedback.text
                    NCWThemeUtils.setTitleColor(this)
                }
                if (from == TYPE_SUBMITTED_SURVEY) {
                    edtAdditionalFeedback.setText(surveyField?.submitSurveyInfo?.additionalFeedback.orEmpty())
                }
                View.VISIBLE
            } else View.GONE
        }
    }
}
