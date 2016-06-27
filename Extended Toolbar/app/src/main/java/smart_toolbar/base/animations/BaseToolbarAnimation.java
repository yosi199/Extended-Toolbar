package smart_toolbar.base.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.toolbar.BaseToolbar;
import smart_toolbar.base.toolbar.ToolbarControllerInterface;


/**
 * Created by yosimizrachi on 29/03/2016.
 * <p/>
 * A special value animation class to use for **Primary** toolbar animations.
 */
public abstract class BaseToolbarAnimation implements
        ToolbarAnimation,
        ValueAnimator.AnimatorUpdateListener,
        Animator.AnimatorListener {

    private final int mPrimaryAnimDuration;

    /**
     * Default animator to use
     */
    private final ValueAnimator mValueAnimator = new ValueAnimator();

    /**
     * The toolbar interface to have callbacks with
     */
    private final ToolbarControllerInterface mToolbarController;
    /**
     * Toolbar height
     */
    public int mHeight;
    /**
     * boolean indicating whether animation is reversing or not
     */
    private boolean isReversing;

    public BaseToolbarAnimation(ToolbarControllerInterface toolbarController) {
        mPrimaryAnimDuration = ((BaseToolbar) toolbarController).getContext().getResources().getInteger(R.integer.primary_anim_duration);
        mToolbarController = toolbarController;
        mHeight = mToolbarController.getToolbarHeight();
        init();
    }

    public void init() {
        mValueAnimator.setFloatValues(0, -mHeight);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.setDuration(mPrimaryAnimDuration);
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

    @Override
    public void cancel() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    /**
     * If toolbar layout has changed, the animation should be updated accordingly
     *
     * @param params the newly changed params
     */
    @Override
    public final void onToolbarLayoutChanges(ViewGroup.LayoutParams params) {
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
