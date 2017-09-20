package com.example.cuongcaov.clonedata.stories

import org.jsoup.Jsoup
import java.io.IOException

/**
 * Ultils.
 *
 * @author CuongCV
 */
class Ultils {

    companion object {
        fun getStoryInfo(url: String): StoryToUp? {
            val storyToUp = StoryToUp()
            try {
                val document = Jsoup.connect(url).get()
                val element = document.getElementsByClass("divListtext").first()
                storyToUp.storyName = element.getElementsByClass("spantile2").first().getElementsByTag("h1").first().text()
                val item1 = element.getElementsByClass("ullist_item").first().getElementsByClass("item1")
                val item2 = element.getElementsByClass("ullist_item").first().getElementsByClass("item2")
                storyToUp.author = "Không rõ tác giả."
                storyToUp.type = ""
                for ((index, value) in item1.withIndex()) {
                    when (value.text()) {
                        "Tác giả" -> {
                            storyToUp.author = item2[index].text().replace(":", "").trim()
                        }
                        "Thể loại" -> {
                            item2[index].getElementsByTag("a").forEach {
                                storyToUp.type = storyToUp.type + ", " + it.text()
                            }

                            storyToUp.type = storyToUp.type.replaceFirst(", ", "")
                        }
                        "Tình trạng" -> {
                            if (item2[index].getElementsByTag("span").first().text() == "FULL") {
                                storyToUp.status = 1
                            }
                        }
                        "Số chương" -> {
                            storyToUp.numOfChapters = item2[index].text().replace(": ", "").toInt()
                        }
                    }
                }

                storyToUp.source = url
                return storyToUp
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        fun serialize(array: MutableList<Int>): String {
            var result = "a:" + array.size + ":{"
            for ((index, value) in array.withIndex()) {
                result = result + "i:$index;i:$value;"
            }
            return result + "}"
        }

        fun getTypeId(type: String): Int = when (type) {
            "Action" -> 1
            "Adult" -> 2
            "Adventure" -> 3
            "Comedy" -> 4
            "Cooking" -> 5
            "Drama" -> 6
            "Ecchi" -> 7
            "Fantasy" -> 8
            "Gender Bender" -> 9
            "Harem" -> 10
            "Historical" -> 11
            "Horror" -> 12
            "Josei" -> 13
            "Manhua" -> 14
            "Manhwa" -> 15
            "Martial Arts" -> 16
            "Mature" -> 17
            "Mecha" -> 18
            "Music" -> 19
            "Mystery" -> 20
            "One Shot" -> 21
            "Psychological" -> 22
            "Romance" -> 23
            "School Life" -> 24
            "Sci-fi" -> 25
            "Seinen" -> 26
            "Shoujo" -> 27
            "Shoujo-ai" -> 28
            "Shounen" -> 29
            "Shounen-ai" -> 30
            "Slice of Life" -> 31
            "Soft Yaoi" -> 32
            "Soft Yuri" -> 33
            "Sports" -> 34
            "Supernatural" -> 35
            "Tragedy" -> 36
            "Anime" -> 37
            "Comic" -> 38
            "Doujinshi" -> 39
            "Live action" -> 40
            "Magic" -> 41
            "manga" -> 42
            "Nấu Ăn" -> 43
            "Smut" -> 44
            "Tạp chí truyện tranh" -> 45
            "Trap (Crossdressing)" -> 46
            "Trinh Thám" -> 47
            "Truyện scan" -> 48
            "Video Clip" -> 49
            "VnComic" -> 50
            "Webtoon" -> 51
            "Incest" -> 52
            "Yaoi" -> 53
            "Xuyên Không" -> 54
            else -> 0
        }
    }

}