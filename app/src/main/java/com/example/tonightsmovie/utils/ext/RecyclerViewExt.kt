package com.example.tonightsmovie.utils.ext

import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.addClickListener(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(bindingAdapterPosition, itemViewType)
    }
    return this
}