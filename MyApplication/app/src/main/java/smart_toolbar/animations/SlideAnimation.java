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
        animator.setFloatValues(-TOOLBAR_HEIGHT, 0);
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

        float value = (float) animation.getAnimatedValue();

        View visibleView = getVisibleView();
        View hiddenView = getHiddenView();

        if (isReversing()) {
            hiddenView.setTranslationY(TOOLBAR_HEIGHT + value);
            visibleView.setTranslationY(value);
        } else {
            hiddenView.setTranslationY(value);
            visibleView.setTranslationY(TOOLBAR_HEIGHT + value);
        }
    }
}
