package com.mnazari2.moviesapp
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mnazari2.moviesapp.R.id


class MovieRecyclerViewAdapter(private val movie: List<Movie>,
                               private val mListener: OnListFragmentInteractionListener?)

    : RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>()

{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movies, parent, false)
        return MovieViewHolder(view)
    }
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieOverview: TextView = mView.findViewById<View>(id.movie_director) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movie_image) as ImageView

        override fun toString(): String {
            return mMovieTitle.toString() + " '" + mMovieOverview.text + "'"
        }
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val m = movie[position]

        holder.mItem = m
        holder.mMovieTitle.text = m.title
        holder.mMovieOverview.text = m.overview
        Glide.with(holder.mView)

            .load("https://image.tmdb.org/t/p/w500/"+m.poster_path.toString())
            .centerInside()
            .into(holder.mMovieImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }






        }
    }

    override fun getItemCount(): Int {
        return movie.size
    }

}