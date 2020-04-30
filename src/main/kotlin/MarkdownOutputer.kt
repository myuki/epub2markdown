package moe.uki.app.epub2markdown

import java.io.File

import nl.siegmann.epublib.domain.Resource

class MarkdownOutputer(val outputDirPath: String, val fileNameNoneExtension: String) {
  val outputDirFile = File(outputDirPath)
  val outputFile: File

  init {
    pathProcessor.checkDir(outputDirFile) // Create directory when it not exists
    
    // Check if the output file exists
    var fileName = "$fileNameNoneExtension"
    var i = 0
    while (File("$outputDirPath/$fileName.md").exists()) {
      println("File: \"$outputDirPath/$fileName.md\" already exist")
      fileName = "${fileName}_$i"
      i++
    }
    outputFile = File("$outputDirPath/${fileName}.md") // Create the output file
  }

  fun outputText(text: String) {
    outputFile.appendText(text)
  }
}
