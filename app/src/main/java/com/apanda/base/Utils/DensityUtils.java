package com.apanda.base.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.apanda.base.base.BaseApplication;


public class DensityUtils {

	public static int dpToPx(int dp){
		float scale = BaseApplication.getInstance().getScreenDensity();
		return (int) (dp*scale+0.5f);
	}
	public static float dpToPxFloat(float dp){
		float scale = BaseApplication.getInstance().getScreenDensity();
		return (dp*scale+0.5f);
	}
	
	public static int pxToDp(int px){
		float scale = BaseApplication.getInstance().getScreenDensity();
		return (int) (px/scale);
	}

	public static int dp2px(Context context, float dp) {
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return Math.round(px);
	}


}
