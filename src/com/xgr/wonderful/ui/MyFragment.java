/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: MyFragment.java
 * Author:   xutework
 * Date:     2015-4-24 下午3:31:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.umeng.message.PushAgent;
import com.xgr.wonderful.MyApplication;
import com.xgr.wonderful.R;
import com.xgr.wonderful.entity.User;
import com.xgr.wonderful.ui.PersonalFragment.IProgressControllor;
import com.xgr.wonderful.ui.base.BaseHomeFragment;
import com.xgr.wonderful.utils.ActivityUtil;
import com.xgr.wonderful.utils.CacheUtils;
import com.xgr.wonderful.utils.Constant;
import com.xgr.wonderful.utils.LogUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MyFragment extends BaseHomeFragment implements OnClickListener, OnCheckedChangeListener {

    private CheckBox sexSwitch;

    private RelativeLayout iconLayout;
    private ImageView userIcon;

    private RelativeLayout nickLayout;
    private TextView nickName;

    private RelativeLayout signLayout;
    private TextView signature;

    private IProgressControllor mIProgressControllor;

    static final int UPDATE_SEX = 11;
    static final int UPDATE_ICON = 12;
    static final int GO_LOGIN = 13;
    static final int UPDATE_SIGN = 14;
    static final int EDIT_SIGN = 15;
    static final int EDIT_NAME = 16;

    public static MyFragment newInstance() {
        MyFragment myFragment = new MyFragment();
        return myFragment;
    }

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_my;
    }

    @Override
    protected void findViews(View view) {
        // TODO Auto-generated method stub
        sexSwitch = (CheckBox) view.findViewById(R.id.sex_choice_switch);

        iconLayout = (RelativeLayout) view.findViewById(R.id.user_icon);
        userIcon = (ImageView) view.findViewById(R.id.user_icon_image);

        nickLayout = (RelativeLayout) view.findViewById(R.id.user_nick);
        nickName = (TextView) view.findViewById(R.id.user_nick_text);

        signLayout = (RelativeLayout) view.findViewById(R.id.user_sign);
        signature = (TextView) view.findViewById(R.id.user_sign_text);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        // TODO Auto-generated method stub
        initPersonalInfo();
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void initPersonalInfo() {
        // TODO Auto-generated method stub
        User user = BmobUser.getCurrentUser(mContext, User.class);
        if (user != null) {
            nickName.setText(user.getUsername());
            signature.setText(user.getSignature());
            if (user.getSex().equals(Constant.SEX_FEMALE)) {
                sexSwitch.setChecked(true);
                sputil.setValue("sex_settings", 0);
            } else {
                sexSwitch.setChecked(false);
                sputil.setValue("sex_settings", 1);
            }
            BmobFile avatarFile = user.getAvatar();
            if (null != avatarFile) {
                ImageLoader.getInstance().displayImage(avatarFile.getFileUrl(), userIcon,
                        MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
                        new SimpleImageLoadingListener() {

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                // TODO Auto-generated method stub
                                super.onLoadingComplete(imageUri, view, loadedImage);
                            }

                        });
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            mIProgressControllor = (IProgressControllor) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
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
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.user_icon:
                if (isLogined()) {
                    showAlbumDialog();
                } else {
                    redictToLogin(UPDATE_ICON);
                }
                break;
            case R.id.user_nick:
                if (isLogined()) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, EditNicknameActivity.class);
                    startActivityForResult(intent, EDIT_NAME);
                } else {
                    redictToLogin(UPDATE_SIGN);
                }
                break;
            case R.id.user_sign:
                if (isLogined()) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, EditSignActivity.class);
                    startActivityForResult(intent, EDIT_SIGN);
                } else {
                    redictToLogin(UPDATE_SIGN);
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

    String dateTime;
    AlertDialog albumDialog;

    public void showAlbumDialog() {
        albumDialog = new AlertDialog.Builder(mContext).create();
        albumDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_usericon, null);
        albumDialog.show();
        albumDialog.setContentView(v);
        albumDialog.getWindow().setGravity(Gravity.CENTER);

        TextView albumPic = (TextView) v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView) v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                albumDialog.dismiss();
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                getAvataFromAlbum();
            }
        });
        cameraPic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                albumDialog.dismiss();
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";
                getAvataFromCamera();
            }
        });
    }

    private void getAvataFromAlbum() {
        Intent intent2 = new Intent(Intent.ACTION_PICK, null);
        intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent2, 2);
    }

    private void getAvataFromCamera() {
        File f = new File(CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Log.e("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, 1);
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        sexSwitch.setOnCheckedChangeListener(this);

        iconLayout.setOnClickListener(this);
        nickLayout.setOnClickListener(this);
        signLayout.setOnClickListener(this);
    }

    @Override
    protected void fetchData() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton,
     * boolean)
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        switch (buttonView.getId()) {
            case R.id.sex_choice_switch:
                if (isChecked) {
                    sputil.setValue("sex_settings", 0);
                    updateSex(0);
                } else {
                    sputil.setValue("sex_settings", 1);
                    updateSex(1);
                }
                break;
            default:
                break;
        }
    }

    private void updateSex(int sex) {
        User user = BmobUser.getCurrentUser(mContext, User.class);
        if (user != null) {
            if (sex == 0) {
                user.setSex(Constant.SEX_FEMALE);
            } else {
                user.setSex(Constant.SEX_MALE);
            }
            if (mIProgressControllor != null) {
                mIProgressControllor.showActionBarProgress();
            }
            user.update(mContext, new UpdateListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    if (mIProgressControllor != null) {
                        mIProgressControllor.hideActionBarProgress();
                    }
                    ActivityUtil.show(getActivity(), "更新信息成功。");
                    LogUtils.i(TAG, "更新信息成功。");
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    // TODO Auto-generated method stub
                    if (mIProgressControllor != null) {
                        mIProgressControllor.hideActionBarProgress();
                    }
                    ActivityUtil.show(getActivity(), "更新信息失败。请检查网络~");
                    LogUtils.i(TAG, "更新失败1-->" + arg1);
                }
            });
        } else {
            redictToLogin(UPDATE_SEX);
        }

    }

    String iconUrl;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case UPDATE_SEX:
                    initPersonalInfo();
                    break;
                case UPDATE_ICON:
                    initPersonalInfo();
                    iconLayout.performClick();
                    break;
                case UPDATE_SIGN:
                    initPersonalInfo();
                    signLayout.performClick();
                    break;
                case EDIT_SIGN:
                    initPersonalInfo();
                    break;
                case EDIT_NAME:
                    initPersonalInfo();
                    break;
                case 1:
                    String files = CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime;
                    File file = new File(files);
                    if (file.exists() && file.length() > 0) {
                        Uri uri = Uri.fromFile(file);
                        startPhotoZoom(uri);
                    } else {

                    }
                    break;
                case 2:
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            // 锟斤拷锟斤拷图片
                            iconUrl = saveToSdCard(bitmap);
                            userIcon.setImageBitmap(bitmap);
                            updateIcon(iconUrl);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void updateIcon(String avataPath) {
        if (avataPath != null) {
            final BmobFile file = new BmobFile(new File(avataPath));
            if (mIProgressControllor != null) {
                mIProgressControllor.showActionBarProgress();
            }
            file.upload(mContext, new UploadFileListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    if (mIProgressControllor != null) {
                        mIProgressControllor.hideActionBarProgress();
                    }
                    LogUtils.i(TAG, "上传文件成功。" + file.getFileUrl());
                    User currentUser = BmobUser.getCurrentUser(mContext, User.class);
                    currentUser.setAvatar(file);
                    Intent intent = new Intent();
                    intent.setAction(Constant.CHANGE_ICON_ACTION);
                    intent.putExtra("FILEURL", file.getFileUrl());
                    getActivity().sendBroadcast(intent);
                    if (mIProgressControllor != null) {
                        mIProgressControllor.showActionBarProgress();
                    }
                    currentUser.update(mContext, new UpdateListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            if (mIProgressControllor != null) {
                                mIProgressControllor.hideActionBarProgress();
                            }
                            ActivityUtil.show(getActivity(), "更改头像成功。");
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            if (mIProgressControllor != null) {
                                mIProgressControllor.hideActionBarProgress();
                            }
                            ActivityUtil.show(getActivity(), "更新头像失败。请检查网络~");
                            LogUtils.i(TAG, "更新失败2-->" + arg1);
                        }
                    });
                }

                @Override
                public void onProgress(Integer arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    // TODO Auto-generated method stub
                    if (mIProgressControllor != null) {
                        mIProgressControllor.hideActionBarProgress();
                    }
                    ActivityUtil.show(getActivity(), "上传头像失败。请检查网络~");
                    LogUtils.i(TAG, "上传文件失败。" + arg1);
                }
            });
        }
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 锟斤拷锟斤拷锟斤拷锟絚rop=true锟斤拷锟斤拷锟斤拷锟节匡拷锟斤拷锟斤拷Intent锟斤拷锟斤拷锟斤拷锟斤拷示锟斤拷VIEW锟缴裁硷拷
        // aspectX aspectY 锟角匡拷叩谋锟斤拷锟�
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 锟角裁硷拷图片锟斤拷锟�
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去锟斤拷锟节憋拷
        intent.putExtra("scaleUpIfNeeded", true);// 去锟斤拷锟节憋拷
        // intent.putExtra("noFaceDetection", true);//锟斤拷锟斤拷识锟斤拷
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);

    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime + ".jpg";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LogUtils.i(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
