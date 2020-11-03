package com.khoiron.imageviewer

import android.os.Build
import android.view.View
import android.app.Activity
import android.content.Intent
import android.content.Context
import androidx.core.app.ActivityOptionsCompat

class ImageViewer {

    val DATA_IMAGE = "image"
    val DATA_CODE = "data"

    fun show(context: Context, view: View, data:ArrayList<ImageModel>){

        val intent = Intent(context, ImageActivity::class.java)
        intent.putExtra(DATA_CODE,data)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            (context as Activity), view, "thumbnailTransition"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, optionsCompat.toBundle())
        }
    }
}