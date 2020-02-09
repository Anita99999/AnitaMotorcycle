package com.anita.anitamotorcycle.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.anita.anitamotorcycle.R;

/**
 * 自定义控件，获得自定义属性
 * input_icon:输入框前面图标
 * input_hint:输入框提示内容
 * is_password:输入内容是否需要密文显示
 */
public class InputView extends FrameLayout {

    //获取attrs.xml中自定义属性
    private int inputIcon;
    private  String inputHint;
    private  boolean isPassword;

    private View mView;
    private ImageView mIvIcon;
    private EditText mEtInput;

    public InputView(Context context) {
        super(context);
        init(context,null);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    /**
     * 初始化方法
     */
    private  void init(Context context, AttributeSet attr){
        if(attr == null)
            return;
        //获得自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.inputView);
        inputIcon = typedArray.getResourceId(R.styleable.inputView_input_icon, R.mipmap.logo);   //当获取不到input_icon时，默认图片是logo
        inputHint = typedArray.getString(R.styleable.inputView_input_hint);
        isPassword=typedArray.getBoolean(R.styleable.inputView_is_password,false);  //当获取不到Boolean变量时，默认为false
        //使用完后手动释放
        typedArray.recycle();

        //绑定layout布局
        mView = LayoutInflater.from(context).inflate(R.layout.input_view,this,false);
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mEtInput = mView.findViewById(R.id.et_input);

        //布局关联属性
        mIvIcon.setImageResource(inputIcon);
        mEtInput.setHint(inputHint);
        mEtInput.setInputType(isPassword ? InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_PHONE);

        addView(mView);
    }

    /*
       返回输入的内容
     */
    public String getInputStr(){
        return mEtInput.getText().toString().trim();
    }


}
