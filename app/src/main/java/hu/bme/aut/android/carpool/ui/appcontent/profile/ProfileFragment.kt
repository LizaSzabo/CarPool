package hu.bme.aut.android.carpool.ui.appcontent.profile

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.CarPoolApplication.Companion.currentUser
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.FragmentProfileBinding
import hu.bme.aut.android.carpool.domain.model.Announcement

@AndroidEntryPoint
class ProfileFragment : RainbowCakeFragment<ProfileViewState, ProfileViewModel>() {

    private lateinit var binding: FragmentProfileBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_profile


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                openSomeActivityForResult()
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.

            }
        }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    if (Build.VERSION.SDK_INT < 28) {
                        @Suppress("DEPRECATION") val bitmap = MediaStore.Images.Media.getBitmap(
                            context?.contentResolver,
                            selectedImageUri
                        )
                        binding.profileImage.setImageBitmap(bitmap)
                        bitmap?.let { viewModel.saveImageToUser(it) }
                    } else {
                        val source = context?.contentResolver?.let {
                            ImageDecoder.createSource(
                                it,
                                selectedImageUri
                            )
                        }
                        val bitmap = source?.let { ImageDecoder.decodeBitmap(it) }
                        binding.profileImage.setImageBitmap(bitmap)
                        bitmap?.let { viewModel.saveImageToUser(it) }
                    }
                }
            }
        }

    override fun render(viewState: ProfileViewState) {
        when (viewState) {
            is InitialProfileState -> {
                binding.userNameEditText.visibility = View.INVISIBLE
                binding.userNameText.visibility = View.VISIBLE
                binding.profileImage.isEnabled = false
                binding.loading.isVisible = false
                binding.cardView.isVisible = false
            }
            is ProfileEditingState -> {
                binding.userNameEditText.visibility = View.VISIBLE
                binding.userNameText.visibility = View.INVISIBLE
                binding.profileImage.isEnabled = true
                binding.loading.isVisible = false
                binding.userNameEditText.setText("add User Name")
                if (currentUser.name?.isEmpty() == true) {
                    binding.userNameEditText.setText("add User Name")
                } else {
                    binding.userNameEditText.setText(currentUser.name)
                }
                binding.cardView.isVisible = true
            }
            is ProfileSuccessfullyEditedState -> {
                binding.userNameEditText.visibility = View.INVISIBLE
                binding.userNameText.visibility = View.VISIBLE
                binding.profileImage.isEnabled = false
                binding.profileImage.setImageBitmap(viewState.user.bitmap)
                binding.loading.isVisible = false
                if (viewState.announcement != null)
                    setupAnnouncementDetails(viewState.announcement, viewState.titleText)
            }
            is ImageSavingError -> {
                Toast.makeText(activity, viewState.errorMessage, Toast.LENGTH_LONG).show()
                binding.loading.isVisible = false
                binding.cardView.isVisible = true
            }
            is ImageSavingSuccess -> {
                Toast.makeText(activity, viewState.successMessage, Toast.LENGTH_LONG).show()
                binding.loading.isVisible = false
                binding.cardView.isVisible = true
            }
            is LoadingError -> {
                Toast.makeText(activity, viewState.errorMessage, Toast.LENGTH_LONG).show()
                binding.loading.isVisible = false
                binding.cardView.isVisible = false
            }
            LoadingState -> {
                binding.loading.isVisible = true
                binding.cardView.isVisible = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadUserDetails()

        binding.editProfileButton.setOnClickListener {
            if (!binding.editProfileButton.isChecked) {
                viewModel.switchToSavedMode()
            } else {
                viewModel.switchToEditMode()
            }
        }

        binding.profileImage.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireActivity(),
                    READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    openSomeActivityForResult()
                }
                // shouldShowRequestPermissionRationale() -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.

                // }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermissionLauncher.launch(READ_MEDIA_IMAGES)
                    } else {
                        requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
                    }
                }
            }
        }
    }

    private fun openSomeActivityForResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private fun setupAnnouncementDetails(announcement: Announcement, titleText: String) {
        binding.cardView.isVisible = true
        binding.ownerName.text = announcement.owner?.name
        binding.takenSeatsNumber.text = announcement.takenSeatsNumber.toString()
        binding.freeSeatsNumber.text = announcement.freeSeatsNumber.toString()
        binding.timeOfDeparture.text = announcement.timeOfDeparture.toString()
        binding.announcementLabel.text = titleText
    }

}