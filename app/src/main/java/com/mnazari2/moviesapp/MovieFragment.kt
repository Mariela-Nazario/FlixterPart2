package com.mnazari2.moviesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.mnazari2.moviesapp.R
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject


private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"


class MovieFragment : Fragment(), OnListFragmentInteractionListener{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progess) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context,1)
        updateAdapter(progressBar, recyclerView)
        return view
    }


    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

        client[
                "https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1",
                params,
                object : JsonHttpResponseHandler()

        {
            /*
             * The onSuccess function gets called when
             * HTTP response status is "200 OK"
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                // The wait for a response is over
                progressBar.hide()


                val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
                val booksRawJSON : String = resultsJSON.toString()

                val gson = Gson()
                val arrayBookType = object : TypeToken<List<Movie>>() {}.type
                val models : List<Movie> = gson.fromJson(booksRawJSON, arrayBookType)

                recyclerView.adapter = MovieRecyclerViewAdapter(models, this@MovieFragment)



                // Look for this in Logcat:
                Log.d("Movie", "response successful")
                Log.d("Movie2",json.toString())
            }
            /*
             * The onFailure function gets called when
             * HTTP response status is "4XX" (eg. 401, 403, 404)
             */
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()
                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("Movie", errorResponse)
                }
            }
        }]


    }



    override fun onItemClick(item: Movie) {
        val intent = Intent(this.context,DetailActivity::class.java)


        val jsonString = Gson().toJson(item)
        intent.putExtra("MovieExtra",jsonString)
        this.context?.startActivity(intent)
    }


}