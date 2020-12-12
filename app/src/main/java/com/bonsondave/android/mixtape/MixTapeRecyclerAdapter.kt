package com.bonsondave.android.mixtape

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MixTapeRecyclerAdapter(private val itemsList: List<MixTapeData>,
                             private val listener: OnItemClickListener
                             ) : RecyclerView.Adapter<MixTapeRecyclerAdapter.MixTapeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MixTapeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_item,
            parent, false)

        return MixTapeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MixTapeViewHolder, position: Int) {
        val currentItem = itemsList[position]
        holder.mixTitle.text = currentItem.mixTitle
    }

    override fun getItemCount() = itemsList.size

    inner class MixTapeViewHolder(itemView:View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        val mixTitle: TextView = itemView.findViewById(R.id.tv_mixTapeTitle)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}