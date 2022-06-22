package com.neqabty.healthcare.modules.subscribtions.presentation.view


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySubscriptionBinding
import com.neqabty.healthcare.modules.subscribtions.presentation.view.model.Follower
import com.neqabty.healthcare.modules.subscribtions.presentation.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class SubscriptionActivity : BaseActivity<ActivitySubscriptionBinding>() {
    private var REQUEST_CODE = 1000
    private val mAdapter = FollowerAdapter()
    private var listFollower = mutableListOf<Follower>()
    private lateinit var relation: String
    private var imageUrl: Uri? = null
    override fun getViewBinding() = ActivitySubscriptionBinding.inflate(layoutInflater)
    private val subscriptionViewModel: SubscriptionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "تسجيل إشتراك")

//        val bitmap = BitmapFactory.decodeFile("/storage/emulated/0/Download/original.jpg")
//        Log.d("paaaaaath",bitmap.byteCount.toString())


        mAdapter.onItemClickListener = object :
            FollowerAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {

            }
        }

        binding.spRelations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                relation = binding.spRelations.getItemAtPosition(i).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    fun addFollower(view: View) {
        binding.addFollower.visibility = View.GONE
        binding.addFollowerText.visibility = View.GONE
        binding.followerInfo.visibility = View.VISIBLE
    }

    fun addNewFollower(view: View) {

        if (imageUrl == null || REQUEST_CODE != 1001){
            Toast.makeText(this, "من فضلك اختر صورة اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etFullName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الاسم اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etNational.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spRelations.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك اختر درجة القرابة اولا.", Toast.LENGTH_LONG).show()
            return
        }

        val follower = Follower(
            id = 1,
            name = binding.etFullName.text.toString(),
            image = imageUrl!!,
            nationalId = binding.etNational.text.toString(),
            relation = relation
        )

        listFollower.add(follower)
        binding.followersRecycler.adapter = mAdapter
        mAdapter.submitList(listFollower)

        binding.etFullName.setText("")
        binding.etNational.setText("")
        binding.spRelations.setSelection(0)
        binding.followerImage.setImageResource(R.drawable.user)
        binding.followerInfo.visibility = View.GONE
        binding.addFollower.visibility = View.VISIBLE
        binding.addFollowerText.visibility = View.VISIBLE
        binding.followersRecycler.visibility = View.VISIBLE


    }

    fun addFollowerImage(view: View) {
        REQUEST_CODE = 1001
        checkPermissionsAndOpenFilePicker()
    }

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    private fun checkPermissionsAndOpenFilePicker() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(this, permission) !== PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    12
                )
            }
        } else {
            getImage()
        }
    }

    private fun getImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent,
                "Select Picture"), REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            imageUrl = data.data
            Log.e("test", "${File(imageUrl?.path.toString())}")
            Log.e("test2", "${imageUrl?.encodedPath}")
            Log.e("test3", "${imageUrl.toString()}")
            Log.e("test", "${File(imageUrl?.path.toString())}")
            when (REQUEST_CODE) {
                1001 -> {
                    binding.followerImage.setImageURI(imageUrl)
                }
                1002 -> {
                    binding.addImage.setImageResource(R.drawable.success)
                    binding.addImageText.text = "تم إرفاق الصورة بنجاح."
                }
                1003 -> {
                    binding.userPicture.setImageURI(imageUrl)
                }
                else -> {
                    imageUrl = null
                }
            }
        }
    }

    fun addImage(view: View) {
        REQUEST_CODE = 1002
        checkPermissionsAndOpenFilePicker()
    }

    fun changeUserPicture(view: View) {
        REQUEST_CODE = 1003
        checkPermissionsAndOpenFilePicker()
    }

    val txt = "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ABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAH0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUy8/49ZPoP5in0y8/49ZPoP5igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFMvP8Aj1k+g/mKfTLz/j1k+g/mKAF8+P8A56L+dHnx/wDPRfzrNooA0vPj/wCei/nR58f/AD0X86zaKANLz4/+ei/nR58f/PRfzrNooA0vPj/56L+dHnx/89F/Os2igDS8+P8A56L+dHnx/wDPRfzrNooA0vPj/wCei/nR58f/AD0X86zaKANLz4/+ei/nR58f/PRfzrNooA0vPj/56L+dHnx/89F/Os2igDS8+P8A56L+dHnx/wDPRfzrNooA0vPj/wCei/nR58f/AD0X86zaKANLz4/+ei/nR58f/PRfzrNooA0vPj/56L+dHnx/89F/Os2igDS8+P8A56L+dHnx/wDPRfzrNooA0vPj/wCei/nR58f/AD0X86zaKANLz4/+ei/nR58f/PRfzrNooA0vPj/56L+dHnx/89F/Os2igDS8+P8A56L+dHnx/wDPRfzrNooA0vPj/wCei/nR58f/AD0X86zaKANLz4/+ei/nR58f/PRfzrNooA0vPj/56L+dHnx/89F/Os2igDS8+P8A56L+dHnx/wDPRfzrNooA0vPj/wCei/nUd5cJ9mkw6k4Hf3FUaKACiu8/si0/59bf/v0P8KP7ItP+fW3/AO/Q/wAKAODorvP7ItP+fW3/AO/Q/wAKP7ItP+fW3/79D/CgDg6K7z+yLT/n1t/+/Q/wo/si0/59bf8A79D/AAoA4Oiu8/si0/59bf8A79D/AAo/si0/59bf/v0P8KAODorvP7ItP+fW3/79D/Cj+yLT/n1t/wDv0P8ACgDg6K7z+yLT/n1t/wDv0P8ACj+yLT/n1t/+/Q/woA4Oiu8/si0/59bf/v0P8KP7ItP+fW3/AO/Q/wAKAODorvP7ItP+fW3/AO/Q/wAKP7ItP+fW3/79D/CgDg6K7z+yLT/n1t/+/Q/wo/si0/59bf8A79D/AAoA4Oiu8/si0/59bf8A79D/AAo/si0/59bf/v0P8KAODorvP7ItP+fW3/79D/Cj+yLT/n1t/wDv0P8ACgDg6K7z+yLT/n1t/wDv0P8ACj+yLT/n1t/+/Q/woA4Oiu8/si0/59bf/v0P8KP7ItP+fW3/AO/Q/wAKAODorvP7ItP+fW3/AO/Q/wAKP7ItP+fW3/79D/CgDg6K7z+yLT/n1t/+/Q/wo/si0/59bf8A79D/AAoA4Oiu8/si0/59bf8A79D/AAo/si0/59bf/v0P8KAODorvP7ItP+fW3/79D/Cj+yLT/n1t/wDv0P8ACgDg6K7z+yLT/n1t/wDv0P8ACj+yLT/n1t/+/Q/woA4Oiu8/si0/59bf/v0P8KP7ItP+fW3/AO/Q/wAKAODorvP7ItP+fW3/AO/Q/wAKP7ItP+fW3/79D/CgDg6K7z+yLT/n1t/+/Q/wo/si0/59bf8A79D/AAoA4Oiu8/si0/59bf8A79D/AAo/si0/59bf/v0P8KAP/9k="
    fun registerUser(view: View) {
        subscriptionViewModel.addSubscription(
            name = "Amr Mohamed",
            email = "Amr@yahoo.com",
            birthDate = "22/2/1992",
            address = "12 ElRawda st, shebeen",
            job = "Engineer",
            mobile = "0111223345",
            nationalId = "29933004122",
            syndicateId = 12,
            packageId = "213212313",
            referralNumber = "12",
            fronIdImage = txt,
            backIdImage = txt,
            personalImage = txt
        )
    }
}