package com.netomi.chat.ui.view
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.utils.NCWThemeUtils

class NCWEndChatBottomSheet(
    private val themeData: NCWThemeResponse?,
    private val onConfirmClick: (isEndChat: Boolean) -> Unit,
    private val onSendTranscript:(from:String?,mailTo:String) -> Unit
) : BottomSheetDialogFragment() {

    private var isEndChat:Boolean=false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        // Apply the rounded background
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_bottom_sheet)
        }

        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_end_chat_media_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rbReturnLater = view.findViewById<RadioButton>(R.id.radioReturnLater)
        val rbEndChat = view.findViewById<RadioButton>(R.id.radioEndChat)
        val btnConfirm=view.findViewById<Button>(R.id.btn_confirm)
        val tvSubTxtReturn=view.findViewById<TextView>(R.id.subtextReturnLater)
        val tvSubTxtEnd=view.findViewById<TextView>(R.id.subtextEndChat)
        val tvTitle=view.findViewById<TextView>(R.id.title)
        val checkboxTranscript=view.findViewById<CheckBox>(R.id.checkboxTranscript)
        val emailTextInputLayout=view.findViewById<ConstraintLayout>(R.id.emailTextInputLayout)
        val constTranscript=view.findViewById<ConstraintLayout>(R.id.constTranscript)
        val emailEditText=view.findViewById<EditText>(R.id.emailEditText)
        val tvTranscript=view.findViewById<TextView>(R.id.tvTranscript)
        val tvSendTranscript=view.findViewById<TextView>(R.id.tvSendTranscript)
        val tvDownload=view.findViewById<TextView>(R.id.tvDownload)
        NCWThemeUtils.setCheckBoxColor(checkboxTranscript)
        checkboxTranscript.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                emailTextInputLayout.visibility=View.VISIBLE

            }
            else{
                emailTextInputLayout.visibility=View.GONE
            }

        }

        NCWThemeUtils.applyButtonBackgroundWithCorners(btnConfirm, isSelected = false)
        NCWThemeUtils.setRadioButtonUserConfig(rbReturnLater)
        NCWThemeUtils.setRadioButtonUserConfig(rbEndChat)


        tvSubTxtEnd.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().descriptionColor))
        tvSubTxtReturn.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().descriptionColor))
        tvTitle.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().titleColor))

        tvTranscript.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().titleColor))
        tvSendTranscript.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().titleColor))
        tvDownload.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().titleColor))

        btnConfirm.isEnabled=false

        rbReturnLater.setOnClickListener {
            if (rbReturnLater.isChecked) {
                isEndChat=false
                btnConfirm.isEnabled=true
                NCWThemeUtils.applyButtonBackgroundWithCorners(btnConfirm, isSelected = true)
                rbEndChat.isChecked = false // Ensure the other radio button is unchecked
                constTranscript.visibility=View.GONE
            }
        }

        rbEndChat.setOnClickListener {
            if (rbEndChat.isChecked) {
                isEndChat=true
                btnConfirm.isEnabled=true
                NCWThemeUtils.applyButtonBackgroundWithCorners(btnConfirm, isSelected = true)
                rbReturnLater.isChecked = false // Ensure the other radio button is unchecked
                transcriptVisibility(constTranscript)

            }
        }

        btnConfirm.setOnClickListener {

            if (isEndChat) {
                if (checkboxTranscript.isChecked && emailEditText.text.toString().isNotEmpty()) {
                    onSendTranscript(themeData?.sendTranscriptEmailSetup?.email, emailEditText.text.toString())
                } else {
                    onConfirmClick.invoke(isEndChat)
                }
            } else {
                onConfirmClick.invoke(isEndChat)
            }

            dismiss()
        }

    }


    private fun transcriptVisibility(constTranscript :ConstraintLayout){
        if (themeData?.sendTranscriptEmailSetup?.enable==true){
            constTranscript.visibility=View.VISIBLE
        }
        else{
            constTranscript.visibility=View.GONE
        }
    }
}
