/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: EditNicknameActivity.java
 * Author:   xutework
 * Date:     2015-4-30 下午4:27:26
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xgr.wonderful.ui;

import com.xgr.wonderful.ui.base.BaseFragment;
import com.xgr.wonderful.ui.base.BaseHomeActivity;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EditNicknameActivity extends BaseHomeActivity {

    @Override
    protected String getActionBarTitle() {
        // TODO Auto-generated method stub
        return "修改昵称";
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void onHomeActionClick() {
        // TODO Auto-generated method stub
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected BaseFragment getFragment() {
        // TODO Auto-generated method stub
        return EditNIcknameFragment.newInstance();
    }

    @Override
    protected void addActions() {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }
}
