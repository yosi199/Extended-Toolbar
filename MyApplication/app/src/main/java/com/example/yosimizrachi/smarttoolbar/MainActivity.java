package com.example.yosimizrachi.smarttoolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import smart_toolbar.app.toolbarViews.ToolbarExample1;
import smart_toolbar.app.toolbarViews.ToolbarExample2;
import smart_toolbar.app.toolbarViews.ToolbarExample3;
import smart_toolbar.app.toolbarViews.ToolbarExample4;
import smart_toolbar.base.animations.FadeAnimation;
import smart_toolbar.base.animations.SlideAnimation;
import smart_toolbar.base.toolbar.BaseToolbar;
import smart_toolbar.base.views.ToolbarViewInterface;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BaseToolbar toolbar;
    private View mChangeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (BaseToolbar) findViewById(R.id.smart_toolbar);
        toolbar.setPrimaryToolbarAnimation(new SlideAnimation(toolbar));
        toolbar.load(ToolbarExample1.getInstance());

        findViewById(R.id.switchBtn1).setOnClickListener(this);
        findViewById(R.id.switchBtn2).setOnClickListener(this);
        findViewById(R.id.switchBtn3).setOnClickListener(this);
        findViewById(R.id.switchBtn4).setOnClickListener(this);
        findViewById(R.id.print_visible).setOnClickListener(this);

        mChangeAnimation = findViewById(R.id.changeAnim);
        mChangeAnimation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ToolbarViewInterface selectedNext = null;
        switch (v.getId()) {
            case R.id.switchBtn1:
                selectedNext = ToolbarExample1.getInstance();
                break;
            case R.id.switchBtn2:
                selectedNext = ToolbarExample2.getInstance();
                break;
            case R.id.switchBtn3:
                selectedNext = ToolbarExample3.getInstance();
                break;
            case R.id.switchBtn4:
                selectedNext = ToolbarExample4.getInstance();
                break;
            case R.id.changeAnim:
                if (toolbar.getPrimaryToolbarAnimation() instanceof SlideAnimation) {
                    toolbar.setPrimaryToolbarAnimation(new FadeAnimation(toolbar));
                } else {
                    toolbar.setPrimaryToolbarAnimation(new SlideAnimation(toolbar));
                }
                break;
        }
        toolbar.load(selectedNext);
    }
}
