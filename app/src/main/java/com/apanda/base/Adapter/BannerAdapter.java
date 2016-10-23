package com.apanda.base.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/9/29 13:37
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: BannerAdapter
 */

public class BannerAdapter extends PagerAdapter {

    private List<View> imageViewContainer;

    public BannerAdapter(List<View> imageViewContainer) {
        this.imageViewContainer = imageViewContainer;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViewContainer.get(position % imageViewContainer.size()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = imageViewContainer.get(position % imageViewContainer.size());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
