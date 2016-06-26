package smart_toolbar.base.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;

import smart_toolbar.base.toolbar.ToolbarControllerInterface;


/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class SlideAnimation extends BaseToolbarAnimation {

    public SlideAnimation(ToolbarControllerInterface toolbarController) {
        super(toolbarController);
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
    public void onNewAnimationSet() {
        getHiddenView().setAlpha(1f);
        getVisibleView().setAlpha(1f);

        getHiddenView().setTranslationY(-mHeight);
        getVisibleView().setTranslationY(0);
    }


}
