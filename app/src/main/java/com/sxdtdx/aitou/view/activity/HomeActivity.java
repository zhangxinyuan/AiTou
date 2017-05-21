package com.sxdtdx.aitou.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.presenter.ActivityManager;
import com.sxdtdx.aitou.view.fragment.PersonalFragment;
import com.sxdtdx.aitou.view.fragment.PublishVoteFragment;
import com.sxdtdx.aitou.view.fragment.VoteListFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HomeActivity";

    public static final int INDEX_TAB_ZERO = 0;
    public static final int INDEX_TAB_ONE = 1;
    public static final int INDEX_TAB_TWO = 2;

    private LinearLayout mTabBtnVotePersonal;
    private LinearLayout mTabBtnVotePublish;
    private LinearLayout mTabBtnVoteList;
    private FragmentManager mFragmentManager;
    private VoteListFragment mVoteListFragment;
    private PublishVoteFragment mPublishVoteFragment;
    private PersonalFragment mPersonalFragment;
    private TextView mTitle;
    private ImageView mSeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        ActivityManager.addActivity(this);
        initView();
        mFragmentManager = getFragmentManager();
        showDefaultTab();
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_text);
        mSeeting = (ImageView) findViewById(R.id.right_btn);
        mSeeting.setBackgroundResource(R.mipmap.icon_seeting);
        mTabBtnVoteList = (LinearLayout) findViewById(R.id.home_btn_vote_list);
        mTabBtnVotePublish = (LinearLayout) findViewById(R.id.home_btn_vote_publish);
        mTabBtnVotePersonal = (LinearLayout) findViewById(R.id.home_btn_vote_personal);

        mTabBtnVoteList.setOnClickListener(this);
        mTabBtnVotePublish.setOnClickListener(this);
        mTabBtnVotePersonal.setOnClickListener(this);
        mSeeting.setOnClickListener(this);
    }

    private void showDefaultTab() {
        setTabSelection(INDEX_TAB_ZERO);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_btn_vote_list:
                setTabSelection(INDEX_TAB_ZERO);
                break;
            case R.id.home_btn_vote_publish:
                setTabSelection(INDEX_TAB_ONE);
                break;
            case R.id.home_btn_vote_personal:
                setTabSelection(INDEX_TAB_TWO);
                break;
            case R.id.right_btn:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

    private void resetAllTabStatus() {
        mTabBtnVoteList.setBackgroundColor(getResources().getColor(R.color.color_btn_normal));
        mTabBtnVotePublish.setBackgroundColor(getResources().getColor(R.color.color_btn_normal));
        mTabBtnVotePersonal.setBackgroundColor(getResources().getColor(R.color.color_btn_normal));
        mTabBtnVoteList.getChildAt(0).setBackgroundResource(R.mipmap.icon_list_normal);
        mTabBtnVotePublish.getChildAt(0).setBackgroundResource(R.mipmap.icon_publish_normal);
        mTabBtnVotePersonal.getChildAt(0).setBackgroundResource(R.mipmap.icon_personal_normal);
    }

    public void setTabSelection(int tabSelection) {
        resetAllTabStatus();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (tabSelection) {
            case INDEX_TAB_ZERO:
                mTitle.setText("投票");
                mTabBtnVoteList.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                mTabBtnVoteList.getChildAt(0).setBackgroundResource(R.mipmap.icon_list_focused);
                if (mVoteListFragment == null) {
                    mVoteListFragment = new VoteListFragment();
                    transaction.add(R.id.home_container, mVoteListFragment);
                } else {
                    transaction.show(mVoteListFragment);
                }
                break;
            case INDEX_TAB_ONE:
                mTitle.setText("发布");
                mTabBtnVotePublish.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                mTabBtnVotePublish.getChildAt(0).setBackgroundResource(R.mipmap.icon_publish_focused);
                if (mPublishVoteFragment == null) {
                    mPublishVoteFragment = new PublishVoteFragment();
                    transaction.add(R.id.home_container, mPublishVoteFragment);
                } else {
                    transaction.show(mPublishVoteFragment);
                }
                break;
            case INDEX_TAB_TWO:
                mTitle.setText("我的");
                mTabBtnVotePersonal.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                mTabBtnVotePersonal.getChildAt(0).setBackgroundResource(R.mipmap.icon_personal_focused);
                if (mPersonalFragment == null) {
                    mPersonalFragment = new PersonalFragment();
                    transaction.add(R.id.home_container, mPersonalFragment);
                } else {
                    transaction.show(mPersonalFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (mVoteListFragment != null) {
            transaction.hide(mVoteListFragment);
        }
        if (mPublishVoteFragment != null) {
            transaction.hide(mPublishVoteFragment);
        }
        if (mPersonalFragment != null) {
            transaction.hide(mPersonalFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
