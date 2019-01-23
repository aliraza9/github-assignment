package com.example.cnexadmin01.github_sample_test.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cnexadmin01.github_sample_test.R
import com.example.cnexadmin01.github_sample_test.activities.DetailsScreenActivity
import com.example.cnexadmin01.github_sample_test.models.Users
import com.squareup.picasso.Picasso
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import com.squareup.picasso.Callback
import java.lang.Exception


class UserAdapter(val usersList: MutableList<Users>, val context: Context) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_row, parent, false))
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {

        holder?.title.setText(usersList[position].login)
        Picasso.get()
                .load(usersList[position].avatarUrl)
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar)
                .into(holder.avatar)

        Picasso.get().load(usersList[position].avatarUrl)
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

        holder.followersTV.setOnClickListener {
            val intent = Intent(context, DetailsScreenActivity::class.java)
            intent.putExtra("user", usersList[position])
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal val title: TextView
        internal val followersTV: TextView
        internal val avatar: ImageView

        init {
            title = view.findViewById(R.id.textview)
            followersTV = view.findViewById(R.id.followersTV)
            avatar = view.findViewById(R.id.avatar)
        }
    }

}