package com.xgr.wonderful.ui;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;

import cn.bmob.v3.Bmob;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.xgr.wonderful.R;
import com.xgr.wonderful.ui.base.BaseActivity;
import com.xgr.wonderful.utils.LogUtils;
import com.xgr.wonderful.utils.UmengStat;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-2-21
 * TODO 闪屏界面，根据指定时间进行跳转
 * 		在activity_splash.xml中加入background属性并传入图片资源ID即可
 */
public class SplashActivity extends BaseActivity {

	private static final long DELAY_TIME = 3500L;
	
	private TextView tv_above;
	
	private TextView tv_below;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Bmob.initialize(this, "99f123a9eae29ac8e544d65514c85831");
		
		LogUtils.i(TAG,TAG + " Launched ！");
		MobclickAgent.openActivityDurationTrack(UmengStat.IS_OPEN_ACTIVITY_AUTO_STAT);
		FeedbackAgent agent = new FeedbackAgent(this);
		agent.sync();
		
		tv_above = (TextView) findViewById(R.id.logo_des1);
		tv_below = (TextView) findViewById(R.id.logo_des2);
		
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.show_logo_anim);
		Animation animBelow = AnimationUtils.loadAnimation(this, R.anim.show_logo_below_anim);
		tv_above.startAnimation(anim);
		tv_below.startAnimation(animBelow);
		
		
		redirectByTime();		
		if(sputil.getValue("isPushOn", true)){
			PushAgent mPushAgent = PushAgent.getInstance(mContext);
			mPushAgent.enable();
			LogUtils.i(TAG,"device_token:"+UmengRegistrar.getRegistrationId(mContext));
		}else{
			PushAgent mPushAgent = PushAgent.getInstance(mContext);
			mPushAgent.disable();
		}
		
		AdManager.getInstance(mContext).init("67daabfc8ffec9c7", "7748a02fe32d6532", false);
		OffersManager.getInstance(mContext);
	}
	
	/**
	 * 根据时间进行页面跳转
	 */
	private void redirectByTime() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				redictToActivity(SplashActivity.this, MainActivity.class, null);
				finish();
			}
		}, DELAY_TIME);
	}

}
