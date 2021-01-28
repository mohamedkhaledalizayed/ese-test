package com.neqabty.presentation.ui

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neqabty.R
import com.tejpratapsingh.pdfcreator.activity.PDFViewerActivity

class PdfViewerScreen : PDFViewerActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer_screen)


        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "Pdf Viewer"
            supportActionBar!!.setBackgroundDrawable(
                    ColorDrawable(
                            resources
                                    .getColor(R.color.colorTransparentBlack)
                    )
            )
        }
    }
}