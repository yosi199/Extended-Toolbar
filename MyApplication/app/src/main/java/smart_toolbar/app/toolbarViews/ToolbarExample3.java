package smart_toolbar.app.toolbarViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.views.ToolbarViewInterface;
import smart_toolbar.base.views.ToolbarType;
import smart_toolbar.base.views.ToolbarViewBaseInterface;

/**
 * Created by yosimizrachi on 26/06/2016.
 */
public class ToolbarExample3 extends ToolbarViewBaseInterface {
    public ToolbarExample3(Context context) {
        super(context);
    }

    public ToolbarExample3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarExample3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static ToolbarViewInterface getInstance() {
        return (ToolbarViewInterface) LayoutInflater.from(App.getAppContext()).inflate(R.layout.toolbar_example3, null, false);
    }

    @Override
    public ToolbarType getToolbarType() {
        return ToolbarType.EXAMPLE3;
    }

    @Override
    public int getToolbarViewHeight() {
        return App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height2);
    }
}
