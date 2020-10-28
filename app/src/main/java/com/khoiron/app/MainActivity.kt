package com.khoiron.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.khoiron.imageviewer.ImageViewer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image.setOnClickListener {
            ImageViewer().show(this,image,R.drawable.hotel)
        }
    }

}