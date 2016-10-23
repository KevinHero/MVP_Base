package com.apanda.base.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apanda.base.R;


/**
 * Created by Android on 2016/10/8.
 */

public class SettingButton extends FrameLayout implements View.OnClickListener {

    private TextView _titleText;
    private TextView _subtitle;
    private ImageView _logo;
    private LinearLayout _button;

    public SettingButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public SettingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        LayoutInflater.from(context).inflate(R.layout.setting_button, this);
        _titleText = (TextView) findViewById(R.id.title);
        _subtitle = (TextView) findViewById(R.id.subtitle);
        _logo = (ImageView) findViewById(R.id.logo);
        _button = (LinearLayout) findViewById(R.id.button);
        _button.setClickable(true);
    }

    public SettingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onClick(View _view) {

    }

    public void setTitleText(String text) {
        _titleText.setText(text);
    }

    public ImageView getImageView() {
        return _logo;
    }

    public void imageGone() {
        _logo.setVisibility(GONE);
    }

    public void imageVisiablity() {
        _logo.setVisibility(VISIBLE);
    }

    public void subtitleGone() {
        _subtitle.setVisibility(GONE);
    }

    public void subtitleVisiablity() {
        _subtitle.setVisibility(VISIBLE);
    }


    public void setSubtitleText(String text) {
        _subtitle.setText(text);
    }

    public void setButtonListener(OnClickListener l) {

        _button.setOnClickListener(l);
    }

}
