package smart_toolbar.toolbar_types;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.IToolbarStrategy;
import smart_toolbar.base.ToolbarType;
import smart_toolbar.base.ToolbarViewBase;

/**
 * Created by yosimizrachi on 21/03/2016.
 */
public class DarkIToolbar4 extends ToolbarViewBase {


    public DarkIToolbar4(Context context) {
        super(context);
    }

    public DarkIToolbar4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DarkIToolbar4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static IToolbarStrategy getInstance() {
        return (IToolbarStrategy) LayoutInflater.from(App.getAppContext()).inflate(R.layout.dark_toolbar4, null, false);
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public ToolbarType getType() {
        return null;
    }

    @Override
    public int getToolbarViewHeight() {
        return App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height2);
//        return 0;
    }
}
