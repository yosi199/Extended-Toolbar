package smart_toolbar.animations;

/**
 * Created by yosimizrachi on 29/03/2016.
 */
public interface ToolbarAnimation {

    void start();

    void reverse();

    boolean isRunning();

    void onViewsCreated();

}
