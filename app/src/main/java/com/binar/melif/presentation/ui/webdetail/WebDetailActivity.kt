package com.binar.melif.presentation.ui.webdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.os.bundleOf
import ccom.binar.melif.presentation.ui.webdetail.ProtectedMediaChromeClient
import com.binar.melif.R
import com.binar.melif.base.BaseViewModelActivity
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.databinding.ActivityWebDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WebDetailActivity : BaseViewModelActivity<ActivityWebDetailBinding, WebDetailViewModel>(
    ActivityWebDetailBinding::inflate
) {
    override val viewModel: WebDetailViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    override fun initView() {
        super.initView()
        setupWebView()
        viewModel.loadUrl()
    }

    override fun observeData() {
        super.observeData()
        viewModel.urlResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    Toast.makeText(this, getString(R.string.text_url_empty), Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Error -> {
                    //do nothing
                }
                is Resource.Loading -> {
                    //do nothing
                }
                is Resource.Success -> it.payload?.let { url -> loadUrl(url) }
            }
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.wvPage.settings.javaScriptEnabled = true
        binding.wvPage.settings.allowContentAccess = true
        binding.wvPage.settings.domStorageEnabled = true
        binding.wvPage.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        binding.wvPage.webChromeClient = ProtectedMediaChromeClient()
    }

    private fun loadUrl(url: String) {
        binding.wvPage.loadUrl(url)
    }

    private fun checkIfWebViewHasBackstack(): Boolean {
        return binding.wvPage.canGoBack()
    }

    override fun onBackPressed() {
        if (checkIfWebViewHasBackstack()) {
            binding.wvPage.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val EXTRAS_TV_SHOW_EPS_URL = "EXTRAS_TV_SHOW_EPS_URL"
        const val EXTRAS_TV_SHOW_NAME = "EXTRAS_TV_SHOW_NAME"
        fun startActivity(context: Context, tvTitle: String, url: String) {
            context.startActivity(Intent(context, WebDetailActivity::class.java).apply {
                putExtra(EXTRAS_TV_SHOW_NAME, tvTitle)
                putExtra(EXTRAS_TV_SHOW_EPS_URL, url)
            })
        }
    }
}