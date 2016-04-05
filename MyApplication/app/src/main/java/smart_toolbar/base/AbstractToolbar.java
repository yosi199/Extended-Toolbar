package smart_toolbar.base;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.animations.SlideAnimation;
import smart_toolbar.animations.ToolbarAnimation;

/**
 * Created by yosimizrachi on 05/04/2016.
 */
public class AbstractToolbar extends Toolbar implements ToolbarViews {

    private static int TOOLBAR_HEIGHT = App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height);
    private ToolbarAnimation mPrimaryAnimation;
    private Animator mSecondaryAnimator;
    private IToolbarStrategy mPendingToolbar = null;
    private IToolbarStrategy mLoadedToolbar = null;
    private final Handler mHandler = new Handler();
    private View mTopLayout;
    private View mBottomLayout;
    private boolean mBottomShown;


    public AbstractToolbar(Context context) {
        super(context);
        init();
    }

    public AbstractToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbstractToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPrimaryAnimation = new SlideAnimation(this);
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

    @Override
    public final View getVisibleLayout() {
        return mBottomShown ? mBottomLayout : mTopLayout;
    }

    @Override
    public final View getHiddenLayout() {
        return mBottomShown ? mTopLayout : mBottomLayout;
    }

    @Override
    public final void onAnimationEnded() {
        setBottomShown();

        // if there is a toolbar waiting to be loaded
        // then load again and reset toolbar
        if (mPendingToolbar != null) {
            mHandler.postDelayed(loadPendingRunnable, 100);
        }
    }

    @Override
    public int getToolbarHeight() {
        return TOOLBAR_HEIGHT;
    }

    @Override
    public void setToolbarHeight(int newHeight) {
        TOOLBAR_HEIGHT = newHeight;
    }

    private final void setBottomShown() {
        if (mBottomShown) {
            mBottomShown = false;
        } else {
            mBottomShown = true;
        }
    }

    private final Runnable loadPendingRunnable = new Runnable() {
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
        mTopLayout = null;
        mBottomLayout = null;
    }


    /**
     * Performs the pre animation before loading the next toolbar
     *
     * @param nextToolbar the next toolbar to load
     * @return true if the pre-animation loads the toolbar inside this method body, so it wont load it again.
     * Return false otherwise
     */
    public boolean onPerformPreAnimation(IToolbarStrategy nextToolbar) {
        return false;
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
            if (mPrimaryAnimation != null && mPrimaryAnimation.isRunning()) {
                mPendingToolbar = nextToolbar;
                return false;
            } else {
                // if next toolbar height is different then current - first animate height and then load
                // the new toolbar
                if (onPerformPreAnimation(nextToolbar)) {
                    // return false because loading is handled inside
                    return false;
                }
                mPendingToolbar = null;
                loadToolbar(nextToolbar);
                return true;
            }
        } else {
            return false;
        }
    }

    private void loadToolbar(IToolbarStrategy nextToolbar) {
        // get hidden layout, remove previous views from it and load the new layout
        FrameLayout hiddenLayout = (FrameLayout) getHiddenLayout();
        hiddenLayout.removeAllViews();
        hiddenLayout.addView((View) nextToolbar);
        animateLoad();
        mLoadedToolbar = nextToolbar;
    }

    public final IToolbarStrategy getPendingToolbar() {
        return mPendingToolbar;
    }

    public final void setPendingToolbar(IToolbarStrategy pendingToolbar) {
        mPendingToolbar = pendingToolbar;
    }

    public final IToolbarStrategy getLoadedToolbar() {
        return mLoadedToolbar;
    }

    private void animateLoad() {

        if (mPrimaryAnimation != null) {
            if (!mPrimaryAnimation.isRunning()) {
                if (mBottomShown) {
                    mPrimaryAnimation.reverse();
                } else {
                    mPrimaryAnimation.start();
                }
            }
        }
    }

    public final void setPrimaryToolbarAnimation(ToolbarAnimation animation) {
        mPrimaryAnimation = animation;
        mPrimaryAnimation.onAnimationSet();
    }

    public final ToolbarAnimation getPrimaryToolbarAnimation() {
        return mPrimaryAnimation;
    }

    public final void setSecondaryAnimator(Animator secondaryAnimator) {
        mSecondaryAnimator = secondaryAnimator;
        mSecondaryAnimator.addListener(mSecondaryListener);
    }

    public final Animator getSecondaryAnimator() {
        return mSecondaryAnimator;
    }

    private final Animator.AnimatorListener mSecondaryListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            next(mPendingToolbar);
            onSecondaryAnimationEnded(animation);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public void onSecondaryAnimationEnded(Animator animator) {

    }

}
