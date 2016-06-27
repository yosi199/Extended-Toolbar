package smart_toolbar.base.animations;

import android.animation.ValueAnimator;
import android.view.View;

import smart_toolbar.base.toolbar.ToolbarControllerInterface;


/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class SlideAnimation extends BaseToolbarAnimation {

    public SlideAnimation(ToolbarControllerInterface toolbarController) {
        super(toolbarController);
    }

    View visibleView;
    View hiddenView;

    @Override
    void initAnimator(ValueAnimator animator) {
        animator.setFloatValues(-mHeight, 0);
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

        float value = (float) animation.getAnimatedValue();


        if (isReversing()) {
            hiddenView.setTranslationY(mHeight + value);
            visibleView.setTranslationY(value);
        } else {
            hiddenView.setTranslationY(mHeight + value);
            visibleView.setTranslationY(value);
        }

    }

    @Override
    public void onNewAnimationSet() {

        visibleView = getVisibleView();
        hiddenView = getHiddenView();

        hiddenView.setAlpha(1f);
        visibleView.setAlpha(1f);

        hiddenView.setTranslationY(-mHeight);
        visibleView.setTranslationY(0);

    }


}
