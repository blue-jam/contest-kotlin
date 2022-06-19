import java.io.InputStream
import java.io.PrintWriter
import java.util.Scanner

fun main() {
    run(System.`in`!!, PrintWriter(System.out))
}

// A + B problem
fun run(inputStream: InputStream, writer: PrintWriter) {
    val scanner = Scanner(inputStream)
    writer.println(scanner.nextLong() + scanner.nextLong())
    writer.flush()
}
