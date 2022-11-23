package hu.bme.aut.android.carpool.ui.appcontent.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.R
import hu.bme.aut.android.carpool.databinding.FragmentGroupBinding


@AndroidEntryPoint
class GroupFragment : RainbowCakeFragment<GroupViewState, GroupViewModel>() {

    private lateinit var binding: FragmentGroupBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_group
    private lateinit var groupMemberListAdapter: GroupMemberListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupAddButton()
        viewModel.loadUsersList()
    }

    override fun render(viewState: GroupViewState) {
        when (viewState) {
            is GroupInitialState -> {
                binding.loading.isVisible = false
            }
            is GroupLoading -> {
                binding.loading.isVisible = true
            }
            is GroupLoaded -> {
                binding.loading.isVisible = false
                groupMemberListAdapter.addAll(viewState.members)
            }
        }
    }

    private fun setupRecyclerView() {
        groupMemberListAdapter = GroupMemberListAdapter()
        binding.rvGroupMembers.layoutManager = LinearLayoutManager(context)
        binding.rvGroupMembers.adapter = groupMemberListAdapter
    }

    private fun setupAddButton() {
        binding.fab.setOnClickListener {
            findNavController().navigate(GroupFragmentDirections.actionGroupFragmentToAddUserToGroupDialog())
        }
    }
}