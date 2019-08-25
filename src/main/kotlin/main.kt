package moe.uki.app.epub2markdown

import java.io.File
import java.io.FileInputStream
import kotlin.collections.List

import nl.siegmann.epublib.domain.Book
import nl.siegmann.epublib.domain.Resource
import nl.siegmann.epublib.epub.EpubReader

fun main(args: Array<String>) {
	val inputFilePath: String
	val outputDirPath: String
	val outputFileNameType: String

	//Get path and title type in command line
	//Default: inputFilePath = "default.epub", outputDirPath = "output", outputFileNameType = "TitleNum"
	//Filename type:"titleNum", "titleWithoutNum", "title"
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

	//If the title in epub's metadata is empty, use filename as title
	if (bookTitle == "") bookTitle = TextProcessor(inputFilePath).removeExtension()

	//Check output directory
	if (PathProcessor(File(outputDirPath)).checkDir()) {
		for (chapter in contents) {
			val markdownOutputer = MarkdownOutputer(chapter, "${outputDirPath}/${bookTitle}", outputFileNameType)

			//Check if the output file exists
			if (!markdownOutputer.outputFile.exists()) {
				markdownOutputer.outputText("# ${markdownOutputer.titleWithoutNum}")
				markdownOutputer.outputText("\n\n翻译: []()\n\n原帖: <>\n\n---\n\n")
				markdownOutputer.outputContent()
			} else println("File: \"${markdownOutputer.outputFile}\" already exist")
		}
		println("Finished the conversion, output Markdown file in \"${outputDirPath}/${bookTitle}\"")
	}
}
