package smart_toolbar.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.ToolbarViews;


/**
 * Created by yosimizrachi on 29/03/2016.
 */
public abstract class BaseToolbarAnimation implements
        ToolbarAnimation,
        ValueAnimator.AnimatorUpdateListener,
        Animator.AnimatorListener {

    public static final int TOOLBAR_HEIGHT = App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height);
    private final ToolbarViews mToolbarViews;
    private ValueAnimator mValueAnimator = new ValueAnimator();
    private boolean isReversing;

    public BaseToolbarAnimation(ToolbarViews toolbarViews) {
        mToolbarViews = toolbarViews;
        init();
    }

    private void init() {
        mValueAnimator.setFloatValues(0, -TOOLBAR_HEIGHT);
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
