package com.momforoneday.momforoneday.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.fragment.CaregiverDetailFragment;
import com.momforoneday.momforoneday.fragment.ContractListFragment;
import com.momforoneday.momforoneday.fragment.ContractsFragment;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Comment;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.model.User;
import com.momforoneday.momforoneday.service.AppService;

import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class CommentAdapter extends RecyclerView.Adapter {

    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);

        CommentHolder holder = new CommentHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        CommentHolder holder = (CommentHolder) viewHolder;

        final Comment currentComment = commentList.get(position);

        holder.comment.setText(currentComment.getOwner() + ": " + currentComment.getComment());
        holder.date.setText(currentComment.getDate());

    }

    @Override
    public int getItemCount() {
        return this.commentList.size();
    }
}

class CommentHolder extends RecyclerView.ViewHolder {

    final TextView comment;
    final TextView date;

    public CommentHolder(View view) {
        super(view);

        comment = (TextView) view.findViewById(R.id.text);
        date = (TextView) view.findViewById(R.id.data);

    }

}