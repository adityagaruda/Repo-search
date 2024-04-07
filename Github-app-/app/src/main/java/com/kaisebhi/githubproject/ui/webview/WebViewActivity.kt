package com.kaisebhi.githubproject.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.kaisebhi.githubproject.R
import com.kaisebhi.githubproject.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)

        val url = intent.getStringExtra("url")
        binding.webview.apply {
            webViewClient = WebViewClient()
            url?.let { loadUrl(it) }
        }
    }
}