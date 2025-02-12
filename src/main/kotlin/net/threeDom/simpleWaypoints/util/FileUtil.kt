package net.threeDom.simpleWaypoints.util

import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.FileReader
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

object FileUtil {

	fun readMapFromFile(fileName: String, path: String = ""): MutableMap<String, String> {
		val filePath: String = getPath(fileName, path) + ".txt"
		val reader: FileReader
		try {
			reader = FileReader(filePath)
		} catch (e: FileNotFoundException) {
			return mutableMapOf()
		}

		val map: MutableMap<String, String> = mutableMapOf()

		for (line in reader.readLines()) {
			val kv: List<String> = line.split(":")

			if (kv.size != 2) {
				continue
			}

			map[kv[0]] = kv[1]
		}

		return map
	}

	fun writeFileFromMap(map: MutableMap<String, String>, fileName: String, path: String = "") {
		val filePath: String = getPath(fileName, path) + ".txt"
		val p: Path = Path.of(path)

		if (!p.exists()) {
			p.createDirectory()
		}

		val writer: FileWriter = FileWriter(filePath)

		map.keys.forEach { key ->
			val value = map[key]
			writer.write("$key: $value\r\n")
		}

		writer.close()
	}

	private fun getPath(fileName: String, path: String): String {
		return (if (path == "") fileName else "$path/$fileName")
	}
}