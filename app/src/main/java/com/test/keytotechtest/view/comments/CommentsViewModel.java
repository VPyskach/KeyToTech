package com.test.keytotechtest.view.comments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.test.keytotechtest.data.comments.CommentsRequest;
import com.test.keytotechtest.data.comments.OnCommentsRequestListener;
import com.test.keytotechtest.data.comments.model.Comment;

import java.util.List;

public class CommentsViewModel extends ViewModel implements OnCommentsRequestListener {

    private MutableLiveData<List<Comment>> commentsData = new MutableLiveData<>();

    public  MutableLiveData<List<Comment>> getCommentsData(){return commentsData;};

    private CommentsRequest commentsRequest;

    public void loadData(int startIndex, int endIndex){
        if(commentsRequest == null){
            commentsRequest = new CommentsRequest(startIndex, endIndex, this);
            commentsRequest.execute();
        }
    }

    @Override
    public void errorLoading() {
        commentsData.setValue(null);
    }

    @Override
    public void finishLoading(List<Comment> comments) {
        commentsRequest = null;
        commentsData.setValue(comments);
    }
}
