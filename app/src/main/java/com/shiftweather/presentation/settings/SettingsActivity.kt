package com.shiftweather.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.shiftweather.R
import com.shiftweather.core.presentation.ui.BaseActivity
import com.shiftweather.databinding.ActivitySettingsBinding
import com.shiftweather.presentation.localization.LocaleSelectorDialogFragment
import com.shiftweather.presentation.localization.SupportedLanguages
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SettingsActivity : BaseActivity<ActivitySettingsBinding, SettingsViewModel>(),
    LocaleSelectorDialogFragment.OnItemSelectedListener {

    override fun onLocaleItemSelected(
        fragment: LocaleSelectorDialogFragment,
        item: Locale,
        index: Int
    ) {
        changeLocale(item)
    }

    private val activityModel: SettingsViewModel by viewModel()


    override fun getLayoutId(): Int {
        return R.layout.activity_settings
    }

    override fun getViewModel(): SettingsViewModel {
        return activityModel
    }


    override fun setUp(savedInstanceState: Bundle?) {
        getViewDataBinding().toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        getViewDataBinding().selectedLocale.text = Locale.getDefault().displayName
        getViewDataBinding().languageContainer.setOnClickListener {
            LocaleSelectorDialogFragment.newInstance()
                .show(supportFragmentManager, "LocaleSelectorDialogFragment")
        }
        SupportedLanguages.getList()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

}