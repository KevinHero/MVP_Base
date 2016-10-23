package com.apanda.base.Utils.qiniu;

import com.apanda.base.Utils.logger.L;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by apanda on 2016/10/20.
 */

public class UploadImage {

    private static final String ACCESS_KEY = "fOMusS0FLKWjqupf33Cv5dSIO70kRDZ6SKiiPCJj";
    private static final String SECRET_KEY = "djMR-7N7v6r30i9-IjfVavDq5_rni0ogeD_aWdAq";
    private static final String BUCKET_NAME = "natrip";

    /**
     * 上传图片到七牛
     *
     * @param filePath 要上传的图片路径
     *                 <p>
     *                 图片的保存格式 ： category /data /hours
     */
    public static void uploadImageToQiniu(String filePath, String key) {
        UploadManager uploadManager = new UploadManager();

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(BUCKET_NAME);

        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                L.json(res.toString());
            }
        }, null);
    }

    public static void uploadImageToQiniu(File file, String key) {
        UploadManager uploadManager = new UploadManager();

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(BUCKET_NAME);

        uploadManager.put(file, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {

                L.d(info.toString());
            }
        }, null);
    }


}
