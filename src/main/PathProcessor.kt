package moe.uki.app.epub2markdown

import java.io.File

class PathProcessor(val path: File) {
	//若目录不存在则创建
	fun checkDir() {
		if (!path.exists()) path.mkdir()
	}
}