package moe.uki.app.epub2markdown

import nl.siegmann.epublib.domain.Book
import nl.siegmann.epublib.domain.Resource
import nl.siegmann.epublib.epub.EpubReader
import java.io.File
import java.io.FileInputStream

class EpubConverter(inputFilePath: String) {
	val book: Book = EpubReader().readEpub(FileInputStream(inputFilePath))
	var bookTitle: String = book.metadata.firstTitle
	val contents: List<Resource> = book.tableOfContents.allUniqueResources

	init {
		// If the title in EPUB's metadata is empty, use filename as title
		if (bookTitle == "") bookTitle = TextProcessor(inputFilePath).removeExtension()
	}

	/**
	 * Filename type:"titleNum", "titleWithoutNum", "title"
	 */
	fun outputEachChapterTextOnly(
		outputDirPath: String = "output",
		outputFileNameType: String = "titleNum"
	) {
		val startTime: Long = System.currentTimeMillis() // For running timer

		// Check output directory
		if (PathProcessor(File(outputDirPath)).checkDir()) {
			// Traversing all resources of contents
			for (chapter in contents) {
				// Use book title as child directory
				val markdownOutputer = MarkdownOutputer(chapter, ("$outputDirPath/$bookTitle"), outputFileNameType)

				// Check if the output file exists
				if (!markdownOutputer.outputFile.exists()) {
					markdownOutputer.outputText("# ${markdownOutputer.titleWithoutNum}\n\n")
					if (customText1 != "") markdownOutputer.outputText(customText1)
					markdownOutputer.outputContent()
					if (customText2 != "") markdownOutputer.outputText(customText2)
				} else println("File: \"${markdownOutputer.outputFile}\" already exist")
			}
		}
		val endTime: Long = System.currentTimeMillis() - startTime // Count runing time
		println("Finished the conversion in ${endTime.toFloat() / 1000}s")
		println("Output Markdown file in \"$outputDirPath/$bookTitle\"")
	}
}
