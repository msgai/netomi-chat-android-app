package com.netomi.chat.ui.view
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.chat.utils.NCWThemeUtils

class NCWEndChatBottomSheet(
    private val onConfirmClick: (isEndChat: Boolean) -> Unit
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
        NCWThemeUtils.applyButtonBackgroundWithCorners(btnConfirm, isSelected = false)
        NCWThemeUtils.setRadioButtonUserConfig(rbReturnLater)
        NCWThemeUtils.setRadioButtonUserConfig(rbEndChat)


        tvSubTxtEnd.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().descriptionColor))
        tvSubTxtReturn.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().descriptionColor))
        tvTitle.setTextColor(Color.parseColor(NCWChatSdk.getUpdatedOtherConfiguration().titleColor))
        btnConfirm.isEnabled=false

        rbReturnLater.setOnClickListener {
            if (rbReturnLater.isChecked) {
                isEndChat=false
                btnConfirm.isEnabled=true
                NCWThemeUtils.applyButtonBackgroundWithCorners(btnConfirm, isSelected = true)
                rbEndChat.isChecked = false // Ensure the other radio button is unchecked
            }
        }

        rbEndChat.setOnClickListener {
            if (rbEndChat.isChecked) {
                isEndChat=true
                btnConfirm.isEnabled=true
                NCWThemeUtils.applyButtonBackgroundWithCorners(btnConfirm, isSelected = true)
                rbReturnLater.isChecked = false // Ensure the other radio button is unchecked
            }
        }

        btnConfirm.setOnClickListener {
            onConfirmClick.invoke(isEndChat)
            dismiss()
        }
    }
}
