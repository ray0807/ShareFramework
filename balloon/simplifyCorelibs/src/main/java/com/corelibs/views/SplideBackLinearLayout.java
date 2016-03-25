package com.corelibs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/3/1.
 */
public class SplideBackLinearLayout extends LinearLayout {

    private static final int OFFSET_DISTANCE = 30;
    private static final int BACK_DISTANCE = 200;

    private float startX = 0;
    private float endX = 0;
    private Context context;
    private Scroller mScroller;
    private BackViewInterface callback;


    public SplideBackLinearLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }


    public SplideBackLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public SplideBackLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        mScroller = new Scroller(context);
    }

    int currentX;
    int distanceX;


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset() && startX <= OFFSET_DISTANCE) {
            scrollTo(mScroller.getCurrX(), 0);
            invalidate();
        }
    }

    private int getScreentWidth() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public void setBackListener(BackViewInterface callback) {
        this.callback = callback;
    }

    public interface BackViewInterface {
        void invokeBack();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                //判断是否是从边缘滑动
                //不是，此事件继续向下分发
                if (startX <= OFFSET_DISTANCE) {
                    return true;
                } else {
                    super.dispatchTouchEvent(event);
                }

            case MotionEvent.ACTION_MOVE:
                if (startX <= OFFSET_DISTANCE) {
                    currentX = (int) event.getX();
                    distanceX = (int) (currentX - startX);
                    mScroller.startScroll(-currentX, 0, -distanceX, 0);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (startX <= OFFSET_DISTANCE) {
                    endX = event.getX();
                    //判断是否到达关闭activity的阀值
                    if (endX - startX > BACK_DISTANCE) {
                        //是 通过接口回调
                        if (callback != null) {
                            mScroller.startScroll(-currentX, 0, -(getScreentWidth() - currentX), 0);
                            callback.invokeBack();
                        } else {
                            mScroller.startScroll(0, 0, 0, 0);
                        }

                    } else {
                        mScroller.startScroll(0, 0, 0, 0);
                    }
                    invalidate();
                }
                break;

        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 当然也可以在这个方法中做滑动处理，不能在onTouchEvent中处理，因为与listview等控件使用，子控件会拦截事件，父view将接收不到事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 不能在这里做滑动处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
