package com.test.keytotechtest.view.comments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.test.keytotechtest.Constans;
import com.test.keytotechtest.R;
import com.test.keytotechtest.data.comments.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private int startIndex;
    private int endIndex;
    private int lastLoaded;
    private int elementCounts;

    private CommentsViewModel commentsViewModel;
    private List<Comment> commentsList = new ArrayList<>();

    private RecyclerView recyclerView;
    private CommentsRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        getIndexValue();
        initViews();

        commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check saved data
        if (commentsList.size() == 0) {
            loadData();
        }else{
            recyclerAdapter.addComments(commentsList);
        }

        commentsViewModel.getCommentsData().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                if (comments != null) {
                    lastLoaded = lastLoaded + comments.size();
                    recyclerAdapter.addComments(comments);
                    commentsList.addAll(comments);
                } else {
                    showErrorMessage();
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save loaded comments
        outState.putParcelableArrayList(Constans.COMMENTS, (ArrayList<? extends Parcelable>) commentsList);
        outState.putInt(Constans.LAST_LOAD, lastLoaded);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restore loaded comments
        commentsList = savedInstanceState.getParcelableArrayList(Constans.COMMENTS);
        lastLoaded = savedInstanceState.getInt(Constans.LAST_LOAD);
    }

    private void initViews() {
        recyclerAdapter = new CommentsRecyclerAdapter(commentsList, elementCounts);
        recyclerView = findViewById(R.id.recyclerViewComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null) {
                    int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (lastVisiblePosition == (commentsList.size()) && lastVisiblePosition != (elementCounts - 1)) {
                        loadData();
                    }
                }
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Server connection error", Toast.LENGTH_SHORT).show();
    }

    private void getIndexValue() {
        Intent intent = getIntent();
        startIndex = intent.getIntExtra(Constans.START_INDEX, 0);
        endIndex = intent.getIntExtra(Constans.END_INDEX, 0);
        lastLoaded = startIndex;
        elementCounts = endIndex - startIndex + 1;
    }

    private void loadData() {
        if (lastLoaded + Constans.REQUEST_DATA_COUNT <= endIndex) {
            commentsViewModel.loadData(lastLoaded, lastLoaded + Constans.REQUEST_DATA_COUNT);
        } else {
            commentsViewModel.loadData(lastLoaded, endIndex);
        }
    }
}
