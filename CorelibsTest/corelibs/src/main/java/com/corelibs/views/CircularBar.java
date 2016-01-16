package com.corelibs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.corelibs.R;
import com.corelibs.utils.DisplayUtil;

/**
 * 特殊动画的圆形进度条
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 *
 * @author ZhuFan
 * @Date: 2015-6-1
 */
public class CircularBar extends View {
	
	/** 默认宽度, dp */
	private static final int DEFAULT_WIDTH_DP = 5;
	
	private Paint paint;
	private final RectF oval = new RectF();
	
	private Context mContext;
	private float startAngle;
	private float sweepAngle;
	
	private ObjectAnimator angleAnimator;
	private ObjectAnimator sweepAnimator;
	
	private boolean flag = true;
	/** 前景色 */
	private int backColor = 0xFFDDDDDD;
	/** 背景色 */
	private int frontColor = 0xFF009077;
	/** 圆弧宽度 */
	private float width;
	
	public CircularBar(Context context) {
		super(context);
		mContext = context;
		init(null);
		init();
	}
	
	public CircularBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(attrs);
		init();
	}

	public CircularBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init(attrs);
		init();
	}
	
	private void init(AttributeSet attrs){
		if(attrs == null) {
			width = DisplayUtil.dip2px(mContext, DEFAULT_WIDTH_DP);
		} else {
			TypedArray attribute = mContext.obtainStyledAttributes(attrs, R.styleable.CircularBar);
			frontColor = attribute.getColor(R.styleable.CircularBar_circularColor, 0xFF009077);
			backColor = attribute.getColor(R.styleable.CircularBar_circularBackgroundColor, 0xFFDDDDDD);
			width = attribute.getDimension(R.styleable.CircularBar_circularWidth, DisplayUtil.dip2px(mContext, DEFAULT_WIDTH_DP));
			attribute.recycle();
		}
	}
	
	private void init(){
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(frontColor);
		paint.setStrokeWidth(width);
		
		startAngle = 0f;
		sweepAngle = 0f;
		
		angleAnimator = ObjectAnimator.ofFloat(this, "startAngle", 360f).setDuration(2000);
		angleAnimator.setRepeatCount(ValueAnimator.INFINITE);
		angleAnimator.setRepeatMode(ValueAnimator.RESTART);
		angleAnimator.setInterpolator(new LinearInterpolator());
		
		angleAnimator.start();
		
		sweepAnimator = ObjectAnimator.ofFloat(this, "sweepAngle", 360f).setDuration(600);
		sweepAnimator.setRepeatCount(ValueAnimator.INFINITE);
		sweepAnimator.setRepeatMode(ValueAnimator.RESTART);
		sweepAnimator.setInterpolator(new DecelerateInterpolator());
		sweepAnimator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				flag = !flag;
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {}
			
			@Override
			public void onAnimationCancel(Animator animation) {}
		});
		sweepAnimator.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		float _sweepAngle = sweepAngle;
		
		if(flag){
			_sweepAngle = sweepAngle;
		} else {
			_sweepAngle = sweepAngle - 360;
		}
		
		oval.top = 10;
		oval.bottom = getHeight()-10;
		oval.left = 10;
		oval.right = getHeight()-10;
		
		paint.setColor(backColor);
		canvas.drawOval(oval, paint);
		paint.setStrokeWidth(width);
		
		paint.setColor(frontColor);
		canvas.drawArc(oval, startAngle, _sweepAngle, false, paint);
		//canvas.drawRect(10, 10, getHeight()-10, getHeight()-10, paint);
		
		super.onDraw(canvas);
	}

	public float getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(float startAngle) {
		if(this.startAngle == startAngle)
			return;
		this.startAngle = startAngle;
		invalidate();
	}

	public float getSweepAngle() {
		return sweepAngle;
	}

	public void setSweepAngle(float sweepAngle) {
		if(this.sweepAngle == sweepAngle)
			return;
		this.sweepAngle = sweepAngle;
		invalidate();
	}
	
	/**
	 * 启动动画, 动画默认启动
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:03:57
	 */
	public void startAnimator(){
		angleAnimator.start();
		sweepAnimator.start();
	}
	
	/**
	 * 设置圆弧颜色
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @param color 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:03:06
	 */
	public void setFrontColor(int color){
		this.frontColor = color;
		invalidate();
	}
	
	/**
	 * 设置圆弧背景色
	 */
	public void setBackgroundColor(int color){
		this.backColor = color;
		invalidate();
	}
	
	/**
	 * 设置圆弧宽度, 单位px
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views
	 * @param width 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:02:32
	 */
	public void setStrokeWidth(float width){
		this.width = width;
		invalidate();
	}
	
	/**
	 * 停止动画
	 * @author ZhuFan
	 * @Package com.bm.corelibs.views 
	 * @return void
	 * @throws 
	 * @Date 2015-6-12 上午10:02:24
	 */
	public void stopAnimation(){
		angleAnimator.cancel();
		sweepAnimator.cancel();
	}
}
