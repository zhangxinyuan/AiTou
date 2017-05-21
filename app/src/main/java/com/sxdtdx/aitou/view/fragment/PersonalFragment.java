package com.sxdtdx.aitou.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.view.activity.PersonalVoteActivity;

public class PersonalFragment extends Fragment {

    public static final String TYPE_LOAD_DATA = "type_load_data";
    public static final String TYPE_VOTED = "voted";
    public static final String TYPE_PUBLISH = "publish";
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
                Intent intent = new Intent(getActivity(), PersonalVoteActivity.class);
                intent.putExtra(TYPE_LOAD_DATA, TYPE_VOTED);
                startActivity(intent);
            }
        });
        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalVoteActivity.class);
                intent.putExtra(TYPE_LOAD_DATA, TYPE_PUBLISH);
                startActivity(intent);
            }
        });
        return view;
    }
}
