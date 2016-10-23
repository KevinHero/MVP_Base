package com.apanda.base.Module.Gank.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import com.apanda.base.consts.Const;
import com.apanda.base.Module.Gank.adapter.ListViewMainAdapter;
import com.apanda.base.Module.Gank.bean.DayBean;
import com.apanda.base.R;
import com.apanda.base.base.BaseActivity;


public class HistoryActivity extends BaseActivity {

    private DayBean mDayBean;
    private ListView lv_history;
    private Context mContext;


    public static void startActivity(Context context, DayBean dayBean) {
        Intent mIntent = new Intent(context, HistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dayBean", dayBean);
        mIntent.putExtras(bundle);
        context.startActivity(mIntent);
    }

    @Override
    protected void refresh(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mContext = this;
        mDayBean = (DayBean) getIntent().getSerializableExtra("dayBean");

        startProgressDialog();
        lv_history = (ListView) findViewById(R.id.lv_history);


        Const.isHistory = true;
        lv_history.setAdapter(new ListViewMainAdapter(mContext, mDayBean, true));

        stopProgressDialog();
    }


}
