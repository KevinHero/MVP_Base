package com.apanda.base.Utils

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/7
 * desc  : Shell相关工具类
 *
 */
class ShellUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    /**
     * 返回的命令结果
     */
    class CommandResult {

        /**
         * 结果码
         */
        var result: Int = 0
        /**
         * 成功的信息
         */
        var successMsg: String
        /**
         * 错误信息
         */
        var errorMsg: String

        constructor(result: Int) {
            this.result = result
        }

        constructor(result: Int, successMsg: String, errorMsg: String) {
            this.result = result
            this.successMsg = successMsg
            this.errorMsg = errorMsg
        }
    }

    companion object {

        val COMMAND_SU = "su"
        val COMMAND_SH = "sh"
        val COMMAND_EXIT = "exit\n"
        val COMMAND_LINE_END = "\n"

        /**
         * 判断设备是否root
         * @return `true`: root`false`: 没root
         */
        val isRoot: Boolean
            get() = execCmd("echo root", true, false).result == 0

        /**
         * 是否是在root下执行命令

         * @param command 命令
         * *
         * @param isRoot  是否root
         * *
         * @return CommandResult
         */
        fun execCmd(command: String, isRoot: Boolean): CommandResult {
            return execCmd(arrayOf(command), isRoot, true)
        }

        /**
         * 是否是在root下执行命令

         * @param commands 多条命令链表
         * *
         * @param isRoot   是否root
         * *
         * @return CommandResult
         */
        fun execCmd(commands: List<String>?, isRoot: Boolean): CommandResult {
            return execCmd(commands?.toTypedArray(), isRoot, true)
        }

        /**
         * 是否是在root下执行命令

         * @param command         命令
         * *
         * @param isRoot          是否root
         * *
         * @param isNeedResultMsg 是否需要结果消息
         * *
         * @return CommandResult
         */
        fun execCmd(command: String, isRoot: Boolean, isNeedResultMsg: Boolean): CommandResult {
            return execCmd(arrayOf(command), isRoot, isNeedResultMsg)
        }

        /**
         * 是否是在root下执行命令

         * @param commands        命令链表
         * *
         * @param isRoot          是否root
         * *
         * @param isNeedResultMsg 是否需要结果消息
         * *
         * @return CommandResult
         */
        fun execCmd(commands: List<String>?, isRoot: Boolean, isNeedResultMsg: Boolean): CommandResult {
            return execCmd(commands?.toTypedArray(), isRoot, isNeedResultMsg)
        }

        /**
         * 是否是在root下执行命令

         * @param commands        命令数组
         * *
         * @param isRoot          是否root
         * *
         * @param isNeedResultMsg 是否需要结果消息
         * *
         * @return CommandResult
         */
        @JvmOverloads fun execCmd(commands: Array<String>?, isRoot: Boolean, isNeedResultMsg: Boolean = true): CommandResult {
            var result = -1
            if (commands == null || commands.size == 0) {
                return CommandResult(result, null, null)
            }
            var process: Process? = null
            var successResult: BufferedReader? = null
            var errorResult: BufferedReader? = null
            var successMsg: StringBuilder? = null
            var errorMsg: StringBuilder? = null
            var os: DataOutputStream? = null
            try {
                process = Runtime.getRuntime().exec(if (isRoot) COMMAND_SU else COMMAND_SH)
                os = DataOutputStream(process!!.outputStream)
                for (command in commands) {
                    if (command == null) {
                        continue
                    }
                    os.write(command.toByteArray())
                    os.writeBytes(COMMAND_LINE_END)
                    os.flush()
                }
                os.writeBytes(COMMAND_EXIT)
                os.flush()

                result = process.waitFor()
                if (isNeedResultMsg) {
                    successMsg = StringBuilder()
                    errorMsg = StringBuilder()
                    successResult = BufferedReader(InputStreamReader(process.inputStream))
                    errorResult = BufferedReader(InputStreamReader(process.errorStream))
                    var s: String
                    while ((s = successResult.readLine()) != null) {
                        successMsg.append(s)
                    }
                    while ((s = errorResult.readLine()) != null) {
                        errorMsg.append(s)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if (os != null) {
                        os.close()
                    }
                    if (successResult != null) {
                        successResult.close()
                    }
                    if (errorResult != null) {
                        errorResult.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                if (process != null) {
                    process.destroy()
                }
            }
            return CommandResult(result, if (successMsg == null) null else successMsg.toString(), if (errorMsg == null)
                null
            else
                errorMsg.toString())
        }
    }
}
/**
 * 是否是在root下执行命令

 * @param commands 多条命令数组
 * *
 * @param isRoot   是否root
 * *
 * @return CommandResult
 */