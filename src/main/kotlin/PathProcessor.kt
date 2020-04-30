package moe.uki.app.epub2markdown

import java.io.File

class PathProcessor() {
  /**
   * Check if the path exists, create directory when it not exists
   */
  fun checkDir(file: File): Boolean {
    if (!file.exists()) {
      file.mkdir()
      return true
    }
    if (file.isDirectory)
      return true
    else {
      // When path is a file, return false and print error
      println("Wrong output path, it is a file")
      return false
    }
  }
}
