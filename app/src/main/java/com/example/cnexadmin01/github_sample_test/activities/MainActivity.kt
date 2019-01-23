package com.example.cnexadmin01.github_sample_test.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.cnexadmin01.github_sample_test.R
import com.example.cnexadmin01.github_sample_test.adapters.UserAdapter
import com.example.cnexadmin01.github_sample_test.interfaces.EventListener
import com.example.cnexadmin01.github_sample_test.models.Followers
import com.example.cnexadmin01.github_sample_test.models.ServerResponse
import com.example.cnexadmin01.github_sample_test.networkManager.NetworkCallsManager
import com.example.cnexadmin01.github_sample_test.utilities.Utilities
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user_list_RV.layoutManager = LinearLayoutManager(this)

        btnSearch.setOnClickListener {
            if (chechEmailValidation()) {
                searchEmail(emailET.text.toString())
            }
        }
    }

    private fun searchEmail(email: String) {

        if (Utilities.isNetworkAvailable(this@MainActivity)) {
            NetworkCallsManager.getNetworkCallsManager().eventListener = object : EventListener {

                override fun success(responseBody: ServerResponse?) {
                    btnSearch.isClickable = true
                    if (responseBody!!.totalCount > 0) {
                        user_list_RV.adapter = UserAdapter(responseBody!!.users, applicationContext)
                    } else {
                        Utilities.showErrorDialog(this@MainActivity, "No user found !!!")
                    }
                    Utilities.hideProgressBar()
                }

                override fun success(response: List<Followers>) {
                }

                override fun failure(msg: String?) {
                    btnSearch.isClickable = true
                    Utilities.hideProgressBar()
                    if (msg != null) {
                        Log.d("share list failure: ", msg)
                        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MainActivity, getString(R.string.net_error), Toast.LENGTH_LONG).show()
                    }
                }

            }

            Utilities.showProgressBar(this@MainActivity)
            NetworkCallsManager.getNetworkCallsManager().searchUserOnGitHub(email)
        } else {
            Utilities.showErrorDialog(this@MainActivity, "No internet availble !!!")
        }
    }


    fun chechEmailValidation(): Boolean {
        if (emailET.text.toString().isEmpty()) {
            Utilities.makeToast(this, getString(R.string.empty_name))
            return false;
        } else {
            btnSearch.isClickable = false
            Utilities.hideKeyboard(this)
            return true;
        }
    }
}
