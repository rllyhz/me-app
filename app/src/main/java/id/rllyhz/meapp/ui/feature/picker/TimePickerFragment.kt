package id.rllyhz.meapp.ui.feature.picker

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var listener: DialogTimeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DialogTimeListener?
    }

    override fun onDetach() {
        super.onDetach()

        if (listener != null)
            listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Calendar.getInstance().let {
            val hour = it.get(Calendar.HOUR_OF_DAY)
            val minute = it.get(Calendar.MINUTE)
            val isFormat24Hour = true

            TimePickerDialog(activity, this, hour, minute, isFormat24Hour)
        }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        listener?.onDialogTimeSet(tag, hourOfDay, minute)
    }

    interface DialogTimeListener {
        fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int)
    }
}