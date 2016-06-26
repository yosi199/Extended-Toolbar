package smart_toolbar.app;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import smart_toolbar.base.animations.SlideAnimation;
import smart_toolbar.base.toolbar.BaseToolbar;
import smart_toolbar.base.toolbar.CustomBehaviourInterface;
import smart_toolbar.base.views.ToolbarViewInterface;


/**
 * Created by yosimizrachi on 05/04/2016.
 */
public class CustomHeightToolbar extends BaseToolbar implements
        ValueAnimator.AnimatorUpdateListener,
        CustomBehaviourInterface {

    private ValueAnimator heightAnimator;

    public CustomHeightToolbar(Context context) {
        super(context);
    }

    public CustomHeightToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHeightToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
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
    public boolean onPerformPreAnimation(ToolbarViewInterface nextToolbar) {
        int newHeight = nextToolbar.getToolbarViewHeight();
        // layout params type of the parent that nest the toolbar inside it
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

        if (getPrimaryToolbarAnimation() != null) {
            getPrimaryToolbarAnimation().onToolbarLayoutChanges(params);
        }
    }


    @Override
    public void setRightBtnText(String text) {
        isToolbarLoaded();
        getLoadedToolbar().setRightBtnText(text);
    }

    @Override
    public void setLeftBtnText(String text) {
        isToolbarLoaded();
        getLoadedToolbar().setLeftBtnText(text);
    }

    @Override
    public void setLeftImageSrc(String src) {
        isToolbarLoaded();
        getLoadedToolbar().setLeftImageSrc(src);
    }

    @Override
    public void setRightImageSrc(String src) {
        isToolbarLoaded();
        getLoadedToolbar().setRightImageSrc(src);
    }

    @Override
    public boolean onBackPressed() {
        isToolbarLoaded();
        return getLoadedToolbar().onBackPressed();
    }

    @Override
    public String getToolbarTitle() {
        isToolbarLoaded();
        return getLoadedToolbar().getToolbarTitle();
    }

    @Override
    public void setToolbarTitle(String title) {
        isToolbarLoaded();
        getLoadedToolbar().setToolbarTitle(title);
    }
}
