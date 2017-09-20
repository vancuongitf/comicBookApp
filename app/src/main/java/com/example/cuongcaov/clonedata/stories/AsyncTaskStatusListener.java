package com.example.cuongcaov.clonedata.stories;

/**
 * AsyncTaskStatusListener.
 *
 * @author CuongCV
 */

public interface AsyncTaskStatusListener {
    void onComplete(long id);
    void onFail(String error);
}
