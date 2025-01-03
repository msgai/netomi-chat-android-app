package com.netomi.chat.ui.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netomi.chat.R

class NCWMediaOptionsBottomSheet(
    private val onCameraClick: () -> Unit,
    private val onGalleryClick: () -> Unit,
    private val onFileClick: () -> Unit,
    private val  supportedExtensions:List<String>
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

        val imageVideoExtensions = listOf("jpg", "jpeg", "png", "mp4", "mov","mp3")
        val fileExtensions = listOf("pdf", "doc", "docx", "txt")

        val hasImageOrVideo = supportedExtensions.any { it in imageVideoExtensions }
        val hasFile = supportedExtensions.any { it in fileExtensions }

        fun setupView(viewId: Int, isVisible: Boolean, action: () -> Unit) {
            view.findViewById<View>(viewId)?.apply {
                visibility = if (isVisible) View.VISIBLE else View.GONE
                setOnClickListener {
                    action()
                    dismiss()
                }
            }
        }

        setupView(R.id.rowCamera, hasImageOrVideo, onCameraClick)
        setupView(R.id.viewCamera, hasImageOrVideo, onCameraClick)
        setupView(R.id.rowGallery, hasImageOrVideo, onGalleryClick)
        setupView(R.id.viewGallery, hasImageOrVideo, onGalleryClick)
        setupView(R.id.rowFile, hasFile, onFileClick)
    }
}
