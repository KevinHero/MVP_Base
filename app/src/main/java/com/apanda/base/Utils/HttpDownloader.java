package com.apanda.base.Utils;

/**
 * Created by kai.xiong on 2016-01-16-0016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpDownloader {

    private URL url = null;


    private static HttpDownloader mHttpDownloader;

    public static HttpDownloader getInstance() {
        if (mHttpDownloader == null) {
            mHttpDownloader = new HttpDownloader();
        }
        return mHttpDownloader;
    }

    /**
     * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
     * 1.创建一个URL对象
     * 2.通过URL对象，创建一个HttpURLConnection对象
     * 3.得到InputStram
     * 4.从InputStream当中读取数据
     *
     * @param urlStr
     * @return
     */
    public String download(String urlStr) {

        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        try {
            // 创建一个URL对象
            url = new URL(urlStr);
            // 创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 使用IO流读取数据
            buffer = new BufferedReader(new InputStreamReader(urlConn
                    .getInputStream(), "UTF-8"));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

//    /**
//     * 该函数返回整形 -1：代表下载文件出错 0：代表下载文件成功 1：代表文件已经存在
//     */
//    public int downFile(String urlStr, String path, String fileName) {
//        InputStream inputStream = null;
//        try {
//            inputStream = getInputStreamFromUrl(urlStr);
//            File resultFile = FileUtils.write2SDFromInput(path, fileName, inputStream);
//            if (resultFile == null) {
//                return -1;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        } finally {
//            try {
//                inputStream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return 0;
//    }

    /**
     * 根据URL得到输入流
     *
     * @param urlStr
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public InputStream getInputStreamFromUrl(String urlStr)
            throws IOException {
        url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.connect();
        InputStream inputStream = urlConn.getInputStream();
        return inputStream;
    }

    static OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {


        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
//  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.connect();
//
//                    InputStream is = conn.getInputStream();
//
//                    FileOutputStream fos = new FileOutputStream(patchFile);