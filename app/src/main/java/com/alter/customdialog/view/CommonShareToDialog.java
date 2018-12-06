package com.alter.customdialog.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alter.customdialog.R;
import com.alter.customdialog.adapter.DialogGroupMemberAdapter;
import com.alter.customdialog.entity.GroupMemberEntity;
import com.alter.customdialog.entity.ShareContent;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreateDate: 2018/1/24
 * @Author: lzsheng
 * @Description: 分享功能的通用对话框，其中展示成员及群头像和名称时用到的适配器 {@link com.alter.customdialog.adapter.DialogGroupMemberAdapter}
 * @Version:
 */
public class CommonShareToDialog extends Dialog {

    public CommonShareToDialog(Context context) {
        super(context);
    }

    public CommonShareToDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private boolean noTitle = true;
        private String title;
        private String message;
        private ImageView ivShowDetail;
        private List<GroupMemberEntity> mGroupMemberEntities;
        private DialogGroupMemberAdapter mGroupMemberAdapter;
        private ShareContent shareContent;
        private boolean isShow;
        private Animation mRotateAnimation;
        private OnSendClickListener mOnSendClickListener;
        private OnCancleClickListener mOnCancleClickListener;
        private OnContentClickListener mOnContentClickListener;

        private Handler mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 0:
                        // 执行动画
                        mRotateAnimation = AnimationUtils.loadAnimation(context, R.anim.imageviwe_rotate);
                        // 动画执行完后停留在执行完的状态
                        mRotateAnimation.setFillAfter(true);
                        ivShowDetail.startAnimation(mRotateAnimation);
                        break;
                    case 1:
                        // 执行动画
                        mRotateAnimation = AnimationUtils.loadAnimation(context, R.anim.imageviwe_rotate_normal);
                        // 动画执行完后停留在执行完的状态
                        mRotateAnimation.setFillAfter(true);
                        ivShowDetail.startAnimation(mRotateAnimation);
                        break;
                    default:
                        break;
                }
            }

            ;
        };

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = message;
            return this;
        }

        public Builder setNoTitle(boolean noTitle) {
            this.noTitle = noTitle;
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param resId
         * @return
         */
        public Builder setTitle(int resId) {
            this.title = (String) context.getText(resId);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param resId
         * @return
         */
        public Builder setMessage(int resId) {
            this.message = (String) context.getText(resId);
            return this;
        }

        public Builder setGroupMemberEntities(List<GroupMemberEntity> groupMemberEntities) {
            mGroupMemberEntities = groupMemberEntities;
            return this;
        }

        public Builder setShareContent(ShareContent shareContent) {
            this.shareContent = shareContent;
            return this;
        }

        public Builder setOnSendClickListener(OnSendClickListener onSendClickListener) {
            mOnSendClickListener = onSendClickListener;
            return this;
        }

        public Builder setOnCancleClickListener(OnCancleClickListener onCancleClickListener) {
            mOnCancleClickListener = onCancleClickListener;
            return this;
        }

        public Builder setOnContentClickListener(OnContentClickListener onContentClickListener) {
            mOnContentClickListener = onContentClickListener;
            return this;
        }

        public interface OnSendClickListener {
            void onSendClick(CommonShareToDialog dialog, String editContent);
        }

        public interface OnCancleClickListener {
            void OnCancleClick(CommonShareToDialog dialog);
        }

        public interface OnContentClickListener {
            void OnContentClick(CommonShareToDialog dialog);
        }

        public CommonShareToDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CommonShareToDialog dialog = new CommonShareToDialog(context, R.style.common_dialog);
            final View layoutContainer = inflater.inflate(R.layout.dialog_common_share_to, null);
            // 标题
            TextView tvTitle = (TextView) layoutContainer.findViewById(R.id.textview_title);
            // 单个群组成员的详细列表
            final RelativeLayout layoutGroupDetail = (RelativeLayout) layoutContainer.findViewById(R.id.layout_group_detail);
            final RecyclerView rvGroupDetail = (RecyclerView) layoutContainer.findViewById(R.id.recyclerview_group_detail);
            GridLayoutManager GroupDetailManager = new GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false);
            rvGroupDetail.setLayoutManager(GroupDetailManager);
            int space = context.getResources().getDimensionPixelSize(R.dimen.seven);
            rvGroupDetail.addItemDecoration(new SpaceItemDecoration(space, space));
            // 点击展开群成员图标
            ivShowDetail = (ImageView) layoutContainer.findViewById(R.id.imageview_arrow_down);
            // 头像显示的RecyclerView
            RecyclerView rvMemberMultiple = (RecyclerView) layoutContainer.findViewById(R.id.recyclerview_member_multiple);
            // 分享内容的布局
            final LinearLayout layoutShareContent = (LinearLayout) layoutContainer.findViewById(R.id.layout_share_content);
            ImageView ivShareIcon = (ImageView) layoutContainer.findViewById(R.id.imageview_share_icon);
            TextView tvShareContent = (TextView) layoutContainer.findViewById(R.id.textview_share_content);
            // 编辑输入框
            final BottomLineEditText etShareText = (BottomLineEditText) layoutContainer.findViewById(R.id.edittext_share_text);
            etShareText.requestFocus(); //获取焦点 光标出现
            final TextView tvInputLabel = (TextView) layoutContainer.findViewById(R.id.textview_input_label);
            tvInputLabel.setVisibility(View.GONE);
            // 操作按钮
            TextView tvConfirm = (TextView) layoutContainer.findViewById(R.id.textview_send);
            TextView tvCancle = (TextView) layoutContainer.findViewById(R.id.textview_cancel);
            if (noTitle) {
                if (title != null) {
                    tvTitle.setText(title);
                }
            } else {
                tvTitle.setVisibility(View.GONE);
            }
            ivShowDetail.setOnClickListener(new View.OnClickListener() {

                DialogGroupMemberAdapter groupAdapter;

                @Override
                public void onClick(View view) {
                    ivShowDetail.setFocusable(true);
                    ivShowDetail.setFocusableInTouchMode(true);
                    ivShowDetail.requestFocus();
                    hideSoftKeyboard(etShareText);
                    isShow = !isShow;
                    if (isShow) {
                        mHandler.sendEmptyMessage(0);
                        layoutGroupDetail.setVisibility(View.VISIBLE);
                        layoutShareContent.setVisibility(View.GONE);
                        if (groupAdapter == null) {
                            groupAdapter = new DialogGroupMemberAdapter(context);
                        } else {
                            runLayoutAnimation(rvGroupDetail);
                        }
                        List<GroupMemberEntity> entities = new ArrayList<>();
                        List<String> urlStrs = mGroupMemberEntities.get(0).getImageUrls();
                        for (String url : urlStrs) {
                            List<String> strs = new ArrayList<>();
                            strs.add(url);
                            GroupMemberEntity entity = new GroupMemberEntity(2, "", strs);
                            entities.add(entity);
                        }
                        groupAdapter.setGroupMemberEntities(entities);
                        rvGroupDetail.setAdapter(groupAdapter);
                    } else {
                        mHandler.sendEmptyMessage(1);
                        if (groupAdapter != null) {
                            groupAdapter.setGroupMemberEntities(null);
                            groupAdapter.notifyDataSetChanged();
                        }
                        layoutGroupDetail.setVisibility(View.GONE);
                        layoutShareContent.setVisibility(View.VISIBLE);
                    }
                }
            });
            if (mGroupMemberEntities != null) {
                ivShowDetail.setVisibility(View.GONE);
                layoutGroupDetail.setVisibility(View.GONE);
                GridLayoutManager gridLayoutManager;
                if (mGroupMemberEntities.size() == 1) {
                    gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
                    GroupMemberEntity groupMemberEntity = mGroupMemberEntities.get(0);
                    if (groupMemberEntity.getMemeberType() == 3) {// 如果是一个单个的群组，则显示展示图标
                        ivShowDetail.setVisibility(View.VISIBLE);
                    }
                } else {
                    gridLayoutManager = new GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false);
                }
                if (mGroupMemberAdapter == null) {
                    mGroupMemberAdapter = new DialogGroupMemberAdapter(context);
                }
                mGroupMemberAdapter.setGroupMemberEntities(mGroupMemberEntities);
                rvMemberMultiple.setLayoutManager(gridLayoutManager);
//                int spacing = context.getResources().getDimensionPixelSize(R.dimen.seven_dp);
//                rvMemberMultiple.addItemDecoration(new SpaceItemDecoration(spacing, spacing));
                rvMemberMultiple.setAdapter(mGroupMemberAdapter);
            }
            if (shareContent != null) {
                tvShareContent.setText(shareContent.getDesc());
                ImageLoader.getInstance().displayImage(shareContent.getIconUrl(), ivShareIcon);
            }
            layoutContainer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    layoutContainer.setFocusable(true);
                    layoutContainer.setFocusableInTouchMode(true);
                    layoutContainer.requestFocus();
                    hideSoftKeyboard(etShareText);
                    return false;
                }
            });
            etShareText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});// 在代码里设置输入字数的上限，也可以在xml文件里设置
            etShareText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s)) {
                        tvInputLabel.setVisibility(View.GONE);
                    } else {
                        if (s.toString().length() >= 150) {
                            tvInputLabel.setVisibility(View.VISIBLE);
                            tvInputLabel.setText("最多输入150个字");
                        }
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnSendClickListener != null) {
                        mOnSendClickListener.onSendClick(dialog, etShareText.getText().toString());
                    }
                }
            });
            tvCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnCancleClickListener != null) {
                        mOnCancleClickListener.OnCancleClick(dialog);
                    }
                }
            });
            layoutShareContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layoutShareContent.setFocusable(true);
                    layoutShareContent.setFocusableInTouchMode(true);
                    layoutShareContent.requestFocus();
                    hideSoftKeyboard(etShareText);
                    if (mOnContentClickListener != null) {
                        mOnContentClickListener.OnContentClick(dialog);
                    }
                }
            });
            dialog.setContentView(layoutContainer);
            return dialog;
        }

        /**
         * @Author: lzsheng
         * @Description: 数据变化的时候触发动画
         * @Params: [recyclerView]
         * @Return: void
         */
        private void runLayoutAnimation(final RecyclerView recyclerView) {
            final Context context = recyclerView.getContext();
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }

        private void hideSoftKeyboard(EditText editText) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = imm.isActive();
            if (isOpen) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
    }
}
