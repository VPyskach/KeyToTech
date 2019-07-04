package com.test.keytotechtest.data.comments;

import android.os.AsyncTask;

import com.test.keytotechtest.data.RetrofitInstance;
import com.test.keytotechtest.data.ServerAPI;
import com.test.keytotechtest.data.comments.model.Comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CommentsRequest extends AsyncTask<Void, Void, List<Comment>> {


    private int startIndex;
    private int endIndex;
    private OnCommentsRequestListener onCommentsRequestListener;

    public CommentsRequest(int startIndex, int endIndex, OnCommentsRequestListener onCommentsRequestListener) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.onCommentsRequestListener = onCommentsRequestListener;
    }

    @Override
    protected List<Comment> doInBackground(Void... voids) {

        List<Comment> list = new ArrayList<>();
        ServerAPI serverAPI = RetrofitInstance.getRetrofitInstance().create(ServerAPI.class);
        Call<List<Comment>> call = serverAPI.getComments(options(startIndex, endIndex));

        try {
            Response<List<Comment>> response = call.execute();
            if (response.body() != null)
                list = response.body();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            publishProgress();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        onCommentsRequestListener.errorLoading();
    }

    @Override
    protected void onPostExecute(List<Comment> comments) {
        super.onPostExecute(comments);
        if (comments == null)
            new CommentsRequest(startIndex, endIndex, onCommentsRequestListener).execute();
        else
            onCommentsRequestListener.finishLoading(comments);
    }

    private List<Integer> options(int startId, int endId) {
        List<Integer> list = new ArrayList<>();
        for (int i = startId; i <= endId; i++) {
            list.add(i);
        }
        return list;
    }
}
