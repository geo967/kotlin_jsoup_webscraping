package com.example.jsoup_kotlin.adapter

import android.app.Activity

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.jsoup_kotlin.R
import com.example.jsoup_kotlin.data.MovieListItem
import com.example.jsoup_kotlin.util.inflate
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.item_design.view.*

class RecyclerViewAdapter(private val movieList:List<MovieListItem>,private val activity: Activity): RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        /*  val view=LayoutInflater.from(parent.context).inflate(R.layout.item_design,parent,false)

          return MyHolder(view)*/

        val inflatedView:View=parent.inflate(R.layout.item_design,false)
        return MyHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val itemCard=movieList[position]
        holder.bindSet(itemCard,activity)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }


    class MyHolder(v: View):RecyclerView.ViewHolder(v),View.OnClickListener {

        private var view:View=v
        private var movieList:MovieListItem?=null
        private var act:Activity?=null

        init {

            v.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {

        }

        fun bindSet(movieList: MovieListItem,activity: Activity){
            this.movieList=movieList
            this.act=activity
            view.titleMovie.text=movieList.name
            //Picasso.get().load(movieList.imageUrl).into(view.imgMovie)
            Glide.with(act!!)
                    .load(movieList.imageUrl)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(view.imgMovie)

        }
    }


}