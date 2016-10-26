package com.apanda.base.Utils

import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.view.CropImageView
import com.lzy.ninegrid.NineGridView

/**
 * Created by Android on 2016/10/12.
 */

object ImagePickerUtils {

    fun initchmaPicker() {
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoader.instance   //设置图片加载器
        //        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.isShowCamera = true  //显示拍照按钮
        imagePicker.isCrop = false        //允许裁剪（单选才有效）
        imagePicker.isSaveRectangle = true //是否按矩形区域保存
        imagePicker.selectLimit = 9    //选中数量限制
        imagePicker.style = CropImageView.Style.RECTANGLE  //裁剪框的形状
        imagePicker.focusWidth = 800   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.focusHeight = 800  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.outPutX = 1000//保存文件的宽度。单位像素
        imagePicker.outPutY = 1000//保存文件的高度。单位像素
    }


    fun initNineGridView() {
        NineGridView.setImageLoader(GlideImageLoader.instance)
    }
}
