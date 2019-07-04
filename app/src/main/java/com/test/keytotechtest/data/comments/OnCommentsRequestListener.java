package com.test.keytotechtest.data.comments;

import com.test.keytotechtest.data.comments.model.Comment;

import java.util.List;

public interface OnCommentsRequestListener {
    void errorLoading();
    void finishLoading(List<Comment> comments);
}
