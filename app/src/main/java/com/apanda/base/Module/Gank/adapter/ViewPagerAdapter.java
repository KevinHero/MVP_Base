package com.apanda.base.Module.Gank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apanda.base.Entity.GankBean;
import com.apanda.base.R;
import com.apanda.base.Utils.GlideImageLoader;
import com.apanda.base.Widget.imagePager.BigImagePagerActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai.xiong on 2016/3/25.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViewContainer = new ArrayList<>();
    List<GankBean.ResultsBean> bitmapBeans;
    private Activity context;
    private ArrayList<String> _arrayList = new ArrayList<>();

    public ViewPagerAdapter(Context context, List<ImageView> imageViewContainer) {
        this.imageViewContainer = imageViewContainer;
        this.context = (Activity) context;
    }

    public ViewPagerAdapter(Activity context, List<GankBean.ResultsBean> bitmapBeans) {
        this.bitmapBeans = bitmapBeans;

        for (int i = 0; i < bitmapBeans.size(); i++) {
            _arrayList.add(bitmapBeans.get(i).url);
            ImageView imageView = new ImageView(context);
            Glide.with(context).load(bitmapBeans.get(i).url).centerCrop().into(imageView);
            imageViewContainer.add(imageView);
        }

        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViewContainer.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = View.inflate(context, R.layout.imageview, null);
        final TextView name = (TextView) view.findViewById(R.id.tv_name);
        final TextView time = (TextView) view.findViewById(R.id.tv_time);
        final ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);

        GlideImageLoader.getInstance().onDisplayImage(context, iv_pic, bitmapBeans.get(position).url);
        name.setText(bitmapBeans.get(position).who);
        time.setText(bitmapBeans.get(position).publishedAt);
        // 为每一个page添加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigImagePagerActivity.startImagePagerActivity(context, _arrayList, position);
            }
        });

        container.addView(view);
        return view;
    }


    @Override
    public int getCount() {
        return imageViewContainer.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}


//@Override
//public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(imageViewContainer.get(position % imageViewContainer.size()));
//        }
//
//@Override
//public Object instantiateItem(ViewGroup container, int position) {
//        View view = imageViewContainer.get(position % imageViewContainer.size());
//
//        // 为每一个page添加点击事件
//        view.setOnClickListener(new View.OnClickListener() {
//
//@Override
//public void onClick(View v) {
//        Toast.makeText(MainActivity.this, "Page 被点击了", Toast.LENGTH_SHORT).show();
//        }
//
//        });
//
//        container.addView(view);
//        return view;
//        }
//
//@Override
//public int getCount() {
//        return Integer.MAX_VALUE;
//        }
//
//@Override
//public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//        }