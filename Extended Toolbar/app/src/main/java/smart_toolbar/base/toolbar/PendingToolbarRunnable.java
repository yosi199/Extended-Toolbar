package smart_toolbar.base.toolbar;


import smart_toolbar.base.views.ToolbarViewInterface;

/**
 * Created by yosimizrachi on 10/04/2016.
 * <p/>
 * If we tried to load a new toolbar strategy while a current load is running
 * wait for it to finish and load this pending toolbar startegy
 */
public final class PendingToolbarRunnable implements Runnable {

    private BaseToolbar mBaseToolbar;
    private ToolbarViewInterface mPendingToolbar;

    public PendingToolbarRunnable(BaseToolbar toolbar, ToolbarViewInterface pendingToolbar) {
        mBaseToolbar = toolbar;
        mPendingToolbar = pendingToolbar;
    }

    @Override
    public void run() {
        if (mBaseToolbar != null && mBaseToolbar.load(mPendingToolbar)) {
            mPendingToolbar = null;
        }
    }

    public void destroy() {
        mBaseToolbar = null;
        mPendingToolbar = null;
    }
}
