package com.khoiron.imageviewer

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.util.*

class ImageModel() : Parcelable {
    var imageUrl     = ""
    var imageBitmaps : Bitmap? = null
    var titleImage   = ""
    var date         = Date()

    constructor(parcel: Parcel) : this() {
        imageUrl = parcel.readString().toString()
        imageBitmaps = parcel.readParcelable(Bitmap::class.java.classLoader)
        titleImage = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeParcelable(imageBitmaps, flags)
        parcel.writeString(titleImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageModel> {
        override fun createFromParcel(parcel: Parcel): ImageModel {
            return ImageModel(parcel)
        }

        override fun newArray(size: Int): Array<ImageModel?> {
            return arrayOfNulls(size)
        }
    }
}