package com.example.memegeneratorusingretrofit

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://meme-api.com/"

class MainActivity : AppCompatActivity() {

    var currentImageUrl:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme() {

        val retrofitbuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitbuilder.Getdata()

        val imageView : ImageView = findViewById(R.id.memeImageView)

        val id1 = findViewById<ProgressBar>(R.id.progressBar)
        id1.visibility=View.VISIBLE

        retrofitData.enqueue(object : Callback<MemeList> {

            override fun onResponse(call: Call<MemeList>, response: Response<MemeList>) {
                val responseBody = response.body()
//
//                val mystringBuilder = StringBuilder()

                val memeBody = responseBody?.memes

                 currentImageUrl = memeBody?.get(0)?.url.toString()

                Glide.with(this@MainActivity).load(currentImageUrl).listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        id1.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        id1.visibility=View.GONE
                        return false
                    }

                }).into(imageView)
            }
            override fun onFailure(call: Call<MemeList>, t: Throwable) {
                Log.d("MainActivity","onfailure:"+t.message)
            }
        })
    }

    fun nextMeme(view: View) {
        loadMeme()
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent(Intent.ACTION_SEND))
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey!Checkout this meme $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share via:")
        startActivity(chooser)
    }
}


