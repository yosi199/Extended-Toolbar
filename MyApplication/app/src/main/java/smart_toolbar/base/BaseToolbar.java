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
 * <p/>
 * The base class for toolbar.
 * <p/>
 * With swappable primary animations and optional secondary animation to be performed when different toolbars type are loaded
 * (Example - different height toolbar will be animate the height adjustment).
 */
public class BaseToolbar extends Toolbar implements IToolbarController {

    /**
     * Default height
     */
    private static int TOOLBAR_HEIGHT = App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height);

    /**
     * The primary animation to use when loading a new toolbar strategy
     */
    private ToolbarAnimation mPrimaryAnimation;

    /**
     * Secondary animation to use when different toolbars loaded
     */
    private Animator mSecondaryAnimator;

    /**
     * A toolbar strategy waiting to be loaded once animation ends
     */
    private IToolbarStrategy mPendingToolbar = null;

    /**
     * The currently loaded toolbar strategy
     */
    private IToolbarStrategy mLoadedToolbar = null;

    /**
     * A handler to schedule delayed operations
     */
    private final Handler mHandler = new Handler();

    /**
     * The top/visible layout
     */
    private View mTopLayout;

    /**
     * The bottom/hidden layout
     */
    private View mBottomLayout;

    /**
     * A boolean indicating which layout is shown at the moment
     */
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
    public final void onPrimaryAnimationEnded() {
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

    // if we tried to load a new toolbar strategy while a current load is running
    // wait for it to finish and load this pending toolbar startegy
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
        mPendingToolbar = null;
        mLoadedToolbar = null;
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

    public final void setPendingToolbar(IToolbarStrategy pendingToolbar) {
        mPendingToolbar = pendingToolbar;
    }

    public final IToolbarStrategy getLoadedToolbar() {
        return mLoadedToolbar;
    }

    /**
     * Sets the default animation to be used when loading different toolbar strategies.
     * Example - SlideAnimation will slide-out the previous toolbar and slide-in the next
     *
     * @param animation the animation type to use
     */
    public final void setPrimaryToolbarAnimation(ToolbarAnimation animation) {
        mPrimaryAnimation = animation;
        mPrimaryAnimation.onNewAnimationSet();
    }

    public final ToolbarAnimation getPrimaryToolbarAnimation() {
        return mPrimaryAnimation;
    }

    /**
     * Sets the secondary animation to use when the next toolbar is different then current one.
     * <p/>
     * Whether a toolbar is different then another, needs to be implemented in the {@link #onPerformPreAnimation(IToolbarStrategy)}
     *
     * @param secondaryAnimator the animator to use
     */
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
