package com.mnazari2.moviesapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import okhttp3.Headers
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val r = findViewById<TextView>(R.id.runtime)
        val b = findViewById<TextView>(R.id.budget)
        val t = findViewById<TextView>(R.id.tagline)
        val a =intent.getStringExtra("MovieExtra")
        var n = Gson().fromJson(a,Movie::class.java)
        var movieId = n.id.toString()

        Log.d("STRING",movieId)
        val client = AsyncHttpClient()

        val params = RequestParams()
        params["api-key"] = API_KEY
        params["movie_id"] = movieId

        client["https://api.themoviedb.org/3/movie/${movieId}?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US", params, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                // Access a JSON array response with `json.jsonArray`
                // Access a JSON object response with `json.jsonObject`

                val budget = json.jsonObject.get("budget").toString()
                val tagline = json.jsonObject.get("tagline").toString()
                val runtime = json.jsonObject.get("runtime").toString()

                  r.text = runtime
                b.text = budget
                t.text = tagline

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                t: Throwable?
            ) {
                t?.message?.let {
                    Log.e("Movie", response)
                }
            }
        }]






    }
}