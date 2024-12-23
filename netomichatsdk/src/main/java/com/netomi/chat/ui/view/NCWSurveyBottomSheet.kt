package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
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
import java.util.UUID

class NCWSurveyBottomSheet(private val surveyField: SurveyField, val conversationId:String,val botRefId:String,private val onSubmitSurveyRequest: (SubmitSurveyRequest) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var recyclerSuggestion:RecyclerView
    private lateinit var rowSuggestion:ConstraintLayout
    private lateinit var rowResolveIssue:ConstraintLayout
    private lateinit var tvIssueTitle:TextView
    private lateinit var rowAdditionalFeedback:ConstraintLayout
    private lateinit var tvFeedbackTitle:TextView
    private lateinit var tvSuggestionTitle:TextView
    private lateinit var edtAdditionFeedback:EditText
    private lateinit var radioGroup:RadioGroup



    lateinit var suggestionAdapter:SuggestionAdapter


    private var selectedRating: Int =0

    private var feedbackValue: String=""
    private var selectedRadioValue: String=""






    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_bottom_sheet)
        }

        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_sheet_survey, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle=view.findViewById<TextView>(R.id.tvTitle)
        val rowRating=view.findViewById<ConstraintLayout>(R.id.rowRating)
        val recyclerRating=view.findViewById<RecyclerView>(R.id.recyclerRating)

         rowResolveIssue=view.findViewById<ConstraintLayout>(R.id.rowResolveIssue)
         tvIssueTitle=view.findViewById<TextView>(R.id.tvIssueTitle)

         rowSuggestion=view.findViewById<ConstraintLayout>(R.id.rowSuggestion)
        tvSuggestionTitle=view.findViewById<TextView>(R.id.tvSuggestionTitle)

         rowAdditionalFeedback=view.findViewById<ConstraintLayout>(R.id.rowAdditionalFeedback)
         tvFeedbackTitle=view.findViewById<TextView>(R.id.tvFeedbackTitle)
        edtAdditionFeedback=view.findViewById<EditText>(R.id.edtAdditionFeedback)
        recyclerSuggestion=view.findViewById<RecyclerView>(R.id.recyclerSuggestion)

        radioGroup = view.findViewById(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = view.findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton.text.toString()
            selectedRadioValue=selectedText
            Toast.makeText(context, "Selected: $selectedText", Toast.LENGTH_SHORT).show()
        }


        val submitButton=view.findViewById<Button>(R.id.submitButton)


        submitButton.setOnClickListener {
            val suggestionTitle = if (feedbackValue=="POSITIVE")
                surveyField.payload?.positiveSuggestionMap?.title else  surveyField.payload?.negativeSuggestionMap?.title

            val selectedSuggestions=suggestionAdapter.getSelectedOptions()

            val issueResolved= selectedRadioValue=="Yes"

            Log.e("selectedSuggestions" ,"selectedSuggestions "+selectedSuggestions)
            val submitSurveyRequest = SubmitSurveyRequest(
                botRefId = botRefId,
                requestBody = RequestBody(
                    eventData = EventData(
                        eventInfo = EventInfo(
                            surveyId = surveyField.surveyId?:"",
                            feedbackValue = feedbackValue,
                            requestId =  UUID.randomUUID().toString(),
                            submitSurveyInfo = SubmitSurveyInfo(
                                rating = selectedRating,
                                suggestions = selectedSuggestions,
                                suggestionTitle = suggestionTitle?:"",
                                issueResolved = issueResolved,
                                additionalFeedback = edtAdditionFeedback.text.toString(),
                                triggerType = surveyField.triggerType?:""
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

        if (surveyField.payload?.feedbackMessage?.enabled == true){

            rowRating.visibility=View.VISIBLE
            tvTitle.text= surveyField.payload.feedbackMessage.text
            val ratingAdapter = context?.let {
                RatingAdapter(it,surveyField.payload?.surveyRatingTypeEnabledInfo?.upperBound?:3,surveyField.payload.ratingTypeEnabled?:"STAR"){
                    selectedRating=it
                        showOptionList(it)
                }
            }
            recyclerRating.layoutManager = GridLayoutManager(context, 5)
            recyclerRating.adapter = ratingAdapter
        }
        else{
            rowRating.visibility=View.GONE
        }




    }

    private fun showOptionList(selectedRating: Int) {
        rowSuggestion.visibility=View.VISIBLE
        tvSuggestionTitle.text= surveyField.payload?.positiveSuggestionMap?.title ?: ""
        val optionsList = if (selectedRating < surveyField.payload?.surveyRatingTypeEnabledInfo?.criteria?:3) {
            surveyField.payload?.negativeSuggestionMap?.options
        } else {
            surveyField.payload?.positiveSuggestionMap?.options
        }
        feedbackValue=if (selectedRating < surveyField.payload?.surveyRatingTypeEnabledInfo?.criteria?:3)
        "NEGATIVE"
        else
            "POSITIVE"

        tvSuggestionTitle.text= if (selectedRating < surveyField.payload?.surveyRatingTypeEnabledInfo?.criteria?:3)
            surveyField.payload?.negativeSuggestionMap?.title else
            surveyField.payload?.positiveSuggestionMap?.title

         suggestionAdapter = SuggestionAdapter(optionsList ?: emptyList())
        recyclerSuggestion.layoutManager = GridLayoutManager(context, 2)
        recyclerSuggestion.adapter = suggestionAdapter


        if (surveyField.payload?.resolutionQuestion?.enabled == true){
            rowResolveIssue.visibility=View.VISIBLE
            tvIssueTitle.text= surveyField.payload.resolutionQuestion.text
        }
        else{
            rowResolveIssue.visibility=View.GONE
        }

        if (surveyField.payload?.additionalFeedback?.enabled == true){
            rowAdditionalFeedback.visibility=View.VISIBLE
            tvFeedbackTitle.text= surveyField.payload.additionalFeedback.text
        }
        else{
            rowAdditionalFeedback.visibility=View.GONE
        }
    }
}
