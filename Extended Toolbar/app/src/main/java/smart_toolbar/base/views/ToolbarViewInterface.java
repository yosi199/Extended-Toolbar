package smart_toolbar.base.views;


import smart_toolbar.base.toolbar.BaseBehaviourInterface;
import smart_toolbar.base.toolbar.ToolbarControllerInterface;

/**
 * Created by yosimizrachi on 21/03/2016.
 */
public interface ToolbarViewInterface extends BaseBehaviourInterface {

    void setRightBtnText(String text);

    void setLeftBtnText(String text);

    void setLeftImageSrc(String src);

    void setRightImageSrc(String src);

    void setToolbarController(ToolbarControllerInterface controller);

    String getPortfolioName();

    void setPortfolioName(String name);

    ToolbarType getType();

    int getToolbarViewHeight();

}
