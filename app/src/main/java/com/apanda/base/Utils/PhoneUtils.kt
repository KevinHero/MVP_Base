package com.apanda.base.Utils

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.SystemClock
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.util.Xml

import org.xmlpull.v1.XmlSerializer

import java.io.File
import java.io.FileOutputStream
import java.util.ArrayList
import java.util.HashMap

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/2
 * desc  : 手机相关工具类
 *
 */
class PhoneUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        /**
         * 判断设备是否是手机

         * @param context 上下文
         * *
         * @return `true`: 是`false`: 否
         */
        fun isPhone(context: Context): Boolean {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.phoneType != TelephonyManager.PHONE_TYPE_NONE
        }

        /**
         * 获取手机的IMIE
         *
         * 需与[.isPhone]一起使用
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.READ_PHONE_STATE&quot;/&gt;`

         * @param context 上下文
         * *
         * @return IMIE码
         */
        fun getPhoneIMEI(context: Context): String {
            val deviceId: String
            if (isPhone(context)) {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                deviceId = tm.deviceId
            } else {
                deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            }
            return deviceId
        }

        /**
         * 获取手机状态信息
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.READ_PHONE_STATE&quot;/&gt;`

         * @param context 上下文
         * *
         * @return DeviceId(IMEI) = 99000311726612
         * * DeviceSoftwareVersion = 00
         * * Line1Number =
         * * NetworkCountryIso = cn
         * * NetworkOperator = 46003
         * * NetworkOperatorName = 中国电信
         * * NetworkType = 6
         * * honeType = 2
         * * SimCountryIso = cn
         * * SimOperator = 46003
         * * SimOperatorName = 中国电信
         * * SimSerialNumber = 89860315045710604022
         * * SimState = 5
         * * SubscriberId(IMSI) = 460030419724900
         * * VoiceMailNumber = *86
         */
        fun getPhoneStatus(context: Context): String {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var str = ""
            str += "DeviceId(IMEI) = " + tm.deviceId + "\n"
            str += "DeviceSoftwareVersion = " + tm.deviceSoftwareVersion + "\n"
            str += "Line1Number = " + tm.line1Number + "\n"
            str += "NetworkCountryIso = " + tm.networkCountryIso + "\n"
            str += "NetworkOperator = " + tm.networkOperator + "\n"
            str += "NetworkOperatorName = " + tm.networkOperatorName + "\n"
            str += "NetworkType = " + tm.networkType + "\n"
            str += "honeType = " + tm.phoneType + "\n"
            str += "SimCountryIso = " + tm.simCountryIso + "\n"
            str += "SimOperator = " + tm.simOperator + "\n"
            str += "SimOperatorName = " + tm.simOperatorName + "\n"
            str += "SimSerialNumber = " + tm.simSerialNumber + "\n"
            str += "SimState = " + tm.simState + "\n"
            str += "SubscriberId(IMSI) = " + tm.subscriberId + "\n"
            str += "VoiceMailNumber = " + tm.voiceMailNumber + "\n"
            return str
        }

        /**
         * 跳至填充好phoneNumber的拨号界面

         * @param context     上下文
         * *
         * @param phoneNumber 电话号码
         */
        fun dial(context: Context, phoneNumber: String) {
            context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)))
        }

        /**
         * 拨打phoneNumber
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.CALL_PHONE&quot;/&gt;`

         * @param context     上下文
         * *
         * @param phoneNumber 电话号码
         */
        fun call(context: Context, phoneNumber: String) {
            context.startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)))
        }

        /**
         * 发送短信

         * @param context     上下文
         * *
         * @param phoneNumber 电话号码
         * *
         * @param content     内容
         */
        fun sendSms(context: Context, phoneNumber: String, content: String) {
            val uri = Uri.parse("smsto:" + if (StringUtils.isEmpty(phoneNumber)) "" else phoneNumber)
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", if (StringUtils.isEmpty(content)) "" else content)
            context.startActivity(intent)
        }

        /**
         * 获取手机联系人
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.READ_EXTERNAL_STORAGE&quot;/&gt;`
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.READ_CONTACTS&quot;/&gt;`

         * @param context 上下文;
         * *
         * @return 联系人链表
         */
        fun getAllContactInfo(context: Context): List<HashMap<String, String>> {
            SystemClock.sleep(3000)
            val list = ArrayList<HashMap<String, String>>()
            // 1.获取内容解析者
            val resolver = context.contentResolver
            // 2.获取内容提供者的地址:com.android.contacts
            // raw_contacts表的地址 :raw_contacts
            // view_data表的地址 : data
            // 3.生成查询地址
            val raw_uri = Uri.parse("content://com.android.contacts/raw_contacts")
            val date_uri = Uri.parse("content://com.android.contacts/data")
            // 4.查询操作,先查询raw_contacts,查询contact_id
            // projection : 查询的字段
            val cursor = resolver.query(raw_uri, arrayOf("contact_id"),
                    null, null, null)
            // 5.解析cursor
            while (cursor!!.moveToNext()) {
                // 6.获取查询的数据
                val contact_id = cursor.getString(0)
                // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
                // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
                // 判断contact_id是否为空
                if (!StringUtils.isEmpty(contact_id)) {//null   ""
                    // 7.根据contact_id查询view_data表中的数据
                    // selection : 查询条件
                    // selectionArgs :查询条件的参数
                    // sortOrder : 排序
                    // 空指针: 1.null.方法 2.参数为null
                    val c = resolver.query(date_uri, arrayOf("data1", "mimetype"), "raw_contact_id=?",
                            arrayOf(contact_id), null)
                    val map = HashMap<String, String>()
                    // 8.解析c
                    while (c!!.moveToNext()) {
                        // 9.获取数据
                        val data1 = c.getString(0)
                        val mimetype = c.getString(1)
                        // 10.根据类型去判断获取的data1数据并保存
                        if (mimetype == "vnd.android.cursor.item/phone_v2") {
                            // 电话
                            map.put("phone", data1)
                        } else if (mimetype == "vnd.android.cursor.item/name") {
                            // 姓名
                            map.put("name", data1)
                        }
                    }
                    // 11.添加到集合中数据
                    list.add(map)
                    // 12.关闭cursor
                    c.close()
                }
            }
            // 12.关闭cursor
            cursor.close()
            return list
        }

        /**
         * 打开手机联系人界面点击联系人后便获取该号码
         *
         * 参照以下注释代码
         */
        fun getContantNum() {
            Log.i("tips", "U should copy the following code.")
            /*
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 0);

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                Uri uri = data.getData();
                String num = null;
                // 创建内容解析者
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(uri,
                        null, null, null, null);
                while (cursor.moveToNext()) {
                    num = cursor.getString(cursor.getColumnIndex("data1"));
                }
                cursor.close();
                num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
            }
        }
        */
        }

        /**
         * 获取手机短信并保存到xml中
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.READ_SMS&quot;/&gt;`
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.WRITE_EXTERNAL_STORAGE&quot;/&gt;`

         * @param context 上下文
         */
        fun getAllSMS(context: Context) {
            // 1.获取短信
            // 1.1获取内容解析者
            val resolver = context.contentResolver
            // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
            // 1.3获取查询路径
            val uri = Uri.parse("content://sms")
            // 1.4.查询操作
            // projection : 查询的字段
            // selection : 查询的条件
            // selectionArgs : 查询条件的参数
            // sortOrder : 排序
            val cursor = resolver.query(uri, arrayOf("address", "date", "type", "body"), null, null, null)
            // 设置最大进度
            val count = cursor!!.count//获取短信的个数
            // 2.备份短信
            // 2.1获取xml序列器
            val xmlSerializer = Xml.newSerializer()
            try {
                // 2.2设置xml文件保存的路径
                // os : 保存的位置
                // encoding : 编码格式
                xmlSerializer.setOutput(FileOutputStream(File("/mnt/sdcard/backupsms.xml")), "utf-8")
                // 2.3设置头信息
                // standalone : 是否独立保存
                xmlSerializer.startDocument("utf-8", true)
                // 2.4设置根标签
                xmlSerializer.startTag(null, "smss")
                // 1.5.解析cursor
                while (cursor.moveToNext()) {
                    SystemClock.sleep(1000)
                    // 2.5设置短信的标签
                    xmlSerializer.startTag(null, "sms")
                    // 2.6设置文本内容的标签
                    xmlSerializer.startTag(null, "address")
                    val address = cursor.getString(0)
                    // 2.7设置文本内容
                    xmlSerializer.text(address)
                    xmlSerializer.endTag(null, "address")
                    xmlSerializer.startTag(null, "date")
                    val date = cursor.getString(1)
                    xmlSerializer.text(date)
                    xmlSerializer.endTag(null, "date")
                    xmlSerializer.startTag(null, "type")
                    val type = cursor.getString(2)
                    xmlSerializer.text(type)
                    xmlSerializer.endTag(null, "type")
                    xmlSerializer.startTag(null, "body")
                    val body = cursor.getString(3)
                    xmlSerializer.text(body)
                    xmlSerializer.endTag(null, "body")
                    xmlSerializer.endTag(null, "sms")
                    println("address:$address   date:$date  type:$type  body:$body")
                }
                xmlSerializer.endTag(null, "smss")
                xmlSerializer.endDocument()
                // 2.8将数据刷新到文件中
                xmlSerializer.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}