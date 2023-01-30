package city.smartb.iris.crypto.rsa.utils

import com.google.common.io.Resources
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.net.MalformedURLException
import java.net.URL

object FileUtils {

    const val FILE = "file:"
    @Throws(MalformedURLException::class)
    fun getFile(filename: String): File {
        val url = getUrl(filename)
        return File(url.file)
    }

    @Throws(MalformedURLException::class)
    fun getUrl(filename: String): URL {
        return if (filename.startsWith(FILE)) {
            URL(filename)
        } else Resources.getResource(filename)
    }

    @Throws(IOException::class)
    fun getReader(filename: String): Reader {
        val url = getUrl(filename)
        return InputStreamReader(url.openStream())
    }

    fun saveFile(filename: String, content: String, forceDir: Boolean = false) {
        if (forceDir) {
            File(filename.split("/").dropLast(1).joinToString("/")).mkdir()
        }

        File(filename).writeText(content)
    }
}
