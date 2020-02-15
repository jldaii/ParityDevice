package com.corelibs.widget;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibs.R;
import com.corelibs.R2;
import com.corelibs.views.navigation.TranslucentNavBar;

import butterknife.BindView;


/**
 * 标题栏
 * Created by Ryan on 2016/5/23.
 */
public class NavBar extends TranslucentNavBar {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_nav_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.iv_right_left)
    ImageView ivRightLeft;
    @BindView(R2.id.iv_right)
    ImageView iv_right;
    @BindView(R2.id.rl_right_icon)
    View rlRightIcon;
    //搜索框
    @BindView(R2.id.et_search)
    EditText et_search;
    @BindView(R2.id.iv_seatch_del)
    ImageView iv_seatch_del;

    private final OnClickListener defaultBackListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Tools.CloseInputWetbod(v);
            ((Activity) getContext()).finish();
        }
    };


    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.nav;
    }

    @Override
    public void initView() {
//        setImageBackground(getResources().getColor(R.color.white));
//        setNavColorRes(R.color.status_color);
//        setStatusBarColorRes(R.color.status_color);
        setOnBackClickedListener(defaultBackListener);
    }

    /**
     * 设置返回监听, 默认的返回监听是结束Activity
     **/
    public void setOnBackClickedListener(OnClickListener listener) {
        ivBack.setVisibility(VISIBLE);
        ivBack.setOnClickListener(listener);
    }


    /**
     * 隐藏返回按钮
     **/
    public void hideBack() {
        ivBack.setVisibility(GONE);
    }

    public void showBack() {
        ivBack.setVisibility(VISIBLE);
        ivBack.setOnClickListener(defaultBackListener);
    }


    /**
     * 设置标题
     **/
    public void setTitle(String title) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(title);
    }

    /**
     * 设置标题
     **/
    public void setTitle(int title) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(title);
    }


    /**
     * 设置左侧图标-部分界面会用到-add by ymw
     *
     * @param icon 图标资源id
     */
    public void setLeftIcon(int icon) {
        ivBack.setImageResource(icon);
    }

    public void setSearchEditText(OnClickListener listener) {
        et_search.setVisibility(VISIBLE);
        et_search.setOnClickListener(listener);
    }

    public EditText getEt_Search() {
        return et_search;
    }

    public ImageView getImg_Del() {
        return iv_seatch_del;
    }

    /**
     * 显示右侧图标按钮的左侧图标
     */
    public void showLeftOfRightIcon(int iconRes, OnClickListener listener) {
        rlRightIcon.setVisibility(VISIBLE);
        ivRightLeft.setVisibility(VISIBLE);
        ivRightLeft.setImageResource(iconRes);
        ivRightLeft.setOnClickListener(listener);
    }

    /**
     * 显示右侧图标
     */
    public void showOfRightIcon(int iconRes, OnClickListener listener) {
        rlRightIcon.setVisibility(VISIBLE);
        iv_right.setVisibility(VISIBLE);
        iv_right.setImageResource(iconRes);
        iv_right.setOnClickListener(listener);
    }

    public ImageView getLeftOfRightIcon() {
        ivRightLeft.setVisibility(VISIBLE);
        return ivRightLeft;
    }

    /**
     * 显示右侧文字按钮
     */
    public void showRightText(int textResId, OnClickListener listener) {
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(textResId);
        tvRight.setOnClickListener(listener);
    }

    /**
     * 显示右侧文字按钮
     */
    public void showRightText(String text, OnClickListener listener) {
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(text);
        tvRight.setOnClickListener(listener);
    }

    /**
     * 设置右侧文字按钮
     */
    public void setRightText(String text) {
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(text);
    }


    /**
     * 获取右侧文字
     */
    public TextView getRightText() {
        tvRight.setVisibility(VISIBLE);
        return tvRight;
    }

    /**
     * 设置标题, 必须获取焦点才有走马灯效果
     */
    public void setMarqueeTitle(String name) {
        String title = name;
        SpannableString styledText = new SpannableString(title);
        styledText.setSpan(new TextAppearanceSpan(getContext(), R.style.small_text),
                tvTitle.getText().length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTitle.setText(styledText);
        requestTitleFocus();
    }

    /**
     * 获取焦点
     */
    public void requestTitleFocus() {
        tvTitle.requestFocus();
        tvTitle.setSelected(true);
    }

}
