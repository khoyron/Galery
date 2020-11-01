package com.khoiron.imageviewer

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import kotlin.collections.ArrayList
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter (var context: Context, var items: ArrayList<ImageModel>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image,parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        if (data.imageUrl.isNotEmpty()){
            Picasso.get()
                .load(data.imageUrl)
                .centerCrop()
                .fit()
                .into(holder.itemView.ic_image)
        }

        if (data.imageBitmaps!=null){
            holder.itemView.ic_image.setImageBitmap(data.imageBitmaps)
        }
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}