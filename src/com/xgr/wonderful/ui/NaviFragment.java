package com.xgr.wonderful.ui;

import net.youmi.android.offers.OffersManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.xgr.wonderful.MyApplication;
import com.xgr.wonderful.R;
import com.xgr.wonderful.entity.User;
import com.xgr.wonderful.utils.Constant;
import com.xgr.wonderful.view.RoundImageView;

/**
 * 导航栏
 * 
 * @author yl
 * 
 */
public class NaviFragment extends Fragment implements OnClickListener {

    private static final int HOMEFRAGMENT = 0;
    private static final int SETTINGSFRAGMENT = 1;
    private static final int FEEDBACKFRAGMENT = 2;
    private static final int INTROFRAGMENT = 3;
    private static final int ABOUTFRAGMENT = 4;
    private static final int DINNERFRAGMENT = 5;
    private static final int MYFRAGMENT = 6;

    private MainActivity mActivity;
    private TextView navi_home;
    private TextView navi_settings;
    private TextView navi_feedback;
    private TextView navi_intro;
    private TextView navi_about;
    private RoundImageView riv_navi_photo;

    Mainfragment mMainFMainfragment;
    SettingsFragment mSettingsfragment;
    AboutFragment mAboutFragment;
    FavFragment mFavFragment;
    MyFragment mMyFragment;

    private FragmentManager fragmentManager;

    private User currentUser;

    private ChangeIconReceiver receiver;
    private String fileUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View rootView;// 缓存Fragment view

    /**
     * 显示左边导航栏fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_navi, null);
        }

        fragmentManager = getFragmentManager();

        IntentFilter refreshFilter = new IntentFilter();
        refreshFilter.addAction(Constant.CHANGE_ICON_ACTION);
        receiver = new ChangeIconReceiver();
        getActivity().registerReceiver(receiver, refreshFilter);

        init();

        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        mActivity = (MainActivity) activity;
        super.onAttach(activity);
    }

    /**
     * 初始化，设置点击事件
     */
    private void init() {
        riv_navi_photo = (RoundImageView) rootView.findViewById(R.id.iv_navi_personalsetting);
        navi_home = (TextView) rootView.findViewById(R.id.tv_navi_main);
        navi_feedback = (TextView) rootView.findViewById(R.id.tv_navi_feedback);
        navi_settings = (TextView) rootView.findViewById(R.id.tv_navi_settings);
        navi_intro = (TextView) rootView.findViewById(R.id.tv_navi_intro);
        navi_about = (TextView) rootView.findViewById(R.id.tv_navi_about);

        currentUser = BmobUser.getCurrentUser(mActivity, User.class);
        if (currentUser != null) {
            BmobFile avatarFile = currentUser.getAvatar();
            if (avatarFile != null) {
                initIcon(avatarFile.getFileUrl());
                
            }
        }

        navi_home.setSelected(true);// 默认选中菜单
        navi_feedback.setSelected(false);
        navi_settings.setSelected(false);
        navi_intro.setSelected(false);
        navi_about.setSelected(false);

        OnTabSelected(HOMEFRAGMENT);

        riv_navi_photo.setOnClickListener(this);
        navi_home.setOnClickListener(this);
        navi_feedback.setOnClickListener(this);
        navi_settings.setOnClickListener(this);
        navi_intro.setOnClickListener(this);
        navi_about.setOnClickListener(this);
    }

    private void initIcon(String url) {
        ImageLoader.getInstance().displayImage(url, riv_navi_photo,
                MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // TODO Auto-generated method stub
                        super.onLoadingComplete(imageUri, view, loadedImage);
                    }

                });
    }

    /**
     * 点击导航栏切换 同时更改标题
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_navi_personalsetting:
                navi_home.setSelected(false);
                navi_feedback.setSelected(false);
                navi_settings.setSelected(false);
                navi_intro.setSelected(false);
                navi_about.setSelected(false);

                OnTabSelected(MYFRAGMENT);
                break;

            case R.id.tv_navi_main:

                navi_home.setSelected(true);// 菜单设置为被选中状态，其余设置为非选中状态
                navi_feedback.setSelected(false);
                navi_settings.setSelected(false);
                navi_intro.setSelected(false);
                navi_about.setSelected(false);

                OnTabSelected(HOMEFRAGMENT);
                break;
            case R.id.tv_navi_feedback:

                navi_home.setSelected(false);
                navi_feedback.setSelected(true);
                navi_settings.setSelected(false);
                navi_intro.setSelected(false);
                navi_about.setSelected(false);

                OnTabSelected(FEEDBACKFRAGMENT);
                break;
            case R.id.tv_navi_settings://

                navi_home.setSelected(false);
                navi_feedback.setSelected(false);
                navi_settings.setSelected(true);
                navi_intro.setSelected(false);
                navi_about.setSelected(false);

                OnTabSelected(SETTINGSFRAGMENT);
                break;
            case R.id.tv_navi_intro:

                navi_home.setSelected(false);
                navi_feedback.setSelected(false);
                navi_settings.setSelected(false);
                navi_intro.setSelected(true);
                navi_about.setSelected(false);

                OnTabSelected(INTROFRAGMENT);
                break;
            case R.id.tv_navi_about:
                navi_home.setSelected(false);
                navi_feedback.setSelected(false);
                navi_settings.setSelected(false);
                navi_intro.setSelected(false);
                navi_about.setSelected(true);

                OnTabSelected(ABOUTFRAGMENT);
                break;
        }
        mActivity.getSlidingMenu().toggle();
    }

    // 选中导航中对应的tab选项
    private void OnTabSelected(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (index) {
            case HOMEFRAGMENT:
                hideFragments(transaction);
                if (null == mMainFMainfragment) {
                    mMainFMainfragment = new Mainfragment();
                    transaction.add(R.id.center, mMainFMainfragment);
                } else {
                    transaction.show(mMainFMainfragment);
                }
                break;
            case SETTINGSFRAGMENT:
                currentUser = BmobUser.getCurrentUser(mActivity, User.class);
                if (currentUser != null) {
                    hideFragments(transaction);
                    if (null == mSettingsfragment) {
                        mSettingsfragment = new SettingsFragment();
                        transaction.add(R.id.center, mSettingsfragment);
                    } else {
                        transaction.show(mSettingsfragment);
                    }

                } else {
                    // 缓存用户对象为空时， 可打开用户注册界面…
                    Toast.makeText(mActivity, "请先登录。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(mActivity, RegisterAndLoginActivity.class);
                    startActivity(intent);
                }
                break;
            case FEEDBACKFRAGMENT:
                currentUser = BmobUser.getCurrentUser(mActivity, User.class);
                // FeedbackAgent agent = new FeedbackAgent(mActivity);
                // agent.startFeedbackActivity();
                if (currentUser != null) {
                    // 允许用户使用应用,即有了用户的唯一标识符，可以作为发布内容的字段
                    hideFragments(transaction);
                    if (null == mFavFragment) {
                        mFavFragment = new FavFragment();
                        transaction.add(R.id.center, mFavFragment);
                    } else {
                        transaction.show(mFavFragment);
                    }
                } else {
                    // 缓存用户对象为空时， 可打开用户注册界面…
                    Toast.makeText(mActivity, "请先登录。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(mActivity, RegisterAndLoginActivity.class);
                    startActivity(intent);
                }
                break;
            case INTROFRAGMENT:

                OffersManager.getInstance(mActivity).showOffersWall();
                break;
            case ABOUTFRAGMENT:
                hideFragments(transaction);
                if (null == mAboutFragment) {
                    mAboutFragment = new AboutFragment();
                    transaction.add(R.id.center, mAboutFragment);
                } else {
                    transaction.show(mAboutFragment);
                }
                break;
            case MYFRAGMENT:
                currentUser = BmobUser.getCurrentUser(mActivity, User.class);
                if (currentUser != null) {
                    // 允许用户使用应用,即有了用户的唯一标识符，可以作为发布内容的字段
                    hideFragments(transaction);
                    if (null == mMyFragment) {
                        mMyFragment = new MyFragment();
                        transaction.add(R.id.center, mMyFragment);
                    } else {
                        transaction.show(mMyFragment);
                    }
                } else {
                    // 缓存用户对象为空时， 可打开用户注册界面…
                    Toast.makeText(mActivity, "请先登录。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(mActivity, RegisterAndLoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有fragment都置为隐藏状态
     * 
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mMainFMainfragment != null) {
            transaction.hide(mMainFMainfragment);
        }
        if (mSettingsfragment != null) {
            transaction.hide(mSettingsfragment);
        }
        if (mAboutFragment != null) {
            transaction.hide(mAboutFragment);
        }
        if (mFavFragment != null) {
            transaction.hide(mFavFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
    }

    private class ChangeIconReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(Constant.CHANGE_ICON_ACTION)) {
                Log.v("RECEIVER", "receiver data");
                fileUrl = intent.getStringExtra("FILEURL");
                initIcon(fileUrl);
            }
        }

    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onDestroy()
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

}
