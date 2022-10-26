package hu.bme.aut.android.carpool.ui.appcontent.addannouncement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.DialogAddAnnouncementBinding

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
    }

    override fun render(viewState: AddAnnouncementViewState) {
        //TODO("Not yet implemented")
    }


}