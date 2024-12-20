package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R
import com.netomi.chat.model.messages.SurveyField

class NCWSurveyBottomSheet(private val surveyField: SurveyField) : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        // Apply the rounded background
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

        val rowResolveIssue=view.findViewById<ConstraintLayout>(R.id.rowResolveIssue)
        val tvIssueTitle=view.findViewById<TextView>(R.id.tvIssueTitle)

        val rowSuggestion=view.findViewById<ConstraintLayout>(R.id.rowSuggestion)
        val tvSuggestionTitle=view.findViewById<TextView>(R.id.tvSuggestionTitle)

        val rowAdditionalFeedback=view.findViewById<ConstraintLayout>(R.id.rowAdditionalFeedback)
        val tvFeedbackTitle=view.findViewById<TextView>(R.id.tvFeedbackTitle)

        if (surveyField.payload?.feedbackMessage?.enabled == true){
            rowRating.visibility=View.VISIBLE
            tvTitle.text= surveyField.payload.feedbackMessage.text
        }
        else{
            rowRating.visibility=View.GONE
        }
        if (surveyField.payload?.resolutionQuestion?.enabled == true){
            rowResolveIssue.visibility=View.VISIBLE
            tvIssueTitle.text= surveyField.payload.resolutionQuestion.text
        }
        else{
            rowResolveIssue.visibility=View.GONE
        }
        tvSuggestionTitle.text= surveyField.payload?.positiveSuggestionMap?.title ?: ""
        if (surveyField.payload?.additionalFeedback?.enabled == true){
            rowAdditionalFeedback.visibility=View.VISIBLE
            tvFeedbackTitle.text= surveyField.payload.additionalFeedback.text
        }
        else{
            rowAdditionalFeedback.visibility=View.GONE
        }









    }
}
