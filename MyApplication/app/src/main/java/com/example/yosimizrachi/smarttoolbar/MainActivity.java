package com.example.yosimizrachi.smarttoolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import smart_toolbar.animations.FadeAnimation;
import smart_toolbar.animations.SlideAnimation;
import smart_toolbar.base.IToolbarStrategy;
import smart_toolbar.base.SmartToolbar;
import smart_toolbar.toolbar_types.DarkIToolbar;
import smart_toolbar.toolbar_types.DarkIToolbar2;
import smart_toolbar.toolbar_types.DarkIToolbar3;
import smart_toolbar.toolbar_types.DarkIToolbar4;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartToolbar toolbar;
    private View mSwitch1;
    private View mSwitch2;
    private View mSwitch3;
    private View mSwitch4;
    private View mChangeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (SmartToolbar) findViewById(R.id.smart_toolbar);
        toolbar.loadNext(DarkIToolbar4.getInstance());

        mSwitch1 = findViewById(R.id.switchBtn1);
        mSwitch1.setOnClickListener(this);

        mSwitch2 = findViewById(R.id.switchBtn2);
        mSwitch2.setOnClickListener(this);

        mSwitch3 = findViewById(R.id.switchBtn3);
        mSwitch3.setOnClickListener(this);

        mSwitch4 = findViewById(R.id.switchBtn4);
        mSwitch4.setOnClickListener(this);

        mChangeAnimation = findViewById(R.id.changeAnim);
        mChangeAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IToolbarStrategy selectedNext = null;
        switch (v.getId()) {
            case R.id.switchBtn1:
                selectedNext = DarkIToolbar.getInstance();
                break;
            case R.id.switchBtn2:
                selectedNext = DarkIToolbar2.getInstance();
                break;
            case R.id.switchBtn3:
                selectedNext = DarkIToolbar3.getInstance();
                break;
            case R.id.switchBtn4:
                selectedNext = DarkIToolbar4.getInstance();
                break;
            case R.id.changeAnim:
                if (toolbar.getToolbarAnimation() instanceof SlideAnimation) {
                    toolbar.setAnimation(new FadeAnimation(toolbar));
                } else {
                    toolbar.setAnimation(new SlideAnimation(toolbar));
                }
                break;
        }
        toolbar.loadNext(selectedNext);
    }
}