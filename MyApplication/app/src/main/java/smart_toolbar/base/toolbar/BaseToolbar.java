package smart_toolbar.base.toolbar;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.animations.ToolbarAnimation;
import smart_toolbar.base.views.ToolbarViewInterface;


/**
 * Created by yosimizrachi on 05/04/2016.
 * <p/>
 * The base class for toolbar.
 * <p/>
 * With swappable primary animations and optional secondary animation to be performed when different toolbars type are loaded
 * (Example - different height toolbar will be animate the height adjustment).
 */
public class BaseToolbar extends Toolbar implements ToolbarControllerInterface {

    /**
     * Default height
     */
    private static int TOOLBAR_HEIGHT = App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height);
    /**
     * A handler to schedule delayed operations
     */
    private final Handler mHandler = new Handler();
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
    private ToolbarViewInterface mPendingToolbar = null;
    /**
     * The currently loaded toolbar strategy
     */
    private ToolbarViewInterface mLoadedToolbar = null;
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

    private PendingToolbarRunnable mToolbarRunnable;

    private ICallbacksListener mFragmentCallbacks;
    private final Animator.AnimatorListener mSecondaryListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            load(mPendingToolbar, mFragmentCallbacks);
            onSecondaryAnimationEnded(animation);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public BaseToolbar(Context context) {
        super(context);
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public BaseToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = findViewById(R.id.toolbar_inner_root);
        mTopLayout = view.findViewById(R.id.toolbar_top_layout);
        mBottomLayout = view.findViewById(R.id.toolbar_bottom_layout);
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
            mToolbarRunnable = new PendingToolbarRunnable(this, mPendingToolbar);
            mHandler.postDelayed(mToolbarRunnable, 100);
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

    @Override
    public Context getActivityContext() {
        return getContext();
    }

    @Override
    public ICallbacksListener getFragmentCallbacks() {
        return mFragmentCallbacks;
    }

    private final void setBottomShown() {
        if (mBottomShown) {
            mBottomShown = false;
        } else {
            mBottomShown = true;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mToolbarRunnable);
        mTopLayout = null;
        mBottomLayout = null;
        mPendingToolbar = null;
        mLoadedToolbar = null;
        mFragmentCallbacks = null;

        if (mToolbarRunnable != null) {
            mToolbarRunnable.destroy();
            mToolbarRunnable = null;
        }
    }

    /**
     * Performs the pre animation before loading the next toolbar
     *
     * @param nextToolbar the next toolbar to load
     * @return true if the pre-animation loads the toolbar inside this method body, so it wont load it again.
     * Return false otherwise
     */
    public boolean onPerformPreAnimation(ToolbarViewInterface nextToolbar) {
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
    public boolean load(@NonNull ToolbarViewInterface nextToolbar, ICallbacksListener listener) {
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
                mLoadedToolbar = nextToolbar;
                mFragmentCallbacks = listener;
                loadToolbar(nextToolbar);
                Log.i("BaseToolbar", nextToolbar.toString());
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean load(@NonNull ToolbarViewInterface nextToolbar) {
        return load(nextToolbar, mFragmentCallbacks);
    }

    private void loadToolbar(ToolbarViewInterface nextToolbar) {
        nextToolbar.setToolbarController(this);

        // get hidden layout, remove previous views from it and load the new layout
        FrameLayout hiddenLayout = (FrameLayout) getHiddenLayout();
        hiddenLayout.removeAllViews();
        hiddenLayout.addView((View) nextToolbar);
        animateLoad();
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

    public final void setPendingToolbar(ToolbarViewInterface pendingToolbar) {
        mPendingToolbar = pendingToolbar;
    }

    public final ToolbarViewInterface getLoadedToolbar() {
        return mLoadedToolbar;
    }

    @Nullable
    public final ToolbarAnimation getPrimaryToolbarAnimation() {
        return mPrimaryAnimation;
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

    public final Animator getSecondaryAnimator() {
        return mSecondaryAnimator;
    }

    /**
     * Sets the secondary animation to use when the next toolbar is different then current one.
     * <p/>
     * Whether a toolbar is different then another, needs to be implemented in the {@link #onPerformPreAnimation(ToolbarViewInterface)}
     *
     * @param secondaryAnimator the animator to use
     */
    public final void setSecondaryAnimator(Animator secondaryAnimator) {
        mSecondaryAnimator = secondaryAnimator;
        mSecondaryAnimator.addListener(mSecondaryListener);
    }

    public void onSecondaryAnimationEnded(Animator animator) {

    }

    public final void isToolbarLoaded() {
        if (getLoadedToolbar() == null) {
            throw new NullPointerException("No toolbar loaded. Did you forget to load one using next() method?");
        }
    }

    public interface ICallbacksListener {
        void onRightTVClicked();

        void onCloseClicked();

        void onLeftTVClicked();
    }

}
