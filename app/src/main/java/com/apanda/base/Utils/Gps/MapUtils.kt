package com.apanda.base.Utils.Gps

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast

import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

/**

 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 作者: created by 熊凯 on 2016/10/9 12:42
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: MapUtils

 */

object MapUtils {


    fun onMapScreenShot(instance: Context, bitmap: Bitmap) {
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        try {
            // 保存在SD卡根目录下，图片为png格式。
            val fos = FileOutputStream(
                    Environment.getExternalStorageDirectory() + "/test_"
                            + sdf.format(Date()) + ".png")
            val b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            try {
                fos.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (b)

                Toast.makeText(instance, "截屏成功", Toast.LENGTH_LONG).show()
            else {
                Toast.makeText(instance, "截屏失败", Toast.LENGTH_LONG).show()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }


}
