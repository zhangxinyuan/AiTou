package com.sxdtdx.aitou.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.view.activity.VotesActivity;

public class PersonalFragment extends Fragment {

    private Button mVotes;
    private Button mPublish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote_personal, container, false);
        mVotes = (Button) view.findViewById(R.id.btn_personal_votes);
        mPublish = (Button) view.findViewById(R.id.btn_personal_publish);
        mVotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), VotesActivity.class));
            }
        });
        return view;
    }
}
