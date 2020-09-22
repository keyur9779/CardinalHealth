package com.view.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.model.AlbumImageModel
import com.test.R
import com.test.databinding.FragmentAlbumDetailsBinding
import com.utils.Constants
import com.view.base.BaseFragment
import com.viewmodel.AlbumDetailsViewModel

/**
 * The album list fragment which is responsible for showing the album details

 */
class AlbumDetailFragment : BaseFragment<AlbumDetailsViewModel, FragmentAlbumDetailsBinding?>() {
    override fun getViewModel(): Class<AlbumDetailsViewModel>? {
        return AlbumDetailsViewModel::class.java
    }

    override val layoutRes: Int
        get() = R.layout.fragment_album_details

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return dataBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments
        if (null != args) {
            viewModel?.url = args.getString(Constants.BUNDLE_KEY_ARTICLE_URL).toString()
            viewModel!!.getImageFromURL()
        }
        viewModel?.albumEntityMutableLiveData?.observe(this, Observer { albumDataEntity: AlbumImageModel ->
            if (albumDataEntity.response) {
                dataBinding!!.imageView.setImageBitmap(albumDataEntity.bitmap)
                dataBinding!!.textContent.visibility = View.GONE
                dataBinding!!.loadingProgress.visibility = View.GONE
            } else {
                dataBinding!!.loadingProgress.visibility = View.GONE
                dataBinding!!.textContent.visibility = View.VISIBLE
                dataBinding!!.textContent.text = albumDataEntity.message
            }

        })

    }
}