package com.example.cuongcaov.clonedata.draft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Result.
 *
 * @author CuongCV
 */

public class Result {
    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("id")
    @Expose
    private long id;

    public Result() {
    }

    public Result(Boolean status, long id) {
        this.status = status;
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }
}
