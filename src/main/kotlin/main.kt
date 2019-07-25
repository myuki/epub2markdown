package moe.uki.app.epub2markdown

import java.io.FileInputStream
import kotlin.collections.List

import nl.siegmann.epublib.domain.Book
import nl.siegmann.epublib.domain.Resource
import nl.siegmann.epublib.epub.EpubReader
import java.io.File

fun main(args: Array<String>) {
	//从命令行读取输入文件
	val inputFilePath: String
	val outputDirPath: String
	val outputFileNameType: String

	//读取输入文件(默认: default.epub)、输出路径(默认: output)、文件名类型(默认：TitleNum)
	when (args.size) {
		0 -> {
			inputFilePath = "default.epub"
			outputDirPath = "output/"
			outputFileNameType = "TitleNum"
		}
		1 -> {
			inputFilePath = args[0]
			outputDirPath = "output/"
			outputFileNameType = "TitleNum"
		}
		2 -> {
			inputFilePath = args[0]
			outputDirPath = args[1]
			outputFileNameType = "TitleNum"
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

	//如果标题为空则使用文件名
	if (bookTitle == "") bookTitle = TextProcessor(inputFilePath).noneExtension

	//检查输出文件夹
	PathProcessor(File(outputDirPath)).checkDir()

	for (chapter in contents) {
		val markdownOutputer = MarkdownOutputer(chapter, outputDirPath + bookTitle + "/", outputFileNameType)

		//判断输出文件是否存在
		if (!markdownOutputer.outputFile.exists()) {
			markdownOutputer.outputText("# ")
			markdownOutputer.outputText(markdownOutputer.titleWithoutNum)
			markdownOutputer.outputText("\n\n" + "翻译: []()\n\n" + "原帖: <>\n\n" + "---\n\n")
			markdownOutputer.outputContent()
		} else println("File: \"" + markdownOutputer.outputFile + "\" already exist")
	}
}
