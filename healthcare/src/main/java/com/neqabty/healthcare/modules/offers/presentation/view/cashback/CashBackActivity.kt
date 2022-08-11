package com.neqabty.healthcare.modules.offers.presentation.view.cashback


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neqabty.healthcare.databinding.ActivityCashBackBinding
import com.neqabty.core.ui.BaseActivity

class CashBackActivity : BaseActivity<ActivityCashBackBinding>(){

    private val mAdapter = PersonAdapter()
    override fun getViewBinding() = ActivityCashBackBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "دعوة 8 أصدقاء للحصول على الخصم")

        binding.peopleRecycler.adapter = mAdapter

        binding.exchangeBtn.setOnClickListener {
            val fm: FragmentManager = supportFragmentManager
            val dialog = InvitationDialog()
            dialog.show(fm, "")
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
        }

        changeFocus()
    }

    private fun changeFocus() {
        binding.code1.requestFocus()
        binding.code1.isCursorVisible = true

        binding.code1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code1.clearFocus()
                    binding.code2.requestFocus()
                    binding.code2.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code1.requestFocus()
                }
            }
        })

        binding.code2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code2.clearFocus()
                    binding.code3.requestFocus()
                    binding.code3.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code2.requestFocus()
                }
            }
        })

        binding.code3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code3.clearFocus()
                    binding.code4.requestFocus()
                    binding.code4.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code3.requestFocus()
                }
            }
        })

        binding.code4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code4.clearFocus()
                    binding.code5.requestFocus()
                    binding.code5.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code4.requestFocus()
                }
            }
        })

        binding.code5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code5.requestFocus()
                    binding.code5.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code5.requestFocus()
                }
            }
        })
    }
}