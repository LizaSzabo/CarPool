package hu.bme.aut.android.carpool.ui.appcontent.actualities

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.carpool.databinding.AnnouncementItemBinding
import hu.bme.aut.android.carpool.domain.model.Announcement

class ActualitiesListAdapter :
    ListAdapter<Announcement, ActualitiesListAdapter.AnnouncementViewHolder>(ItemCallBack) {

    private var announcements = emptyList<Announcement>()

    var itemClickListener: AnnouncementClickListener? = null

    interface AnnouncementClickListener {
        fun onItemClickListener(announcement: Announcement)
    }

    inner class AnnouncementViewHolder(binding: AnnouncementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvOwnerName: TextView = binding.ownerName
        var tvTimeOfDeparture: TextView = binding.timeOfDeparture
        val tvTakenSeatsNumber: TextView = binding.takenSeatsNumber
        var tvFreeSeatsNumber: TextView = binding.freeSeatsNumber
        var announcement: Announcement? = null

        init{
            itemView.setOnClickListener{
                Log.i("announcementId", announcement.toString())
                announcement?.let { itemClickListener?.onItemClickListener(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AnnouncementViewHolder(
        AnnouncementItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    )


    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        val announcement = announcements[position]

        holder.announcement = announcement
        holder.tvOwnerName.text = announcement.owner?.name ?: announcement.owner?.email
        holder.tvTimeOfDeparture.text = announcement.timeOfDeparture
        holder.tvTakenSeatsNumber.text = announcement.takenSeatsNumber.toString()
        holder.tvFreeSeatsNumber.text = announcement.freeSeatsNumber.toString()

    }

    override fun getItemCount(): Int {
        return announcements.size
    }

    fun addAllAnnouncements(announcement: List<Announcement>) {
        announcements -= announcements
        announcements += announcement
        submitList(announcements)
    }

    companion object {

        object ItemCallBack : DiffUtil.ItemCallback<Announcement>() {
            override fun areItemsTheSame(oldItem: Announcement, newItem: Announcement): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Announcement, newItem: Announcement): Boolean {
                return oldItem == newItem
            }
        }
    }
}