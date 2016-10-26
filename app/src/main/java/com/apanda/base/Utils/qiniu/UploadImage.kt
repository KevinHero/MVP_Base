package com.apanda.base.Utils.qiniu

import com.apanda.base.Utils.logger.L
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager

import org.json.JSONObject

import java.io.File

/**
 * Created by apanda on 2016/10/20.
 */

object UploadImage {

    private val ACCESS_KEY = "fOMusS0FLKWjqupf33Cv5dSIO70kRDZ6SKiiPCJj"
    private val SECRET_KEY = "djMR-7N7v6r30i9-IjfVavDq5_rni0ogeD_aWdAq"
    private val BUCKET_NAME = "natrip"

    /**
     * 上传图片到七牛

     * @param filePath 要上传的图片路径
     * *
     *
     *
     * *                 图片的保存格式 ： category /data /hours
     */
    fun uploadImageToQiniu(filePath: String, key: String) {
        val uploadManager = UploadManager()

        val auth = Auth.create(ACCESS_KEY, SECRET_KEY)
        val token = auth.uploadToken(BUCKET_NAME)

        uploadManager.put(filePath, key, token, { key, info, res -> L.json(res.toString()) }, null)
    }

    fun uploadImageToQiniu(file: File, key: String) {
        val uploadManager = UploadManager()

        val auth = Auth.create(ACCESS_KEY, SECRET_KEY)
        val token = auth.uploadToken(BUCKET_NAME)

        uploadManager.put(file, key, token, { key, info, res -> L.d(info.toString()) }, null)
    }


}
