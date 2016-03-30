package smart_toolbar.base;

import android.view.View;

/**
 * Created by yosimizrachi on 30/03/2016.
 */
public interface ToolbarViews {

    View getVisibleLayout();

    View getHiddenLayout();

    void onAnimationEnded();
}
