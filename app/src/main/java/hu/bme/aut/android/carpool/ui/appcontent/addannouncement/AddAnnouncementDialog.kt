package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.DialogAddAnnouncementBinding
import hu.bme.aut.android.carpool.domain.model.Announcement
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddAnnouncementDialog :
    RainbowCakeDialogFragment<AddAnnouncementViewState, AddAnnouncementViewModel>() {

    private lateinit var binding: DialogAddAnnouncementBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.dialog_add_announcement

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddAnnouncementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setBackgroundResource(R.drawable.dialog_background)

        setupUserName()
        setupTimePicker()
        setupCancelButton()
        setupSaveButton()
    }

    override fun render(viewState: AddAnnouncementViewState) {
        when (viewState) {
            is Initial -> {}
            is AddAnnouncementSaving -> {}
            is AddAnnouncementSuccess -> {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "fromNewAnnouncement",
                    true
                )
                findNavController().popBackStack()
            }
            is AddAnnouncementFail -> {}
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setupTimePicker() {

        val cal = Calendar.getInstance()

        binding.etTime.setText(SimpleDateFormat("HH:mm").format(cal.time))

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            binding.etTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }

        binding.etTime.setOnClickListener {
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.etTime.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                TimePickerDialog(
                    context,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
        }
    }

    private fun setupCancelButton() {
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            val announcement = Announcement(
                id = "",
                owner = currentUser,
                timeOfDeparture = binding.etTime.text.toString(),
                takenSeatsNumber = if (binding.etTakenSeatsNumber.text.toString().isNotEmpty())
                    binding.etTakenSeatsNumber.text.toString().toInt()
                else 0,
                freeSeatsNumber = if (binding.etAvailableSeatsNumber.text.toString().isNotEmpty())
                    binding.etAvailableSeatsNumber.text.toString().toInt()
                else 0,
                date = Calendar.getInstance().toString()
            )
            viewModel.saveNewAnnouncement(announcement)
        }
    }

    private fun setupUserName(){
        binding.tvUserName.text = currentUser.name ?: currentUser.email
    }
}