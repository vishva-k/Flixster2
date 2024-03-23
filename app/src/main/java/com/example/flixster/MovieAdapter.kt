package com.example.flixster

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.parceler.Parcels
import com.example.flixster.Movie


class MovieAdapter(private val context: Context, private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("MovieAdapter", "onCreateViewHolder")
        val movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(movieView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("MovieAdapter", "onBindViewHolder$position")

        val movie = movies[position]

        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: RelativeLayout = itemView.findViewById(R.id.container)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvOverview: TextView = itemView.findViewById(R.id.tvOverview)
        private val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster)

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            val imageURL = if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                movie.backdropPath
            } else {
                movie.posterPath
            }
            Glide.with(context).load(movie.posterPath).into(ivPoster)

            container.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("movie", Parcels.wrap(movie))
                context.startActivity(intent)
            }
        }
    }
}

