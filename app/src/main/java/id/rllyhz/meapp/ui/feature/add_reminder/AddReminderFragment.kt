package id.rllyhz.meapp.ui.feature.add_reminder

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.databinding.FragmentAddReminderBinding
import id.rllyhz.meapp.ui.adding_item.AddItemActivity
import id.rllyhz.meapp.ui.feature.picker.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddReminderFragment : Fragment() {
    private var _binding: FragmentAddReminderBinding? = null
    private val binding: FragmentAddReminderBinding get() = _binding!!

    private var titleTextWatcher: TextWatcher? = null
    private var descriptionTextWatcher: TextWatcher? = null

    private var activity: AddItemActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        titleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(newTitle: CharSequence?, p1: Int, p2: Int, p3: Int) {
                activity?.sharedViewModel?.setTitleItem(newTitle.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        descriptionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(newDescription: CharSequence?, p1: Int, p2: Int, p3: Int) {
                activity?.sharedViewModel?.setDescriptionItem(newDescription.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        titleTextWatcher = null
        descriptionTextWatcher = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AddItemActivity
    }

    override fun onDetach() {
        super.onDetach()
        if (activity != null)
            activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            etTitleAddingReminder.addTextChangedListener(titleTextWatcher)
            etDescriptionAddingReminder.addTextChangedListener(descriptionTextWatcher)

            btnGoBackAddingReminder.setOnClickListener {
                requireActivity().finish()
            }

            btnSaveAddingReminder.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.successfully_scheduled_reminder_message),
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }

            llPickATimeAddingReminder.setOnClickListener {
                TimePickerFragment().show(requireActivity().supportFragmentManager, TIME_PICKER_TAG)
            }

            activity?.sharedViewModel?.apply {
                etTitleAddingReminder.setText(titleText.value)
                etDescriptionAddingReminder.setText(descriptionText.value)

                selectedTime.observe(requireActivity()) { date ->
                    if (date != null) {
                        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                        tvPickATimeLabelAddingReminder.text = dateFormat.format(date)
                    }
                }
            }
        }
    }

    companion object {
        private const val TIME_PICKER_TAG = "TimePickerOnce"
    }
}