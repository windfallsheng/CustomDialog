package com.alter.customdialog.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alter.customdialog.view.AutoImageDownloader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Stack;

/**
 * @CreateDate: 2018/2/8
 * @Author: lzsheng
 * @Description:
 * @Version:
 */
public class MyApplication extends Application {

    // 退出整个应用可以用到
    private static Stack<Activity> activityStack;
    private static MyApplication singleton;
    //
    private static Context mContext;
    private static Thread mMainThread;
    private static long mMainTreadId;
    private static Looper mMainLooper;
    private static Handler mHandler;

    public static Handler getHandler() {
        return mHandler;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainTreadId() {
        return mMainTreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    // 退出整个应用可以用到
    // Returns the application instance
    public static MyApplication getInstance() {
        return singleton;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        Log.i("MyApplication", "addActivity==>" + activity.getClass().toString());
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                Log.i("MyApplication", "finishActivity==>" + activity.getClass().toString());
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                Log.i("MyApplication", "finishAllActivity==>" + activityStack.get(i).getClass().toString());
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApplication() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    @Override
    public void onCreate() { // 程序的入口
        // 初始化化一些.常用属性.然后放到盒子里面来
        // 上下文
        mContext = getApplicationContext();
        // 主线程
        mMainThread = Thread.currentThread();
        // 主线程Id
        mMainTreadId = android.os.Process.myTid();
        mMainLooper = getMainLooper();
        mHandler = new Handler();
        // 退出整个应用可以用到
        singleton = this;
        super.onCreate();
        init();
    }

    public void init() {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) mContext
                    .getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
            // limit
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
        // 图片加载器初始化
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize)
                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(5)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new AutoImageDownloader(this))
                .defaultDisplayImageOptions(
                        new DisplayImageOptions.Builder().cacheInMemory(true)// 启用内存缓存
                                .cacheOnDisc(true).build()).build();// 启用外存缓存
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
