package smart_toolbar.base.toolbar;

/**
 * Created by yosimizrachi on 06/04/2016.
 * <p/>
 * <p/>
 * Implement this in your own toolbar instance to provide
 * api to communicate with the loaded toolbar views
 */
public interface ICustomBehaviour extends IBaseBehaviour {

    void setRightBtnText(String text);

    void setLeftBtnText(String text);

    void setLeftImageSrc(String src);

    void setRightImageSrc(String src);

    String getPortfolioName();

    void setPortfolioName(String name);

}
