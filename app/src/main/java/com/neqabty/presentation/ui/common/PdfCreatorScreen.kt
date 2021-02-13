package com.neqabty.presentation.ui.common

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import com.neqabty.R
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.tejpratapsingh.pdfcreator.activity.PDFCreatorActivity
import com.tejpratapsingh.pdfcreator.activity.PDFViewerActivity
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.views.PDFBody
import com.tejpratapsingh.pdfcreator.views.PDFHeaderView
import com.tejpratapsingh.pdfcreator.views.PDFTableView
import com.tejpratapsingh.pdfcreator.views.basic.PDFHorizontalView
import com.tejpratapsingh.pdfcreator.views.basic.PDFImageView
import com.tejpratapsingh.pdfcreator.views.basic.PDFLineSeparatorView
import com.tejpratapsingh.pdfcreator.views.basic.PDFTextView
import java.io.File
import java.net.URLConnection
import java.util.*
import kotlin.collections.ArrayList

class PdfCreatorScreen : PDFCreatorActivity()  {

    private lateinit var data: MedicalRenewalUI
    private lateinit var pdfFile: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getParcelableExtra("data")
        Log.e("TEST", "${data.contact?.name}")
        findViewById<Button>(R.id.buttonSendEmail).visibility = View.GONE

        createPDF("neqabty", object : PDFUtil.PDFUtilListener {
            override fun pdfGenerationSuccess(savedPDFFile: File) {
                pdfFile = savedPDFFile
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
        val title = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H2)
        title.view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val titleContent = SpannableString(getString(R.string.titleContent))
        titleContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, titleContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        title.text = titleContent
        title.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 0, 20, 5);
        title.view.gravity = Gravity.CENTER_VERTICAL
        title.view.setTypeface(title.view.typeface, Typeface.SANS_SERIF.style)
        horizontalTitle.addView(title)
        pdfBody.addView(horizontalTitle)

        //Body
        val horizontalBody = PDFHorizontalView(applicationContext)
        val body = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
        val bodyContent = SpannableString(getString(R.string.body_content))
        bodyContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, bodyContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        body.text = bodyContent
        body.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F))
                .setPadding(20, 5, 20, 10)
        body.view.gravity = Gravity.CENTER
        body.view.setTypeface(body.view.typeface, Typeface.SANS_SERIF.style)
        horizontalBody.addView(body)
        pdfBody.addView(horizontalBody)

        //Engineer Name
        val horizontalEngineerName = PDFHorizontalView(applicationContext)
        val name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
        val nameContent = SpannableString("اسم المهندس: ${data.contact?.name}")
        nameContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, nameContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        name.text = nameContent
        name.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 5, 20, 5)
        name.view.gravity = Gravity.CENTER_VERTICAL
        name.view.setTypeface(name.view.typeface, Typeface.SANS_SERIF.style)
        horizontalEngineerName.addView(name)
        pdfBody.addView(horizontalEngineerName)

        //Engineer Number
        val horizontalEngineerNumber = PDFHorizontalView(applicationContext)
        val number = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
        val numberContent = SpannableString("رقم العضوية: ${data.oldRefId}")
        numberContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, numberContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        number.text = numberContent
        number.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 5, 20, 5);
        number.view.gravity = Gravity.CENTER_VERTICAL
        number.view.setTypeface(number.view.typeface, Typeface.SANS_SERIF.style)
        horizontalEngineerNumber.addView(number)
        pdfBody.addView(horizontalEngineerNumber)

        //Subscription
        val horizontalSubscription = PDFHorizontalView(applicationContext)
        val subscription = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
        val subscriptionContent = SpannableString("الاشتراك الحالي: 2021")
        subscriptionContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, subscriptionContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        subscription.text = subscriptionContent
        subscription.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 5, 20, 5);
        subscription.view.gravity = Gravity.CENTER_VERTICAL
        subscription.view.setTypeface(subscription.view.typeface, Typeface.SANS_SERIF.style)
        horizontalSubscription.addView(subscription)
        pdfBody.addView(horizontalSubscription)

        //BenID
        val horizontalBenID = PDFHorizontalView(applicationContext)
        val benID = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
        val benIDContent = SpannableString("رقم المنظومة العلاجية: ${data.contact?.benID}")
        benIDContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, subscriptionContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        benID.text = benIDContent
        benID.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 5, 20, 5);
        benID.view.gravity = Gravity.CENTER_VERTICAL
        benID.view.setTypeface(benID.view.typeface, Typeface.SANS_SERIF.style)
        horizontalBenID.addView(benID)
        pdfBody.addView(horizontalBenID)

        //Followers
        val horizontalFollowers = PDFHorizontalView(applicationContext)
        val followers = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
        val followersContent = SpannableString("التابعين:")
        followersContent.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, followersContent.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        followers.text = followersContent
        followers.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 5, 20, 10);
        followers.view.gravity = Gravity.CENTER_VERTICAL
        followers.view.setTypeface(followers.view.typeface, Typeface.SANS_SERIF.style)
        horizontalFollowers.addView(followers)
        pdfBody.addView(horizontalFollowers)


        val tableHeaderTitles = arrayOf("رقم المنظومة العلاجية", "درجة القرابة","الاسم")

        val lineSeparatorView2 =
                PDFLineSeparatorView(applicationContext).setBackgroundColor(Color.WHITE)
        pdfBody.addView(lineSeparatorView2)

        val tableHeader = PDFTableView.PDFTableRowView(applicationContext)
        for (s in tableHeaderTitles) {
            val pdfTextView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
            pdfTextView.setText(s)
            tableHeader.addToRow(pdfTextView)
        }
        val tableRowView1 = PDFTableView.PDFTableRowView(applicationContext)
        val tableView = PDFTableView(applicationContext, tableHeader, tableRowView1)

        for (item: MedicalRenewalUI.FollowerItem in data.followers!!) {
            val tableRowView = PDFTableView.PDFTableRowView(applicationContext)
            val benID = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
            benID.setText(item.id.toString())
            tableRowView.addToRow(benID)
            val Name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
            Name.setText(item.relationTypeName)
            tableRowView.addToRow(Name)
            val Id = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
            Id.setText(item.name)
            tableRowView.addToRow(Id)
            tableRowView.view.gravity = Gravity.CENTER_HORIZONTAL

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pdf_viewer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.menuPrintPdf -> {
                val fileToPrint: File? = pdfFile
                if (fileToPrint == null || !fileToPrint.exists()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }else{
                    val printAttributeBuilder = PrintAttributes.Builder()
                    printAttributeBuilder.setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    printAttributeBuilder.setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    PDFUtil.printPdf(this@PdfCreatorScreen, fileToPrint, printAttributeBuilder.build())
                }
            }
            R.id.menuSharePdf -> {
                val fileToShare: File? = pdfFile
                if (fileToShare == null || !fileToShare.exists()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }else{
                    val intentShareFile = Intent(Intent.ACTION_SEND)
                    val apkURI: Uri = FileProvider.getUriForFile(
                            applicationContext,
                            applicationContext
                                    .packageName + ".fileprovider", fileToShare)
                    intentShareFile.setDataAndType(apkURI, URLConnection.guessContentTypeFromName(fileToShare.name))
                    intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intentShareFile.putExtra(Intent.EXTRA_STREAM,
                            Uri.parse("file://" + fileToShare.absolutePath))
                    startActivity(Intent.createChooser(intentShareFile, "Share File"))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}