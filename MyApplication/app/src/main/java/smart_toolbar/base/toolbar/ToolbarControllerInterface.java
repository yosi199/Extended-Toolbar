package smart_toolbar.base.toolbar;

import android.content.Context;
import android.view.View;

import smart_toolbar.base.toolbar.BaseToolbar.ICallbacksListener;


/**
 * Created by yosimizrachi on 30/03/2016.
 */
public interface ToolbarControllerInterface {

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

    Context getActivityContext();

    ICallbacksListener getFragmentCallbacks();
}
