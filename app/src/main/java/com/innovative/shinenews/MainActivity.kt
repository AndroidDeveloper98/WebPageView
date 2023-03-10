package com.innovative.shinenews

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.innovative.shinenews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy (LazyThreadSafetyMode.NONE){
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mContext = this
        initView()
    }

    private fun initView() {

        val settings = binding.webView.settings
        /**
         * Enable java script in web view
         */
        settings.javaScriptEnabled = true
        /**
         * Enable and setup web view cache
         */
        //settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        //settings.setAppCachePath(cacheDir.path)
        settings.loadsImagesAutomatically = true
        /**
         *  Enable zooming in web view
         */
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        /**
         * Zoom web view text
         */
        settings.textZoom = 100
        /**
         * Enable disable images in web view
         */
        settings.blockNetworkImage = false
        /**
         * Whether the WebView should load image resources
         */
        settings.loadsImagesAutomatically = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true  // api 26
        }
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false
        /**
         *  More optional settings, you can enable it by yourself
         */
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.loadWithOverviewMode = true
        settings.allowContentAccess = true
        settings.setGeolocationEnabled(true)
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowFileAccess = true
        /**
         *  WebView settings
         */
        binding.webView.fitsSystemWindows = true
        /**
        if SDK version is greater of 19 then activate hardware acceleration
        otherwise activate software acceleration
         */
        binding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        /**
         * Set web view client
         */

        //binding.webView.webViewClient = WebViewClient()

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                //binding.animationView.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                //binding.animationView.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url ?: return false
                url.let {
                    view?.loadUrl(it.toString())
                }
                /*try {
                    val chromeIntent = Intent(
                        Intent.ACTION_VIEW,
                        url
                    )
                    chromeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    chromeIntent.setPackage("com.android.chrome")
                    startActivity(chromeIntent)
                } catch (e: ActivityNotFoundException) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url.toString())
                    )
                    startActivity(browserIntent)
                }*/

                //you can do checks here e.g. url.host equals to target one
                //startActivity(Intent(Intent.ACTION_VIEW, url))
                return false
            }
        }

        /**
         * Code block for open hyper link with target="_blank" attributes
         * */
        binding.webView.settings.setSupportMultipleWindows(true)
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                val result = view!!.hitTestResult
                val data = result.extra
                val context = view.context
                /*val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data))
                context.startActivity(browserIntent)*/

                /*try {
                    val chromeIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(data)
                    )
                    chromeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    chromeIntent.setPackage("com.android.chrome")
                    startActivity(chromeIntent)
                } catch (e: ActivityNotFoundException) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(data)
                    )
                    startActivity(browserIntent)
                }*/
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            }
        }
        /**
         * Load url in web view
         *
         */
        binding.webView.loadUrl("https://www.shineindianews.in")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}