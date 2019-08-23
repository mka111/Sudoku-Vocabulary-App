package com.bignerdranch.android.sudokuapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class GridCellView extends FrameLayout {
    public GridCellView(Context context) {
        super(context);
    }
    public GridCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public GridCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
