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

    void setToolbarContext(IToolbarContext toolbarContext);

    ToolbarType getType();

    int getToolbarViewHeight();

}
