package smart_toolbar.animations;

import android.widget.RelativeLayout;

/**
 * Created by yosimizrachi on 29/03/2016.
 */
public interface ToolbarAnimation {

    void start();

    void reverse();

    boolean isRunning();

    void onAnimationSet();

    void onRootLayoutChanges(RelativeLayout.LayoutParams params);

}
