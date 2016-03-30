package smart_toolbar.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.animations.SlideAnimation;
import smart_toolbar.animations.ToolbarAnimation;

/**
 * Created by yosimizrachi on 21/03/2016.
 */
public class SmartToolbar extends Toolbar implements
        ToolbarViews {

    private View mTopLayout;
    private View mBottomLayout;
    private static final int TOOLBAR_HEIGHT = App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height);
    private ToolbarAnimation mAnimation;
    private boolean mBottomShown;


    public SmartToolbar(Context context) {
        super(context);
        init();
    }

    public SmartToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmartToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = findViewById(R.id.toolbar_inner_root);
        mTopLayout = view.findViewById(R.id.toolbar_top_layout);
        mBottomLayout = view.findViewById(R.id.toolbar_bottom_layout);
        mBottomLayout.setTranslationY(-TOOLBAR_HEIGHT);
        mBottomShown = false;
    }

    public void setTitle(String title) {

    }

    private void init() {
        mAnimation = new SlideAnimation(this);
    }

    private void switchViews() {

        if (mAnimation != null) {
            if (!mAnimation.isRunning()) {
                if (mBottomShown) {
                    mAnimation.reverse();
                } else {
                    mAnimation.start();
                }
            }
        }
    }

    /**
     * Gets whichever layout is not displayed on screen
     *
     * @return the layout who is invisible at the moment.
     */
    public View getVisibleLayout() {
        return mBottomShown ? mBottomLayout : mTopLayout;
    }

    public View getHiddenLayout() {
        return mBottomShown ? mTopLayout : mBottomLayout;
    }

    @Override
    public void onAnimationEnded() {
        if (mBottomShown) {
            mBottomShown = false;
        } else {
            mBottomShown = true;
        }
    }

    /**
     * Set up and switch to the next toolbar view
     * <p/>
     * This method will ask for the next available (hidden from display) layout and
     * will load a new toolbar into it and call to animate it
     *
     * @param nextToolbar the toolbar to display in the next available layout
     */
    public void loadNext(@NonNull IToolbarStrategy nextToolbar) {
        if (mAnimation != null && mAnimation.isRunning()) {
            return;
        }
        if (nextToolbar != null) {
            FrameLayout hiddenLayout = (FrameLayout) getHiddenLayout();
            hiddenLayout.removeAllViews();
            hiddenLayout.addView((View) nextToolbar);
            switchViews();
        }
    }

    public void setAnimation(ToolbarAnimation animation) {
        mAnimation = animation;
    }

    public ToolbarAnimation getToolbarAnimation() {
        return mAnimation;
    }

}
