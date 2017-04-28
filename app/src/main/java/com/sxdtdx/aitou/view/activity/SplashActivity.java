package com.sxdtdx.aitou.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.utils.ThreadUtils;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Side;
import su.levenetc.android.textsurface.contants.TYPE;

public class SplashActivity extends AppCompatActivity {

    private TextSurface mTextSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mTextSurface = (TextSurface) findViewById(R.id.splash_text_surface);
        initView();
        ThreadUtils.runOnUIThreadDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }

    public void initView() {
        Text textOne = TextBuilder
                .create("投")
                .setSize(100)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.SURFACE_CENTER)
                .build();

        Text textTwo = TextBuilder
                .create("你所爱")
                .setSize(30)
                .setAlpha(100)
                .setColor(Color.WHITE)
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textOne)
                .build();

        mTextSurface.play(
                TYPE.SEQUENTIAL,
                Alpha.show(textOne, 700),
                Slide.showFrom(Side.TOP, textTwo, 1000)
        );
    }
}
