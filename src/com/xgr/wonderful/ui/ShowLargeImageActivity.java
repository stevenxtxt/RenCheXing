/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: ShowLargeImageActivity.java
 * Author:   xutework
 * Date:     2015-4-29 下午5:11:58
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xgr.wonderful.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.xgr.wonderful.MyApplication;
import com.xgr.wonderful.R;
import com.xgr.wonderful.ui.base.BasePageActivity;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ShowLargeImageActivity extends BasePageActivity {

    private ActionBar ab_title;

    private ImageView iv_content;

    private String url;

    @Override
    protected void setLayoutView() {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_image);
    }

    @Override
    protected void findViews() {
        // TODO Auto-generated method stub
        ab_title = (ActionBar) findViewById(R.id.actionbar_image);
        iv_content = (ImageView) findViewById(R.id.iv_large);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        // TODO Auto-generated method stub
        ab_title.setTitle("查看大图");
        ab_title.setDisplayHomeAsUpEnabled(true);
        ab_title.setHomeAction(new Action() {

            @Override
            public void performAction(View view) {
                // TODO Auto-generated method stub
                finish();
            }

            @Override
            public int getDrawable() {
                // TODO Auto-generated method stub
                return R.drawable.logo;
            }
        });
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void fetchData() {
        // TODO Auto-generated method stub
        url = getIntent().getStringExtra("url");
        ImageLoader.getInstance().displayImage(url, iv_content,
                MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // TODO Auto-generated method stub
                        super.onLoadingComplete(imageUri, view, loadedImage);
                    }

                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        finish();
        return super.onTouchEvent(event);
    }

}
