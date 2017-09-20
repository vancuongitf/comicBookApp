package com.example.cuongcaov.clonedata.stories

import android.util.Log
import org.jsoup.Jsoup
import java.io.IOException

/**
 * ComicBook.
 *
 * @author CuongCV
 */
data class ComicBook(var id: Long,
                     var name: String,
                     var author: String,
                     var type: String,
                     var numOfChapters: Int,
                     var status: String,
                     var source: String,
                     var timeUpdate: Long,
                     var readCount: Int,
                     var intro: String) {

    companion object {

        fun getStoryToUp(myDatabase: MyDatabase): MutableList<ComicBook>? {
            myDatabase.open()
            val result = mutableListOf<ComicBook>();
            var i = 0
            var j = 0
            while (true) {
                i++
                try {
                    val document = Jsoup.connect("http://thichtruyentranh.com/truyen-moi-nhat/trang.$i.html").get()
                    val listStory = document.getElementsByClass("ulListruyen").first().getElementsByTag("li")
                    if (listStory.size == 0) {
                        break;
                    }
                    listStory.forEach {
                        val comicBook = getComicBookInfo("http://thichtruyentranh.com" + it.getElementsByTag("a").first().attr("href"))
                        if (comicBook != null) {
                            j++
                            Log.i("tag11", "$i--$j" + comicBook.name)
                            myDatabase.insertStory(comicBook.source)
                            result.add(comicBook)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return result
        }

        fun getComicBookInfo(url: String): ComicBook? {
            val comicBook = ComicBook()
            try {
                val document = Jsoup.connect(url).get()
                val element = document.getElementsByClass("divListtext").first()
                comicBook.name = element.getElementsByClass("spantile2").first().getElementsByTag("h1").first().text()
                comicBook.name = comicBook.name.replace("'","\\'")
                val item1 = element.getElementsByClass("ullist_item").first().getElementsByClass("item1")
                val item2 = element.getElementsByClass("ullist_item").first().getElementsByClass("item2")
                comicBook.author = "Không rõ tác giả."
                comicBook.type = ""
                comicBook.intro = "non-intro"
                for ((index, value) in item1.withIndex()) {
                    when (value.text()) {
                        "Tác giả" -> {
                            comicBook.author = item2[index].text().replace(":", "").trim()
                        }
                        "Thể loại" -> {
                            val types = mutableListOf<Int>()
                            item2[index].getElementsByTag("a").forEach {
                                types.add(Ultils.getTypeId(it.text()))
                            }
                            comicBook.type = Ultils.serialize(types)
                        }
                        "Tình trạng" -> {
                            comicBook.status = item2[index].getElementsByTag("span").first().text()
                        }
                        "Số chương" -> {
                            comicBook.numOfChapters = item2[index].text().replace(": ", "").toInt()
                        }
                    }
                }
                comicBook.source = url
                val elementIntro = document.getElementsByClass("ulpro_line")
                        .first().getElementsByTag("li")
                        .last()
                        .getElementsByTag("p")
                        .get(1)
                comicBook.intro = elementIntro.toString()
                return comicBook
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }

    constructor() : this(0, "", "", "", 0, "", "", 0L, 0, "")

    override fun toString(): String =
            "$id - $name - $author - $type - $numOfChapters - " +
                    "$status - $source - $timeUpdate - $readCount - $intro"
}