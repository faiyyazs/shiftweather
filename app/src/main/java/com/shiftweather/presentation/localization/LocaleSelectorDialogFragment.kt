package com.shiftweather.presentation.localization


import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.shiftweather.R
import java.util.*

/**
 * Dialog Fragment to select supported localisation
 * */
class LocaleSelectorDialogFragment : DialogFragment() {


    private val items = SupportedLanguages.getList()
    private var selectedIndex: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        selectedIndex = SupportedLanguages.getList().indexOf(Locale.getDefault())

        val itemTitles = getTitlesArray()
        val builder =
            AlertDialog.Builder(activity!!, R.style.Theme_MaterialComponents_DayNight_Dialog)
        builder.setTitle(getString(R.string.language))
            .setPositiveButton(R.string.Ok) { dialog, which ->
                val activity = activity
                if (activity is OnItemSelectedListener) {
                    if (selectedIndex >= 0 && selectedIndex < items.size) {
                        val item = items[selectedIndex]
                        val listener = activity as OnItemSelectedListener
                        listener.onLocaleItemSelected(
                            this@LocaleSelectorDialogFragment,
                            item,
                            selectedIndex
                        )
                        dismiss()
                    }
                }
            }
            .setSingleChoiceItems(itemTitles, selectedIndex) { dialog, which ->
                selectedIndex = which
            }
        return builder.create()
    }

    private fun getTitlesArray(): Array<String?>? {
        val itemCount = items.size
        val itemTitles = arrayOfNulls<String>(itemCount)
        for (i in 0 until itemCount) {
            itemTitles[i] = items[i].displayName
        }
        return itemTitles
    }

    interface OnItemSelectedListener {
        fun onLocaleItemSelected(fragment: LocaleSelectorDialogFragment, item: Locale, index: Int)
    }

    companion object {
        fun newInstance(): LocaleSelectorDialogFragment {
            return LocaleSelectorDialogFragment()
        }

    }
}