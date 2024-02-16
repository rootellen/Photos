package br.edu.ifsp.scl.sdm.dummyproducts.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.dummyproducts.databinding.TilePhotoImageBinding

class PhotoImageAdapter(
    private val context: Context,
    val photoImageList: MutableList<Bitmap>
): RecyclerView.Adapter<PhotoImageAdapter.PhotoImageViewHolder>() {
    inner class PhotoImageViewHolder(tilePhotoImageBinding: TilePhotoImageBinding)
        : RecyclerView.ViewHolder(tilePhotoImageBinding.photoIv) {
        val photoIv: ImageView = tilePhotoImageBinding.photoIv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoImageViewHolder =
        PhotoImageViewHolder(TilePhotoImageBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun getItemCount(): Int = photoImageList.size

    override fun onBindViewHolder(holder: PhotoImageViewHolder, position: Int) =
        holder.photoIv.setImageBitmap(photoImageList[position])
}