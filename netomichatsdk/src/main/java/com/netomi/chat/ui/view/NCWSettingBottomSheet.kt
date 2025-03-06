package com.netomi.chat.ui.view

import android.app.Dialog
import android.content.res.ColorStateList
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
import com.netomi.chat.model.theme.NCWShowWarning
import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.chat.utils.NCWAppConstant.EN
import com.netomi.chat.utils.NCWAppConstant.ENABLED
import com.netomi.chat.utils.NCWAppUtils
import com.netomi.chat.utils.NCWThemeUtils

class NCWSettingBottomSheet(
    private val themeData: NCWThemeResponse,
    private val onRestartClick: (showWarning: NCWShowWarning?) -> Unit,
    private val onLanguageClick: () -> Unit,
    private val onCrossClick: () -> Unit,

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
        val constSound = view.findViewById<ConstraintLayout>(R.id.constSound)
        val viewLineSound = view.findViewById<View>(R.id.viewLineSound)
        val constLang = view.findViewById<ConstraintLayout>(R.id.constLang)


        val tvLanguage = view.findViewById<TextView>(R.id.tvLanguage)
        val viewLineLang = view.findViewById<View>(R.id.viewLineLang)

        val tvSound = view.findViewById<TextView>(R.id.tvSound)
        val textRestart = view.findViewById<TextView>(R.id.text_restart)

        tvTitle.text= NCWThemeUtils.getThemeData()?.otherlocalized?.settings ?: getString(R.string.setting)

        if (themeData?.multilingual?.enabled == true) {
            constLang.visibility=View.VISIBLE
            viewLineLang.visibility=View.VISIBLE
            val languageCode = themeData?.multilingual?.selectedCode?.uppercase() ?: EN
            val languageText = NCWThemeUtils.getThemeData()?.otherlocalized?.language
                ?: getString(R.string.language)
            tvLanguage.text = "$languageText - $languageCode"
        }
        else{
            constLang.visibility=View.GONE
            viewLineLang.visibility=View.GONE
        }

        tvSound.text= NCWThemeUtils.getThemeData()?.otherlocalized?.sound ?: getString(R.string.sound)
        textRestart.text= NCWThemeUtils.getThemeData()?.otherlocalized?.restart_chat ?: getString(R.string.restart_chat)

        val isVisible = themeData?.sound?.status == ENABLED
        constSound.visibility = if (isVisible) View.VISIBLE else View.GONE
        viewLineSound.visibility = if (isVisible) View.VISIBLE else View.GONE

        val isRestartChatVisible = themeData?.restartChat?.isEnabled   == true
        constRestart.visibility = if (isRestartChatVisible) View.VISIBLE else View.GONE


        val greyColor =
            context?.let { ContextCompat.getColor(it, R.color.gray) }
        val greenColor =
            context?.let { ContextCompat.getColor(it, R.color.green) }


        NCWThemeUtils.setTitleColor(tvTitle)
        switchSound.isChecked= themeData.sound.isSound == true

        val trackColor = if (themeData.sound.isSound == true) greenColor else greyColor
        switchSound.trackTintList = trackColor?.let { ColorStateList.valueOf(it) }

        switchSound.setOnCheckedChangeListener { button, isChecked ->
            if (button.isPressed){
                themeData.sound.isSound=isChecked
                val trackColor = if (isChecked) greenColor else greyColor
                switchSound.trackTintList = trackColor?.let { ColorStateList.valueOf(it) }

            }
        }


        ivClose.setOnClickListener {
            onCrossClick()
            dismiss()
        }

        constRestart.setOnClickListener {
            themeData.restartChat?.showWarning?.let { warning ->
                if (warning.isEnabled) {
                    onRestartClick(warning)
                } else {
                    onRestartClick(null)
                }
            } ?: onRestartClick(null)

            dismiss()
        }

        constLang.setOnClickListener {
            context?.let {
                if (!NCWAppUtils.isNetworkAvailable(it)) {
                    NCWAppUtils.showToast(it, NCWThemeUtils.getThemeData()?.otherlocalized?.please_check_your_network ?:getString(R.string.please_check_your_network_and_try_again))
                    return@setOnClickListener
                }
            }

            onLanguageClick()
            dismiss()
        }
    }


}
