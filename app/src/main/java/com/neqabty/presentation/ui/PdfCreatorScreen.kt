package com.neqabty.presentation.ui

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.neqabty.R
import com.tejpratapsingh.pdfcreator.activity.PDFCreatorActivity
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.views.PDFBody
import com.tejpratapsingh.pdfcreator.views.PDFHeaderView
import com.tejpratapsingh.pdfcreator.views.PDFTableView
import com.tejpratapsingh.pdfcreator.views.basic.PDFHorizontalView
import com.tejpratapsingh.pdfcreator.views.basic.PDFImageView
import com.tejpratapsingh.pdfcreator.views.basic.PDFLineSeparatorView
import com.tejpratapsingh.pdfcreator.views.basic.PDFTextView
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class PdfCreatorScreen : PDFCreatorActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        createPDF("neqabty", object : PDFUtil.PDFUtilListener {
            override fun pdfGenerationSuccess(savedPDFFile: File) {
                Toast.makeText(this@PdfCreatorScreen, "PDF Created", Toast.LENGTH_SHORT).show()
            }

            override fun pdfGenerationFailure(exception: Exception) {
                Toast.makeText(this@PdfCreatorScreen, "PDF NOT Created", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun getHeaderView(pageIndex: Int): PDFHeaderView {
        val headerView = PDFHeaderView(applicationContext)

        val horizontalView = PDFHorizontalView(applicationContext)

        val pdfTextView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.HEADER)
        val word = SpannableString("")
        word.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        pdfTextView.text = word
        pdfTextView.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F))
        pdfTextView.view.gravity = Gravity.CENTER_VERTICAL
        pdfTextView.view.setTypeface(pdfTextView.view.typeface, Typeface.BOLD)
        horizontalView.addView(pdfTextView)

        //Logo
        val imageView = PDFImageView(applicationContext)
        val imageLayoutParam = LinearLayout.LayoutParams(120, 90, 0F)
        imageView.setImageScale(ImageView.ScaleType.CENTER_INSIDE)
        imageView.setImageResource(R.drawable.eg)
        imageLayoutParam.setMargins(0, 0, 10, 0)
        imageView.setLayout(imageLayoutParam)
        horizontalView.addView(imageView)
        headerView.addView(horizontalView)

        val lineSeparatorView1 = PDFLineSeparatorView(applicationContext).setBackgroundColor(Color.WHITE)
        headerView.addView(lineSeparatorView1)

        return headerView
    }

    data class Item(val relation: String, val name: String)

    override fun getBodyViews(): PDFBody {
        val pdfBody = PDFBody()

        //Title
        val horizontalTitle = PDFHorizontalView(applicationContext)
        val title = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.HEADER)
        title.view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val titleContent = SpannableString(getString(R.string.titleContent))
        titleContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, titleContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        title.text = titleContent
        title.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 0, 20, 10);
        title.view.gravity = Gravity.CENTER_VERTICAL
        title.view.setTypeface(title.view.typeface, Typeface.SANS_SERIF.style)
        horizontalTitle.addView(title)
        pdfBody.addView(horizontalTitle)

        //Body
        val horizontalBody = PDFHorizontalView(applicationContext)
        val body = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H1)
        val bodyContent = SpannableString(getString(R.string.body_content))
        bodyContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, bodyContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        body.text = bodyContent
        body.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F))
                .setPadding(20, 8, 20, 10)
        body.view.gravity = Gravity.CENTER_VERTICAL
        body.view.setTypeface(body.view.typeface, Typeface.SANS_SERIF.style)
        horizontalBody.addView(body)
        pdfBody.addView(horizontalBody)

        //Engineer Name
        val horizontalEngineerName = PDFHorizontalView(applicationContext)
        val name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H1)
        val nameContent = SpannableString("اسم المهندس: محمد احمد على")
        nameContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, nameContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        name.text = nameContent
        name.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 8, 20, 5)
        name.view.gravity = Gravity.CENTER_VERTICAL
        name.view.setTypeface(name.view.typeface, Typeface.SANS_SERIF.style)
        horizontalEngineerName.addView(name)
        pdfBody.addView(horizontalEngineerName)

        //Engineer Number
        val horizontalEngineerNumber = PDFHorizontalView(applicationContext)
        val number = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H1)
        val numberContent = SpannableString("رقم العضوية: 2549654")
        numberContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, numberContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        number.text = numberContent
        number.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 10, 20, 5);
        number.view.gravity = Gravity.CENTER_VERTICAL
        number.view.setTypeface(number.view.typeface, Typeface.SANS_SERIF.style)
        horizontalEngineerNumber.addView(number)
        pdfBody.addView(horizontalEngineerNumber)

        //Subscription
        val horizontalSubscription = PDFHorizontalView(applicationContext)
        val subscription = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H1)
        val subscriptionContent = SpannableString("الاشتراك الحالي: 2021")
        subscriptionContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, subscriptionContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        subscription.text = subscriptionContent
        subscription.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 10, 20, 5);
        subscription.view.gravity = Gravity.CENTER_VERTICAL
        subscription.view.setTypeface(subscription.view.typeface, Typeface.SANS_SERIF.style)
        horizontalSubscription.addView(subscription)
        pdfBody.addView(horizontalSubscription)

        val arr = ArrayList<Item>()
        arr.add(Item("محمد خالد على زايد", "ابن / ابنه"))
        arr.add(Item("محمد خالد على زايد", "ابن / ابنه"))
        arr.add(Item("محمد خالد على زايد", "ابن / ابنه"))
        arr.add(Item("محمد خالد على زايد", "ابن / ابنه"))

        val tableHeaderTitles = arrayOf("الاسم", "الصلة")

        val lineSeparatorView2 =
                PDFLineSeparatorView(applicationContext).setBackgroundColor(Color.WHITE)
        pdfBody.addView(lineSeparatorView2)
        val pdfTableTitleView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H1)
        pdfTableTitleView.setText("التابعين:")
        pdfBody.addView(pdfTableTitleView)

        val tableHeader = PDFTableView.PDFTableRowView(applicationContext)
        for (s in tableHeaderTitles) {
            val pdfTextView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P)
            pdfTextView.setText(s)
            tableHeader.addToRow(pdfTextView)
        }

        val tableRowView1 = PDFTableView.PDFTableRowView(applicationContext)
        val tableView = PDFTableView(applicationContext, tableHeader, tableRowView1)
        for (item: Item in arr) {
            val tableRowView = PDFTableView.PDFTableRowView(applicationContext)
            val Id = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.SMALL)
            Id.setText(item.relation)
            tableRowView.addToRow(Id)
            val Name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P)
            Name.setText(item.name)
            tableRowView.addToRow(Name)
            tableView.addRow(tableRowView)
        }
        pdfBody.addView(tableView)

        val imageView = PDFImageView(applicationContext)
        val imageLayoutParam = LinearLayout.LayoutParams(200, 140, 0F)
        imageView.setImageScale(ImageView.ScaleType.CENTER_INSIDE)
        imageView.setImageResource(R.drawable.signature)
        imageLayoutParam.setMargins(0, 0, 10, 0)
        imageView.setLayout(imageLayoutParam)
        pdfBody.addView(imageView)

        return pdfBody
    }

    override fun onNextClicked(savedPDFFile: File?) {
//        val pdfUri = Uri.fromFile(savedPDFFile)
//
//        val intentPdfViewer = Intent(this, PdfViewerScreen::class.java)
//        intentPdfViewer.putExtra(PDFViewerActivity.PDF_FILE_URI, pdfUri)
//
//        startActivity(intentPdfViewer)
    }
}