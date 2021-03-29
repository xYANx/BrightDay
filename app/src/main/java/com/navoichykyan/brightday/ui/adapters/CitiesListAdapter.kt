package com.navoichykyan.brightday.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.navoichykyan.brightday.R
import com.navoichykyan.brightday.data.db.CityEntity
import kotlinx.android.synthetic.main.item_city.view.*

class CitiesListAdapter(private val touchClickListener: TouchClickListener) :
    RecyclerView.Adapter<CitiesListAdapter.CitiesItemViewHolder>() {
    private val itemList = mutableListOf<CityEntity>()

    interface TouchClickListener {
        fun onClickDelete(cityEntity: CityEntity)
        fun onClickTouch(cityEntity: CityEntity)
    }

    class CitiesItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cityEntity: CityEntity, touchClickListener: TouchClickListener) {
            with(cityEntity) {
                itemView.apply {
                    textCityList.text = city
                    removeButton.setOnClickListener{touchClickListener.onClickDelete(cityEntity)}
                }
            }
            itemView.setOnClickListener {
                touchClickListener.onClickTouch(cityEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitiesItemViewHolder(
            itemView = parent.run {
                LayoutInflater.from(context).inflate(
                    R.layout.item_city,
                    this,
                    false
                )
            })

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: CitiesItemViewHolder, position: Int) {
        holder.bind(itemList[position], touchClickListener)
    }

    fun updateItemList(itemListIn: List<CityEntity>) {
        itemList.apply {
            clear()
            addAll(itemListIn)
        }
        notifyDataSetChanged()
    }
}