package com.khoiron.imageviewer

import android.os.Build
import android.view.View
import android.app.Activity
import android.content.Intent
import android.content.Context
import androidx.core.app.ActivityOptionsCompat

class ImageViewer {

    fun show(context: Context, view: View, drawable: Int){

        val intent = Intent(context, ImageActivity::class.java)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            (context as Activity), view, "thumbnailTransition"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, optionsCompat.toBundle())
        }
    }
}