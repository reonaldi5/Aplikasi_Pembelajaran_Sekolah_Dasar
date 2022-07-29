package com.learning.learningbydoing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.learningbydoing.databinding.ItemMaterialBinding
import com.learning.learningbydoing.model.Material

class MaterialsAdapter : RecyclerView.Adapter<MaterialsAdapter.ViewHolder>(), Filterable {

    private var listener: ((Material, Int) -> Unit)? = null
    var materials = mutableListOf<Material>()
        set(value) {
            field = value
            materialsFilter = value
            notifyDataSetChanged()
        }

    private var materialsFilter = mutableListOf<Material>()

    private val filters = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList= mutableListOf<Material>()
            val filterPattern = constraint.toString().trim().toLowerCase()
            if (filterPattern.isEmpty()){
                filteredList = materials
            } else {
                for (material in materials){
                    val title = material.titleMaterial?.trim()?.toLowerCase()

                    if (title?.contains(filterPattern) == true){
                        filteredList.add(material)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            materialsFilter = results?.values as MutableList<Material>
            notifyDataSetChanged()
        }

    }

    class ViewHolder(private val materialBinding: ItemMaterialBinding)

        :RecyclerView.ViewHolder(materialBinding.root){
        fun bindItem(material: Material, listener: ((Material, Int) -> Unit)?) {
            Glide.with(itemView)
                    .load(material.thumbnailMaterial)
                    .placeholder(android.R.color.darker_gray)
                    .into(materialBinding.ivMaterial)

            materialBinding.tvTitleMaterial.text = material.titleMaterial

            listener?.let {
                itemView.setOnClickListener {
                    it(material, adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialsAdapter.ViewHolder {
        val binding = ItemMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaterialsAdapter.ViewHolder, position: Int) {
        holder.bindItem(materialsFilter[position], listener)
    }

    override fun getItemCount(): Int = materialsFilter.size
    override fun getFilter(): Filter = filters

    fun onClick(listener: ((Material, Int) -> Unit)){
        this.listener = listener
    }

}