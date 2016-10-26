package com.apanda.base.Module.Gank.activity


import android.os.Message
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.*
import butterknife.Bind
import butterknife.OnClick
import cn.pedant.SweetAlert.SweetAlertDialog
import com.apanda.base.R
import com.apanda.base.Utils.SharedPrefsUtils
import com.apanda.base.Widget.AutoLinkStyleTextView
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter

class SignupActivity : BaseActivity<BasePresenter<*, *>, BaseModel>() {


    @Bind(R.id.toolbar)
    internal var _Toolbar: Toolbar? = null
    @Bind(R.id.iv_logo)
    internal var _IvLogo: TextView? = null
    @Bind(R.id.edit_username)
    internal var _EditUsername: EditText? = null
    @Bind(R.id.edit_code)
    internal var _EditCode: EditText? = null
    @Bind(R.id.edit_password)
    internal var _EditPassword: EditText? = null
    @Bind(R.id.btn_signin)
    internal var _BtnSignin: Button? = null
    @Bind(R.id.ll_input_area)
    internal var _LlInputArea: LinearLayout? = null
    @Bind(R.id.btn_forget_password)
    internal var _BtnForgetPassword: AutoLinkStyleTextView? = null
    @Bind(R.id.sv_layout)
    internal var _SvLayout: ScrollView? = null
    @Bind(R.id.content_logins)
    internal var _ContentLogins: RelativeLayout? = null
    @Bind(R.id.fab)
    internal var _Fab: FloatingActionButton? = null
    @Bind(R.id.ib_qq_login)
    internal var _IbQqLogin: Button? = null
    @Bind(R.id.tv_getcode)
    internal var _TvGetcode: TextView? = null


    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_signup

    override fun initPresenter() {

    }


    protected fun initEvent() {
        _BtnForgetPassword!!.setOnClickCallBack { position ->
            if (position == 0) {
                Toast.makeText(_instance, "用户协议", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initView() {

        initToolBar(_Toolbar as Toolbar, "", R.color.title_color)
        initEvent()
    }


    @OnClick(R.id.btn_signin, R.id.ib_qq_login, R.id.tv_getcode)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_signin -> if (!TextUtils.isEmpty(_EditUsername!!.text) && !TextUtils.isEmpty(_EditCode!!.text)) {

                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("提示").setContentText("登陆成功!!!").setConfirmText("确定").setConfirmClickListener {
                    //   startActivity(UserActivity.class);
                    SharedPrefsUtils.setBooleanPreference(_instance, "LOGIN_STATUS", true)
                    finish()
                }.show()
            }
            R.id.ib_qq_login -> {
            }
            R.id.tv_getcode -> {
            }
            else -> {
            }
        }
    }


}
