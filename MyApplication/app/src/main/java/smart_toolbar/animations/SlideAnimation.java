package smart_toolbar.animations;

import android.animation.ValueAnimator;
import android.view.View;

import smart_toolbar.base.ToolbarViews;

/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class SlideAnimation extends BaseToolbarAnimation {

    public SlideAnimation(ToolbarViews toolbarViews) {
        super(toolbarViews);
    }

    @Override
    void initAnimator(ValueAnimator animator) {
        animator.setFloatValues(-mHeight, 0);
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

        float value = (float) animation.getAnimatedValue();

        View visibleView = getVisibleView();
        View hiddenView = getHiddenView();

        if (isReversing()) {
            hiddenView.setTranslationY(mHeight + value);
            visibleView.setTranslationY(value);
        } else {
            hiddenView.setTranslationY(value);
            visibleView.setTranslationY(mHeight + value);
        }
    }

    @Override
    public void onAnimationSet() {
        getHiddenView().setAlpha(1f);
        getVisibleView().setAlpha(1f);

        getHiddenView().setTranslationY(-mHeight);
        getVisibleView().setTranslationY(0);
    }
}
