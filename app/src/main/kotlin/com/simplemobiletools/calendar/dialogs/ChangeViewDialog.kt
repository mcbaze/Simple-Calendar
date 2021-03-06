package com.simplemobiletools.calendar.dialogs

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.RadioGroup
import com.simplemobiletools.calendar.R
import com.simplemobiletools.calendar.helpers.Config
import com.simplemobiletools.calendar.helpers.EVENTS_LIST_VIEW
import com.simplemobiletools.calendar.helpers.MONTHLY_VIEW
import com.simplemobiletools.calendar.helpers.YEARLY_VIEW
import com.simplemobiletools.commons.extensions.setupDialogStuff
import kotlinx.android.synthetic.main.dialog_change_views.view.*

class ChangeViewDialog(val activity: Activity, val callback: (newView: Int) -> Unit) : AlertDialog.Builder(activity), RadioGroup.OnCheckedChangeListener {
    val dialog: AlertDialog?

    init {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_change_views, null).dialog_radio_view.apply {
            check(getSavedItem())
            setOnCheckedChangeListener(this@ChangeViewDialog)
        }

        dialog = AlertDialog.Builder(activity)
                .create().apply {
            activity.setupDialogStuff(view, this, R.string.change_view)
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        callback.invoke(getNewView(checkedId))
        dialog?.dismiss()
    }

    fun getNewView(id: Int) = when (id) {
        R.id.dialog_radio_yearly -> YEARLY_VIEW
        R.id.dialog_radio_events_list -> EVENTS_LIST_VIEW
        else -> MONTHLY_VIEW
    }

    fun getSavedItem() = when (Config.newInstance(activity).storedView) {
        YEARLY_VIEW -> R.id.dialog_radio_yearly
        EVENTS_LIST_VIEW -> R.id.dialog_radio_events_list
        else -> R.id.dialog_radio_monthly
    }
}
