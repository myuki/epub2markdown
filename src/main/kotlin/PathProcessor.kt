package moe.uki.app.epub2markdown

import java.io.File

class PathProcessor(val path: File) {
	/**
	 * Check if the path exists, create directory when it not exists
	 */
	fun checkDir(): Boolean {
		if (!path.exists()) {
			path.mkdir()
			return true
		}
		if (path.isDirectory)
			return true
		else {
			// When path is a file, return false and print error
			println("Wrong output path, it is a file")
			return false
		}
	}
}
