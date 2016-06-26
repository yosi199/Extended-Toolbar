package smart_toolbar.base.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;

import smart_toolbar.base.toolbar.ToolbarControllerInterface;


/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class FadeAnimation extends BaseToolbarAnimation {

    public FadeAnimation(ToolbarControllerInterface toolbarController) {
        super(toolbarController);
    }

    @Override
    void initAnimator(ValueAnimator animator) {
        animator.setFloatValues(0f, 1f);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();

        float sum = 1.0f - value;
        if (!isReversing()) {
            getVisibleView().setAlpha(sum);
            getHiddenView().setAlpha(value);
        } else {
            getVisibleView().setAlpha(value);
            getHiddenView().setAlpha(sum);
        }
    }


    @Override
    public void onAnimationStart(Animator animation) {
        getHiddenView().setTranslationY(0);
        getVisibleView().setTranslationY(0);
    }

    @Override
    public void onNewAnimationSet() {

    }
}
