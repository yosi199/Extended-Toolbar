package smart_toolbar.app.toolbarViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.views.IToolbarView;
import smart_toolbar.base.views.ToolbarType;
import smart_toolbar.base.views.ToolbarViewBase;

/**
 * Created by yosimizrachi on 26/06/2016.
 */
public class ToolbarExample2 extends ToolbarViewBase {
    public ToolbarExample2(Context context) {
        super(context);
    }

    public ToolbarExample2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarExample2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static IToolbarView getInstance() {
        return (IToolbarView) LayoutInflater.from(App.getAppContext()).inflate(R.layout.toolbar_example2, null, false);
    }

    @Override
    public ToolbarType getToolbarType() {
        return ToolbarType.EXAMPLE2;
    }

    @Override
    public int getToolbarViewHeight() {
        return App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height);
    }
}
