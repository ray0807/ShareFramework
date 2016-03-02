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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                if (startX <= OFFSET_DISTANCE) {
                    return true;
                } else {
                    super.onTouchEvent(event);
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
                    if (endX - startX > BACK_DISTANCE) {
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

        return super.onTouchEvent(event);
    }

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
}
