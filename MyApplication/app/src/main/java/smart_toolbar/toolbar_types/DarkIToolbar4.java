package smart_toolbar.toolbar_types;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.IToolbarStrategy;
import smart_toolbar.base.ToolbarData;
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
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    @Override
    public void setTitle(String title) {

    }


    @Override
    public int getToolbarViewHeight() {
//        return App.getAppContext().getResources().getDimensionPixelSize(R.dimen.toolbar_height2);
        return 0;
    }

    @Override
    public ToolbarType getToolbarType() {
        return ToolbarType.DARK4;
    }

    @Override
    public ToolbarData getData() {
        ToolbarData data = new ToolbarData();
        data.title = "Yosi The King 4";
        return data;
    }

    @Override
    public void setData(ToolbarData data) {
        mTitle.setText(data.title);
    }
}
