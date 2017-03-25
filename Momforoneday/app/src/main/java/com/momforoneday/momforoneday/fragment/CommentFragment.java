package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.CommentAdapter;
import com.momforoneday.momforoneday.model.Comment;
import com.momforoneday.momforoneday.service.AppService;

import java.util.List;

/**
 * Created by gabrielguimo on 25/03/17.
 */

public class CommentFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private List<Comment> comments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_comments, container, false);

        FloatingActionButton backBtn = (FloatingActionButton) rootView.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, new CaregiverDetailFragment());
                fragmentTransaction.commit();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        comments = AppService.getSelectedCaregiver().getComments();

        recyclerView.setAdapter(new CommentAdapter(comments, getContext()));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

        return rootView;
    }


}
