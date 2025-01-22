package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.utils.NCWThemeUtils

class NCWSettingBottomSheet(
    private val themeData: NCWThemeResponse,
    private val onRestartClick: () -> Unit,
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
        return inflater.inflate(R.layout.bottom_sheet_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ivClose = view.findViewById<ImageView>(R.id.ivClose)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val switchSound = view.findViewById<SwitchCompat>(R.id.toggle_sound)
        val constRestart = view.findViewById<ConstraintLayout>(R.id.constRestart)

        NCWThemeUtils.setTitleColor(tvTitle)
        switchSound.isChecked=themeData.sound.defaultSound

        switchSound.setOnCheckedChangeListener { button, isChecked ->
            if (button.isPressed){
                themeData.sound.defaultSound=isChecked
            }
        }

        view.findViewById<View>(R.id.icon_language_arrow)?.setOnClickListener {

        }

        ivClose.setOnClickListener {
            dismiss()
        }

        constRestart.setOnClickListener {
            onRestartClick()
            dismiss()
        }
    }
}
