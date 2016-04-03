package smart_toolbar.base;

import android.content.Context;
import android.util.AttributeSet;

import smart_toolbar.animations.SlideAnimation;

/**
 * Created by yosimizrachi on 21/03/2016.
 */
public class SmartToolbar extends BaseToolbar {


    public SmartToolbar(Context context) {
        super(context);
        init();
    }

    public SmartToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmartToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mAnimation = new SlideAnimation(this);
    }


}
