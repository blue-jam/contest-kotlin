import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.PrintWriter
import java.util.Arrays
import java.util.InputMismatchException

{% if mod %}
val MOD = {{ mod }};
{% endif %}
{% if yes_str %}
val YES = "{{ yes_str }}";
{% endif %}
{% if no_str %}
val NO = "{{ no_str }}";
{% endif %}

fun main() {
    Thread(null, { run(System.`in`, PrintWriter(System.`out`)) }, "", 16 * 1024 * 1024).start()
}

{% if prediction_success %}
fun solve({{ formal_arguments }}, out: PrintWriter){

}
{% endif %}

fun run(inputStream: InputStream, out: PrintWriter) {
    val scanner = FastScanner(inputStream)
    {% if prediction_success %}
    {{input_part}}
    solve({{ actual_arguments }}, out);
    {% else %}
    // Failed to predict input format
    {% endif %}
    out.flush()
}

/*
from https://github.com/wata-orz/vertex_cover

The MIT License (MIT)

Copyright (c) 2015 Yoichi Iwata

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
class FastScanner : Closeable {
    val `in`: InputStream?
    var buf: ByteArray
    var p = 0
    var m = 0

    /**
     * InputStream??????????????????
     */
    constructor(`in`: InputStream?) {
        this.`in` = `in`
        buf = ByteArray(1 shl 15)
    }

    /**
     * ???????????????????????????
     */
    constructor(str: String) {
        `in` = null
        buf = str.toByteArray()
        m = buf.size
    }

    /**
     * ????????????????????????'\r'???'\n'?????????????????????????????????
     */
    fun setSpace(vararg cs: Char) {
        Arrays.fill(isSpace, false)
        isSpace['\n'.toInt()] = true
        isSpace['\r'.toInt()] = isSpace['\n'.toInt()]
        for (c in cs) isSpace[c.toInt()] = true
    }

    fun read(): Int {
        if (m == -1) return -1
        if (p >= m) {
            p = 0
            if (`in` == null) return -1.also { m = it }
            m = try {
                `in`.read(buf)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            if (m <= 0) return -1
        }
        return buf[p++].toInt()
    }

    /**
     * ??????1?????????????????????????????????????????????????????????????????????????????????
     */
    fun peek(): Int {
        val c = read()
        if (c >= 0) p--
        return c
    }

    /**
     * ???????????????????????????????????????
     */
    operator fun hasNext(): Boolean {
        var c = read()
        while (c >= 0 && isSpace[c]) c = read()
        if (c == -1) return false
        p--
        return true
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    fun hasNextInLine(): Boolean {
        if (p > 0 && buf[p - 1] == '\n'.toByte()) return false
        var c = read()
        while (c >= 0 && isSpace[c] && c != '\n'.toInt() && c != '\r'.toInt()) c = read()
        if (c == -1) return false
        p--
        return c != '\n'.toInt() && c != '\r'.toInt()
    }

    /**
     * ?????????????????????????????????
     */
    operator fun next(): String {
        if (!hasNext()) throw InputMismatchException()
        val sb = StringBuilder()
        var c = read()
        while (c >= 0 && !isSpace[c]) {
            sb.append(c.toChar())
            c = read()
        }
        return sb.toString()
    }

    /**
     * ??????????????????????????????
     */
    fun nextLine(): String {
        val sb = StringBuilder()
        if (p > 0 && buf[p - 1] == '\n'.toByte()) {
            buf[p - 1] = ' '.toByte()
            return ""
        }
        var c = read()
        if (c < 0) throw InputMismatchException()
        while (c >= 0 && c != '\n'.toInt() && c != '\r'.toInt()) {
            sb.append(c.toChar())
            c = read()
        }
        if (c == '\r'.toInt()) read()
        if (p > 0) buf[p - 1] = ' '.toByte()
        return sb.toString()
    }

    /**
     * ?????????????????????????????????
     */
    fun skipLine() {
        if (p > 0 && buf[p - 1] == '\n'.toByte()) {
            buf[p - 1] = ' '.toByte()
            return
        }
        var c = read()
        if (c < 0) return
        while (c >= 0 && c != '\n'.toInt() && c != '\r'.toInt()) {
            c = read()
        }
        if (c == '\r'.toInt()) read()
        if (p > 0) buf[p - 1] = ' '.toByte()
    }

    /**
     * ??????int?????????????????????
     */
    fun nextInt(): Int {
        if (!hasNext()) throw InputMismatchException()
        var c = read()
        var sgn = 1
        if (c == '-'.toInt()) {
            sgn = -1
            c = read()
        }
        var res = 0
        do {
            if (c < '0'.toInt() || c > '9'.toInt()) throw InputMismatchException()
            res *= 10
            res += c - '0'.toInt()
            c = read()
        } while (c >= 0 && !isSpace[c])
        return res * sgn
    }

    /**
     * ???????????????int?????????????????????
     * @param is ??????????????????????????????????????????????????????????????????????????????????????????
     * @return ??????????????????????????????????????????????????????-1????????????
     */
    fun nextInts(`is`: IntArray): Int {
        var len = 0
        while (len < `is`.size && hasNextInLine()) {
            `is`[len++] = nextInt()
        }
        if (hasNextInLine()) return -1
        skipLine()
        return len
    }

    /**
     * ??????long?????????????????????
     */
    fun nextLong(): Long {
        if (!hasNext()) throw InputMismatchException()
        var c = read()
        var sgn = 1
        if (c == '-'.toInt()) {
            sgn = -1
            c = read()
        }
        var res: Long = 0
        do {
            if (c < '0'.toInt() || c > '9'.toInt()) throw InputMismatchException()
            res *= 10
            res += (c - '0'.toInt()).toLong()
            c = read()
        } while (c >= 0 && !isSpace[c])
        return res * sgn
    }

    /**
     * ??????double?????????????????????
     */
    fun nextDouble(): Double {
        return next().toDouble()
    }

    override fun close() {
        try {
            if (`in` != null) `in`.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        var isSpace = BooleanArray(128)

        init {
            isSpace['\t'.toInt()] = true
            isSpace['\r'.toInt()] = isSpace['\t'.toInt()]
            isSpace['\n'.toInt()] = isSpace['\r'.toInt()]
            isSpace[' '.toInt()] = isSpace['\n'.toInt()]
        }
    }
}
