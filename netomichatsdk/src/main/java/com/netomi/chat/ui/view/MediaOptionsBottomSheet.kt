package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R

class MediaOptionsBottomSheet(
    private val onCameraClick: () -> Unit,
    private val onGalleryClick: () -> Unit,
    private val onFileClick: () -> Unit
) : BottomSheetDialogFragment() {

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
        return inflater.inflate(R.layout.layout_bottom_sheet_media_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.rowCamera).setOnClickListener {
            onCameraClick.invoke()
            dismiss()
        }

        view.findViewById<View>(R.id.rowGallery).setOnClickListener {
            onGalleryClick.invoke()
            dismiss()
        }

        view.findViewById<View>(R.id.rowFile).setOnClickListener {
            onFileClick.invoke()
            dismiss()
        }
    }
}
