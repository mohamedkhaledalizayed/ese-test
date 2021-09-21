package com.neqabty.presentation.ui.common

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import com.neqabty.R
import com.neqabty.presentation.entities.MedicalRenewalUI
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
import java.net.URLConnection
import java.util.*


class PdfCreatorScreen : PDFCreatorActivity()  {

    private lateinit var data: MedicalRenewalUI
    private lateinit var pdfFile: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getParcelableExtra("data")!!
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

        val pdfTextView = PDFTextView(applicationContext, 20f, false)
        val word = SpannableString("")
        word.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                word.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        pdfTextView.text = word
        pdfTextView.setLayout(
                LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1F
                )
        )
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

    data class Item(val relation: String, val name: String, val number: String)

    override fun getBodyViews(): PDFBody {
        val pdfBody = PDFBody()

        //Title
        val horizontalTitle = PDFHorizontalView(applicationContext)
        val title = PDFTextView(applicationContext, 20f, true)
        title.view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val titleContent = SpannableString(getString(R.string.titleContent))
        titleContent.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                titleContent.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        title.text = titleContent
        title.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)).setPadding(20, 2, 20, 5);
        horizontalTitle.addView(title)
        pdfBody.addView(horizontalTitle)

        //Body
        val horizontalBody = PDFHorizontalView(applicationContext)
        val body = PDFTextView(applicationContext, 14f, false)
        body.view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val bodyContent = SpannableString(getString(R.string.body_content))
        bodyContent.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                bodyContent.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        body.text = bodyContent
        body.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F))
                .setPadding(20, 7, 20, 8)
        horizontalBody.addView(body)
        pdfBody.addView(horizontalBody)

////////////////////////////////////اسم المهندس/////////////////////

        //Engineer Name
        val horizontalEngineerName = PDFHorizontalView(applicationContext)

        //Engineer Name Value
        val nameValue = PDFTextView(applicationContext, 14f, false)
        val nameContentValue = SpannableString(data.contact?.name)
        nameContentValue.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                nameContentValue.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        nameValue.text = nameContentValue
        nameValue.setLayout(
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(20, 5, 0, 4)
        horizontalEngineerName.addView(nameValue)

        val name = PDFTextView(applicationContext, 14f, true)
        val nameContent = SpannableString("اسم المهندس :")
        nameContent.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                nameContent.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        name.text = nameContent
        name.setLayout(
                LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        0F
                )
        ).setPadding(2, 5, 20, 4)
        horizontalEngineerName.addView(name)
        pdfBody.addView(horizontalEngineerName)



        //////////////////////////
        //Engineer Number
        val horizontalEngineerNumber = PDFHorizontalView(applicationContext)

        val numberValue = PDFTextView(applicationContext, 14f, false)
        val numberContentValue = SpannableString(data.oldRefId)
        numberContentValue.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                numberContentValue.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        numberValue.text = numberContentValue
        numberValue.setLayout(
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(20, 5, 0, 4)
        horizontalEngineerNumber.addView(numberValue)


        val number = PDFTextView(applicationContext, 14f, true)
        val numberContent = SpannableString("رقم العضوية : ")
        numberContent.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                numberContent.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        number.text = numberContent
        number.setLayout(
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
        ).setPadding(2, 5, 20, 4)
        horizontalEngineerNumber.addView(number)
        pdfBody.addView(horizontalEngineerNumber)

        ////////////رقم المنظومة العلاجية/////////////

        val horizontalTreatmentNumber = PDFHorizontalView(applicationContext)

        val treatmentNumber = PDFTextView(applicationContext, 14f, false)
        val treatmentNumberContent = SpannableString(data.contact?.benID)
        treatmentNumberContent.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                treatmentNumberContent.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        treatmentNumber.text = treatmentNumberContent
        treatmentNumber.setLayout(
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(20, 5, 0, 4)
        horizontalTreatmentNumber.addView(treatmentNumber)


        val treatmentNumberValue = PDFTextView(applicationContext, 14f, true)
        val treatmentNumberContentValue = SpannableString("رقم المنظومة العلاجية : ")
        treatmentNumberContentValue.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                treatmentNumberContentValue.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        treatmentNumberValue.text = treatmentNumberContentValue
        treatmentNumberValue.setLayout(
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
        ).setPadding(2, 5, 20, 4)
        horizontalTreatmentNumber.addView(treatmentNumberValue)
        pdfBody.addView(horizontalTreatmentNumber)

//////////////////////////////////////الاشتراك الحالى//////////////////////

        //Subscription
        val horizontalSubscription = PDFHorizontalView(applicationContext)

        val subscriptionValue = PDFTextView(applicationContext, 14f, false)
        val subscriptionContentValue = SpannableString("2021")
        subscriptionContentValue.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                subscriptionContentValue.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        subscriptionValue.text = subscriptionContentValue
        subscriptionValue.setLayout(
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(20, 5, 0, 4)
        horizontalSubscription.addView(subscriptionValue)


        val subscription = PDFTextView(applicationContext, 14f, true)
        val subscriptionContent = SpannableString("الاشتراك الحالي : ")
        subscriptionContent.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                subscriptionContent.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        subscription.text = subscriptionContent
        subscription.setLayout(
                LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        0F
                )
        ).setPadding(2, 5, 20, 4)
        horizontalSubscription.addView(subscription)
        pdfBody.addView(horizontalSubscription)


        //////////////////////////////////
        //Followers
        val horizontalFollowers = PDFHorizontalView(applicationContext)
        val followers = PDFTextView(applicationContext, 14f, true)
        val followersContent = SpannableString("التابعين (1) :")
        followersContent.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                followersContent.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        followers.text = followersContent
        followers.setLayout(
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        ).setPadding(20, 5, 20, 5)
        horizontalFollowers.addView(followers)
        pdfBody.addView(horizontalFollowers)


        val tableHeaderTitles = arrayOf("رقم المنظومة العلاجية", "درجة القرابة", "الاسم")
        val lineSeparatorView2 = PDFLineSeparatorView(applicationContext).setBackgroundColor(Color.WHITE)
        pdfBody.addView(lineSeparatorView2)

        val tableHeader = PDFTableView.PDFTableRowView(applicationContext)
        for (s in tableHeaderTitles) {
            val pdfTextView = PDFTextView(applicationContext, 13f, true)
            pdfTextView.setText(s)
            tableHeader.addToRow(pdfTextView)
        }

        var items: MutableList<Item> = mutableListOf()
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))
        items.add(Item("الزوج/الزوجة", "ناهد عبد العظيم", "2797772"))

        val tableRowView1 = PDFTableView.PDFTableRowView(applicationContext)
        val tableView = PDFTableView(applicationContext, tableHeader, tableRowView1)

        for (item: MedicalRenewalUI.FollowerItem in data.followers!!) {
            val tableRowView = PDFTableView.PDFTableRowView(applicationContext)
            val benID = PDFTextView(applicationContext, 14f, false)
            benID.setText(item.id.toString())
            tableRowView.addToRow(benID)
            val Name = PDFTextView(applicationContext, 14f, false)
            Name.setText(item.relationTypeName)
            tableRowView.addToRow(Name)
            val Id = PDFTextView(applicationContext, 14f, false)
            Id.setText(item.name)
            tableRowView.addToRow(Id)
            tableRowView.view.gravity = Gravity.CENTER_HORIZONTAL

            tableView.addRow(tableRowView)
        }
        pdfBody.addView(tableView)

        val imageView = PDFImageView(applicationContext)
        val imageLayoutParam = LinearLayout.LayoutParams(150, 100, 0F)
        imageView.setImageScale(ImageView.ScaleType.CENTER_INSIDE)
        imageView.setImageResource(R.drawable.signature)
        imageLayoutParam.setMargins(0, 0, 10, 0)
        imageView.setLayout(imageLayoutParam)
        pdfBody.addView(imageView)

        return pdfBody
    }

    override fun onNextClicked(savedPDFFile: File?) {}

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
                } else {
                    val printAttributeBuilder = PrintAttributes.Builder()
                    printAttributeBuilder.setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    printAttributeBuilder.setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    PDFUtil.printPdf(
                            this@PdfCreatorScreen,
                            fileToPrint,
                            printAttributeBuilder.build()
                    )
                }
            }
            R.id.menuSharePdf -> {
                val fileToShare: File? = pdfFile
                if (fileToShare == null || !fileToShare.exists()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                } else {
                    val intentShareFile = Intent(Intent.ACTION_SEND)
                    val apkURI: Uri = FileProvider.getUriForFile(
                            applicationContext,
                            applicationContext
                                    .packageName + ".fileprovider", fileToShare
                    )
                    intentShareFile.setDataAndType(
                            apkURI, URLConnection.guessContentTypeFromName(
                            fileToShare.name
                    )
                    )
                    intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intentShareFile.putExtra(
                            Intent.EXTRA_STREAM,
                            Uri.parse("file://" + fileToShare.absolutePath)
                    )
                    startActivity(Intent.createChooser(intentShareFile, "Share File"))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}