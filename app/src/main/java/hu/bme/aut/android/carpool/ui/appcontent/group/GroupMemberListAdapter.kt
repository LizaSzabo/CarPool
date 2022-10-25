package hu.bme.aut.android.carpool.ui.appcontent.group

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.carpool.databinding.GroupMemberItemBinding
import hu.bme.aut.android.carpool.model.User

class GroupMemberListAdapter :
    ListAdapter<User, GroupMemberListAdapter.GroupMemberViewHolder>(ItemCallBack) {

    var groupMembers = emptyList<User>()

    inner class GroupMemberViewHolder(binding: GroupMemberItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvUserName: TextView = binding.userName
        var groupMember: User? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GroupMemberViewHolder(
        GroupMemberItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    )


    override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        val groupMember = groupMembers[position]

        holder.groupMember = groupMember
        holder.tvUserName.text = groupMember.name

    }

    override fun getItemCount() = groupMembers.size


    fun add(groupMember: List<User>) {
        groupMembers -= groupMembers
        groupMembers += groupMember
        submitList(groupMembers)
    }

    companion object {

        object ItemCallBack : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}