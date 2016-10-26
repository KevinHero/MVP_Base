package com.apanda.base.Utils.security

object Base64 {
    private val encodingTable = byteArrayOf('A'.toByte(), 'B'.toByte(), 'C'.toByte(), 'D'.toByte(), 'E'.toByte(), 'F'.toByte(), 'G'.toByte(), 'H'.toByte(), 'I'.toByte(), 'J'.toByte(), 'K'.toByte(), 'L'.toByte(), 'M'.toByte(), 'N'.toByte(), 'O'.toByte(), 'P'.toByte(), 'Q'.toByte(), 'R'.toByte(), 'S'.toByte(), 'T'.toByte(), 'U'.toByte(), 'V'.toByte(), 'W'.toByte(), 'X'.toByte(), 'Y'.toByte(), 'Z'.toByte(), 'a'.toByte(), 'b'.toByte(), 'c'.toByte(), 'd'.toByte(), 'e'.toByte(), 'f'.toByte(), 'g'.toByte(), 'h'.toByte(), 'i'.toByte(), 'j'.toByte(), 'k'.toByte(), 'l'.toByte(), 'm'.toByte(), 'n'.toByte(), 'o'.toByte(), 'p'.toByte(), 'q'.toByte(), 'r'.toByte(), 's'.toByte(), 't'.toByte(), 'u'.toByte(), 'v'.toByte(), 'w'.toByte(), 'x'.toByte(), 'y'.toByte(), 'z'.toByte(), '0'.toByte(), '1'.toByte(), '2'.toByte(), '3'.toByte(), '4'.toByte(), '5'.toByte(), '6'.toByte(), '7'.toByte(), '8'.toByte(), '9'.toByte(), '+'.toByte(), '/'.toByte())
    private val decodingTable: ByteArray

    init {
        decodingTable = ByteArray(128)

        for (i in 0..127) {
            decodingTable[i] = (-1).toByte()
        }

        run {
            var i: Int = 'A'
            while (i <= 'Z') {
                decodingTable[i] = (i - 'A').toByte()
                i++
            }
        }

        run {
            var i: Int = 'a'
            while (i <= 'z') {
                decodingTable[i] = (i - 'a' + 26).toByte()
                i++
            }
        }

        var i: Int = '0'
        while (i <= '9') {
            decodingTable[i] = (i - '0' + 52).toByte()
            i++
        }

        decodingTable['+'] = 62
        decodingTable['/'] = 63
    }

    fun encode(data: ByteArray): String {
        val bytes: ByteArray

        val modulus = data.size % 3

        if (modulus == 0) {
            bytes = ByteArray(4 * data.size / 3)
        } else {
            bytes = ByteArray(4 * (data.size / 3 + 1))
        }

        val dataLength = data.size - modulus
        var a1: Int
        var a2: Int
        var a3: Int

        var i = 0
        var j = 0
        while (i < dataLength) {
            a1 = data[i] and 0xff
            a2 = data[i + 1] and 0xff
            a3 = data[i + 2] and 0xff

            bytes[j] = encodingTable[a1.ushr(2) and 0x3f]
            bytes[j + 1] = encodingTable[a1 shl 4 or a2.ushr(4) and 0x3f]
            bytes[j + 2] = encodingTable[a2 shl 2 or a3.ushr(6) and 0x3f]
            bytes[j + 3] = encodingTable[a3 and 0x3f]
            i += 3
            j += 4
        }

        val b1: Int
        val b2: Int
        val b3: Int
        val d1: Int
        val d2: Int

        when (modulus) {
            0 /* nothing left to do */ -> {
            }

            1 -> {
                d1 = data[data.size - 1] and 0xff
                b1 = d1.ushr(2) and 0x3f
                b2 = d1 shl 4 and 0x3f

                bytes[bytes.size - 4] = encodingTable[b1]
                bytes[bytes.size - 3] = encodingTable[b2]
                bytes[bytes.size - 2] = '='.toByte()
                bytes[bytes.size - 1] = '='.toByte()
            }

            2 -> {
                d1 = data[data.size - 2] and 0xff
                d2 = data[data.size - 1] and 0xff

                b1 = d1.ushr(2) and 0x3f
                b2 = d1 shl 4 or d2.ushr(4) and 0x3f
                b3 = d2 shl 2 and 0x3f

                bytes[bytes.size - 4] = encodingTable[b1]
                bytes[bytes.size - 3] = encodingTable[b2]
                bytes[bytes.size - 2] = encodingTable[b3]
                bytes[bytes.size - 1] = '='.toByte()
            }
        }

        return String(bytes)
    }

    fun decode(data: ByteArray): String {
        var data = data
        val bytes: ByteArray
        var b1: Byte
        var b2: Byte
        var b3: Byte
        var b4: Byte

        data = discardNonBase64Bytes(data)

        if (data[data.size - 2] == '=') {
            bytes = ByteArray((data.size / 4 - 1) * 3 + 1)
        } else if (data[data.size - 1] == '=') {
            bytes = ByteArray((data.size / 4 - 1) * 3 + 2)
        } else {
            bytes = ByteArray(data.size / 4 * 3)
        }

        var i = 0
        var j = 0
        while (i < data.size - 4) {
            b1 = decodingTable[data[i]]
            b2 = decodingTable[data[i + 1]]
            b3 = decodingTable[data[i + 2]]
            b4 = decodingTable[data[i + 3]]

            bytes[j] = (b1 shl 2 or (b2 shr 4)).toByte()
            bytes[j + 1] = (b2 shl 4 or (b3 shr 2)).toByte()
            bytes[j + 2] = (b3 shl 6 or b4).toByte()
            i += 4
            j += 3
        }

        if (data[data.size - 2] == '=') {
            b1 = decodingTable[data[data.size - 4]]
            b2 = decodingTable[data[data.size - 3]]

            bytes[bytes.size - 1] = (b1 shl 2 or (b2 shr 4)).toByte()
        } else if (data[data.size - 1] == '=') {
            b1 = decodingTable[data[data.size - 4]]
            b2 = decodingTable[data[data.size - 3]]
            b3 = decodingTable[data[data.size - 2]]

            bytes[bytes.size - 2] = (b1 shl 2 or (b2 shr 4)).toByte()
            bytes[bytes.size - 1] = (b2 shl 4 or (b3 shr 2)).toByte()
        } else {
            b1 = decodingTable[data[data.size - 4]]
            b2 = decodingTable[data[data.size - 3]]
            b3 = decodingTable[data[data.size - 2]]
            b4 = decodingTable[data[data.size - 1]]

            bytes[bytes.size - 3] = (b1 shl 2 or (b2 shr 4)).toByte()
            bytes[bytes.size - 2] = (b2 shl 4 or (b3 shr 2)).toByte()
            bytes[bytes.size - 1] = (b3 shl 6 or b4).toByte()
        }

        return String(bytes)
    }

    fun decode(data: String): ByteArray {
        var data = data
        val bytes: ByteArray
        var b1: Byte
        var b2: Byte
        var b3: Byte
        var b4: Byte

        data = discardNonBase64Chars(data)

        if (data[data.length - 2] == '=') {
            bytes = ByteArray((data.length / 4 - 1) * 3 + 1)
        } else if (data[data.length - 1] == '=') {
            bytes = ByteArray((data.length / 4 - 1) * 3 + 2)
        } else {
            bytes = ByteArray(data.length / 4 * 3)
        }

        var i = 0
        var j = 0
        while (i < data.length - 4) {
            b1 = decodingTable[data[i]]
            b2 = decodingTable[data[i + 1]]
            b3 = decodingTable[data[i + 2]]
            b4 = decodingTable[data[i + 3]]

            bytes[j] = (b1 shl 2 or (b2 shr 4)).toByte()
            bytes[j + 1] = (b2 shl 4 or (b3 shr 2)).toByte()
            bytes[j + 2] = (b3 shl 6 or b4).toByte()
            i += 4
            j += 3
        }

        if (data[data.length - 2] == '=') {
            b1 = decodingTable[data[data.length - 4]]
            b2 = decodingTable[data[data.length - 3]]

            bytes[bytes.size - 1] = (b1 shl 2 or (b2 shr 4)).toByte()
        } else if (data[data.length - 1] == '=') {
            b1 = decodingTable[data[data.length - 4]]
            b2 = decodingTable[data[data.length - 3]]
            b3 = decodingTable[data[data.length - 2]]

            bytes[bytes.size - 2] = (b1 shl 2 or (b2 shr 4)).toByte()
            bytes[bytes.size - 1] = (b2 shl 4 or (b3 shr 2)).toByte()
        } else {
            b1 = decodingTable[data[data.length - 4]]
            b2 = decodingTable[data[data.length - 3]]
            b3 = decodingTable[data[data.length - 2]]
            b4 = decodingTable[data[data.length - 1]]

            bytes[bytes.size - 3] = (b1 shl 2 or (b2 shr 4)).toByte()
            bytes[bytes.size - 2] = (b2 shl 4 or (b3 shr 2)).toByte()
            bytes[bytes.size - 1] = (b3 shl 6 or b4).toByte()
        }

        return bytes
    }

    private fun discardNonBase64Bytes(data: ByteArray): ByteArray {
        val temp = ByteArray(data.size)
        var bytesCopied = 0

        for (i in data.indices) {
            if (isValidBase64Byte(data[i])) {
                temp[bytesCopied++] = data[i]
            }
        }

        val newData = ByteArray(bytesCopied)

        System.arraycopy(temp, 0, newData, 0, bytesCopied)

        return newData
    }

    private fun discardNonBase64Chars(data: String): String {
        val sb = StringBuffer()

        val length = data.length

        for (i in 0..length - 1) {
            if (isValidBase64Byte(data[i].toByte())) {
                sb.append(data[i])
            }
        }

        return sb.toString()
    }

    private fun isValidBase64Byte(b: Byte): Boolean {
        if (b == '=') {
            return true
        } else if (b < 0 || b >= 128) {
            return false
        } else if (decodingTable[b].toInt() == -1) {
            return false
        }

        return true
    }

}
