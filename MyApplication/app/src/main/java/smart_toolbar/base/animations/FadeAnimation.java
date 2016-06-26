package smart_toolbar.base.animations;

import android.animation.ValueAnimator;

import smart_toolbar.base.toolbar.IToolbarController;


/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class FadeAnimation extends BaseToolbarAnimation {

    public FadeAnimation(IToolbarController toolbarController) {
        super(toolbarController);
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

    @Override
    public void onNewAnimationSet() {
    }
}
