package smart_toolbar.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import smart_toolbar.base.toolbar.IToolbarController;


/**
 * Created by yosimizrachi on 29/03/2016.
 */
public abstract class ToolbarViewBase extends FrameLayout implements
        IToolbarView {

    public TextView mTitle;
    public View mLeftBtn;
    public View mRightBtn;
    private IToolbarController mController;

    public ToolbarViewBase(Context context) {
        super(context);
    }

    public ToolbarViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void setRightBtnText(String text) {

    }

    @Override
    public void setLeftBtnText(String text) {

    }

    @Override
    public void setLeftImageSrc(String src) {

    }

    @Override
    public void setRightImageSrc(String src) {

    }

    @Override
    public String getPortfolioName() {
        return null;
    }

    @Override
    public void setPortfolioName(String name) {

    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public void setToolbarTitle(String title) {

    }

    @Override
    public ToolbarType getType() {
        return getToolbarType();
    }

    @Override
    public int getToolbarViewHeight() {
        return 0;
    }

    public IToolbarController getToolbarController() {
        return mController;
    }

    @Override
    public void setToolbarController(IToolbarController controller) {
        mController = controller;
    }

    /**
     * @return the toolbar type
     */
    public abstract ToolbarType getToolbarType();

    @Override
    public String toString() {
        return "Current toolbar class: " + getClass().getSimpleName();
    }
}
