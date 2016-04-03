package smart_toolbar.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import smart_toolbar.base.SmartToolbar;
import smart_toolbar.base.ToolbarViews;


/**
 * Created by yosimizrachi on 29/03/2016.
 */
public abstract class BaseToolbarAnimation implements
        ToolbarAnimation,
        ValueAnimator.AnimatorUpdateListener,
        Animator.AnimatorListener {

    private final ToolbarViews mToolbarViews;
    public int mHeight = SmartToolbar.TOOLBAR_HEIGHT;
    private boolean isReversing;
    private ValueAnimator mValueAnimator = new ValueAnimator();

    public BaseToolbarAnimation(ToolbarViews toolbarViews) {
        mToolbarViews = toolbarViews;
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

    @Override
    public final void onRootLayoutChanges(RelativeLayout.LayoutParams params) {
        mHeight = params.height;
        init();
        onViewsCreated();
    }

    public boolean isReversing() {
        return isReversing;
    }

    @NonNull
    public View getVisibleView() {
        return mToolbarViews.getVisibleLayout();
    }

    @Nullable
    public View getHiddenView() {
        return mToolbarViews.getHiddenLayout();
    }

    @Override
    public abstract void onAnimationUpdate(ValueAnimator animation);


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mToolbarViews.onAnimationEnded();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
