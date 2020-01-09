package com.robelseyoum3.safetonet.ui

import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.robelseyoum3.safetonet.R
import com.robelseyoum3.safetonet.di.DaggerAppComponent
import com.robelseyoum3.safetonet.di.NetworkModule
import com.robelseyoum3.safetonet.di.RepositoryModule
import com.robelseyoum3.safetonet.model.Rockets
import com.robelseyoum3.safetonet.util.Constant
import com.robelseyoum3.safetonet.viewmodel.RocketViewModel
import com.robelseyoum3.safetonet.viewmodel.RocketViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var rocketViewModelFactory: RocketViewModelFactory
    lateinit var viewModel: RocketViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //show welcome dialogue box for the first time user
        checkFirstTimeOpened()

        //getting swipeRefreshLayout from xml
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

        //for dependency injection
        getDependencies()

        //creating ViewModel variable for referencing the ViewModel methods
        viewModel = ViewModelProviders.of(this, rocketViewModelFactory).get(RocketViewModel::class.java)

        //fetching rocket data from web
        viewModel.getAllRocketsData()

        //display the rocket data on RecycleView through adaptor
        viewModel.returnAllRocketsResult().observe(this,
            Observer<List<Rockets>> {
                    t ->  allRocketAdapter(t)
            })

        //checking error while fetching and displaying data
        viewModel.returnError().observe(this, Observer {

            if(it == true) {
                error_message_container.visibility = View.VISIBLE
            } else {
                error_message_container.visibility = View.GONE
            }

        })

        //showing the progressbar before the data displayed
        viewModel.returnProgressBarValue().observe(this, Observer {

            if (it == true){
                progress_id_main.visibility = View.VISIBLE
            } else {
                progress_id_main.visibility = View.GONE
            }
        })

        //Here refreshing the page, when user swiping
        swipeRefreshLayout.setOnRefreshListener {

            viewModel.getAllRocketsData()
            Toast.makeText(this, "Swipe called", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout.isRefreshing = false
        }


    }

    private fun checkFirstTimeOpened() {
        val pref =  getSharedPreferences(Constant.PREFS_NAME,0)
        val editor = pref.edit()
        val firstRun = pref.getBoolean(Constant.FIRST_RUN, true)

        if(firstRun){
            editor.putBoolean(Constant.FIRST_RUN, false)
            editor.apply()
            // Log.i("MainActivity", "This is a first Run")
            //here we will display a welcome page
            showWelcomeDialogue()
        }
    }

    private fun showWelcomeDialogue(){

        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle(R.string.dialog_title)
            setMessage(R.string.dialog_message) //hard coded
            setNegativeButton("OK", DialogInterface.OnClickListener{
                    dialog, with -> dialog.dismiss()
            })
            show()
        }
    }

    private fun allRocketAdapter(t: List<Rockets>) {

        val adaptor = RocketAdaptor(t)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = adaptor
    }

    private fun getDependencies() {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .build()
            .inject(this)

    }
}
