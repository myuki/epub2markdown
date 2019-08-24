package moe.uki.app.epub2markdown

import java.io.File

class PathProcessor(val path: File) {
	//Check if the directory exists, create directory when it not exists
	fun checkDir(): Boolean {
		if (!path.exists()) {
			path.mkdir()
			return true
		}
		if (path.isDirectory)
			return true
		else {
			println("Wrong Output path, it is a file")
			return false
		}
	}
}