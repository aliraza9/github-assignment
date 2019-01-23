package com.example.cnexadmin01.github_sample_test.adapters

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cnexadmin01.github_sample_test.R
import com.example.cnexadmin01.github_sample_test.models.Followers
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class FollowerAdapter(val followersList: List<Followers>, val context: Context) : RecyclerView.Adapter<FollowerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.follower_list_row, parent, false))
    }

    override fun onBindViewHolder(holder: FollowerAdapter.MyViewHolder, position: Int) {

        holder?.title.setText(followersList[position].login)

        Picasso.get().load(followersList[position].avatarUrl)
                .resize(60,60)
                .into(holder.avatar, object : Callback {

                    override fun onSuccess() {
                        val imageBitmap = (holder.avatar.getDrawable() as BitmapDrawable).bitmap
                        val imageDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), imageBitmap)
                        imageDrawable.isCircular = true
                        imageDrawable.cornerRadius = Math.max(imageBitmap.width, imageBitmap.height) / 2.0f
                        holder.avatar.setImageDrawable(imageDrawable)
                    }

                    override fun onError(e: Exception?) {
                        holder.avatar.setImageResource(R.drawable.ic_avatar)
                    }
                })

    }

    override fun getItemCount(): Int {
        return followersList.size
    }

    inner class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal val title: TextView
        internal val avatar: ImageView

        init {
            title = view.findViewById(R.id.textview)
            avatar = view.findViewById(R.id.avatar)
        }
    }

}