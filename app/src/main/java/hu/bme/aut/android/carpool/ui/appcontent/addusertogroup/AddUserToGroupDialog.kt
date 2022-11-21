package hu.bme.aut.android.carpool.ui.appcontent.addusertogroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.DialogAddUserToGroupBinding

@AndroidEntryPoint
class AddUserToGroupDialog :
    RainbowCakeDialogFragment<AddUserToGroupViewState, AddUserToGroupViewModel>() {

    private lateinit var binding: DialogAddUserToGroupBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.dialog_add_user_to_group

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddUserToGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCancelButton()
    }

    override fun render(viewState: AddUserToGroupViewState) {
        // TODO("Not yet implemented")
    }

    private fun setupCancelButton() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

}