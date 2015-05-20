/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: FeedbackActivity.java
 * Author:   xutework
 * Date:     2015-5-8 下午2:17:17
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xgr.wonderful.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import cn.bmob.v3.listener.SaveListener;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.xgr.wonderful.R;
import com.xgr.wonderful.entity.Feedback;
import com.xgr.wonderful.ui.base.BasePageActivity;
import com.xgr.wonderful.utils.ActivityUtil;
import com.xgr.wonderful.utils.LogUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FeedbackActivity extends BasePageActivity implements OnClickListener {

    private ActionBar actionbar;
    private EditText et_content;
    private RelativeLayout rl_pic;
    private Feedback feedback;

    @Override
    protected void setLayoutView() {
        // TODO Auto-generated method stub

        setContentView(R.layout.activity_edit);
    }

    protected void findViews() {
        // TODO Auto-generated method stub

        actionbar = (ActionBar) findViewById(R.id.actionbar_edit);
        et_content = (EditText) findViewById(R.id.edit_content);
        rl_pic = (RelativeLayout) findViewById(R.id.pic_content);

        feedback = new Feedback();
    }

    @Override
    protected void setupViews(Bundle bundle) {
        // TODO Auto-generated method stub

        rl_pic.setVisibility(View.GONE);
        et_content.setHint("请提出您的宝贵建议吧！");

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        actionbar.setTitle("反馈建议");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAction(new Action() {

            @Override
            public int getDrawable() {
                // TODO Auto-generated method stub
                return R.drawable.logo;
            }

            @Override
            public void performAction(View view) {
                // TODO Auto-generated method stub
                finish();
            }
            
        });
        actionbar.addAction(new Action() {
            
            @Override
            public void performAction(View view) {
                // TODO Auto-generated method stub
                String feedbackstr = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(feedbackstr)) {
                    ActivityUtil.show(mContext, "内容不能为空");
                    return;
                }
                commit(feedbackstr);
            }
            
            @Override
            public int getDrawable() {
                // TODO Auto-generated method stub
                return R.drawable.btn_comment_publish;
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

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    private void commit(String content) {
        feedback.setContent(content);
        feedback.save(FeedbackActivity.this, new SaveListener() {
            
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ActivityUtil.show(FeedbackActivity.this, "提交成功！");
                LogUtils.i(TAG,"创建成功。");
                finish();
            }
            
            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                ActivityUtil.show(FeedbackActivity.this, "提交失败！");
                LogUtils.i(TAG,"创建失败。");
            }
        });
    }
}
