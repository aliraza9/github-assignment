package com.example.cnexadmin01.github_sample_test.activities

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.cnexadmin01.github_sample_test.R
import com.example.cnexadmin01.github_sample_test.adapters.FollowerAdapter
import com.example.cnexadmin01.github_sample_test.interfaces.EventListener
import com.example.cnexadmin01.github_sample_test.models.Followers
import com.example.cnexadmin01.github_sample_test.models.ServerResponse
import com.example.cnexadmin01.github_sample_test.models.Users
import com.example.cnexadmin01.github_sample_test.networkManager.NetworkCallsManager
import com.example.cnexadmin01.github_sample_test.utilities.Utilities
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_screen.*
import java.lang.Exception

class DetailsScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_screen)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        val user = getIntent().extras.get("user") as? Users

        Picasso.get().load(user!!.avatarUrl)
                .into(image_profile)

        follower_list_RV.layoutManager = LinearLayoutManager(this)
        username_TV.setText(user!!.login)
        getFollowers(user.login)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getFollowers(username: String) {
        if (Utilities.isNetworkAvailable(this)) {
            NetworkCallsManager.getNetworkCallsManager().eventListener = object : EventListener {

                override fun success(responseBody: ServerResponse?) {
                }

                override fun success(response: List<Followers>) {
                    Utilities.hideProgressBar()
                    if (response.size > 0) {

                        followersTV.setText("" + response.size)
                        follower_list_RV.adapter = FollowerAdapter(response, applicationContext)
                    } else {
                        follower_list_RV.visibility = View.GONE
                    }
                }

                override fun failure(msg: String?) {
                    Utilities.hideProgressBar()
                    if (msg != null) {
                        Log.d("share list failure: ", msg)
                        Toast.makeText(this@DetailsScreenActivity, msg, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@DetailsScreenActivity, getString(R.string.net_error), Toast.LENGTH_LONG).show()
                    }
                }

            }

            Utilities.showProgressBar(this)
            NetworkCallsManager.getNetworkCallsManager().getfollowers(username)
        } else {
            Utilities.showErrorDialog(this, "No internet availble !!!")
        }
    }
}
