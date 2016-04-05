package smart_toolbar.base;

import android.view.View;

/**
 * Created by yosimizrachi on 30/03/2016.
 */
public interface IToolbarController {

    /**
     * @return the currently visible layout
     */
    View getVisibleLayout();

    /**
     * @return the currently hidden layout
     */
    View getHiddenLayout();

    /**
     * called when animating the load of the next toolbar is done
     */
    void onPrimaryAnimationEnded();

    int getToolbarHeight();

    void setToolbarHeight(int newHeight);
}
