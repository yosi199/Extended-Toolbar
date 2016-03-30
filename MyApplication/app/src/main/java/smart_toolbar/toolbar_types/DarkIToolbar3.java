package smart_toolbar.toolbar_types;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.IToolbarStrategy;
import smart_toolbar.base.ToolbarViewBase;

/**
 * Created by yosimizrachi on 21/03/2016.
 */
public class DarkIToolbar3 extends ToolbarViewBase {


    public DarkIToolbar3(Context context) {
        super(context);
    }

    public DarkIToolbar3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DarkIToolbar3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(String title) {

    }

    public static IToolbarStrategy getInstance() {
        return (IToolbarStrategy) LayoutInflater.from(App.getAppContext()).inflate(R.layout.dark_toolbar3, null, false);
    }
}
