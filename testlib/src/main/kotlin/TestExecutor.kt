import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.PrintWriter
import java.nio.file.Paths
import java.util.regex.Pattern

abstract class TestExecutor(
    protected val run: (InputStream, PrintWriter) -> Unit
) {
    companion object {
        const val DIRECTORY = "/sample"
        const val INPUT_NAME_PREFIX = "in"
        const val INPUT_NAME_PATTERN = "in_%s.txt"
        const val OUTPUT_NAME_PATTERN = "out_%s.txt"
        val NUMBER_PATTERN = Pattern.compile("\\d+")!!
    }

    @TestFactory
    fun execute(): List<DynamicTest> {
        val resourceDirectory = File(run.javaClass.getResource(DIRECTORY)!!.toURI())
        val numbers = resourceDirectory.listFiles { file -> file.name.startsWith(INPUT_NAME_PREFIX) }!!
            .map {
                val matcher = NUMBER_PATTERN.matcher(it.name)
                matcher.find()
                matcher.group()
            }

        return numbers.map { id ->
            val name = INPUT_NAME_PATTERN.format(id)
            val input = getStreamOfFile(name)
            val expected = String(getStreamOfFile(OUTPUT_NAME_PATTERN.format(id)).readAllBytes())

            DynamicTest.dynamicTest("sample $id") {
                val output = ByteArrayOutputStream()
                run(input, PrintWriter(output))

                Assertions.assertEquals(expected, output.toString("UTF-8"))
            }
        }
    }

    private fun getStreamOfFile(name: String) =
        run.javaClass.getResourceAsStream(Paths.get(DIRECTORY, name).toString())!!
}
