package smart_toolbar.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.animations.SlideAnimation;
import smart_toolbar.animations.ToolbarAnimation;

/**
 * Created by yosimizrachi on 03/04/2016.
 */
public class BaseToolbar extends Toolbar implements ToolbarViews, Animator.AnimatorListener {

    public static int TOOLBAR_HEIGHT = App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height);
    private IToolbarStrategy mPendingToolbar = null;
    private IToolbarStrategy mLoadedToolbar = null;
    private ToolbarAnimation mAnimation;
    private Handler mHandler = new Handler();
    private ValueAnimator mHeightAnimator;
    private View mTopLayout;
    private View mBottomLayout;
    private boolean mBottomShown;

    public BaseToolbar(Context context) {
        super(context);
        init();
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mAnimation = new SlideAnimation(this);
        mHeightAnimator = new ValueAnimator();
        mHeightAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mHeightAnimator.setDuration(200);
        mHeightAnimator.addUpdateListener(mHeightUpdateListener);
        mHeightAnimator.addListener(this);
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

        // if there is a toolbar waiting to be loaded
        // then load again and reset toolbar
        if (mPendingToolbar != null) {
            mHandler.postDelayed(loadPendingRunnable, 100);
        }
    }

    private Runnable loadPendingRunnable = new Runnable() {
        @Override
        public void run() {
            if (next(mPendingToolbar)) {
                mPendingToolbar = null;
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(loadPendingRunnable);
    }

    private ValueAnimator.AnimatorUpdateListener mHeightUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int value = (int) animation.getAnimatedValue();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
            params.height = value;
            setLayoutParams(params);

            FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) getVisibleLayout().getLayoutParams();
            params2.height = value;
            getVisibleLayout().setLayoutParams(params2);
            getHiddenLayout().setLayoutParams(params2);
        }
    };

    @Override
    public int getToolbarHeight() {
        return TOOLBAR_HEIGHT;
    }

    private boolean animateHeight(IToolbarStrategy nextToolbar) {
        int newHeight = nextToolbar.getToolbarViewHeight();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        int height = params.height;
        if (newHeight == height || newHeight == 0) {
            return false;
        }
        mPendingToolbar = nextToolbar;
        mHeightAnimator.setIntValues(TOOLBAR_HEIGHT, newHeight);
        mHeightAnimator.start();
        TOOLBAR_HEIGHT = newHeight;
        return true;

    }


    /**
     * Set up and switch to the next toolbar view
     * <p/>
     * This method will ask for the next available (hidden from display) layout and
     * will load a new toolbar into it and call to animate it
     *
     * @param nextToolbar the toolbar to display in the next available layout
     */
    public boolean next(@NonNull IToolbarStrategy nextToolbar) {
        if (nextToolbar != null) {
            // if different toolbar is loaded while animation is running,
            // set as pending and execute when animation finish.
            if (mAnimation != null && mAnimation.isRunning()) {
                mPendingToolbar = nextToolbar;
                return false;
            } else {
                // if next toolbar height is different then current - first animate height and then load
                // the new toolbar
                if (animateHeight(nextToolbar)) {
                    mPendingToolbar = nextToolbar;
                    return false;
                }
//                loadToolbar(nextToolbar);
                return true;
            }
        } else {
            return false;
        }
    }

    private void loadToolbar(IToolbarStrategy nextToolbar) {
        // get hidden layout, remove previous views from it and load the new layout
        FrameLayout hiddenLayout = (FrameLayout) getHiddenLayout();
//        Log.d("TOOLBAR", "HIDDEN IS " + getResources().getResourceName(hiddenLayout.getId()));
        hiddenLayout.removeAllViews();
        hiddenLayout.addView((View) nextToolbar);
        animateLoad();
        mLoadedToolbar = nextToolbar;
    }

    private void animateLoad() {

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

    public void setAnimation(ToolbarAnimation animation) {
        mAnimation = animation;
        mAnimation.onViewsCreated();
    }

    public ToolbarAnimation getToolbarAnimation() {
        return mAnimation;
    }

    public IToolbarStrategy getLoadedToolbar() {
        return mLoadedToolbar;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {


    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
