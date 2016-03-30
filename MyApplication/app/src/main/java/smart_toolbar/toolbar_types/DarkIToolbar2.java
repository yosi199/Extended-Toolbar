package smart_toolbar.toolbar_types;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.yosimizrachi.smarttoolbar.App;
import com.example.yosimizrachi.smarttoolbar.R;

import smart_toolbar.base.IToolbarStrategy;
import smart_toolbar.base.ToolbarViewBase;


/**
 * Created by yosimizrachi on 21/03/2016.
 */
public class DarkIToolbar2 extends ToolbarViewBase implements View.OnClickListener {


    public DarkIToolbar2(Context context) {
        super(context);
    }

    public DarkIToolbar2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DarkIToolbar2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(String title) {

    }

    public static IToolbarStrategy getInstance() {
        return (IToolbarStrategy) LayoutInflater.from(App.getAppContext()).inflate(R.layout.dark_toolbar2, null, false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title:
                App.toast("Title Dark toolbar 2");
                break;
            case R.id.toolbar_right_btn:
                App.toast("Right button toolbar 2");
                break;
        }

    }
}