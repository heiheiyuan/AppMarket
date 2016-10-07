package com.itheima.appmarket.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.itheima.appmarket.R;
import com.itheima.appmarket.utils.UIUtils;

public class RatioImageView extends ImageView {

	private float ratio;
	public RatioImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initRatio(attrs);
	}
	public RatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initRatio(attrs);
	}
	public RatioImageView(Context context) {
		this(context,null);
	}
	private void initRatio(AttributeSet attrs) {
		TypedArray typeArray = UIUtils.getContext().obtainStyledAttributes(attrs,R.styleable.RatioImageView);
		ratio = typeArray.getFloat(R.styleable.RatioImageView_ratio, 0);
		typeArray.recycle();
	}
	//测量控件的宽高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//measure(0, 0);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		int heighMode = MeasureSpec.getMode(heightMeasureSpec);
		int heighSize = MeasureSpec.getSize(heightMeasureSpec);

		int imageHeigh = heighSize - getPaddingBottom() - getPaddingTop();
		int imageWidth = widthSize - getPaddingLeft() - getPaddingRight();
		//以宽度为确切模式
		if (widthMode == MeasureSpec.EXACTLY && heighMode != MeasureSpec.EXACTLY && ratio != 0.0f) {
			int height = (int) (imageWidth / ratio + getPaddingBottom() + getPaddingTop());
			//告知当前控件绘画高度的模式和大小值
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		}else if (widthMode != MeasureSpec.EXACTLY && heighMode == MeasureSpec.EXACTLY && ratio != 0.0f) {
			int width = (int) (imageHeigh / ratio + getPaddingLeft() + getPaddingRight());
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
