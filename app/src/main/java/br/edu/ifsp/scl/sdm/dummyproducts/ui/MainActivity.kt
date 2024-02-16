package br.edu.ifsp.scl.sdm.dummyproducts.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.dummyproducts.R
import br.edu.ifsp.scl.sdm.dummyproducts.adapter.PhotoAdapter
import br.edu.ifsp.scl.sdm.dummyproducts.adapter.PhotoImageAdapter
import br.edu.ifsp.scl.sdm.dummyproducts.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.dummyproducts.model.Photo
import br.edu.ifsp.scl.sdm.dummyproducts.model.PhotoJSONAPI
import com.android.volley.toolbox.ImageRequest

class MainActivity : AppCompatActivity() {

    private val photosList: MutableList<Photo> = mutableListOf()
    private val photoImageList: MutableList<Bitmap> = mutableListOf()

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter(this, photosList)
    }

    private val photoImageAdapter: PhotoImageAdapter by lazy {
        PhotoImageAdapter(this, photoImageList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.mainTb.apply {
            title = getString(R.string.app_name)
        })

        amb.photosSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val size = photoImageList.size
                    photoImageList.clear()
                    photoImageAdapter.notifyItemRangeRemoved(0, size)

                    retrievePhotoImage(photosList[position].thumbnailUrl)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // NSA
                }

            }
        }

        amb.photoImagesRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = photoImageAdapter
        }

        retrievePhotos()
    }

    private fun retrievePhotoImage(thumbnailUrl: String) {
        PhotoJSONAPI.PhotoRequest(thumbnailUrl, {
            response ->
            photoImageList.add(response)
            photoImageAdapter.notifyItemInserted(photoImageList.lastIndex)
        }, {
            Toast.makeText(this, getString(R.string.request_problem), Toast.LENGTH_SHORT).show()
        }).also {
            PhotoJSONAPI.getInstance(this).addToRequestQueue(it)
        }
    }

    private fun retrievePhotos() {
        PhotoJSONAPI.PhotoListRequest(
            {photoList ->
                photoList.also {
                    photoAdapter.addAll(it)
                }
            }, {
                Toast.makeText(this, getString(R.string.request_problem), Toast.LENGTH_SHORT).show()
            }
        ).also {
            PhotoJSONAPI.getInstance(this).addToRequestQueue(it)
        }
    }
}