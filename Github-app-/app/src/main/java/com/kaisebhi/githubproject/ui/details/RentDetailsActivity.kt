package com.kaisebhi.githubproject.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kaisebhi.githubproject.R
import com.kaisebhi.githubproject.adapter.ContributorAdapter
import com.kaisebhi.githubproject.databinding.ActivityRentDetailsBinding
import com.kaisebhi.githubproject.ui.webview.WebViewActivity
import com.kaisebhi.githubproject.utils.ApplicationClass
import com.kaisebhi.githubproject.utils.ResponseHandler

class RentDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityRentDetailsBinding
    lateinit var viewModel: RentDetailsViewModel
    lateinit var adapter: ContributorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rent_details)
        viewModel = ViewModelProvider(
            this,
            RentDetailsViewModelFactory((application as ApplicationClass).recentRepo)
        )[RentDetailsViewModel::class.java]

        val url = intent.getStringExtra("url")

        url?.let {
            viewModel.getDetails(it)
        }

        adapter = ContributorAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setObservers()
    }

    private fun setObservers() {
        viewModel.contLiveData.observe(this) {
            when (it) {
                is ResponseHandler.Success -> {
                    if (!it.responseList.isNullOrEmpty()) {
                        adapter.submitList(it.responseList)
                        adapter.notifyDataSetChanged()
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                }

                is ResponseHandler.Error -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {
                }

            }
        }
        viewModel.detLiveData.observe(this) {
            when (it) {
                is ResponseHandler.Success -> {
                    it.responseList?.let {
                        binding.itemName.text = it.name
                        binding.desc.text = it.description

                        it.owner?.let {
                            Glide.with(this).load(it.avatar_url).into(binding.itemImg)
                        }

                        it.contributors_url?.let { it1 ->
                            viewModel.getContributors(
                                getString(R.string.bearer_token),
                                it1
                            )
                        }

                        binding.btnViewRepo.setOnClickListener { view ->
                            startActivity(Intent(this,WebViewActivity::class.java).putExtra("url",it.url))
                        }
                    }
                }

                is ResponseHandler.Error -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }

                else -> {
                }
            }
        }
    }
}