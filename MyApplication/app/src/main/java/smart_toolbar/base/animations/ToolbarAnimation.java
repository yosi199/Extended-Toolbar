package smart_toolbar.base.animations;

import android.view.ViewGroup;

/**
 * Created by yosimizrachi on 29/03/2016.
 */
public interface ToolbarAnimation {

    void start();

    void reverse();

    boolean isRunning();

    void onNewAnimationSet();

    void onToolbarLayoutChanges(ViewGroup.LayoutParams params);

}
