package smart_toolbar.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class ToolbarViewBase extends FrameLayout implements IToolbarStrategy {

    public TextView mTitle;
    public Button mLeftBtn;
    public Button mRightBtn;

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
    public void setTitle(String title) {

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
    public void setToolbarContext(IToolbarContext toolbarContext) {

    }

    @Override
    public ToolbarType getType() {
        return null;
    }

}
