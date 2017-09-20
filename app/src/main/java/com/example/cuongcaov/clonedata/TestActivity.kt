package com.example.cuongcaov.clonedata

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.cuongcaov.clonedata.stories.AsyncTaskStatusListener
import com.example.cuongcaov.clonedata.stories.ComicBook
import com.example.cuongcaov.clonedata.stories.MyDatabase
import com.example.cuongcaov.clonedata.stories.UpStoryAsyncTask
import org.jsoup.Jsoup
import java.io.IOException

/**
 * TestActivity.
 *
 * @author CuongCV
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myDatabase = MyDatabase(this)
        myDatabase.open()
        val comics = myDatabase.allData
        val thread = Thread {
            for (i in comics.size - 1 downTo 0) {
                val task = UpStoryAsyncTask(object : AsyncTaskStatusListener {
                    override fun onComplete(id: Long) {
                        Log.i("tag11", "$i: $id---" + comics[i])
                    }

                    override fun onFail(error: String?) {
                        Log.i("tag11", error + comics[i])
                    }

                })
                val comic = ComicBook.getComicBookInfo(comics[i])
                if (comic != null) {
                    task.execute(comic)
                }

            }
        }
        thread.start()
    }

    private fun upAllComic() {
        var i = 1
        var url = ""
        while (true) {
            url = getString(R.string.base_url, i)
            try {
                val document = Jsoup.connect(url).get()
                val elements = document.getElementsByClass("ulListruyen").first().getElementsByClass("newsContent")
                if (elements.size == 0) {
                    break
                }
                for (e in elements) {
                    var storyUrl = e.getElementsByClass("tile").first().attr("href")
                    storyUrl = "http://thichtruyentranh.com" + storyUrl
                    val comicBook = ComicBook.getComicBookInfo(storyUrl)
                    val task = UpStoryAsyncTask(object : AsyncTaskStatusListener {
                        override fun onComplete(id: Long) {
                            Log.i("tag11", id.toString())
                        }

                        override fun onFail(error: String?) {
                            Log.i("tag11", error)
                        }

                    })
                    task.execute(comicBook)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            i++
        }
    }

}