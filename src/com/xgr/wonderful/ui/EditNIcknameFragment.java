/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: EditNIcknamaFragment.java
 * Author:   xutework
 * Date:     2015-4-30 下午3:33:41
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xgr.wonderful.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.xgr.wonderful.R;
import com.xgr.wonderful.entity.User;
import com.xgr.wonderful.ui.base.BaseHomeFragment;
import com.xgr.wonderful.utils.ActivityUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EditNIcknameFragment extends BaseHomeFragment {
    private Button commit;
    private EditText input;

    public static EditNIcknameFragment newInstance() {
        EditNIcknameFragment fragment = new EditNIcknameFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.change_sign;
    }

    @Override
    protected void findViews(View view) {
        // TODO Auto-generated method stub
        input = (EditText) view.findViewById(R.id.sign_comment_content);
        commit = (Button) view.findViewById(R.id.sign_comment_commit);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        // TODO Auto-generated method stub
        input.setHint("请输入昵称");
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        commit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(input.getText().toString().trim())) {
                    ActivityUtil.show(getActivity(), "请先输入。。。");
                } else {
                    updateName(input.getText().toString().trim());
                }
            }
        });
    }

    @Override
    protected void fetchData() {
        // TODO Auto-generated method stub

    }

    private void updateName(String sign) {
        User user = BmobUser.getCurrentUser(mContext, User.class);
        if (user != null && sign != null) {
            user.setUsername(sign);
            user.update(mContext, new UpdateListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    ActivityUtil.show(getActivity(), "更改信息成功。");
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    // TODO Auto-generated method stub
                    ActivityUtil.show(getActivity(), "更改信息失败。请检查网络");
                }
            });
        }
    }
}
