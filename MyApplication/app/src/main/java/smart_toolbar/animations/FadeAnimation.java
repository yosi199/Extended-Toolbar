package smart_toolbar.animations;

import android.animation.ValueAnimator;

import smart_toolbar.base.ToolbarViews;

/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class FadeAnimation extends BaseToolbarAnimation {

    public FadeAnimation(ToolbarViews toolbarViews) {
        super(toolbarViews);
    }

    @Override
    void initAnimator(ValueAnimator animator) {
        animator.setFloatValues(0f, 1f);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();

        getVisibleView().setAlpha(1 - value);

        if (getHiddenView() != null) {
            getHiddenView().setAlpha(value);
        }
    }

}
