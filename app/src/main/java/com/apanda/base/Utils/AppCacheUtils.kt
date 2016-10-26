package com.apanda.base.Utils

import android.content.Context
import android.text.TextUtils

import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.RandomAccessFile
import java.io.Serializable
import java.util.HashMap

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/22
 * 描    述：
 * 修订历史：
 * ================================================
 */
class AppCacheUtils private constructor(private val mCacheFile: File) {

    init {
        if (!mCacheFile.exists()) {
            mCacheFile.mkdirs()
        }
    }

    fun put(key: String, value: Int) {
        put(key, value + "")
    }

    fun put(key: String, value: Float) {
        put(key, value + "")
    }

    fun put(key: String, value: Double) {
        put(key, value + "")
    }

    fun put(key: String, value: Boolean) {
        put(key, value + "")
    }

    fun put(key: String, value: Long) {
        put(key, value + "")
    }

    /**
     * 保存 String数据 到 缓存中

     * @param key   保存的key
     * *
     * @param value 保存的String数据
     */
    fun put(key: String, value: String) {
        var value = value

        if (TextUtils.isEmpty(key)) {
            return
        }

        if (TextUtils.isEmpty(value)) {
            value = ""
        }
        val file = newFile(key)
        var out: BufferedWriter? = null
        try {
            out = BufferedWriter(FileWriter(file), 1024)
            out.write(value)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (out != null) {
                try {
                    out.flush()
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 保存 byte数据 到 缓存中

     * @param key   保存的key
     * *
     * @param value 保存的数据
     */
    fun put(key: String, value: ByteArray?) {
        if (value == null || value.size == 0 || TextUtils.isEmpty(key)) {
            return
        }
        val file = newFile(key)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            out.write(value)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (out != null) {
                try {
                    out.flush()
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 保存 JSONArray数据 到 缓存中

     * @param key   保存的key
     * *
     * @param value 保存的JSONArray数据
     */
    fun put(key: String, value: JSONArray?) {
        if (value == null) {
            return
        }
        put(key, value.toString())
    }

    /**
     * 保存 JSONObject数据 到 缓存中

     * @param key   保存的key
     * *
     * @param value 保存的JSON数据
     */
    fun put(key: String, value: JSONObject?) {
        if (value == null) {
            return
        }
        put(key, value.toString())
    }

    /**
     * 保存 Serializable数据到 缓存中

     * @param key   保存的key
     * *
     * @param value 保存的value
     */
    fun put(key: String, value: Serializable?) {
        if (TextUtils.isEmpty(key) || value == null) {
            return
        }
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(value)
            val data = baos.toByteArray()
            put(key, data)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                oos!!.close()
            } catch (e: IOException) {
            }

        }
    }

    fun getInt(key: String, defValue: Int): Int {
        val sValue = getString(key)
        if (!TextUtils.isEmpty(sValue)) {
            try {
                val iValue = Integer.parseInt(sValue)
                return iValue
            } catch (e: Exception) {
            }

        }

        return defValue
    }

    fun getFloat(key: String, defValue: Float): Float {
        val sValue = getString(key)
        if (!TextUtils.isEmpty(sValue)) {
            try {
                val fValue = java.lang.Float.parseFloat(sValue)
                return fValue
            } catch (e: Exception) {
            }

        }

        return defValue
    }

    fun getDouble(key: String, defValue: Double): Double? {
        val sValue = getString(key)
        if (!TextUtils.isEmpty(sValue)) {
            try {
                val dValue = java.lang.Double.parseDouble(sValue)
                return dValue
            } catch (e: Exception) {
            }

        }

        return defValue
    }

    fun getLong(key: String, defValue: Long): Long {
        val sValue = getString(key)
        if (!TextUtils.isEmpty(sValue)) {
            try {
                val dValue = java.lang.Long.parseLong(sValue)
                return dValue
            } catch (e: Exception) {
            }

        }

        return defValue
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        val sValue = getString(key)
        if (!TextUtils.isEmpty(sValue)) {
            try {
                val bValue = java.lang.Boolean.parseBoolean(sValue)
                return bValue
            } catch (e: Exception) {
            }

        }

        return defValue
    }

    /**
     * 读取 String数据

     * @return String 数据
     */
    fun getString(key: String): String? {
        if (TextUtils.isEmpty(key)) {
            return null
        }
        val file = newFile(key)
        if (!file.exists()) {
            return null
        }
        var `in`: BufferedReader? = null
        var readString = ""
        try {
            `in` = BufferedReader(FileReader(file))
            var currentLine: String
            while ((currentLine = `in`.readLine()) != null) {
                readString += currentLine
            }
            return readString
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return readString
    }

    /**
     * 读取 Serializable数据

     * @return Serializable 数据
     */
    fun getObject(key: String): Any? {
        if (TextUtils.isEmpty(key)) {
            return null
        }
        val data = getBinary(key)
        if (data != null) {
            var bais: ByteArrayInputStream? = null
            var ois: ObjectInputStream? = null
            try {
                bais = ByteArrayInputStream(data)
                ois = ObjectInputStream(bais)
                val reObject = ois.readObject()
                return reObject
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            } finally {
                try {
                    if (bais != null) {
                        bais.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                try {
                    if (ois != null) {
                        ois.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return null
    }

    /**
     * 获取 byte 数据

     * @return byte 数据
     */
    fun getBinary(key: String): ByteArray? {
        if (TextUtils.isEmpty(key)) {
            return null
        }
        var rAFile: RandomAccessFile? = null
        var byteArray: ByteArray? = null
        try {
            val file = newFile(key)
            if (!file.exists()) {
                return null
            }
            rAFile = RandomAccessFile(file, "r")
            val fLength = rAFile.length()
            if (fLength != 0) {
                byteArray = ByteArray(rAFile.length().toInt())
                rAFile.read(byteArray)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (rAFile != null) {
                try {
                    rAFile.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return byteArray
    }

    /**
     * 读取JSONArray数据

     * @return JSONArray数据
     */
    fun getJSONArray(key: String): JSONArray? {
        val JSONString = getString(key)
        try {
            val obj = JSONArray(JSONString)
            return obj
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 读取JSONObject数据

     * @return JSONObject数据
     */
    fun getJSONObject(key: String): JSONObject? {
        val JSONString = getString(key)
        try {
            val obj = JSONObject(JSONString)
            return obj
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    private fun newFile(key: String): File {
        return File(mCacheFile, MD5Utils.encode(key))
    }

    companion object {

        val DEFAULT_CACHE_NAME = "appCache"
        private val mCacheUtilsMap = HashMap<String, AppCacheUtils>()

        fun getInstance(ctx: Context): AppCacheUtils {
            return getInstance(File(ctx.cacheDir, DEFAULT_CACHE_NAME))
        }

        fun getInstance(path: String, cacheDirName: String): AppCacheUtils {
            return getInstance(File(path, cacheDirName))
        }

        fun getInstance(ctx: Context, cacheDirName: String): AppCacheUtils {
            return getInstance(File(ctx.cacheDir, cacheDirName))
        }

        fun getInstance(file: File): AppCacheUtils {
            var appCacheUtils: AppCacheUtils? = mCacheUtilsMap[file.absolutePath]
            if (appCacheUtils == null) {
                appCacheUtils = AppCacheUtils(file)
                mCacheUtilsMap.put(file.absolutePath, appCacheUtils)
            }
            return appCacheUtils
        }
    }
}

