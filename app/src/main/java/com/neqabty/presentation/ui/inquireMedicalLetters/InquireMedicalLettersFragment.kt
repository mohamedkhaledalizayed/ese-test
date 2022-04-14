package com.neqabty.presentation.ui.inquireMedicalLetters

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.InquireMedicalLettersFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.medical_letters_fragment.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class InquireMedicalLettersFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<InquireMedicalLettersFragmentBinding>()

    private val inquireMedicalLettersViewModel: InquireMedicalLettersViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var uri: Uri
    lateinit var file: File
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.inquire_medical_letters_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inquireMedicalLettersViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        inquireMedicalLettersViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        initializeViews()
    }

    private fun handleViewState(state: InquireMedicalLettersViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        llContent.visibility = if (state.isLoading) View.INVISIBLE else View.VISIBLE

        if (state.medicalLetterItemUI?.id.isNullOrEmpty()){
            binding.tvNoDataFound.visibility = View.VISIBLE
            binding.llResult.visibility = View.GONE
        } else {
            binding.tvNoDataFound.visibility = View.GONE
            binding.llResult.visibility = View.VISIBLE
            binding.clResult.letter = state.medicalLetterItemUI!!
        }
        state.medicalLetterItemUI?.report?.let {
            savePDF(Base64.getDecoder().decode(it))
            binding.clResult.bDownloadReport.visibility = View.VISIBLE
        }
    }

    fun initializeViews() {
        binding.bInquire.setOnClickListener{
            hideKeyboard()
            if(binding.edApprovalNumber.text.isNullOrBlank())
                showAlert(message = getString(R.string.invalid_data))
            else {
                llSuperProgressbar.visibility = View.VISIBLE
                binding.tvNoDataFound.visibility = View.GONE
                inquireMedicalLettersViewModel.getMedicalLetterByID(sharedPref.mobile, sharedPref.user, binding.edApprovalNumber.text.toString())
            }
        }
        binding.clResult.bDownloadReport.setOnClickListener {
            openPDF()
        }
    }
//region

    private fun savePDF(bytes: ByteArray){
        try {
            val fileName = "استمارة تحويل.pdf"
            val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path
            file = File("$storageDir/$fileName")
            var os = FileOutputStream(file)
            os.write(bytes)
            os.close()

            uri = FileProvider.getUriForFile(
                requireContext(),
                "com.neqabty.fileprovider",
                file
            )
        } catch (e: ActivityNotFoundException) {
            println("*************************NO PDF**************************")
        }
    }

    private fun openPDF(){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val resInfoList: List<ResolveInfo> = requireActivity().getPackageManager()
            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            requireActivity().grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
        startActivity(intent)
    }
// endregion

    fun navController() = findNavController()
}
