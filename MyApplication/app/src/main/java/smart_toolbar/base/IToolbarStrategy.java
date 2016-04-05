package smart_toolbar.base;

/**
 * Created by yosimizrachi on 21/03/2016.
 */
public interface IToolbarStrategy {

    void setTitle(String title);

    void setRightBtnText(String text);

    void setLeftBtnText(String text);

    void setLeftImageSrc(String src);

    void setRightImageSrc(String src);

    void setToolbarController(IToolbarController toolbarContext);

    ToolbarType getType();

    int getToolbarViewHeight();

    ToolbarData getData();

    void setData(ToolbarData data);

}
