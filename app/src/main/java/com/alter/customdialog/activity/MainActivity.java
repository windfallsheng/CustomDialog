package com.alter.customdialog.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.alter.customdialog.R;
import com.alter.customdialog.entity.GroupMemberEntity;
import com.alter.customdialog.entity.ShareContent;
import com.alter.customdialog.view.CommonShareToDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mBtnShareSingle;
    private Button mBtnShareMult;
    private Button mBtnShareSingleGroup;
    private Button mBtnShareMultGroup;
    private CommonShareToDialog mShareDialogSingle;
    private CommonShareToDialog mShareDialogMult;
    private CommonShareToDialog mShareDialogSingleGroup;
    private CommonShareToDialog mShareDialogMultGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mBtnShareSingle = (Button) findViewById(R.id.button_share_single);
        mBtnShareMult = (Button) findViewById(R.id.button_share_mult);
        mBtnShareSingleGroup = (Button) findViewById(R.id.button_share_single_group);
        mBtnShareMultGroup = (Button) findViewById(R.id.button_share_mult_group);
        mBtnShareSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<GroupMemberEntity> groupMemberEntities = new ArrayList<>();
                List<String> picUrls = new ArrayList<>();
                picUrls.add("drawable://" + R.drawable.icon_user);
                GroupMemberEntity memberEntity = new GroupMemberEntity(1, "Mia", picUrls);
                groupMemberEntities.add(memberEntity);
                ShareContent shareContent = new ShareContent("百万英雄赢百万，终极一问，脱离贫困，快来参与答题吧！", "http://app.toutiao.com/news_article/#news_article", "");
                if (mShareDialogSingle == null) {
                    mShareDialogSingle = new CommonShareToDialog.Builder(MainActivity.this)
                            .setTitle("发送给：")
                            .setGroupMemberEntities(groupMemberEntities)
                            .setShareContent(shareContent)
                            .setOnSendClickListener(new CommonShareToDialog.Builder.OnSendClickListener() {
                                @Override
                                public void onSendClick(CommonShareToDialog dialog, String editContent) {
                                    Toast.makeText(MainActivity.this, "已分享", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .setOnContentClickListener(new CommonShareToDialog.Builder.OnContentClickListener() {
                                @Override
                                public void OnContentClick(CommonShareToDialog dialog) {
                                    // todo startActivity(intent);跳转页面
                                }
                            })
                            .setOnCancleClickListener(new CommonShareToDialog.Builder.OnCancleClickListener() {
                                @Override
                                public void OnCancleClick(CommonShareToDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).create();
                    Window window = mShareDialogSingle.getWindow();
                    window.setGravity(Gravity.CENTER);
                    window.setWindowAnimations(R.style.CenterDialog_Animation);
                    window.getDecorView().setPadding(0, 0, 0, 0);
                    WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;// 设置宽度为全屏
                    lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75);
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);
                    mShareDialogSingle.setCanceledOnTouchOutside(true);
                }
                if (mShareDialogSingle != null && !mShareDialogSingle.isShowing()) {
                    mShareDialogSingle.show();
                }
            }
        });

        mBtnShareMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<GroupMemberEntity> groupMemberEntities = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    List<String> picUrls = new ArrayList<>();
                    picUrls.add("drawable://" + R.drawable.icon_user);
                    GroupMemberEntity memberEntity = new GroupMemberEntity(5, "", picUrls);
                    groupMemberEntities.add(memberEntity);
                }
                ShareContent shareContent = new ShareContent("百万英雄赢百万，终极一问，脱离贫困，快来参与答题吧！", "http://app.toutiao.com/news_article/#news_article", "drawable://" + R.drawable.icon_share);
                if (mShareDialogMult == null) {
                    mShareDialogMult = new CommonShareToDialog.Builder(MainActivity.this)
                            .setTitle("发送给：")
                            .setGroupMemberEntities(groupMemberEntities)
                            .setShareContent(shareContent)
                            .setOnSendClickListener(new CommonShareToDialog.Builder.OnSendClickListener() {
                                @Override
                                public void onSendClick(CommonShareToDialog dialog, String editContent) {
                                    Toast.makeText(MainActivity.this, "已分享", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .setOnContentClickListener(new CommonShareToDialog.Builder.OnContentClickListener() {
                                @Override
                                public void OnContentClick(CommonShareToDialog dialog) {
                                    // todo startActivity(intent);跳转页面
                                }
                            })
                            .setOnCancleClickListener(new CommonShareToDialog.Builder.OnCancleClickListener() {
                                @Override
                                public void OnCancleClick(CommonShareToDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).create();
                    Window window = mShareDialogMult.getWindow();
                    window.setGravity(Gravity.CENTER);
                    window.setWindowAnimations(R.style.CenterDialog_Animation);
                    window.getDecorView().setPadding(0, 0, 0, 0);
                    WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;// 设置宽度为全屏
                    lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75);
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);
                    mShareDialogMult.setCanceledOnTouchOutside(true);
                }
                if (mShareDialogMult != null && !mShareDialogMult.isShowing()) {
                    mShareDialogMult.show();
                }
            }
        });
        mBtnShareSingleGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<GroupMemberEntity> groupMemberEntities = new ArrayList<>();
                List<String> picUrls = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    picUrls.add("drawable://" + R.drawable.icon_user);
                }
                GroupMemberEntity memberEntity = new GroupMemberEntity(3, "幸福一家人", picUrls);
                groupMemberEntities.add(memberEntity);
                ShareContent shareContent = new ShareContent("百万英雄赢百万，终极一问，脱离贫困，快来参与答题吧！", "http://app.toutiao.com/news_article/#news_article", "drawable://" + R.drawable.icon_share);
                if (mShareDialogSingleGroup == null) {
                    mShareDialogSingleGroup = new CommonShareToDialog.Builder(MainActivity.this)
                            .setTitle("发送给：")
                            .setGroupMemberEntities(groupMemberEntities)
                            .setShareContent(shareContent)
                            .setOnSendClickListener(new CommonShareToDialog.Builder.OnSendClickListener() {
                                @Override
                                public void onSendClick(CommonShareToDialog dialog, String editContent) {
                                    Toast.makeText(MainActivity.this, "已分享", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .setOnContentClickListener(new CommonShareToDialog.Builder.OnContentClickListener() {
                                @Override
                                public void OnContentClick(CommonShareToDialog dialog) {
                                    // todo startActivity(intent);跳转页面
                                }
                            })
                            .setOnCancleClickListener(new CommonShareToDialog.Builder.OnCancleClickListener() {
                                @Override
                                public void OnCancleClick(CommonShareToDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).create();
                    Window window = mShareDialogSingleGroup.getWindow();
                    window.setGravity(Gravity.CENTER);
                    window.setWindowAnimations(R.style.CenterDialog_Animation);
                    window.getDecorView().setPadding(0, 0, 0, 0);
                    WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;// 设置宽度为全屏
                    lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75);
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);
                    mShareDialogSingleGroup.setCanceledOnTouchOutside(true);
                }
                if (mShareDialogSingleGroup != null && !mShareDialogSingleGroup.isShowing()) {
                    mShareDialogSingleGroup.show();
                }
            }
        });
        mBtnShareMultGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<GroupMemberEntity> groupMemberEntities = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    List<String> picUrls = new ArrayList<>();
                    for (int j = 0; j < 9; j++) {
                        picUrls.add("drawable://" + R.drawable.icon_user);
                    }
                    GroupMemberEntity memberEntity = new GroupMemberEntity(4, "", picUrls);
                    groupMemberEntities.add(memberEntity);
                }
                ShareContent shareContent = new ShareContent("百万英雄赢百万，终极一问，脱离贫困，快来参与答题吧！", "http://app.toutiao.com/news_article/#news_article", "drawable://" + R.drawable.icon_share);
                if (mShareDialogMultGroup == null) {
                    mShareDialogMultGroup = new CommonShareToDialog.Builder(MainActivity.this)
                            .setTitle("发送给：")
                            .setGroupMemberEntities(groupMemberEntities)
                            .setShareContent(shareContent)
                            .setOnSendClickListener(new CommonShareToDialog.Builder.OnSendClickListener() {
                                @Override
                                public void onSendClick(CommonShareToDialog dialog, String editContent) {
                                    Toast.makeText(MainActivity.this, "已分享", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .setOnContentClickListener(new CommonShareToDialog.Builder.OnContentClickListener() {
                                @Override
                                public void OnContentClick(CommonShareToDialog dialog) {
                                    // todo startActivity(intent);跳转页面
                                }
                            })
                            .setOnCancleClickListener(new CommonShareToDialog.Builder.OnCancleClickListener() {
                                @Override
                                public void OnCancleClick(CommonShareToDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).create();
                    Window window = mShareDialogMultGroup.getWindow();
                    window.setGravity(Gravity.CENTER);
                    window.setWindowAnimations(R.style.CenterDialog_Animation);
                    window.getDecorView().setPadding(0, 0, 0, 0);
                    WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;// 设置宽度为全屏
                    lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75);
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);
                    mShareDialogMultGroup.setCanceledOnTouchOutside(true);
                }
                if (mShareDialogMultGroup != null && !mShareDialogMultGroup.isShowing()) {
                    mShareDialogMultGroup.show();
                }
            }
        });
    }
}
