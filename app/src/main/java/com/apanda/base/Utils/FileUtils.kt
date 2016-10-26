package com.apanda.base.Utils

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.FilenameFilter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.util.ArrayList
import java.util.Collections

import com.apanda.base.Utils.ConstUtils.KB


/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/11
 * desc  : 文件相关工具类
 *
 */
class FileUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        /**
         * 根据文件路径获取文件

         * @param filePath 文件路径
         * *
         * @return 文件
         */
        fun getFileByPath(filePath: String): File? {
            return if (StringUtils.isSpace(filePath)) null else File(filePath)
        }

        /**
         * 判断文件是否存在

         * @param filePath 文件路径
         * *
         * @return `true`: 存在`false`: 不存在
         */
        fun isFileExists(filePath: String): Boolean {
            return isFileExists(getFileByPath(filePath))
        }

        /**
         * 判断文件是否存在

         * @param file 文件
         * *
         * @return `true`: 存在`false`: 不存在
         */
        fun isFileExists(file: File?): Boolean {
            return file != null && file.exists()
        }

        /**
         * 判断是否是目录

         * @param dirPath 目录路径
         * *
         * @return `true`: 是`false`: 否
         */
        fun isDir(dirPath: String): Boolean {
            return isDir(getFileByPath(dirPath))
        }

        /**
         * 判断是否是目录

         * @param file 文件
         * *
         * @return `true`: 是`false`: 否
         */
        fun isDir(file: File): Boolean {
            return isFileExists(file) && file.isDirectory
        }

        /**
         * 判断是否是文件

         * @param filePath 文件路径
         * *
         * @return `true`: 是`false`: 否
         */
        fun isFile(filePath: String): Boolean {
            return isFile(getFileByPath(filePath))
        }

        /**
         * 判断是否是文件

         * @param file 文件
         * *
         * @return `true`: 是`false`: 否
         */
        fun isFile(file: File): Boolean {
            return isFileExists(file) && file.isFile
        }

        /**
         * 判断目录是否存在，不存在则判断是否创建成功

         * @param dirPath 文件路径
         * *
         * @return `true`: 存在或创建成功`false`: 不存在或创建失败
         */
        fun createOrExistsDir(dirPath: String): Boolean {
            return createOrExistsDir(getFileByPath(dirPath))
        }

        /**
         * 判断目录是否存在，不存在则判断是否创建成功

         * @param file 文件
         * *
         * @return `true`: 存在或创建成功`false`: 不存在或创建失败
         */
        fun createOrExistsDir(file: File?): Boolean {
            // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
            return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
        }

        /**
         * 判断文件是否存在，不存在则判断是否创建成功

         * @param filePath 文件路径
         * *
         * @return `true`: 存在或创建成功`false`: 不存在或创建失败
         */
        fun createOrExistsFile(filePath: String): Boolean {
            return createOrExistsFile(getFileByPath(filePath))
        }

        /**
         * 判断文件是否存在，不存在则判断是否创建成功

         * @param file 文件
         * *
         * @return `true`: 存在或创建成功`false`: 不存在或创建失败
         */
        fun createOrExistsFile(file: File?): Boolean {
            if (file == null) return false
            // 如果存在，是文件则返回true，是目录则返回false
            if (file.exists()) return file.isFile
            if (!createOrExistsDir(file.parentFile)) return false
            try {
                return file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }

        }

        /**
         * 判断文件是否存在，存在则在创建之前删除

         * @param filePath 文件路径
         * *
         * @return `true`: 创建成功`false`: 创建失败
         */
        fun createFileByDeleteOldFile(filePath: String): Boolean {
            return createFileByDeleteOldFile(getFileByPath(filePath))
        }

        /**
         * 判断文件是否存在，存在则在创建之前删除

         * @param file 文件
         * *
         * @return `true`: 创建成功`false`: 创建失败
         */
        fun createFileByDeleteOldFile(file: File?): Boolean {
            if (file == null) return false
            // 文件存在并且删除失败返回false
            if (file.exists() && file.isFile && !file.delete()) return false
            // 创建目录失败返回false
            if (!createOrExistsDir(file.parentFile)) return false
            try {
                return file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }

        }

        /**
         * 复制或移动目录

         * @param srcDirPath  源目录路径
         * *
         * @param destDirPath 目标目录路径
         * *
         * @param isMove      是否移动
         * *
         * @return `true`: 复制或移动成功`false`: 复制或移动失败
         */
        private fun copyOrMoveDir(srcDirPath: String, destDirPath: String, isMove: Boolean): Boolean {
            return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), isMove)
        }

        /**
         * 复制或移动目录

         * @param srcDir  源目录
         * *
         * @param destDir 目标目录
         * *
         * @param isMove  是否移动
         * *
         * @return `true`: 复制或移动成功`false`: 复制或移动失败
         */
        private fun copyOrMoveDir(srcDir: File?, destDir: File?, isMove: Boolean): Boolean {
            if (srcDir == null || destDir == null) return false
            // 如果目标目录在源目录中则返回false，看不懂的话好好想想递归怎么结束
            // srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
            // destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
            // 为防止以上这种情况出现出现误判，须分别在后面加个路径分隔符
            val srcPath = srcDir.path + File.separator
            val destPath = destDir.path + File.separator
            if (destPath.contains(srcPath)) return false
            // 源文件不存在或者不是目录则返回false
            if (!srcDir.exists() || !srcDir.isDirectory) return false
            // 目标目录不存在返回false
            if (!createOrExistsDir(destDir)) return false
            val files = srcDir.listFiles()
            for (file in files) {
                val oneDestFile = File(destPath + file.name)
                if (file.isFile) {
                    // 如果操作失败返回false
                    if (!copyOrMoveFile(file, oneDestFile, isMove)) return false
                } else if (file.isDirectory) {
                    // 如果操作失败返回false
                    if (!copyOrMoveDir(file, oneDestFile, isMove)) return false
                }
            }
            return !isMove || deleteDir(srcDir)
        }

        /**
         * 复制或移动文件

         * @param srcFilePath  源文件路径
         * *
         * @param destFilePath 目标文件路径
         * *
         * @param isMove       是否移动
         * *
         * @return `true`: 复制或移动成功`false`: 复制或移动失败
         */
        private fun copyOrMoveFile(srcFilePath: String, destFilePath: String, isMove: Boolean): Boolean {
            return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove)
        }

        /**
         * 复制或移动文件

         * @param srcFile  源文件
         * *
         * @param destFile 目标文件
         * *
         * @param isMove   是否移动
         * *
         * @return `true`: 复制或移动成功`false`: 复制或移动失败
         */
        private fun copyOrMoveFile(srcFile: File?, destFile: File?, isMove: Boolean): Boolean {
            if (srcFile == null || destFile == null) return false
            // 源文件不存在或者不是文件则返回false
            if (!srcFile.exists() || !srcFile.isFile) return false
            // 目标文件存在且是文件则返回false
            if (destFile.exists() && destFile.isFile) return false
            // 目标目录不存在返回false
            if (!createOrExistsDir(destFile.parentFile)) return false
            try {
                return writeFileFromIS(destFile, FileInputStream(srcFile), false) && !(isMove && !deleteFile(srcFile))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return false
            }

        }

        /**
         * 复制目录

         * @param srcDirPath  源目录路径
         * *
         * @param destDirPath 目标目录路径
         * *
         * @return `true`: 复制成功`false`: 复制失败
         */
        fun copyDir(srcDirPath: String, destDirPath: String): Boolean {
            return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath))
        }

        /**
         * 复制目录

         * @param srcDir  源目录
         * *
         * @param destDir 目标目录
         * *
         * @return `true`: 复制成功`false`: 复制失败
         */
        fun copyDir(srcDir: File, destDir: File): Boolean {
            return copyOrMoveDir(srcDir, destDir, false)
        }

        /**
         * 复制文件

         * @param srcFilePath  源文件路径
         * *
         * @param destFilePath 目标文件路径
         * *
         * @return `true`: 复制成功`false`: 复制失败
         */
        fun copyFile(srcFilePath: String, destFilePath: String): Boolean {
            return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath))
        }

        /**
         * 复制文件

         * @param srcFile  源文件
         * *
         * @param destFile 目标文件
         * *
         * @return `true`: 复制成功`false`: 复制失败
         */
        fun copyFile(srcFile: File, destFile: File): Boolean {
            return copyOrMoveFile(srcFile, destFile, false)
        }

        /**
         * 移动目录

         * @param srcDirPath  源目录路径
         * *
         * @param destDirPath 目标目录路径
         * *
         * @return `true`: 移动成功`false`: 移动失败
         */
        fun moveDir(srcDirPath: String, destDirPath: String): Boolean {
            return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath))
        }

        /**
         * 移动目录

         * @param srcDir  源目录
         * *
         * @param destDir 目标目录
         * *
         * @return `true`: 移动成功`false`: 移动失败
         */
        fun moveDir(srcDir: File, destDir: File): Boolean {
            return copyOrMoveDir(srcDir, destDir, true)
        }

        /**
         * 移动文件

         * @param srcFilePath  源文件路径
         * *
         * @param destFilePath 目标文件路径
         * *
         * @return `true`: 移动成功`false`: 移动失败
         */
        fun moveFile(srcFilePath: String, destFilePath: String): Boolean {
            return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath))
        }

        /**
         * 移动文件

         * @param srcFile  源文件
         * *
         * @param destFile 目标文件
         * *
         * @return `true`: 移动成功`false`: 移动失败
         */
        fun moveFile(srcFile: File, destFile: File): Boolean {
            return copyOrMoveFile(srcFile, destFile, true)
        }

        /**
         * 删除目录

         * @param dirPath 目录路径
         * *
         * @return `true`: 删除成功`false`: 删除失败
         */
        fun deleteDir(dirPath: String): Boolean {
            return deleteDir(getFileByPath(dirPath))
        }

        /**
         * 删除目录

         * @param dir 目录
         * *
         * @return `true`: 删除成功`false`: 删除失败
         */
        fun deleteDir(dir: File?): Boolean {
            if (dir == null) return false
            // 目录不存在返回true
            if (!dir.exists()) return true
            // 不是目录返回false
            if (!dir.isDirectory) return false
            // 现在文件存在且是文件夹
            val files = dir.listFiles()
            for (file in files) {
                if (file.isFile) {
                    if (!deleteFile(file)) return false
                } else if (file.isDirectory) {
                    if (!deleteDir(file)) return false
                }
            }
            return dir.delete()
        }

        /**
         * 删除文件

         * @param srcFilePath 文件路径
         * *
         * @return `true`: 删除成功`false`: 删除失败
         */
        fun deleteFile(srcFilePath: String): Boolean {
            return deleteFile(getFileByPath(srcFilePath))
        }

        /**
         * 删除文件

         * @param file 文件
         * *
         * @return `true`: 删除成功`false`: 删除失败
         */
        fun deleteFile(file: File?): Boolean {
            return file != null && (!file.exists() || file.isFile && file.delete())
        }

        /**
         * 获取目录下所有文件

         * @param dirPath     目录路径
         * *
         * @param isRecursive 是否递归进子目录
         * *
         * @return 文件链表
         */
        fun listFilesInDir(dirPath: String, isRecursive: Boolean): List<File> {
            return listFilesInDir(getFileByPath(dirPath), isRecursive)
        }

        /**
         * 获取目录下所有文件

         * @param dir         目录
         * *
         * @param isRecursive 是否递归进子目录
         * *
         * @return 文件链表
         */
        fun listFilesInDir(dir: File?, isRecursive: Boolean): List<File>? {
            if (isRecursive) return listFilesInDir(dir)
            if (dir == null || !isDir(dir)) return null
            val list = ArrayList<File>()
            val files = dir.listFiles()
            Collections.addAll(list, *files)
            return list
        }

        /**
         * 获取目录下所有文件包括子目录

         * @param dir 目录
         * *
         * @return 文件链表
         */
        fun listFilesInDir(dir: File?): List<File>? {
            if (dir == null || !isDir(dir)) return null
            val list = ArrayList<File>()
            val files = dir.listFiles()
            for (file in files) {
                list.add(file)
                if (file.isDirectory) {
                    list.addAll(listFilesInDir(file)!!)
                }
            }
            return list
        }

        /**
         * 获取目录下所有后缀名为suffix的文件
         *
         * 大小写忽略

         * @param dirPath     目录路径
         * *
         * @param isRecursive 是否递归进子目录
         * *
         * @return 文件链表
         */
        fun listFilesInDirWithFilter(dirPath: String, suffix: String, isRecursive: Boolean): List<File> {
            return listFilesInDirWithFilter(getFileByPath(dirPath), suffix, isRecursive)
        }

        /**
         * 获取目录下所有后缀名为suffix的文件
         *
         * 大小写忽略

         * @param dir         目录
         * *
         * @param isRecursive 是否递归进子目录
         * *
         * @return 文件链表
         */
        fun listFilesInDirWithFilter(dir: File?, suffix: String, isRecursive: Boolean): List<File>? {
            if (isRecursive) return listFilesInDirWithFilter(dir, suffix)
            if (dir == null || !isDir(dir)) return null
            val list = ArrayList<File>()
            val files = dir.listFiles()
            for (file in files) {
                if (file.name.toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file)
                }
            }
            return list
        }

        /**
         * 获取目录下所有后缀名为suffix的文件包括子目录
         *
         * 大小写忽略

         * @param dirPath 目录路径
         * *
         * @return 文件链表
         */
        fun listFilesInDirWithFilter(dirPath: String, suffix: String): List<File> {
            return listFilesInDirWithFilter(getFileByPath(dirPath), suffix)
        }

        /**
         * 获取目录下所有后缀名为suffix的文件包括子目录
         *
         * 大小写忽略

         * @param dir 目录
         * *
         * @return 文件链表
         */
        fun listFilesInDirWithFilter(dir: File?, suffix: String): List<File>? {
            if (dir == null || !isDir(dir)) return null
            val list = ArrayList<File>()
            val files = dir.listFiles()
            for (file in files) {
                if (file.name.toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file)
                }
                if (file.isDirectory) {
                    list.addAll(listFilesInDirWithFilter(file, suffix)!!)
                }
            }
            return list
        }

        /**
         * 获取目录下所有符合filter的文件

         * @param dirPath     目录路径
         * *
         * @param isRecursive 是否递归进子目录
         * *
         * @return 文件链表
         */
        fun listFilesInDirWithFilter(dirPath: String, filter: FilenameFilter, isRecursive: Boolean): List<File> {
            return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive)
        }

        /**
         * 获取目录下所有符合filter的文件

         * @param dir         目录
         * *
         * @param isRecursive 是否递归进子目录
         * *
         * @return 文件链表
         */
        fun listFilesInDirWithFilter(dir: File?, filter: FilenameFilter, isRecursive: Boolean): List<File>? {
            if (isRecursive) return listFilesInDirWithFilter(dir, filter)
            if (dir == null || !isDir(dir)) return null
            val list = ArrayList<File>()
            val files = dir.listFiles()
            for (file in files) {
                if (filter.accept(file.parentFile, file.name)) {
                    list.add(file)
                }
            }
            return list
        }

        /**
         * 获取目录下所有符合filter的文件包括子目录

         * @param dirPath 目录路径
         * *
         * @return 文件链表
         */
        fun listFilesInDirWithFilter(dirPath: String, filter: FilenameFilter): List<File> {
            return listFilesInDirWithFilter(getFileByPath(dirPath), filter)
        }

        /**
         * 获取目录下所有符合filter的文件包括子目录

         * @param dir 目录
         * *
         * @return 文件链表
         */
        fun listFilesInDirWithFilter(dir: File?, filter: FilenameFilter): List<File>? {
            if (dir == null || !isDir(dir)) return null
            val list = ArrayList<File>()
            val files = dir.listFiles()
            for (file in files) {
                if (filter.accept(file.parentFile, file.name)) {
                    list.add(file)
                }
                if (file.isDirectory) {
                    list.addAll(listFilesInDirWithFilter(file, filter)!!)
                }
            }
            return list
        }

        /**
         * 获取目录下指定文件名的文件包括子目录
         *
         * 大小写忽略

         * @param dirPath 目录路径
         * *
         * @return 文件链表
         */
        fun searchFileInDir(dirPath: String, fileName: String): List<File> {
            return searchFileInDir(getFileByPath(dirPath), fileName)
        }

        /**
         * 获取目录下指定文件名的文件包括子目录
         *
         * 大小写忽略

         * @param dir 目录
         * *
         * @return 文件链表
         */
        fun searchFileInDir(dir: File?, fileName: String): List<File>? {
            if (dir == null || !isDir(dir)) return null
            val list = ArrayList<File>()
            val files = dir.listFiles()
            for (file in files) {
                if (file.name.toUpperCase() == fileName.toUpperCase()) {
                    list.add(file)
                }
                if (file.isDirectory) {
                    list.addAll(listFilesInDirWithFilter(file, fileName)!!)
                }
            }
            return list
        }

        /**
         * 将输入流写入文件

         * @param filePath 路径
         * *
         * @param is       输入流
         * *
         * @param append   是否追加在文件末
         * *
         * @return `true`: 写入成功`false`: 写入失败
         */
        fun writeFileFromIS(filePath: String, `is`: InputStream, append: Boolean): Boolean {
            return writeFileFromIS(getFileByPath(filePath), `is`, append)
        }

        /**
         * 将输入流写入文件

         * @param file   文件
         * *
         * @param is     输入流
         * *
         * @param append 是否追加在文件末
         * *
         * @return `true`: 写入成功`false`: 写入失败
         */
        fun writeFileFromIS(file: File?, `is`: InputStream?, append: Boolean): Boolean {
            if (file == null || `is` == null) return false
            if (!createOrExistsFile(file)) return false
            var os: OutputStream? = null
            try {
                os = BufferedOutputStream(FileOutputStream(file, append))
                val data = ByteArray(KB)
                var len: Int
                while ((len = `is`.read(data)) != -1) {
                    os.write(data, 0, len)
                }
                return true
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            } finally {
                closeIO(`is`)
                closeIO(os)
            }
        }

        /**
         * 将字符串写入文件

         * @param filePath 文件路径
         * *
         * @param content  写入内容
         * *
         * @param append   是否追加在文件末
         * *
         * @return `true`: 写入成功`false`: 写入失败
         */
        fun writeFileFromString(filePath: String, content: String, append: Boolean): Boolean {
            return writeFileFromString(getFileByPath(filePath), content, append)
        }

        /**
         * 将字符串写入文件

         * @param file    文件
         * *
         * @param content 写入内容
         * *
         * @param append  是否追加在文件末
         * *
         * @return `true`: 写入成功`false`: 写入失败
         */
        fun writeFileFromString(file: File?, content: String?, append: Boolean): Boolean {
            if (file == null || content == null) return false
            if (!createOrExistsFile(file)) return false
            var fileWriter: FileWriter? = null
            try {
                fileWriter = FileWriter(file, append)
                fileWriter.write(content)
                return true
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            } finally {
                closeIO(fileWriter)
            }
        }

        /**
         * 简单获取文件编码格式

         * @param filePath 文件路径
         * *
         * @return 文件编码
         */
        fun getFileCharsetSimple(filePath: String): String {
            return getFileCharsetSimple(getFileByPath(filePath))
        }

        /**
         * 简单获取文件编码格式

         * @param file 文件
         * *
         * @return 文件编码
         */
        fun getFileCharsetSimple(file: File): String {
            var p = 0
            var `is`: InputStream? = null
            try {
                `is` = BufferedInputStream(FileInputStream(file))
                p = (`is`.read() shl 8) + `is`.read()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                closeIO(`is`)
            }
            when (p) {
                0xefbb -> return "UTF-8"
                0xfffe -> return "Unicode"
                0xfeff -> return "UTF-16BE"
                else -> return "GBK"
            }
        }

        /**
         * 获取文件行数

         * @param filePath 文件路径
         * *
         * @return 文件行数
         */
        fun getFileLines(filePath: String): Int {
            return getFileLines(getFileByPath(filePath))
        }

        /**
         * 获取文件行数

         * @param file 文件
         * *
         * @return 文件行数
         */
        fun getFileLines(file: File): Int {
            var count = 1
            var `is`: InputStream? = null
            try {
                `is` = BufferedInputStream(FileInputStream(file))
                val buffer = ByteArray(KB)
                var readChars: Int
                while ((readChars = `is`.read(buffer)) != -1) {
                    for (i in 0..readChars - 1) {
                        if (buffer[i] == '\n') ++count
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                closeIO(`is`)
            }
            return count
        }

        /**
         * 指定编码按行读取文件到List

         * @param filePath    文件路径
         * *
         * @param charsetName 编码格式
         * *
         * @return 文件行链表
         */
        fun readFile2List(filePath: String, charsetName: String): List<String> {
            return readFile2List(getFileByPath(filePath), charsetName)
        }

        /**
         * 指定编码按行读取文件到List

         * @param file        文件
         * *
         * @param charsetName 编码格式
         * *
         * @return 文件行链表
         */
        fun readFile2List(file: File, charsetName: String): List<String> {
            return readFile2List(file, 0, 0x7FFFFFFF, charsetName)
        }

        /**
         * 指定编码按行读取文件到List

         * @param filePath    文件路径
         * *
         * @param st          需要读取的开始行数
         * *
         * @param end         需要读取的结束行数
         * *
         * @param charsetName 编码格式
         * *
         * @return 包含制定行的list
         */
        fun readFile2List(filePath: String, st: Int, end: Int, charsetName: String): List<String> {
            return readFile2List(getFileByPath(filePath), st, end, charsetName)
        }

        /**
         * 指定编码按行读取文件到List

         * @param file        文件
         * *
         * @param st          需要读取的开始行数
         * *
         * @param end         需要读取的结束行数
         * *
         * @param charsetName 编码格式
         * *
         * @return 包含从start行到end行的list
         */
        fun readFile2List(file: File?, st: Int, end: Int, charsetName: String): List<String>? {
            if (file == null) return null
            if (st > end) return null
            var reader: BufferedReader? = null
            try {
                var line: String
                var curLine = 1
                val list = ArrayList<String>()
                if (StringUtils.isSpace(charsetName)) {
                    reader = BufferedReader(FileReader(file))
                } else {
                    reader = BufferedReader(InputStreamReader(FileInputStream(file), charsetName))
                }
                while ((line = reader.readLine()) != null) {
                    if (curLine > end) break
                    if (st <= curLine && curLine <= end) list.add(line)
                    ++curLine
                }
                return list
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                closeIO(reader)
            }
        }

        /**
         * 指定编码按行读取文件到字符串中

         * @param filePath    文件路径
         * *
         * @param charsetName 编码格式
         * *
         * @return 字符串
         */
        fun readFile2String(filePath: String, charsetName: String): String {
            return readFile2String(getFileByPath(filePath), charsetName)
        }

        /**
         * 指定编码按行读取文件到字符串中

         * @param file        文件
         * *
         * @param charsetName 编码格式
         * *
         * @return 字符串
         */
        fun readFile2String(file: File?, charsetName: String): String? {
            if (file == null) return null
            try {
                return ConvertUtils.inputStream2String(FileInputStream(file), charsetName)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * 指定编码按行读取文件到字符串中

         * @param filePath 文件路径
         * *
         * @return StringBuilder对象
         */
        fun readFile2Bytes(filePath: String): ByteArray {
            return readFile2Bytes(getFileByPath(filePath))
        }

        /**
         * 指定编码按行读取文件到字符串中

         * @param file 文件
         * *
         * @return StringBuilder对象
         */
        fun readFile2Bytes(file: File?): ByteArray? {
            if (file == null) return null
            try {
                return ConvertUtils.inputStream2Bytes(FileInputStream(file))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * byte单位转换（单位：unit）

         * @param size 大小
         * *
         * @param unit
         * *              * [ConstUtils.BYTE]: 字节
         * *              * [ConstUtils.KB]  : 千字节
         * *              * [ConstUtils.MB]  : 兆
         * *              * [ConstUtils.GB]  : GB
         * *
         * *
         * @return 大小以unit为单位
         */
        fun byte2Unit(size: Long, unit: Int): Double {
            when (unit) {
                ConstUtils.BYTE, KB, ConstUtils.MB, ConstUtils.GB -> return size.toDouble() / unit
            }
            return -1.0
        }

        /**
         * 获取文件大小
         *
         * 例如：getFileSize(filePath, ConstUtils.MB); 返回文件大小单位为MB

         * @param filePath 文件路径
         * *
         * @param unit
         * *                  * [ConstUtils.BYTE]: 字节
         * *                  * [ConstUtils.KB]  : 千字节
         * *                  * [ConstUtils.MB]  : 兆
         * *                  * [ConstUtils.GB]  : GB
         * *
         * *
         * @return 文件大小以unit为单位
         */
        fun getFileSize(filePath: String, unit: Int): Double {
            return getFileSize(getFileByPath(filePath), unit)
        }

        /**
         * 获取文件大小
         *
         * 例如：getFileSize(file, ConstUtils.MB); 返回文件大小单位为MB

         * @param file 文件
         * *
         * @param unit
         * *              * [ConstUtils.BYTE]: 字节
         * *              * [ConstUtils.KB]  : 千字节
         * *              * [ConstUtils.MB]  : 兆
         * *              * [ConstUtils.GB]  : GB
         * *
         * *
         * @return 文件大小以unit为单位
         */
        fun getFileSize(file: File, unit: Int): Double {
            if (!isFileExists(file)) return -1.0
            return byte2Unit(file.length(), unit)
        }

        /**
         * 关闭IO

         * @param closeable closeable
         */
        fun closeIO(closeable: Closeable?) {
            if (closeable == null) return
            try {
                closeable.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        /**
         * 获取全路径中的最长目录

         * @param file 文件
         * *
         * @return filePath最长目录
         */
        fun getDirName(file: File): String {
            if (!isFileExists(file)) return ""
            return getDirName(file.path)
        }

        /**
         * 获取全路径中的最长目录

         * @param filePath 文件路径
         * *
         * @return filePath最长目录
         */
        fun getDirName(filePath: String): String {
            if (StringUtils.isSpace(filePath)) return filePath
            val lastSep = filePath.lastIndexOf(File.separator)
            return if (lastSep == -1) "" else filePath.substring(0, lastSep + 1)
        }

        /**
         * 获取全路径中的文件名

         * @param file 文件
         * *
         * @return 文件名
         */
        fun getFileName(file: File): String {
            if (!isFileExists(file)) return ""
            return getFileName(file.path)
        }

        /**
         * 获取全路径中的文件名

         * @param filePath 文件路径
         * *
         * @return 文件名
         */
        fun getFileName(filePath: String): String {
            if (StringUtils.isSpace(filePath)) return filePath
            val lastSep = filePath.lastIndexOf(File.separator)
            return if (lastSep == -1) filePath else filePath.substring(lastSep + 1)
        }

        /**
         * 获取全路径中的不带拓展名的文件名

         * @param file 文件
         * *
         * @return 不带拓展名的文件名
         */
        fun getFileNameNoExtension(file: File): String {
            if (!isFileExists(file)) return ""
            return getFileNameNoExtension(file.path)
        }

        /**
         * 获取全路径中的不带拓展名的文件名

         * @param filePath 文件路径
         * *
         * @return 不带拓展名的文件名
         */
        fun getFileNameNoExtension(filePath: String): String {
            if (StringUtils.isSpace(filePath)) return filePath
            val lastPoi = filePath.lastIndexOf('.')
            val lastSep = filePath.lastIndexOf(File.separator)
            if (lastSep == -1) {
                return if (lastPoi == -1) filePath else filePath.substring(0, lastPoi)
            }
            if (lastPoi == -1 || lastSep > lastPoi) {
                return filePath.substring(lastSep + 1)
            }
            return filePath.substring(lastSep + 1, lastPoi)
        }


        /**
         * 获取全路径中的文件拓展名

         * @param file 文件
         * *
         * @return 文件拓展名
         */
        fun getFileExtension(file: File): String {
            if (!isFileExists(file)) return ""
            return getFileExtension(file.path)
        }

        /**
         * 获取全路径中的文件拓展名

         * @param filePath 文件路径
         * *
         * @return 文件拓展名
         */
        fun getFileExtension(filePath: String): String {
            if (StringUtils.isSpace(filePath)) return filePath
            val lastPoi = filePath.lastIndexOf('.')
            val lastSep = filePath.lastIndexOf(File.separator)
            if (lastPoi == -1 || lastSep >= lastPoi) return ""
            return "." + filePath.substring(lastPoi + 1)
        }
    }
}