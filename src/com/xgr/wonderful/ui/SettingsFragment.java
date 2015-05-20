package com.xgr.wonderful.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.xgr.wonderful.R;
import com.xgr.wonderful.MyApplication;
import com.xgr.wonderful.entity.User;
import com.xgr.wonderful.ui.PersonalFragment.IProgressControllor;
import com.xgr.wonderful.ui.base.BaseHomeFragment;
import com.xgr.wonderful.utils.ActivityUtil;
import com.xgr.wonderful.utils.CacheUtils;
import com.xgr.wonderful.utils.Constant;
import com.xgr.wonderful.utils.LogUtils;

public class SettingsFragment extends BaseHomeFragment implements OnClickListener, OnCheckedChangeListener {

    TextView logout;
    RelativeLayout update;
    RelativeLayout cleanCache;
    CheckBox pushSwitch;
    RelativeLayout feedback;
    
    private FragmentManager fragmentManager;
    Mainfragment mMainFMainfragment;

    static final int UPDATE_SEX = 11;
    static final int UPDATE_ICON = 12;
    static final int GO_LOGIN = 13;
    static final int UPDATE_SIGN = 14;
    static final int EDIT_SIGN = 15;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_settings;
    }

    @Override
    protected void findViews(View view) {
        // TODO Auto-generated method stub
        logout = (TextView) view.findViewById(R.id.user_logout);
        update = (RelativeLayout) view.findViewById(R.id.settings_update);
        cleanCache = (RelativeLayout) view.findViewById(R.id.settings_cache);
        pushSwitch = (CheckBox) view.findViewById(R.id.settings_push_switch);
        feedback = (RelativeLayout) view.findViewById(R.id.settings_feedback);

        fragmentManager = getFragmentManager();
    }

    @Override
    protected void setupViews(Bundle bundle) {
        // TODO Auto-generated method stub
        initPersonalInfo();
    }

    private void initPersonalInfo() {
        User user = BmobUser.getCurrentUser(mContext, User.class);
        if (user != null) {

            logout.setText("退出登录");
        } else {
            logout.setText("登录");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);

    }

    /**
     * 判断用户是否登录
     * 
     * @return
     */
    private boolean isLogined() {
        BmobUser user = BmobUser.getCurrentUser(mContext, User.class);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        logout.setOnClickListener(this);
        update.setOnClickListener(this);
        cleanCache.setOnClickListener(this);
        pushSwitch.setOnCheckedChangeListener(this);
        feedback.setOnClickListener(this);
    }

    @Override
    protected void fetchData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.user_logout:
                if (isLogined()) {
                    BmobUser.logOut(mContext);
                    ActivityUtil.show(getActivity(), "登出成功。");
                    redictToLogin(GO_LOGIN);
                } else {
                    redictToLogin(GO_LOGIN);
                }

                break;
            case R.id.settings_update:
                Toast.makeText(mContext, "正在检查。。。", Toast.LENGTH_SHORT).show();
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        // TODO Auto-generated method stub
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                UmengUpdateAgent.forceUpdate(mContext);
                break;
            case R.id.settings_cache:
                ImageLoader.getInstance().clearDiscCache();
                ActivityUtil.show(getActivity(), "清除缓存完毕");
                break;
            case R.id.settings_feedback:
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);

            default:
                break;
        }
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void backToMainFragment() {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(this);
//        if (null == mMainFMainfragment) {
//            mMainFMainfragment = new Mainfragment();
//            transaction.add(R.id.center, mMainFMainfragment);
//        } else {
//            transaction.show(mMainFMainfragment);
//        }
        mMainFMainfragment = new Mainfragment();
        transaction.show(mMainFMainfragment);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        switch (buttonView.getId()) {
            case R.id.settings_push_switch:
                if (isChecked) {
                    // 接受推送，储存值
                    sputil.setValue("isPushOn", true);
                    PushAgent mPushAgent = PushAgent.getInstance(mContext);
                    mPushAgent.enable();
                } else {
                    // 关闭推送，储存值
                    sputil.setValue("isPushOn", false);
                    PushAgent mPushAgent = PushAgent.getInstance(mContext);
                    mPushAgent.disable();
                }
                break;

            default:
                break;
        }

    }

    private void redictToLogin(int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), RegisterAndLoginActivity.class);
        startActivityForResult(intent, requestCode);
        ActivityUtil.show(mContext, "请先登录。");
    }

    String iconUrl;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case GO_LOGIN:
                    initPersonalInfo();
                    logout.setText("退出登录");
                    break;
                default:
                    break;
            }
        }
    }

}
