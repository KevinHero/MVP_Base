package com.apanda.base.Utils

/**
 * Created by kai.xiong on 2016-01-16-0016.
 */

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class HttpDownloader {

    private var url: URL? = null

    /**
     * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
     * 1.创建一个URL对象
     * 2.通过URL对象，创建一个HttpURLConnection对象
     * 3.得到InputStram
     * 4.从InputStream当中读取数据

     * @param urlStr
     * *
     * @return
     */
    fun download(urlStr: String): String {

        val sb = StringBuffer()
        var line: String? = null
        var buffer: BufferedReader? = null
        try {
            // 创建一个URL对象
            url = URL(urlStr)
            // 创建一个Http连接
            val urlConn = url!!.openConnection() as HttpURLConnection
            // 使用IO流读取数据
            buffer = BufferedReader(InputStreamReader(urlConn.inputStream, "UTF-8"))
            while ((line = buffer.readLine()) != null) {
                sb.append(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                buffer!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return sb.toString()
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

     * @param urlStr
     * *
     * @return
     * *
     * @throws MalformedURLException
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getInputStreamFromUrl(urlStr: String): InputStream {
        url = URL(urlStr)
        val urlConn = url!!.openConnection() as HttpURLConnection
        urlConn.connect()
        val inputStream = urlConn.inputStream
        return inputStream
    }

    @Throws(IOException::class)
    fun run(url: String): String {


        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        return response.body().string()
    }

    companion object {


        private var mHttpDownloader: HttpDownloader? = null

        val instance: HttpDownloader
            get() {
                if (mHttpDownloader == null) {
                    mHttpDownloader = HttpDownloader()
                }
                return mHttpDownloader
            }

        internal var client = OkHttpClient()
    }
}
//  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.connect();
//
//                    InputStream is = conn.getInputStream();
//
//                    FileOutputStream fos = new FileOutputStream(patchFile);