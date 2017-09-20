package com.example.cuongcaov.clonedata.stories;

import android.os.AsyncTask;

import com.example.cuongcaov.clonedata.draft.APIService;
import com.example.cuongcaov.clonedata.draft.Result;
import com.example.cuongcaov.clonedata.draft.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * UpChapterAsyncTask.
 *
 * @author CuongCV
 */

public class UpChapterAsyncTask extends AsyncTask<ChapterToUp, Void, Void> {

    private AsyncTaskStatusListener mListener;
    private boolean mCheck = true;

    public UpChapterAsyncTask(AsyncTaskStatusListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Void doInBackground(ChapterToUp... chapterToUps) {
        APIService apiService = RetrofitClient.getApiService();
        ChapterToUp chapterToUp = chapterToUps[0];
        apiService.upChapter(chapterToUp.getStoryId(), chapterToUp.getPosition(), chapterToUp.getSource()).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getStatus()) {
                    mListener.onComplete(response.body().getId());
                } else {
                    mListener.onFail(response.body().getId() + "");
                }
                mCheck = false;
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                mListener.onFail(t.getMessage());
                mCheck = false;
            }
        });

        while (mCheck){

        }
        return null;
    }
}
