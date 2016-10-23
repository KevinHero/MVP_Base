package com.apanda.base.Adapter;

import android.content.Context;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridViewAdapter;

import java.util.List;

/**
 * Created by Android on 2016/10/12.
 */
public class GridViewAdapter extends NineGridViewAdapter {
    public GridViewAdapter(Context context, List<ImageInfo> imageInfo) {
        super(context, imageInfo);
    }
}
