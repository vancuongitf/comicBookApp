package com.example.cuongcaov.clonedata.stories;

import android.os.AsyncTask;

import com.example.cuongcaov.clonedata.draft.APIService;
import com.example.cuongcaov.clonedata.draft.Result;
import com.example.cuongcaov.clonedata.draft.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * UpStoryAsyncTask.
 *
 * @author CuongCV
 */

public class UpStoryAsyncTask extends AsyncTask<ComicBook, Void, Void> {

    private AsyncTaskStatusListener mListener;
    private boolean mCheck;

    public UpStoryAsyncTask(AsyncTaskStatusListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Void doInBackground(ComicBook... comicBooks) {
        mCheck = true;
        APIService apiService = RetrofitClient.getApiService();
        ComicBook comicBook = comicBooks[0];
        apiService.upComic(comicBook.getName(),
                comicBook.getAuthor(),
                comicBook.getNumOfChapters(),
                comicBook.getType(),
                comicBook.getStatus(),
                comicBook.getSource(),
                comicBook.getIntro()).enqueue(new Callback<Result>() {
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

        while (mCheck) {

        }
        return null;
    }
}
