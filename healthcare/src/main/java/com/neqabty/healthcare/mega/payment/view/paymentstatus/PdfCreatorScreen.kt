package com.neqabty.healthcare.mega.payment.view.paymentstatus

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.mega.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.squareup.picasso.Picasso
import com.tejpratapsingh.pdfcreator.activity.PDFCreatorActivity
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.views.PDFBody
import com.tejpratapsingh.pdfcreator.views.PDFHeaderView
import com.tejpratapsingh.pdfcreator.views.basic.PDFHorizontalView
import com.tejpratapsingh.pdfcreator.views.basic.PDFImageView
import com.tejpratapsingh.pdfcreator.views.basic.PDFLineSeparatorView
import com.tejpratapsingh.pdfcreator.views.basic.PDFTextView
import java.io.File
import java.net.URLConnection

class PdfCreatorScreen : PDFCreatorActivity()  {

    private lateinit var data: PaymentStatusEntity
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
        //other syndicates
        val syndicateLogo = PDFImageView(applicationContext)
        val syndicateLogoLayoutParam = LinearLayout.LayoutParams(120, 90, 0F)
        Picasso.get().load(data.imageUrl ?: "").into(syndicateLogo.view)
        syndicateLogoLayoutParam.setMargins(0, 0, 10, 0)
        syndicateLogo.setLayout(syndicateLogoLayoutParam)

        if (data.entityType != "owner"){
            horizontalView.addView(syndicateLogo)
        }

        //neqabty
        val neqabtyLogo = PDFImageView(applicationContext)
        val imageLayoutParam = LinearLayout.LayoutParams(120, 90, 0F)
        neqabtyLogo.setImageResource(R.drawable.logo)
        imageLayoutParam.setMargins(0, 0, 10, 0)
        neqabtyLogo.setLayout(imageLayoutParam)

        if (data.serviceCategory == "Health"){
            horizontalView.addView(neqabtyLogo)
        }
        headerView.addView(horizontalView)
        val lineSeparatorView1 = PDFLineSeparatorView(applicationContext).setBackgroundColor(Color.WHITE)
        headerView.addView(lineSeparatorView1)

        return headerView
    }

    override fun getBodyViews(): PDFBody {
        val pdfBody = PDFBody()

        //Title
        val horizontalTitle = PDFHorizontalView(applicationContext)
        val title = PDFTextView(applicationContext, 20f, true)
        title.view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val titleContent = SpannableString("الفاتورة")
        titleContent.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            titleContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        title.text = titleContent
        title.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)).setPadding(20, 2, 20, 10);
        horizontalTitle.addView(title)
        pdfBody.addView(horizontalTitle)

        //Body
        val horizontalBody = PDFHorizontalView(applicationContext)
        val body = PDFTextView(applicationContext, 18f, true)
        body.view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val bodyContent = SpannableString("البيانات الشخصية")
        bodyContent.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            bodyContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        body.text = bodyContent
        body.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F))
            .setPadding(20, 30, 20, 28)
        horizontalBody.addView(body)
        pdfBody.addView(horizontalBody)

////////////////////////////////////اسم المهندس/////////////////////

        //Engineer Name
        val horizontalEngineerName = PDFHorizontalView(applicationContext)

        //Engineer Name Value
        val nameValue = PDFTextView(applicationContext, 14f, false)
        val nameContentValue = SpannableString("${data.member_name}")
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
        val nameContent = SpannableString("الاسم : ")
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
        if (data.itemId != null){
            val horizontalEngineerNumber = PDFHorizontalView(applicationContext)

            val numberValue = PDFTextView(applicationContext, 14f, false)
            val numberContentValue = SpannableString("${data.itemId}")
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
        }

        ////////////رقم المنظومة العلاجية/////////////

        val horizontalTreatmentNumber = PDFHorizontalView(applicationContext)

        val treatmentNumber = PDFTextView(applicationContext, 14f, false)
        val treatmentNumberContent = SpannableString(data.mobile ?: "")
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
        val treatmentNumberContentValue = SpannableString("رقم الهاتف : ")
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


        //receipt
        val horizontalReceipt = PDFHorizontalView(applicationContext)
        val receipt = PDFTextView(applicationContext, 18f, true)
        receipt.view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val receiptContent = SpannableString("بيانات الفاتورة")
        receiptContent.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            receiptContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        receipt.text = receiptContent
        receipt.setLayout(LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F))
            .setPadding(20, 30, 20, 28)
        horizontalReceipt.addView(receipt)
        pdfBody.addView(horizontalReceipt)


        //////syndicate

//        val horizontalSyndicate = PDFHorizontalView(applicationContext)
//
//        val syndicate = PDFTextView(applicationContext, 14f, true)
//        val syndicateContent = SpannableString("${data.entity}")
//        syndicateContent.setSpan(
//            ForegroundColorSpan(Color.DKGRAY),
//            0,
//            syndicateContent.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        syndicate.text = syndicateContent
//        syndicate.setLayout(
//            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
//        ).setPadding(2, 5, 50, 4)
//        horizontalSyndicate.addView(syndicate)
//
//
//        val syndicateValue = PDFTextView(applicationContext, 14f, true)
//        val syndicateContentValue = SpannableString(" النقابة : ")
//        syndicateContentValue.setSpan(
//            ForegroundColorSpan(Color.DKGRAY),
//            0,
//            syndicateContentValue.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        syndicateValue.text = syndicateContentValue
//        syndicateValue.setLayout(
//            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
//        ).setPadding(2, 5, 20, 4)
//        horizontalSyndicate.addView(syndicateValue)
//        pdfBody.addView(horizontalSyndicate)
        ////////////////////


        //////service name

        val horizontalService = PDFHorizontalView(applicationContext)

        val service = PDFTextView(applicationContext, 14f, true)
        val serviceContent = SpannableString("${data.serviceAction}")
        serviceContent.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            serviceContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        service.text = serviceContent
        service.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(2, 5, 20, 4)
        horizontalService.addView(service)


        val serviceValue = PDFTextView(applicationContext, 14f, true)
        val serviceContentValue = SpannableString(" اسم الخدمة : ")
        serviceContentValue.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            serviceContentValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        serviceValue.text = serviceContentValue
        serviceValue.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
        ).setPadding(2, 5, 20, 4)
        horizontalService.addView(serviceValue)
        pdfBody.addView(horizontalService)
        ////////////////////


        //////receipt number

        val horizontalReceiptNumber = PDFHorizontalView(applicationContext)

        val receiptNumber = PDFTextView(applicationContext, 14f, true)
        val receiptNumberContent = SpannableString(data.gatewayReferenceId ?: "")
        receiptNumberContent.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            receiptNumberContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        receiptNumber.text = receiptNumberContent
        receiptNumber.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(2, 5, 20, 4)
        horizontalReceiptNumber.addView(receiptNumber)


        val receiptNumberValue = PDFTextView(applicationContext, 14f, true)
        val receiptNumberContentValue = SpannableString(" رقم الفاتورة : ")
        receiptNumberContentValue.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            receiptNumberContentValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        receiptNumberValue.text = receiptNumberContentValue
        receiptNumberValue.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
        ).setPadding(2, 5, 20, 4)
        horizontalReceiptNumber.addView(receiptNumberValue)
        pdfBody.addView(horizontalReceiptNumber)
        ////////////////////


        //////receipt date

        val horizontalReceiptDate = PDFHorizontalView(applicationContext)

        val receiptDate = PDFTextView(applicationContext, 14f, true)
        val receiptDateContent = SpannableString(AppUtils().dateFormat(data.createdAt))
        receiptDateContent.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            receiptDateContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        receiptDate.text = receiptDateContent
        receiptDate.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(2, 5, 18, 4)
        horizontalReceiptDate.addView(receiptDate)


        val receiptDateValue = PDFTextView(applicationContext, 14f, true)
        val receiptDateContentValue = SpannableString(" تاريخ الفاتورة : ")
        receiptDateContentValue.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            receiptDateContentValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        receiptDateValue.text = receiptDateContentValue
        receiptDateValue.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
        ).setPadding(2, 5, 20, 4)
        horizontalReceiptDate.addView(receiptDateValue)
        pdfBody.addView(horizontalReceiptDate)
        ////////////////////

        //////net price

        val horizontalPrice = PDFHorizontalView(applicationContext)

        val price = PDFTextView(applicationContext, 14f, true)
        val priceContent = SpannableString("${data.netAmount}  جنيه")
        priceContent.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            priceContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        price.text = priceContent
        price.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(2, 5, 10, 4)
        horizontalPrice.addView(price)


        val priceValue = PDFTextView(applicationContext, 14f, true)
        val priceContentValue = SpannableString("المبلغ الصافى  : ")
        priceContentValue.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            priceContentValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        priceValue.text = priceContentValue
        priceValue.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
        ).setPadding(2, 5, 20, 4)
        horizontalPrice.addView(priceValue)
        pdfBody.addView(horizontalPrice)
        ////////////////////


        //////fees

        val horizontalFees = PDFHorizontalView(applicationContext)

        val fees = PDFTextView(applicationContext, 14f, true)
        val feesContent = SpannableString("${data.totalFees}  جنيه")
        feesContent.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            feesContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        fees.text = feesContent
        fees.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(2, 5, 12, 4)
        horizontalFees.addView(fees)


        val feesValue = PDFTextView(applicationContext, 14f, true)
        val feesContentValue = SpannableString("الرسوم الكلية  : ")
        feesContentValue.setSpan(
            ForegroundColorSpan(Color.DKGRAY),
            0,
            feesContentValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        feesValue.text = feesContentValue
        feesValue.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
        ).setPadding(2, 5, 20, 4)
        horizontalFees.addView(feesValue)
        pdfBody.addView(horizontalFees)
        ////////////////////


        //////total

        val horizontalTotal = PDFHorizontalView(applicationContext)

        val total = PDFTextView(applicationContext, 14f, true)
        val totalContent = SpannableString("${data.totalAmount}  جنيه")
        totalContent.setSpan(
            ForegroundColorSpan(Color.argb(100, 7, 112, 200)),
            0,
            totalContent.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        total.text = totalContent
        total.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        ).setPadding(2, 5, 45, 4)
        horizontalTotal.addView(total)


        val totalValue = PDFTextView(applicationContext, 14f, true)
        val totalContentValue = SpannableString("الاجمالى  : ")
        totalContentValue.setSpan(
            ForegroundColorSpan(Color.argb(100, 7, 112, 200)),
            0,
            totalContentValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        totalValue.text = totalContentValue
        totalValue.setLayout(
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F)
        ).setPadding(2, 5, 20, 4)
        horizontalTotal.addView(totalValue)
        pdfBody.addView(horizontalTotal)
        ////////////////////


        return pdfBody
    }

    override fun onNextClicked(savedPDFFile: File?) {}

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_pdf_viewer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.menuPrintPdf -> {
                val fileToPrint: File = pdfFile
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
                val fileToShare: File = pdfFile
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