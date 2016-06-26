package smart_toolbar.base.views;


import smart_toolbar.base.toolbar.IBaseBehaviour;
import smart_toolbar.base.toolbar.IToolbarController;

/**
 * Created by yosimizrachi on 21/03/2016.
 */
public interface IToolbarView extends IBaseBehaviour {

    void setRightBtnText(String text);

    void setLeftBtnText(String text);

    void setLeftImageSrc(String src);

    void setRightImageSrc(String src);

    void setToolbarController(IToolbarController controller);

    String getPortfolioName();

    void setPortfolioName(String name);

    ToolbarType getType();

    int getToolbarViewHeight();

}
