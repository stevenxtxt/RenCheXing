/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: MyImageView.java
 * Author:   xutework
 * Date:     2015-4-27 下午5:11:48
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xgr.wonderful.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MyImageView extends ImageView {

    private int mWidth;
    
    private Bitmap mBitmap;
    
    private int retX, retY;
    

    /**
     * @param context
     */
    public MyImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mWidth = height > width ? width : height;
        retX = width > height ? (width - height) / 2 : 0;
        retY = width > height ? 0 : (width - height) / 2;
//        setMeasuredDimension(mWidth, mWidth);
    }
}
