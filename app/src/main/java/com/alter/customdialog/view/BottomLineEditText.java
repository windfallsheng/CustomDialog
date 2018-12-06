package com.alter.customdialog.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import com.alter.customdialog.R;


/**
 * @CreateDate: 2018/1/26
 * @Author: lzsheng
 * @Description:
 * @Version:
 */
public class BottomLineEditText extends EditText implements View.OnFocusChangeListener {

    private Context context;

    public void setOnCheckInputListener(OnCheckInputListener onCheckInputListener) {
        this.onCheckInputListener = onCheckInputListener;
    }

    private OnCheckInputListener onCheckInputListener;
    private GradientDrawable drawable;

    /**
     * 检测输入是否符合要求的回调
     */
    public interface OnCheckInputListener {
        /**
         * 检测输入的方法
         *
         * @param v   点击的view
         * @param str 输入的字符串
         * @return 检测成功返回true, 检测失败返回false
         */
        boolean checkInput(View v, String str);
    }

    public BottomLineEditText(Context context) {
        this(context, null);
    }

    public BottomLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayerDrawable layerDrawable = (LayerDrawable) getBackground();
        drawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.shape_item_edit_text_bottom);
        setOnFocusChangeListener(this);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            drawable.setStroke((int)context.getResources().getDimension(R.dimen.d_seven), context.getResources().getColor(R.color.green_09bb07));
        } else {
            if (onCheckInputListener != null && onCheckInputListener.checkInput(this, getText().toString().trim())) {
                drawable.setStroke((int)context.getResources().getDimension(R.dimen.d_seven), context.getResources().getColor(R.color.gray_666));
            } else if (onCheckInputListener == null) {
                drawable.setStroke((int)context.getResources().getDimension(R.dimen.d_seven), context.getResources().getColor(R.color.gray_666));
            } else {
                drawable.setStroke((int)context.getResources().getDimension(R.dimen.d_seven), context.getResources().getColor(R.color.gray_666));

            }
        }
    }
}
