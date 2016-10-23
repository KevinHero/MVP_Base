package com.apanda.base.Module.Login;

import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.apanda.base.R;
import com.apanda.base.Utils.SharedPrefsUtils;
import com.apanda.base.Widget.AutoLinkStyleTextView;
import com.apanda.base.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignupActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar _Toolbar;
    @Bind(R.id.iv_logo)
    TextView _IvLogo;
    @Bind(R.id.edit_username)
    EditText _EditUsername;
    @Bind(R.id.edit_code)
    EditText _EditCode;
    @Bind(R.id.edit_password)
    EditText _EditPassword;
    @Bind(R.id.btn_signin)
    Button _BtnSignin;
    @Bind(R.id.ll_input_area)
    LinearLayout _LlInputArea;
    @Bind(R.id.btn_forget_password)
    AutoLinkStyleTextView _BtnForgetPassword;
    @Bind(R.id.sv_layout)
    ScrollView _SvLayout;
    @Bind(R.id.content_logins)
    RelativeLayout _ContentLogins;
    @Bind(R.id.fab)
    FloatingActionButton _Fab;
    @Bind(R.id.ib_qq_login)
    Button _IbQqLogin;
    @Bind(R.id.tv_getcode)
    TextView _TvGetcode;


    @Override
    protected void refresh(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    public void initPresenter() {

    }


    protected void initEvent() {
        _BtnForgetPassword.setOnClickCallBack(new AutoLinkStyleTextView.ClickCallBack() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    Toast.makeText(_instance, "用户协议", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initView() {

        initToolBar(_Toolbar, "", R.color.title_color);
        initEvent();
    }


    @OnClick({R.id.btn_signin, R.id.ib_qq_login, R.id.tv_getcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signin:
                if (!TextUtils.isEmpty(_EditUsername.getText()) && !TextUtils.isEmpty(_EditCode.getText())) {

                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("提示")
                            .setContentText("登陆成功!!!")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                 //   startActivity(UserActivity.class);
                                    SharedPrefsUtils.setBooleanPreference(_instance, "LOGIN_STATUS", true);
                                    finish();
                                }
                            })
                            .show();
                }
                break;
            case R.id.ib_qq_login:
                break;
            case R.id.tv_getcode:
                break;
            default:
                break;
        }
    }


}
