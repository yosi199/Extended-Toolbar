package smart_toolbar.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import smart_toolbar.animations.SlideAnimation;

/**
 * Created by yosimizrachi on 05/04/2016.
 */
public class CustomHeightToolbar extends BaseToolbar implements ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator heightAnimator;

    public CustomHeightToolbar(Context context) {
        super(context);
        init();
    }

    public CustomHeightToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomHeightToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setPrimarySlideAnimation();
        setSecondaryAnimation();
    }

    private void setSecondaryAnimation() {
        heightAnimator = new ValueAnimator();
        heightAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        heightAnimator.setDuration(200);
        heightAnimator.addUpdateListener(this);
        setSecondaryAnimator(heightAnimator);
    }

    private void setPrimarySlideAnimation() {
        setPrimaryToolbarAnimation(new SlideAnimation(this));
    }


    @Override
    public boolean onPerformPreAnimation(IToolbarStrategy nextToolbar) {
        int newHeight = nextToolbar.getToolbarViewHeight();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        int height = params.height;
        if (newHeight == height || newHeight == 0) {
            return false;
        }
        setPendingToolbar(nextToolbar);
        heightAnimator.setIntValues(getToolbarHeight(), newHeight);
        heightAnimator.start();
        setToolbarHeight(newHeight);
        return true;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        params.height = value;
        setLayoutParams(params);

        getPrimaryToolbarAnimation().onRootLayoutChanges(params);

    }
}
