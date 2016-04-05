package smart_toolbar.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import smart_toolbar.base.IToolbarController;


/**
 * Created by yosimizrachi on 29/03/2016.
 * <p/>
 * A special value animation class to use for **Primary** toolbar animations.
 */
public abstract class BaseToolbarAnimation implements
        ToolbarAnimation,
        ValueAnimator.AnimatorUpdateListener,
        Animator.AnimatorListener {

    /**
     * Default animator to use
     */
    private final ValueAnimator mValueAnimator = new ValueAnimator();

    /**
     * The toolbar interface to have callbacks with
     */
    private final IToolbarController mToolbarController;

    /**
     * boolean indicating whether animation is reversing or not
     */
    private boolean isReversing;

    /**
     * Toolbar height
     */
    public int mHeight;

    public BaseToolbarAnimation(IToolbarController toolbarController) {
        mToolbarController = toolbarController;
        mHeight = mToolbarController.getToolbarHeight();
        init();
    }

    public void init() {
        mValueAnimator.setFloatValues(0, -mHeight);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.setDuration(300);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
        initAnimator(mValueAnimator);
    }

    abstract void initAnimator(ValueAnimator animator);

    @Override
    public void start() {
        if (mValueAnimator != null) {
            isReversing = false;
            mValueAnimator.start();
        }
    }

    @Override
    public void reverse() {
        if (mValueAnimator != null) {
            isReversing = true;
            mValueAnimator.reverse();
        }
    }

    @Override
    public boolean isRunning() {
        if (mValueAnimator != null) {
            return mValueAnimator.isRunning();
        } else {
            return false;
        }
    }

    /**
     * If toolbar layout has changed, the animation should be updated accordingly
     *
     * @param params the newly changed params
     */
    @Override
    public final void onToolbarLayoutChanges(RelativeLayout.LayoutParams params) {
        mHeight = params.height;
        getHiddenView().setTranslationY(mHeight);
        initAnimator(mValueAnimator);
    }

    public boolean isReversing() {
        return isReversing;
    }

    @NonNull
    public View getVisibleView() {
        return mToolbarController.getVisibleLayout();
    }

    @Nullable
    public View getHiddenView() {
        return mToolbarController.getHiddenLayout();
    }

    @Override
    public abstract void onAnimationUpdate(ValueAnimator animation);

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mToolbarController.onPrimaryAnimationEnded();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
