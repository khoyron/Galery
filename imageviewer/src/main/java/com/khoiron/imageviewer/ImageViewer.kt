package com.khoiron.imageviewer

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import java.io.ByteArrayOutputStream


class ImageViewer {

    fun show(context: Context, view: View, drawable: Int){
        val bitmap = BitmapFactory.decodeResource(context.getResources(), drawable)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()

        val intent = Intent(context, ImageActivity::class.java)
        intent.putExtra("picture", b);
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            (context as Activity), view, "thumbnailTransition"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, optionsCompat.toBundle())
        }
    }
}