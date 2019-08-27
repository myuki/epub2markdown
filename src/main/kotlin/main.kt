package moe.uki.app.epub2markdown

import java.io.File
import java.io.FileInputStream
import kotlin.collections.List

import nl.siegmann.epublib.domain.Book
import nl.siegmann.epublib.domain.Resource
import nl.siegmann.epublib.epub.EpubReader

const val customText: String = "翻译: []()\n\n原帖: <>\n\n---\n\n"

/**
 * Get input file, output directory and title type in command line
 * Default: inputFilePath = "default.epub", outputDirPath = "output", outputFileNameType = "TitleNum"
 * Filename type:"titleNum", "titleWithoutNum", "title"
 */
fun main(args: Array<String>) {
	val startTime: Long = System.currentTimeMillis() // For running timer
	val inputFilePath: String
	val outputDirPath: String
	val outputFileNameType: String

	when (args.size) {
		0 -> {
			inputFilePath = "default.epub"
			outputDirPath = "output"
			outputFileNameType = "titleNum"
		}
		1 -> {
			inputFilePath = args[0]
			outputDirPath = "output"
			outputFileNameType = "titleNum"
		}
		2 -> {
			inputFilePath = args[0]
			outputDirPath = args[1]
			outputFileNameType = "titleNum"
		}
		else -> {
			inputFilePath = args[0]
			outputDirPath = args[1]
			outputFileNameType = args[2]
		}
	}

	val book: Book = EpubReader().readEpub(FileInputStream(inputFilePath))
	val contents: List<Resource> = book.tableOfContents.allUniqueResources
	var bookTitle: String = book.metadata.firstTitle

	// If the title in epub's metadata is empty, use filename as title
	if (bookTitle == "") bookTitle = TextProcessor(inputFilePath).removeExtension()

	// Check output directory
	if (PathProcessor(File(outputDirPath)).checkDir()) {
		// Traversing all resources of contents
		for (chapter in contents) {
			// Use book title as child directory
			val markdownOutputer = MarkdownOutputer(chapter, "${outputDirPath}/${bookTitle}", outputFileNameType)

			// Check if the output file exists
			if (!markdownOutputer.outputFile.exists()) {
				markdownOutputer.outputText("# ${markdownOutputer.titleWithoutNum}\n\n")
				markdownOutputer.outputText(customText)
				markdownOutputer.outputContent()
			} else println("File: \"${markdownOutputer.outputFile}\" already exist")
		}

		val endTime: Long = System.currentTimeMillis() - startTime // Count runing time
		println("Finished the conversion in ${endTime.toFloat() / 1000}s\nOutput Markdown file in \"${outputDirPath}/${bookTitle}\"")
	}
}
