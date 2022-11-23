package hu.bme.aut.android.carpool.ui.appcontent.group

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.carpool.databinding.GroupMemberItemBinding


class GroupMemberListAdapter :
    ListAdapter<String, GroupMemberListAdapter.GroupMemberViewHolder>(ItemCallBack) {

    var groupMembers = emptyList<String>()
    lateinit var listener: GroupMemberClickListener


    inner class GroupMemberViewHolder(binding: GroupMemberItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvUserName: TextView = binding.userName
        var groupMember: String? = null
        private val deleteIcon: ImageButton = binding.deleteImageButton

        init {
            deleteIcon.setOnClickListener {
                listener.onDeleteClick(binding.userName.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GroupMemberViewHolder(
        GroupMemberItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    )


    override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        val groupMember = groupMembers[position]

        holder.groupMember = groupMember
        holder.tvUserName.text = groupMember

    }

    override fun getItemCount() = groupMembers.size


    fun addAll(groupMember: List<String>) {
        groupMembers -= groupMembers
        groupMembers += groupMember
        submitList(groupMembers)
    }

    companion object {

        object ItemCallBack : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface GroupMemberClickListener {
        fun onDeleteClick(userName: String)
    }
}