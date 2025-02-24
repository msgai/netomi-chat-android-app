package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R
import com.netomi.chat.model.theme.NCWShowWarning
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.utils.NCWAppUtils.isValidEmail
import com.netomi.chat.utils.NCWThemeUtils
import com.netomi.chat.utils.NCWThemeUtils.createErrorDrawable
import com.netomi.chat.utils.NCWThemeUtils.createNormalDrawable

class NCWRestartChatBottomSheet(
    private val themeData: NCWThemeResponse?,
    private val ncwShowWarning: NCWShowWarning,
    val onYesClick: () -> Unit,
    private val onSendTranscript: (from: String?, mailTo: String) -> Unit,
    private val onCrossClick: () -> Unit,
    private val onDownloadClick: () -> Unit,

) : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

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
        return inflater.inflate(R.layout.restart_bottom_sheet, container, false)
    }

    override fun onViewCreated(dialogView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(dialogView, savedInstanceState)

        val tvTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.dialogSubtitle)
        val btnConfirm = dialogView.findViewById<TextView>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<TextView>(R.id.btnCancel)

        val checkboxTranscript = dialogView.findViewById<CheckBox>(R.id.checkboxTranscript)
        val emailTextInputLayout =
            dialogView.findViewById<ConstraintLayout>(R.id.emailTextInputLayout)
        val constTranscript = dialogView.findViewById<ConstraintLayout>(R.id.constTranscript)
        val emailEditText = dialogView.findViewById<EditText>(R.id.emailEditText)
        val tvTranscript = dialogView.findViewById<TextView>(R.id.tvTranscript)
        val tvSendTranscript = dialogView.findViewById<TextView>(R.id.tvSendTranscript)
        val tvDownload = dialogView.findViewById<TextView>(R.id.tvDownload)
        val tvErrorEmail = dialogView.findViewById<TextView>(R.id.tvErrorEmail)
        val tvEmail = dialogView.findViewById<TextView>(R.id.tvEmail)
        val constDownload = dialogView.findViewById<ConstraintLayout>(R.id.constDownload)





        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().trim()
                if (isValidEmail(input)) {
                    createNormalDrawable(emailEditText)
                    tvErrorEmail.visibility = View.GONE
                    enableButton(btnConfirm, true)
                } else {
                    createErrorDrawable(emailEditText)
                    tvErrorEmail.visibility = View.VISIBLE
                    tvErrorEmail.text =  NCWThemeUtils.getThemeData()?.otherlocalized?.please_provide_valid_email ?:getString(R.string.please_provide_valid_email)
                    enableButton(btnConfirm, false)
                }
            }
        })


        createNormalDrawable(emailEditText)
        if (themeData?.sendTranscriptEmailSetup?.enable == true) {
            constTranscript.visibility = View.VISIBLE
        } else {
            constTranscript.visibility = View.GONE
        }
        val transcriptVisibility = if (ncwShowWarning.isSendTranscriptEnabled) View.VISIBLE else View.INVISIBLE
        tvTranscript.visibility = transcriptVisibility
        checkboxTranscript.visibility = transcriptVisibility
        tvSendTranscript.visibility = transcriptVisibility
        constDownload.visibility = if (ncwShowWarning.isSendTranscriptEnabled) View.VISIBLE else View.GONE

        NCWThemeUtils.setCheckBoxColor(checkboxTranscript)

        checkboxTranscript.setOnCheckedChangeListener { _, isChecked ->
            emailTextInputLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
            enableButton(btnConfirm, !isChecked || isValidEmail(emailEditText.text.toString()))
        }

        NCWThemeUtils.setTitleColor(tvTitle)
        NCWThemeUtils.setDescriptionColor(tvSubtitle)
        NCWThemeUtils.setTitleColor(tvSendTranscript)
        NCWThemeUtils.setTitleColor(tvTranscript)

        tvSendTranscript.text=NCWThemeUtils.getThemeData()?.otherlocalized?.send_transcript ?: getString(R.string.send_transcript)
       tvDownload.text=NCWThemeUtils.getThemeData()?.otherlocalized?.download ?: getString(R.string.download)
        tvTitle.text = ncwShowWarning.restartButtonText ?: getString(R.string.restart_chat)
        tvSubtitle.text = ncwShowWarning.warningText ?: getString(R.string.confirm_restart_chat)
        btnConfirm.text = ncwShowWarning.restartButtonText ?: getString(R.string.restart_chat)
        btnCancel.text = ncwShowWarning.cancelButtonText ?: getString(R.string.cancel)
        tvEmail.text= NCWThemeUtils.getThemeData()?.otherlocalized?.email ?: getString(R.string.email)
        tvDownload.text= NCWThemeUtils.getThemeData()?.otherlocalized?.download ?: getString(R.string.download)

        emailEditText.hint= NCWThemeUtils.getThemeData()?.otherlocalized?.enter_email ?: getString(R.string.enter_email)
        NCWThemeUtils.createRoundedDrawable(btnConfirm)
        NCWThemeUtils.createRoundedDrawableClose(btnCancel)


        btnConfirm.setOnClickListener {

            if (checkboxTranscript.isChecked && emailEditText.text.toString().isNotEmpty()) {
                if (themeData?.sendTranscriptEmailSetup?.enable == false)
                onSendTranscript(themeData?.sendTranscriptEmailSetup?.email, emailEditText.text.toString())
                else
                    onSendTranscript(null, emailEditText.text.toString())

            } else {
                onYesClick()
            }
            dismiss()
        }
        constDownload.setOnClickListener {
            onDownloadClick()

        }
        btnCancel.setOnClickListener {
            onCrossClick()
            dismiss()
        }



    }

    private fun enableButton(view: TextView, value: Boolean) {
        view.isEnabled = value
        view.alpha = if (value) 1f else 0.5f
    }

}
