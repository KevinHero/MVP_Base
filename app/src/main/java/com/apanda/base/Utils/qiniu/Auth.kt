package com.apanda.base.Utils.qiniu

import com.qiniu.android.http.Client
import com.qiniu.android.utils.StringMap
import com.qiniu.android.utils.UrlSafeBase64
import com.qiniu.android.utils.StringUtils


import java.net.URI
import java.security.GeneralSecurityException

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class Auth private constructor(val accessKey: String, private val secretKey: SecretKeySpec) {


    private fun createMac(): Mac {
        val mac: Mac
        try {
            mac = Mac.getInstance("HmacSHA1")
            mac.init(secretKey)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
            throw IllegalArgumentException(e)
        }

        return mac
    }

    fun sign(data: ByteArray): String {
        val mac = createMac()
        val encodedSign = UrlSafeBase64.encodeToString(mac.doFinal(data))
        return this.accessKey + ":" + encodedSign
    }

    fun sign(data: String): String {
        return sign(StringUtils.utf8Bytes(data))
    }

    fun signWithData(data: ByteArray): String {
        val s = UrlSafeBase64.encodeToString(data)
        return sign(StringUtils.utf8Bytes(s)) + ":" + s
    }

    fun signWithData(data: String): String {
        return signWithData(StringUtils.utf8Bytes(data))
    }

    /**
     * 生成HTTP请求签名字符串

     * @param urlString
     * *
     * @param body
     * *
     * @param contentType
     * *
     * @return
     */
    fun signRequest(urlString: String, body: ByteArray?, contentType: String): String {
        val uri = URI.create(urlString)
        val path = uri.rawPath
        val query = uri.rawQuery

        val mac = createMac()

        mac.update(StringUtils.utf8Bytes(path))

        if (query != null && query.length != 0) {
            mac.update('?'.toByte())
            mac.update(StringUtils.utf8Bytes(query))
        }
        mac.update('\n'.toByte())
        if (body != null && body.size > 0 && !StringUtils.isNullOrEmpty(contentType)) {
            if (contentType == Client.FormMime || contentType == Client.JsonMime) {
                mac.update(body)
            }
        }

        val digest = UrlSafeBase64.encodeToString(mac.doFinal())

        return this.accessKey + ":" + digest
    }

    /**
     * 验证回调签名是否正确

     * @param originAuthorization 待验证签名字符串，以 "QBox "作为起始字符
     * *
     * @param url                 回调地址
     * *
     * @param body                回调请求体。原始请求体，不要解析后再封装成新的请求体--可能导致签名不一致。
     * *
     * @param contentType         回调ContentType
     * *
     * @return
     */
    fun isValidCallback(originAuthorization: String, url: String, body: ByteArray, contentType: String): Boolean {
        val authorization = "QBox " + signRequest(url, body, contentType)
        return authorization == originAuthorization
    }

    /**
     * 下载签名

     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、
     * *                http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * *
     * @param expires 有效时长，单位秒。默认3600s
     * *
     * @return
     */
    @JvmOverloads fun privateDownloadUrl(baseUrl: String, expires: Long = 3600): String {
        val deadline = System.currentTimeMillis() / 1000 + expires
        return privateDownloadUrlWithDeadline(baseUrl, deadline)
    }

    internal fun privateDownloadUrlWithDeadline(baseUrl: String, deadline: Long): String {
        val b = StringBuilder()
        b.append(baseUrl)
        val pos = baseUrl.indexOf("?")
        if (pos > 0) {
            b.append("&e=")
        } else {
            b.append("?e=")
        }
        b.append(deadline)
        val token = sign(StringUtils.utf8Bytes(b.toString()))
        b.append("&token=")
        b.append(token)
        return b.toString()
    }

    /**
     * 生成上传token

     * @param bucket  空间名
     * *
     * @param key     key，可为 null
     * *
     * @param expires 有效时长，单位秒。默认3600s
     * *
     * @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
     * *                scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
     * *
     * @param strict  是否去除非限定的策略字段，默认true
     * *
     * @return 生成的上传token
     */
    @JvmOverloads fun uploadToken(bucket: String, key: String? = null, expires: Long = 3600, policy: StringMap? = null, strict: Boolean = true): String {
        val deadline = System.currentTimeMillis() / 1000 + expires
        return uploadTokenWithDeadline(bucket, key, deadline, policy, strict)
    }

    fun uploadTokenWithDeadline(bucket: String, key: String?, deadline: Long, policy: StringMap, strict: Boolean): String {
        // TODO   UpHosts Global
        var scope = bucket
        if (key != null) {
            scope = bucket + ":" + key
        }
        val x = StringMap()
        copyPolicy(x, policy, strict)
        x.put("scope", scope)
        x.put("deadline", deadline)

        val s = Json.encode(x)
        return signWithData(StringUtils.utf8Bytes(s))
    }

    fun uploadTokenWithPolicy(obj: Any): String {
        val s = Json.encode(obj)
        return signWithData(StringUtils.utf8Bytes(s))
    }

    @JvmOverloads fun authorization(url: String, body: ByteArray? = null, contentType: String? = null): StringMap {
        val authorization = "QBox " + signRequest(url, body, contentType)
        return StringMap().put("Authorization", authorization)
    }

    /**
     * 生成HTTP请求签名字符串

     * @param urlString
     * *
     * @param body
     * *
     * @param contentType
     * *
     * @return
     */
    fun signRequestV2(urlString: String, method: String, body: ByteArray?, contentType: String?): String {
        val uri = URI.create(urlString)

        val mac = createMac()


        //
        val sb = StringBuilder()

        sb.append(String.format("%s %s", method, uri.path))
        if (uri.query != null) {
            sb.append(String.format("?%s", uri.query))
        }

        sb.append(String.format("\nHost: %s", uri.host))
        if (uri.port > 0) {
            sb.append(String.format(":%d", uri.port))
        }

        if (contentType != null) {
            sb.append(String.format("\nContent-Type: %s", contentType))
        }

        // body
        sb.append("\n\n")
        if (body != null && body.size > 0 && !StringUtils.isNullOrEmpty(contentType)) {
            if (contentType == Client.FormMime || contentType == Client.JsonMime) {
                sb.append(String(body))
            }
        }
        mac.update(StringUtils.utf8Bytes(sb.toString()))

        val digest = UrlSafeBase64.encodeToString(mac.doFinal())

        return this.accessKey + ":" + digest
    }

    @JvmOverloads fun authorizationV2(url: String, method: String = "GET", body: ByteArray? = null, contentType: String? = null): StringMap {
        val authorization = "Qiniu " + signRequestV2(url, method, body, contentType)
        return StringMap().put("Authorization", authorization)
    }

    companion object {

        /**
         * 上传策略，参数规格详见
         *
         *
         * http://developer.qiniu.com/docs/v6/api/reference/security/put-policy.html
         */
        private val policyFields = arrayOf("callbackUrl", "callbackBody", "callbackHost", "callbackBodyType", "callbackFetchKey",

                "returnUrl", "returnBody",

                "endUser", "saveKey", "insertOnly",

                "detectMime", "mimeLimit", "fsizeLimit", "fsizeMin",

                "persistentOps", "persistentNotifyUrl", "persistentPipeline",

                "deleteAfterDays")
        private val deprecatedPolicyFields = arrayOf("asyncOps")

        fun create(accessKey: String, secretKey: String): Auth {
            if (StringUtils.isNullOrEmpty(accessKey) || StringUtils.isNullOrEmpty(secretKey)) {
                throw IllegalArgumentException("empty key")
            }
            val sk = StringUtils.utf8Bytes(secretKey)
            val secretKeySpec = SecretKeySpec(sk, "HmacSHA1")
            return Auth(accessKey, secretKeySpec)
        }

        private fun copyPolicy(policy: StringMap, originPolicy: StringMap?, strict: Boolean) {
            if (originPolicy == null) {
                return
            }
            originPolicy.forEach(StringMap.Consumer { key, value ->
                if (inStringArray(key, deprecatedPolicyFields)) {
                    throw IllegalArgumentException(key + " is deprecated!")
                }
                if (!strict || inStringArray(key, policyFields)) {
                    policy.put(key, value)
                }
            })
        }


        private fun inStringArray(s: String, array: Array<String>): Boolean {
            for (x in array) {
                if (x == s) {
                    return true
                }
            }
            return false
        }
    }
}
/**
 * 下载签名

 * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、
 * *                http://img.domain.com/u/3.jpg?imageView2/1/w/120
 * *
 * @return
 */
/**
 * scope = bucket
 * 一般情况下可通过此方法获取token

 * @param bucket 空间名
 * *
 * @return 生成的上传token
 */
/**
 * scope = bucket:key
 * 同名文件覆盖操作、只能上传指定key的文件可以可通过此方法获取token

 * @param bucket 空间名
 * *
 * @param key    key，可为 null
 * *
 * @return 生成的上传token
 */
/**
 * 生成上传token

 * @param bucket  空间名
 * *
 * @param key     key，可为 null
 * *
 * @param expires 有效时长，单位秒
 * *
 * @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
 * *                scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
 * *
 * @return 生成的上传token
 */
