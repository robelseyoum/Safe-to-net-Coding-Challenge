package com.robelseyoum3.safetonet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.robelseyoum3.safetonet.R
import com.robelseyoum3.safetonet.di.DaggerAppComponent
import com.robelseyoum3.safetonet.di.NetworkModule
import com.robelseyoum3.safetonet.di.RepositoryModule
import com.robelseyoum3.safetonet.model.Rockets
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

        //here check if this field is true or false
        //true == welcome page else


        //getting swipeRefreshLayput from xml
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

        //for dependency injection
        getDependencies()

        //creating viewmodel variable for referencing the viewmodel methods
        viewModel = ViewModelProviders.of(this, rocketViewModelFactory).get(RocketViewModel::class.java)

        //fetching rocket data from web
        viewModel.getAllRocketsData()

        //desplay the rocket data on RecycleView through adaptor
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

        //Here refreshing the page, when user swipe
        swipeRefreshLayout.setOnRefreshListener {

            viewModel.getAllRocketsData()

            Toast.makeText(this, "Swipe called", Toast.LENGTH_SHORT).show()

            swipeRefreshLayout.isRefreshing = false

        }


    }

    private fun allRocketAdapter(t: List<Rockets>) {

        var adaptor = RocketAdaptor(t)
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
