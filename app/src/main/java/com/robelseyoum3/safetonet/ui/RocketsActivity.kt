package com.robelseyoum3.safetonet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_rockets.*
import javax.inject.Inject

class RocketsActivity : AppCompatActivity() {

    @Inject
    lateinit var rocketViewModelFactory: RocketViewModelFactory
    private lateinit var viewModel: RocketViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rockets)

        checkFirstTimeOpened()

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

        getDependencies()

        viewModel = ViewModelProviders.of(this, rocketViewModelFactory).get(RocketViewModel::class.java)

        viewModel.getRockets()

        viewModel.returnAllRocketsResult().observe(this,
            Observer<List<Rockets>> {
                    rocket ->  allRocketAdapter(rocket)
            })

        viewModel.returnError().observe(this, Observer {

            if(it == true) {
                error_message_container.visibility = View.VISIBLE
            } else {
                error_message_container.visibility = View.GONE
            }

        })


        viewModel.returnProgressBarValue().observe(this, Observer {

            if (it == true){
                progress_id_main.visibility = View.VISIBLE
            } else {
                progress_id_main.visibility = View.GONE
            }
        })

        swipeRefreshLayout.setOnRefreshListener {

            viewModel.getRockets()
            Toast.makeText(this, "Swipe called", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout.isRefreshing = false
        }

        rgFilters.setOnCheckedChangeListener { _, checkedId ->
            viewModel.getRockets(checkedId == R.id.rbActiveRockets)
        }
        btnRetry.setOnClickListener {
            viewModel.getRockets(rbActiveRockets.isChecked)
        }

    }

    private fun checkFirstTimeOpened() {
        val pref =  getSharedPreferences(Constant.PREFS_NAME,0)
        val editor = pref.edit()
        val firstRun = pref.getBoolean(Constant.FIRST_RUN, true)

        if(firstRun){
            editor.putBoolean(Constant.FIRST_RUN, false)
            editor.apply()
            showWelcomeDialogue()
        }
    }

    private fun showWelcomeDialogue(){

        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle(R.string.dialog_title)
            setMessage(R.string.dialog_message)
            setNegativeButton("OK") { dialog, _ -> dialog.dismiss()
            }
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
