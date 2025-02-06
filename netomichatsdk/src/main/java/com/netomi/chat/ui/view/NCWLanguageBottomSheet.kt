package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R
import com.netomi.chat.model.theme.NCWLanguage
import com.netomi.chat.model.theme.NCWQuickMenuOption
import com.netomi.chat.utils.NCWThemeUtils

class NCWLanguageBottomSheet(
    private val languageMenuOptions: List<NCWLanguage>,
    private val onLanguageClicked: (NCWLanguage) -> Unit,
) : BottomSheetDialogFragment() {

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
        return inflater.inflate(R.layout.layout_bottom_sheet_quick_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerLanguageMenu = view.findViewById<RecyclerView>(R.id.recyclerQuickMenu)
        val ivClose = view.findViewById<ImageView>(R.id.ivClose)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        NCWThemeUtils.setTitleColor(tvTitle)
        tvTitle.text = getString(R.string.languages)
        recyclerLanguageMenu.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val carouselAdapter = NCWLanguageAdapter(languageMenuOptions) {
            if (it != null) {
                onLanguageClicked(it)
                dismiss()
            }
        }
        recyclerLanguageMenu.adapter = carouselAdapter
        ivClose.setOnClickListener {
            dismiss()
        }
    }
}
