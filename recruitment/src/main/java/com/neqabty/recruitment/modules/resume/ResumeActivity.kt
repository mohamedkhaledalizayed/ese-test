package com.neqabty.recruitment.modules.resume


import android.content.Intent
import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivityResumeBinding
import com.neqabty.recruitment.modules.address.view.AddressActivity
import com.neqabty.recruitment.modules.courses.CoursesActivity
import com.neqabty.recruitment.modules.experience.ExperienceActivity
import com.neqabty.recruitment.modules.languages.LanguagesActivity
import com.neqabty.recruitment.modules.personalinfo.view.PersonalInfoActivity
import com.neqabty.recruitment.modules.qualifications.view.QualificationsActivity
import com.neqabty.recruitment.modules.skills.SkillsActivity
import com.neqabty.recruitment.modules.specialneeds.view.SpecialNeedsActivity
import com.neqabty.recruitment.modules.work.view.WorkActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeActivity : BaseActivity<ActivityResumeBinding>() {

    override fun getViewBinding() = ActivityResumeBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "السيرة الذاتية")

        binding.personalInfo.setOnClickListener {
            startActivity(Intent(this, PersonalInfoActivity::class.java))
        }
        binding.addressContainer.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }
        binding.workContainer.setOnClickListener {
            startActivity(Intent(this, WorkActivity::class.java))
        }
        binding.qualifications.setOnClickListener {
            startActivity(Intent(this, QualificationsActivity::class.java))
        }
        binding.qualifications.setOnClickListener {
            startActivity(Intent(this, QualificationsActivity::class.java))
        }
        binding.specialNeedsContainer.setOnClickListener {
            startActivity(Intent(this, SpecialNeedsActivity::class.java))
        }
        binding.languages.setOnClickListener {
            startActivity(Intent(this, LanguagesActivity::class.java))
        }
        binding.courses.setOnClickListener {
            startActivity(Intent(this, CoursesActivity::class.java))
        }
        binding.skills.setOnClickListener {
            startActivity(Intent(this, SkillsActivity::class.java))
        }
        binding.experience.setOnClickListener {
            startActivity(Intent(this, ExperienceActivity::class.java))
        }
    }
}