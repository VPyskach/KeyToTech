package com.test.keytotechtest.view.comments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.keytotechtest.Constans;
import com.test.keytotechtest.R;
import com.test.keytotechtest.data.comments.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Comment> commentList;

    private int elementCounts;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    CommentsRecyclerAdapter(List<Comment> commentList, int elementCounts) {
        this.commentList = new ArrayList<>(commentList);
        this.elementCounts = elementCounts;
        if(this.commentList.size() < this.elementCounts)
            this.commentList.add(null);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment_layout, null);
            return new ViewHolderItem(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_progress_bar_layout, null);
            return new ViewHolderLoad(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolderItem) {
            ViewHolderItem itemHolder = (ViewHolderItem) viewHolder;
            itemHolder.textName.setText(commentList.get(i).getName());
            itemHolder.textEmail.setText(commentList.get(i).getEmail());
            itemHolder.textBody.setText(commentList.get(i).getBody());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return commentList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    void addComments(List<Comment> item) {
        commentList.remove(commentList.size() - 1);
        commentList.addAll(item);
        if (commentList.size() < elementCounts)
            commentList.add(null);
        notifyDataSetChanged();
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textEmail;
        TextView textBody;

        ViewHolderItem(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
            textBody = itemView.findViewById(R.id.textBody);
        }
    }

    class ViewHolderLoad extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        ViewHolderLoad(@NonNull View view) {
            super(view);
            progressBar = view.findViewById(R.id.itemProgressbar);
        }
    }


}
