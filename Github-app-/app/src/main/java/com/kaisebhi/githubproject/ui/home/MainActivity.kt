package com.kaisebhi.githubproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kaisebhi.githubproject.R
import com.kaisebhi.githubproject.adapter.GithubRepoAdapter
import com.kaisebhi.githubproject.databinding.ActivityMainBinding
import com.kaisebhi.githubproject.utils.ApplicationClass
import com.kaisebhi.githubproject.utils.NetworkUtils
import com.kaisebhi.githubproject.utils.ResponseHandler

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity.kt"
    lateinit var viewModel: MainViewModel
    lateinit var adapter: GithubRepoAdapter

    //    lateinit var mainRepo: MainRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val app = application as ApplicationClass
//        mainRepo = MainRepository(this, RetrofitClient.getRetrofitClient().create(ApiInterface::class.java), DatabaseRoom.getDatabase(this))
        viewModel = ViewModelProvider(this, MainViewModelFactory(app.mainRepo))[MainViewModel::class.java]

        adapter = GithubRepoAdapter(this@MainActivity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        viewModel.searchRepo(getString(R.string.bearer_token), "q")
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(NetworkUtils.getNetworkState(this@MainActivity)){
                    val snackBar = Snackbar.make(binding.root, "Please wait", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                    viewModel.searchRepo(getString(R.string.bearer_token), "$query in:name")

                }else{
                    Toast.makeText(this@MainActivity, "You are offline", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setObservers() {
        viewModel.searchResults.observe(this) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }
    }

}