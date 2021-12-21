package com.example.jsoup_kotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.jsoup_kotlin.adapter.RecyclerViewAdapter
import com.example.jsoup_kotlin.data.MovieListItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_design.*
import kotlinx.android.synthetic.main.item_design.view.*
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import java.security.KeyManagementException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrieveWebInfo()
    }

    private fun retrieveWebInfo() {
        thread{

            val doc = Jsoup.connect("https://www.wildaboutmovies.com/2019_movies/").timeout(60000).validateTLSCertificates(false).get()


            val movieGrid=doc.getElementsByClass("post-grid")
            val movieItems=movieGrid[0].getElementsByTag("a")

            val movieList=ArrayList<MovieListItem>()


            for(movieItem in movieItems){
                val movieName=movieItem.text()
                val movieImageUrl=movieItem.getElementsByTag("img")[0].absUrl("data-original").toString()
                movieList.add(MovieListItem(movieName,movieImageUrl))
            }
            //can't access UI elements from the background thread, so

            this.runOnUiThread {
                val recyclerViewAdapter= RecyclerViewAdapter(movieList,this)
                val linearLayoutManager=LinearLayoutManager(this)

                recyclerViewId.layoutManager=linearLayoutManager
                recyclerViewId.adapter=recyclerViewAdapter
            }

        }
    }

/*    private fun socketFactory(): SSLSocketFactory {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            when (e) {
                is RuntimeException, is KeyManagementException -> {
                    throw RuntimeException("Failed to create a SSL socket factory", e)
                }
                else -> throw e
            }
        }
    }*/


}