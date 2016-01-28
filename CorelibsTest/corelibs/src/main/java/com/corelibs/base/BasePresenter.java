package com.corelibs.base;

/**
 * Presenter基类, Fragment需使用继承自此类的子类, 泛型需传入继承自{@link BaseView}的MVPView.
 * <br/>
 * 子类可直接调用通过attachView传递过来的view来操作Activity, 无需再声明绑定.
 * <br/>
 * 如子类需要自行管理生命周期, 请在Activity/Fragment的onCreate中调用{@link #attachView}方法,
 * 并一定要在onDestroy中调用{@link #detachView}, 以防内存溢出.
 * <br/>
 * Created by Ryan on 2015/12/29.
 */
public abstract class BasePresenter<T extends BaseView> {

    protected T view;
    
    protected void onViewAttached() {}

    public T getView() {
        return view;
    }

    /**
     * 将Presenter与MVPView绑定起来.
     * @param view MVPView
     */
    public void attachView(T view) {
        this.view = view;
        onViewAttached();
    }

    /**
     * 将Presenter与MVPView解除.
     */
    public void detachView() {
        view = null;
    }

    /**
     * 根据String的resourceId来获取String对象
     * @param resId resourceId
     * @return 对应的字符串
     */
    protected String getString(int resId) {
        return view.getViewContext().getString(resId);
    }

    /**
     * 判断字符串是否为空
     */
    protected boolean stringIsNull (String str) {
        return str == null || str.trim().length() <= 0 || str.trim().equals("null") || str.trim().equals("NULL");
    }

}
