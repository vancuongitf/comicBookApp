package com.example.cuongcaov.clonedata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.example.cuongcaov.clonedata.stories.AsyncTaskStatusListener;
import com.example.cuongcaov.clonedata.stories.ChapterToUp;
import com.example.cuongcaov.clonedata.stories.MyDatabase;
import com.example.cuongcaov.clonedata.stories.StoryToUp;
import com.example.cuongcaov.clonedata.stories.UpChapterAsyncTask;
import com.example.cuongcaov.clonedata.stories.UpStoryAsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText mTvId;
    EditText mTvPosition;
    MyDatabase myDatabase;
    List<StoryToUp> list;
    SharedPreferences preferences;
    private static final String PREFERENCES_NAME = "Preferences";
    private static final String KEY_ID = "keyId";
    private static final String KEY_POSITION = "keyPosition";
    private int mId;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myDatabase = new MyDatabase(this);
//        myDatabase.open();
//        list = myDatabase.getAllData();
//        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
//        mId = preferences.getInt(KEY_ID, 1019);
//        mPosition = preferences.getInt(KEY_POSITION, 80);
//
//        final Button btnUp = (Button) findViewById(R.id.btnUp);
//        mTvId = (EditText) findViewById(R.id.edtId);
//        mTvPosition = (EditText) findViewById(R.id.edtPosition);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                List<ChapterToUp> chapterToUps = new ArrayList<>();
//                for (StoryToUp s : list) {
//                    if (s.getId() < mId) {
//                        continue;
//                    }
//                    chapterToUps = getChapterToUp(s);
//                    for (ChapterToUp chapterToUp : chapterToUps) {
//                        if (s.getId() == mId && chapterToUp.getPosition() < mPosition + 1) {
//                            continue;
//                        }
//                        upChapter(chapterToUp);
//                    }
//                }

            }
        });
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDatabase.close();
    }



    private StoryToUp getStoryInfo(String url) {
        StoryToUp storyToUp = new StoryToUp();
        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementsByClass("divListtext").first();
            storyToUp.setStoryName(element.getElementsByClass("spantile2").first().getElementsByTag("h1").first().text());
            Elements item1 = element.getElementsByClass("ullist_item").first().getElementsByClass("item1");
            Elements item2 = element.getElementsByClass("ullist_item").first().getElementsByClass("item2");
            storyToUp.setSource(url);
            return storyToUp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void upStory(final StoryToUp storyToUp) {
        UpStoryAsyncTask task = new UpStoryAsyncTask(new AsyncTaskStatusListener() {
            @Override
            public void onComplete(long id) {
                myDatabase.insertStory(storyToUp.getSource());
                Log.i("tag11", "thanh cong---" + id + "---" + storyToUp.getStoryName());
            }

            @Override
            public void onFail(String s) {
                Log.i("tag11", s);
            }
        });

    }

    private void upChapter(final ChapterToUp chapterToUp) {
        UpChapterAsyncTask task = new UpChapterAsyncTask(new AsyncTaskStatusListener() {
            @Override
            public void onComplete(long id) {
                myDatabase.insertChapter(chapterToUp.getSource(), id);
                Log.i("tag11", "thanh cong---" + id + "---" + chapterToUp.getSource() + "---" + chapterToUp.getPosition());
                SharedPreferences.Editor mEditor = preferences.edit();
                mEditor.putInt(KEY_ID, chapterToUp.getStoryId());
                mEditor.putInt(KEY_POSITION, chapterToUp.getPosition());
                mEditor.commit();
            }

            @Override
            public void onFail(String error) {
                Log.i("tag11", error);
            }
        });
        task.execute(chapterToUp);
    }

    private List<ChapterToUp> getChapterToUp(StoryToUp storyToUp) {
        List<ChapterToUp> result = new ArrayList<>();
        try {
            Document document = Jsoup.connect(storyToUp.getSource()).get();
            Elements paging = document.getElementsByClass("paging");
            List<String> pageUrls = new ArrayList<>();
            pageUrls.add(storyToUp.getSource());
            if (paging.size() > 0) {
                Elements pageList = paging.first().getElementsByTag("a");
                if (pageList.size() > 0) {
                    String page2Url = "http://thichtruyentranh.com" + pageList.first().attr("href");
                    pageUrls.add(page2Url);
                    int i = 3;
                    while (true) {
                        String pageUrl = page2Url.replace(".2.", "." + i++ + ".");
                        if (checkPage(pageUrl)) {
                            pageUrls.add(pageUrl);
                            continue;
                        }
                        break;
                    }
                }
            }
            for (String pageUrl : pageUrls) {
                List<ChapterToUp> list = getChaptersToUp(pageUrl);
                for (ChapterToUp chapterToUp : list) {
                    chapterToUp.setStoryId(storyToUp.getId());
                    chapterToUp.setPosition(result.size() + 1);
                    result.add(chapterToUp);
                }
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkPage(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("ul_listchap");
            if (elements.size() == 0) {
                return false;
            }
            if (elements.size() == 1) {
                if (elements.first().getElementsByTag("a").size() > 0) {
                    return true;
                }
            } else {
                if (elements.get(1).getElementsByTag("a").size() > 0) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<ChapterToUp> getChaptersToUp(String url) {
        List<ChapterToUp> result = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("ul_listchap");
            if (elements.size() == 0) {
                return null;
            }
            if (elements.size() == 1) {
                Elements list = elements.first().getElementsByTag("a");
                if (list.size() > 0) {
                    for (Element e : list) {
                        ChapterToUp chapterToUp = new ChapterToUp();
                        chapterToUp.setSource("http://thichtruyentranh.com" + e.attr("href"));
                        result.add(chapterToUp);
                    }
                }
            } else {
                Elements list = elements.get(1).getElementsByTag("a");
                if (list.size() > 0) {
                    for (Element e : list) {
                        ChapterToUp chapterToUp = new ChapterToUp();
                        chapterToUp.setSource("http://thichtruyentranh.com" + e.attr("href"));
                        result.add(chapterToUp);
                    }
                }
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
