package br.edu.ifsp.scl.sdm.dummyproducts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.sdm.dummyproducts.model.Photo

class PhotoAdapter(
    private val context: Context,
    private val photoList: MutableList<Photo>
): ArrayAdapter<Photo>(context, android.R.layout.simple_list_item_1, photoList) {
    private data class PhotoHolder(val photoTitleTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val photoView = convertView ?: LayoutInflater.from(context).inflate(
            android.R.layout.simple_list_item_1, parent, false
        ).apply {
            tag = PhotoHolder(findViewById(android.R.id.text1))
        }

        (photoView.tag as PhotoHolder).photoTitleTv.text = photoList[position].title
        return super.getView(position, convertView, parent)
    }
}