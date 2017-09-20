package com.example.cuongcaov.clonedata.draft;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * APIService.
 *
 * @author CuongCV
 */

public interface APIService {

    /*
        (var id: Int,
                     var name: String,
                     var author: String,
                     var type: String,
                     var numOfChapters: Int,
                     var status: String,
                     var source: String,
                     var timeUpdate: Long,
                     var readCount: Int,
                     var intro: String) {
     */

    @POST("/upchapter.php")
    @FormUrlEncoded
    Call<Result> upChapter(@Field("storyId") int storyId,
                           @Field("position") int position,
                           @Field("source") String content);

    @POST("/api/api-upstory.php")
    @FormUrlEncoded
    Call<Result> upComic(@Field("storyName") String name,
                         @Field("author") String author,
                         @Field("chaptersCount") int chaptersCount,
                         @Field("type") String type,
                         @Field("status") String status,
                         @Field("source") String source,
                         @Field("intro") String intro);
}
