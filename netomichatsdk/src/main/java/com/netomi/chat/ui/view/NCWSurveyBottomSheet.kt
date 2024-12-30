package com.netomi.chat.ui.view


import android .app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.google.android.material.bottomsheet.BottomSheetDialog
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
    private val onSkipSurvey: (String,String) -> Unit,
) : BottomSheetDialogFragment() {

    private lateinit var recyclerSuggestion: RecyclerView
    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var edtAdditionalFeedback: EditText
    private lateinit var tvSuggestionTitle: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var rowSuggestion: ConstraintLayout
    private lateinit var submitButton: TextView
    private lateinit var viewSpace: View


    private var selectedRating: Int = 0
    private var feedbackValue: String = ""
    private var selectedRadioValue: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        dialog.setOnShowListener {
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_bottom_sheet)
        }

        return dialog
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
        val tvFeedbackCount = findViewById<TextView>(R.id.tvFeedbackCount)
        val constAdd = findViewById<ConstraintLayout>(R.id.constAdd)
        val tvFeedbackTitle = findViewById<TextView>(R.id.tvFeedbackTitle)
        viewSpace= findViewById(R.id.space)
        tvFeedbackCount.visibility=View.GONE

        if (from == TYPE_SUBMITTED_SURVEY) {
            constAdd.visibility=View.GONE
            tvFeedbackCount.visibility=View.GONE
            tvFeedbackTitle.text=getString(R.string.additional_feedback)
        }

        constAdd.setOnClickListener {
            edtAdditionalFeedback.visibility=View.VISIBLE
            constAdd.visibility=View.GONE
            tvFeedbackCount.visibility=View.VISIBLE
            tvFeedbackTitle.text= getString(R.string.write_your_feedback_here)
        }


        edtAdditionalFeedback.visibility=View.GONE

        radioGroup = findViewById(R.id.radioGroup)
        tvSuggestionTitle = findViewById(R.id.tvSuggestionTitle)
        submitButton= view.findViewById(R.id.submitButton)

        findViewById<View>(R.id.overlayView)?.apply {
            visibility = if (from == TYPE_SUBMITTED_SURVEY) View.VISIBLE else View.GONE
            isClickable = from == TYPE_SUBMITTED_SURVEY
        }

        edtAdditionalFeedback.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentLength = s?.length ?: 0
                tvFeedbackCount.text = "$currentLength/200"
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedRadioValue = view?.findViewById<RadioButton>(checkedId)?.text.toString()
        }
        val radioYes = view?.findViewById<RadioButton>(R.id.radioYes)
        val radioNo = view?.findViewById<RadioButton>(R.id.radioNo)

        if (radioYes != null) {
            NCWThemeUtils.setRadioButtonUserConfig(radioYes)
        }
        if (radioNo != null) {
            NCWThemeUtils.setRadioButtonUserConfig(radioNo)
        }

        if (from == TYPE_SUBMITTED_SURVEY) {
            surveyField?.submitSurveyInfo?.issueResolved?.let { isResolved ->
                radioGroup.check(if (isResolved) R.id.radioYes else R.id.radioNo)
            }
        }

    }

    private fun setupSubmitButton() {
        setButtonState(false)
        NCWThemeUtils.createRoundedDrawableSubmit(submitButton)
        submitButton.setOnClickListener { handleSubmitClick() }
        submitButton.visibility = if (from == TYPE_SUBMITTED_SURVEY) View.GONE else View.VISIBLE

        view?.findViewById<TextView>(R.id.closeButton)?.let { closeButton ->
            NCWThemeUtils.createRoundedDrawableClose(closeButton)
            closeButton.text= if (from == TYPE_SUBMITTED_SURVEY) "Close" else "Skip"

            closeButton.setOnClickListener {
                if (from == TYPE_SUBMITTED_SURVEY) {
                    dismiss()
                } else {
                    val text = "event://;SKIP_EVENT;resumeWorkflow::value=true^$^requestId::value=$requestId"
                    onSkipSurvey(text, "SKIP")
                    dismiss()
                }
            }


            closeButton.visibility = if (from == TYPE_SUBMITTED_SURVEY || surveyField?.payload?.isSkipEnabled == true) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        if (from == TYPE_SUBMITTED_SURVEY){
            viewSpace.visibility=View.GONE
        }
        viewSpace.visibility = if (from != TYPE_SUBMITTED_SURVEY && surveyField?.payload?.isSkipEnabled == true) {
            View.VISIBLE
        } else {
            View.GONE
        }




    }
    fun setButtonState(isEnabled: Boolean) {
        submitButton.isEnabled = isEnabled
        submitButton.alpha = if (isEnabled) 1.0f else 0.5f
    }

    private fun handleSubmitClick() {
        val suggestionTitle = when (feedbackValue) {
            "POSITIVE" -> surveyField?.payload?.positiveSuggestionMap?.title
            else -> surveyField?.payload?.negativeSuggestionMap?.title
        }
        val selectedSuggestions =if (surveyField?.payload?.reasonOfRating?.enabled ==true)
          suggestionAdapter.getSelectedOptions() else emptyList()

        val issueResolved: Boolean? = if (selectedRadioValue.isNotEmpty()) selectedRadioValue == "Yes" else null

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
            if (from == TYPE_SUBMITTED_SURVEY) {
                tvTitle?.text = getString(R.string.view_response)
            }
            else{
                tvTitle?.text = surveyField.payload.feedbackMessage.text
            }

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
                    Log.e("Ratidnndn","xzxkzklx "+selectedRating)
                    submitButton.isEnabled=true
                    if (surveyField.payload.reasonOfRating?.enabled==true) {
                        showOptionList(rating)
                    }
                    else{
                        configureResolutionQuestion()
                        configureAdditionalFeedback()
                    }
                    setButtonState(true)
                }
            }

            recyclerRating?.apply {
                layoutManager = GridLayoutManager(context, 5)
                adapter = ratingAdapter

                if (from == TYPE_SUBMITTED_SURVEY) {
                    val rating = surveyField.submitSurveyInfo.rating
                    val  ratingTypeEnabled=surveyField.payload.ratingTypeEnabled
                    val finalRating = if (ratingTypeEnabled != "THUMBS_UP_DOWN") {
                        rating
                    } else {
                        if (rating == 2) 1 else 2
                    }
                    ratingAdapter?.apply {
                        selectedRating = finalRating
                        notifyDataSetChanged()
                    }
                    if (surveyField.payload.reasonOfRating?.enabled==true) {
                        showOptionList(rating)
                    }


                }

            }

        } else {
            rowRating?.visibility = View.GONE
        }





    }


    private fun showOptionList(selectedRating: Int) {
        val criteria = surveyField?.payload?.surveyRatingTypeEnabledInfo?.criteria ?: 3
        val isPositiveFeedback = selectedRating >= criteria
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
                 //   text = surveyField.payload.additionalFeedback.text
                    NCWThemeUtils.setTitleColor(this)
                }
                if (from == TYPE_SUBMITTED_SURVEY) {
                    edtAdditionalFeedback.setText(surveyField?.submitSurveyInfo?.additionalFeedback.orEmpty())
                    edtAdditionalFeedback.visibility=View.VISIBLE
                }
                View.VISIBLE
            } else View.GONE
        }
    }
}
