package smart_toolbar.base.animations;

import android.animation.ValueAnimator;
import android.view.View;

import smart_toolbar.base.toolbar.IToolbarController;


/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class SlideAnimation extends BaseToolbarAnimation {

    public SlideAnimation(IToolbarController toolbarController) {
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
