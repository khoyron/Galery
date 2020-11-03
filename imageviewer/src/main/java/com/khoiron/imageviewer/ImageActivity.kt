package com.khoiron.imageviewer

import android.os.Bundle
import android.view.View
import android.view.MotionEvent
import java.text.SimpleDateFormat
import android.widget.LinearLayout
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : AppCompatActivity(),OnclickListenerRecyclerView {

    private var xCoOrdinate = 0f
    private var yCoOrdinate = 0f
    private var screenCenterX = 0.0
    private var screenCenterY = 0.0
    private var alpha = 0
    lateinit var view: View
    var data = ArrayList<ImageModel>()
    var dataImage = ImageModel()
    val adapter by lazy { ImageAdapter(this, data) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val parms: LinearLayout.LayoutParams = LinearLayout.LayoutParams(getWidth(), getWidth())
        imageView.setLayoutParams(parms)

        initAnimationLayout()
        initRecyclerView()
        setDataFromIntent()
    }

    private fun setDataFromIntent() {
        try {
            data = intent.extras?.getParcelableArrayList(ImageViewer().DATA_CODE)!!
            adapter.setData(data)
        }catch (e:Exception){
            e.printStackTrace()
        }

        try {
            dataImage = intent.getParcelableExtra(ImageViewer().DATA_IMAGE)
            if (dataImage.imageBitmaps!=null){
                imageView.setImageBitmap(dataImage.imageBitmaps)
            }
            else {
                Picasso.get().load(dataImage.imageUrl).fit().into(imageView)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_image.setLayoutManager(layoutManager)
        rv_image.setNestedScrollingEnabled(false)
        rv_image.setHasFixedSize(true)
        rv_image.setAdapter(adapter)

        adapter.setOnclickListener(this)
        ic_back.setOnClickListener { finish() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAnimationLayout() {
        view = findViewById(R.id.layout)
        view.getBackground().alpha = 255

        /**
         * Calculate max hypo value and center of screen.
         */
        val display = resources.displayMetrics
        screenCenterX = display.widthPixels / 2.toDouble()
        screenCenterY = ((display.heightPixels - getStatusBarHeight()) / 2).toDouble()
        val maxHypo = Math.hypot(screenCenterX, screenCenterY)

        imageView.setOnTouchListener(OnTouchListener { v, event ->
            /**
             * Calculate hypo value of current imageview position according to center
             */
            val centerYPos = imageView.y + (imageView.height / 2).toDouble()
            val centerXPos = imageView.x + (imageView.width / 2).toDouble()
            val a = screenCenterX - centerXPos
            val b = screenCenterY - centerYPos
            val hypo = Math.hypot(a, b)
            /**
             * change alpha of background of layout
             */
            alpha = (hypo * 255).toInt() / maxHypo.toInt()
            if (alpha < 255) view.getBackground().alpha = 255 - alpha
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    xCoOrdinate = imageView.x - event.rawX
                    yCoOrdinate = imageView.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> imageView.animate().x(event.rawX + xCoOrdinate)
                    .y(event.rawY + yCoOrdinate).setDuration(0).start()
                MotionEvent.ACTION_UP -> {
                    if (alpha > 70) {
                        supportFinishAfterTransition()
                        return@OnTouchListener false
                    } else {
                        imageView.animate().x(0f)
                            .y(screenCenterY.toFloat() - imageView.height / 2).setDuration(100)
                            .start()
                        view.getBackground().alpha = 255
                    }
                    return@OnTouchListener false
                }
                else -> return@OnTouchListener false
            }
            true
        })
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun onclick(view: Int, position: Int) {
        tv_title_image.text = data[position].titleImage
        tv_total_and_date.text = "${position} of 10"
        if (data[position].date!=null){
            tv_total_and_date.text  = "${position} of 10, ${SimpleDateFormat("MMM yy").format(data[position].date)} at ${SimpleDateFormat(
                "HH:mm"
            ).format(data[position].date)}"
        }
    }

    fun getWidth(): Int {
        val configuration: Configuration = getResources().getConfiguration()
        return configuration.screenWidthDp //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
    }

}