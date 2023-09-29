package com.neqabty.presentation.ui.medicalArchiveAddFile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalArchiveAddFileFragmentBinding
import com.neqabty.databinding.MedicalArchiveFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.ArchiveUploadCategoryUI
import com.neqabty.presentation.entities.ArchiveUploadItemUI
import com.neqabty.presentation.entities.ProviderTypeUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class MedicalArchiveAddFileFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalArchiveAddFileFragmentBinding>()

    private val medicalArchiveViewModel: MedicalArchiveAddFileViewModel by viewModels()

    var categoriesResultList: MutableList<ArchiveUploadCategoryUI>? = mutableListOf()
    var selectedCategoryID: Int = 0
    private val PICK_FILE_REQUEST_CODE = 101
    private val ACCEPTED_MIME_TYPES = arrayOf("application/pdf", "image/jpeg", "image/png")

    private var selectedFile: File? = null

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.medical_archive_add_file_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        medicalArchiveViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalArchiveViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })
        medicalArchiveViewModel.getCategories()
    }

    private fun initializeViews() {
        renderCategories()
        binding.bAddFile.setOnClickListener{
            openFilePicker()
        }
        binding.bSubmit.setOnClickListener{
            if(selectedFile == null){
                Toast.makeText(requireActivity(), getString(R.string.invalid_data), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else {
                medicalArchiveViewModel.uploadFile(sharedPref.user, selectedFile!!.name, "", selectedCategoryID.toString(), 1, selectedFile)
            }
        }
    }

    private fun handleViewState(state: MedicalArchiveAddFileViewState) {
        sharedPref.user = "94"
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.archiveUploadAcknowledgementUI != null) {
            showAlert(getString(R.string.medical_archive_file_added)){
                navController().navigateUp()
            }
        }

        if (state.archiveUploadCategoryUIList != null) {
            binding.llHolder.visibility = View.VISIBLE
            categoriesResultList = state.archiveUploadCategoryUIList!!.toMutableList()
            state.archiveUploadCategoryUIList = null
            initializeViews()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // This allows users to pick all file types
        intent.putExtra(Intent.EXTRA_MIME_TYPES, ACCEPTED_MIME_TYPES)
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val fileType = requireActivity().contentResolver.getType(uri)
                if (fileType in ACCEPTED_MIME_TYPES) {
                    selectedFile = getFileFromUri(uri)
                    binding.clAttachment.visibility = View.VISIBLE
                    binding.tvName.text = selectedFile?.name
                    binding.ivClose.setOnClickListener {
                        selectedFile = null
                        binding.clAttachment.visibility = View.INVISIBLE
                        binding.bAddFile.visibility = View.VISIBLE
                    }
                    binding.bAddFile.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(requireActivity(), getString(R.string.file_unsupported), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        val contentResolver = requireActivity().contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val inputStream = contentResolver.openInputStream(uri)
                inputStream?.use { input ->
                    val outputFile = File(requireActivity().cacheDir, fileName)
                    FileOutputStream(outputFile).use { output ->
                        input.copyTo(output)
                        return outputFile
                    }
                }
            }
        }
        return null
    }


    //region
    fun renderCategories() {
        binding.spCategoryType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, categoriesResultList!!)
        binding.spCategoryType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCategoryID = (parent.getItemAtPosition(position) as ArchiveUploadCategoryUI).id
            }
        }
        binding.spCategoryType.setSelection(0)
    }
// endregion

    fun navController() = findNavController()
}
