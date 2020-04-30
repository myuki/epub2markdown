package moe.uki.app.epub2markdown

import java.io.File

class Converter(inputFilePath: String) {
	val epubParser = EpubParser(inputFilePath)
	val outputDirPath = "output"

	/**
	 * Filename type:"titleNum", "titleWithoutNum", "title"
	 */
	fun outputEachChapterTextOnly(outputFileNameType: String = "titleNum") {
		val startTime: Long = System.currentTimeMillis() // For running timer
		var outputFileName: String

		// Traversing all resources of contents
		for (chapter in epubParser.contents) {
			// Check output directory
			if (pathProcessor.checkDir(File(outputDirPath))) {
				val htmlParser = HtmlParser(chapter.inputStream)
				// If HTML's title is empty, use id as filename
				if (htmlParser.title != "") {
					// Choose filename type
					when (outputFileNameType) {
						"title" -> outputFileName = htmlParser.title
						"titleNum" -> outputFileName = textProcessor.removeMatcher(htmlParser.titleNum, "^0*") // Remove 0 in head
						"titleWithoutNum" -> outputFileName = htmlParser.titleWithoutNum
						else -> outputFileName = htmlParser.title
					}
					if (outputFileName == "") outputFileName = htmlParser.title
				} else outputFileName = chapter.id

				// Use book title as child directory
				val markdownOutputer = MarkdownOutputer("$outputDirPath/${epubParser.bookTitle}", outputFileName)
				markdownOutputer.outputText("# ${htmlParser.titleWithoutNum}\n\n")
				if (customText1 != "") markdownOutputer.outputText(customText1)
				markdownOutputer.outputText(htmlParser.contentTextOnly)
				if (customText2 != "") markdownOutputer.outputText(customText2)
			}
		}
		val endTime: Long = System.currentTimeMillis() - startTime // Count runing time
		println("Finished the conversion in ${endTime.toFloat() / 1000}s")
		println("Output Markdown file in \"$outputDirPath\"")
	}
}
