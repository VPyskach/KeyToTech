package com.test.keytotechtest.data;

import com.test.keytotechtest.data.comments.model.Comment;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServerAPI {

    @GET("/comments")
    Call<List<Comment>> getComments(@Query("id") List<Integer> commentsIds);
}
