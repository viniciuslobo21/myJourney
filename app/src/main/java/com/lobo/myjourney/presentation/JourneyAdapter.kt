package com.lobo.myjourney.presentation

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lobo.myjourney.R
import com.lobo.myjourney.databinding.JourneyItemBinding
import com.lobo.myjourney.presentation.model.JourneyUiModel

class JourneyAdapter(
    private val list: List<JourneyUiModel>,
    private val onClick: ((JourneyUiModel) -> Unit)? = null
) : RecyclerView.Adapter<JourneyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {
        return JourneyViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.journey_item, parent, false),
            onClick = onClick
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: JourneyViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class JourneyViewHolder internal constructor(
    itemView: View,
    val onClick: ((JourneyUiModel) -> Unit)? = null
) :
    RecyclerView.ViewHolder(itemView) {

    private var binding = JourneyItemBinding.bind(itemView)

    fun bind(item: JourneyUiModel) {
        binding.tvTitle.text = item.title
        binding.tvDescription.text = item.subtitle
        binding.tvNumber.text = item.day.toString()
        (binding.tvNumber.background as GradientDrawable).setColor(
            ContextCompat.getColor(itemView.context, R.color.circle)
        )
        binding.tvDay.text = item.dayInFull

        itemView.setOnClickListener { onClick?.invoke(item) }
    }
}